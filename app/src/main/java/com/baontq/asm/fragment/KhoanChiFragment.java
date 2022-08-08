package com.baontq.asm.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baontq.asm.DAO.KhoanChiDAO;
import com.baontq.asm.DAO.LoaiChiDAO;
import com.baontq.asm.DTO.KhoanChi;
import com.baontq.asm.DTO.LoaiChi;
import com.baontq.asm.R;
import com.baontq.asm.adapter.KhoanChiAdapter;
import com.baontq.asm.dialog.MDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KhoanChiFragment extends Fragment {
    private RecyclerView rvKhoanChi;
    private FloatingActionButton fabThemkhoanchi;
    private KhoanChiDAO dao;
    private KhoanChiAdapter kcAdapter;
    private LoaiChiDAO lcDao;
    private List<LoaiChi> loaiChiList;
    private Calendar myCalendar = Calendar.getInstance();
    private TextView tvEmpty;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvKhoanChi = view.findViewById(R.id.rv_khoanchi);
        tvEmpty = view.findViewById(R.id.tv_empty);
        fabThemkhoanchi = view.findViewById(R.id.fab_themkhoanchi);
        fabThemkhoanchi.setOnClickListener(view1 -> {
            loaiChiList = lcDao.getAll();
            if (loaiChiList.size() == 0) {
                errorDialog("Bạn chưa có loại chi nào. Vui lòng bổ sung trước khi thêm khoản chi");
            } else {
                addKhoanChiDialog();
            }
        });
        dao = new KhoanChiDAO(getContext());
        lcDao = new LoaiChiDAO(getContext());
        kcAdapter = new KhoanChiAdapter(dao.getAll(), getContext(), () -> checkListEmpty());
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        rvKhoanChi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvKhoanChi.addItemDecoration(decoration);
        rvKhoanChi.setAdapter(kcAdapter);
    }

    private void errorDialog(String message) {
        new MDialog.Builder(getContext())
                .setTitle("⚠️ Thông báo")
                .setMessage(message)
                .setPositiveButton("OK", null).create().show();
    }

    private void addKhoanChiDialog() {
        KhoanChi kc = new KhoanChi();
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_add_khoanchi_dialog);
        TextInputLayout tilKhoanchiName, tilKhoanchiCost;
        TextInputEditText edtDialogAddKhoanchi, edtDialogAddKhoanchiCost;
        Spinner snLoaichi;
        TextView tvDateChoose;
        MaterialButton btnDatePicker, btnDialogAddKhoanchiCancel, btnDialogAddKhoanchiSubmit;
        ArrayAdapter<LoaiChi> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, loaiChiList);
        tilKhoanchiCost = dialog.findViewById(R.id.til_khoanchi_cost);
        edtDialogAddKhoanchiCost = dialog.findViewById(R.id.edt_dialog_add_khoanchi_cost);
        tilKhoanchiName = dialog.findViewById(R.id.til_khoanchi_name);
        edtDialogAddKhoanchi = dialog.findViewById(R.id.edt_dialog_add_khoanchi);
        snLoaichi = dialog.findViewById(R.id.sn_loaichi);
        tvDateChoose = dialog.findViewById(R.id.tv_date_choose);
        btnDatePicker = dialog.findViewById(R.id.btn_date_picker);
        btnDialogAddKhoanchiCancel = dialog.findViewById(R.id.btn_dialog_add_khoanchi_cancel);
        btnDialogAddKhoanchiSubmit = dialog.findViewById(R.id.btn_dialog_add_khoanchi_submit);
        snLoaichi.setAdapter(adapter);
        snLoaichi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kc.setIdLc(loaiChiList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnDatePicker.setOnClickListener(view -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        tvDateChoose.setText(dayOfMonth + "/" + String.valueOf(monthOfYear + 1) + "/" + year);
                        Date selectedDate = myCalendar.getTime();
                        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        kc.setTime(dateFormatter.format(selectedDate));
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnDialogAddKhoanchiCancel.setOnClickListener(view -> dialog.dismiss());
        btnDialogAddKhoanchiSubmit.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtDialogAddKhoanchi.getText().toString().trim())) {
                tilKhoanchiName.setError("Chưa nhập tên khoản chi");
                return;
            } else {
                tilKhoanchiName.setErrorEnabled(false);
                kc.setName(edtDialogAddKhoanchi.getText().toString().trim());
            }
            if (TextUtils.isEmpty(edtDialogAddKhoanchiCost.getText().toString().trim())) {
                tilKhoanchiCost.setError("Chưa nhập khoản chi");
                return;
            } else {
                tilKhoanchiCost.setErrorEnabled(false);
                kc.setCost(Double.parseDouble(edtDialogAddKhoanchiCost.getText().toString().trim()));
            }
            if (kc.getTime() == null) {
                errorDialog("Chưa chọn ngày chi");
                return;
            }
            if (dao.insert(kc) > 0) {
                kcAdapter.addItem();
                Toast.makeText(getContext(), "Đã thêm khoản chi!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                checkListEmpty();
            }
        });
        dialog.show();
    }

    private void checkListEmpty() {
        if (kcAdapter.getItemCount() > 0) {
            tvEmpty.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        kcAdapter.setList(dao.getAll());
        checkListEmpty();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_khoanchi, container, false);
    }
}