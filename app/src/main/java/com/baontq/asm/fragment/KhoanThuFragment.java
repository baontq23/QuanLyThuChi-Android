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

import com.baontq.asm.DAO.KhoanThuDAO;
import com.baontq.asm.DAO.LoaiThuDAO;
import com.baontq.asm.DTO.KhoanThu;
import com.baontq.asm.DTO.LoaiThu;
import com.baontq.asm.R;
import com.baontq.asm.adapter.KhoanThuAdapter;
import com.baontq.asm.dialog.MDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KhoanThuFragment extends Fragment {
    private FloatingActionButton fabThemKhoanThu;
    private TextView tvEmpty;
    private RecyclerView rvKhoanThu;
    private KhoanThuAdapter ktAdapter;
    private KhoanThuDAO ktDao;
    private LoaiThuDAO ltDao;
    private List<LoaiThu> ltList;
    private final Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvEmpty = view.findViewById(R.id.tv_empty);
        rvKhoanThu = view.findViewById(R.id.rv_khoanthu);
        ktDao = new KhoanThuDAO(getContext());
        ltDao = new LoaiThuDAO(getContext());
        ktAdapter = new KhoanThuAdapter(getContext(), ktDao.getAll(), () -> checkListEmpty());
        fabThemKhoanThu = view.findViewById(R.id.fab_themkhoanthu);
        fabThemKhoanThu.setOnClickListener(view1 -> {
            ltList = ltDao.getAll();
            if (ltList.size() == 0) {
                errorDialog("Bạn chưa có loại thu nào. Vui lòng bổ sung trước khi thêm khoản thu");
            } else {
                showAddKhoanThuDialog();
            }
        });
        rvKhoanThu.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        rvKhoanThu.addItemDecoration(decoration);
        rvKhoanThu.setAdapter(ktAdapter);
        //checkListEmpty();

    }

    @Override
    public void onResume() {
        super.onResume();
        ktAdapter.setKtList(ktDao.getAll());
        checkListEmpty();
    }

    private void errorDialog(String message) {
        new MDialog.Builder(getContext())
                .setTitle("⚠️ Thông báo")
                .setMessage(message)
                .setPositiveButton("OK", null).create().show();
    }

    private void showAddKhoanThuDialog() {
        KhoanThu kt = new KhoanThu();
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_add_khoanthu_dialog);
        TextView tvDateChoose;
        TextInputLayout tilKhoanthuName, tilKhoanthuCost;
        TextInputEditText edtDialogAddKhoanthu, edtDialogAddKhoanthuCost;
        Spinner snLoaithu;
        MaterialButton btnDatePicker, btnDialogAddKhoanthuCancel, btnDialogAddKhoanthuSubmit;

        tilKhoanthuName = dialog.findViewById(R.id.til_khoanthu_name);
        edtDialogAddKhoanthu = dialog.findViewById(R.id.edt_dialog_add_khoanthu);
        tilKhoanthuCost = dialog.findViewById(R.id.til_khoanthu_cost);
        edtDialogAddKhoanthuCost = dialog.findViewById(R.id.edt_dialog_add_khoanthu_cost);
        snLoaithu = dialog.findViewById(R.id.sn_loaithu);
        tvDateChoose = dialog.findViewById(R.id.tv_date_choose);
        btnDatePicker = dialog.findViewById(R.id.btn_date_picker);
        btnDialogAddKhoanthuCancel = dialog.findViewById(R.id.btn_dialog_add_khoanthu_cancel);
        btnDialogAddKhoanthuSubmit = dialog.findViewById(R.id.btn_dialog_add_khoanthu_submit);
        ArrayAdapter<LoaiThu> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, ltList);
        snLoaithu.setAdapter(adapter);
        snLoaithu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kt.setIdLt(ltList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnDatePicker.setOnClickListener(view -> {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    tvDateChoose.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    Date selectedDate = myCalendar.getTime();
                    SimpleDateFormat dateFormatter = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    kt.setTime(dateFormatter.format(selectedDate));
                }
            };
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnDialogAddKhoanthuCancel.setOnClickListener(view -> dialog.dismiss());
        btnDialogAddKhoanthuSubmit.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtDialogAddKhoanthu.getText().toString().trim())) {
                tilKhoanthuName.setError("Chưa nhập tên khoản thu");
                return;
            } else {
                tilKhoanthuName.setErrorEnabled(false);
                kt.setName(edtDialogAddKhoanthu.getText().toString().trim());
            }
            if (TextUtils.isEmpty(edtDialogAddKhoanthuCost.getText().toString().trim())) {
                tilKhoanthuCost.setError("Chưa nhập số tiền");
                return;
            } else {
                tilKhoanthuCost.setErrorEnabled(false);
                kt.setCost(Double.parseDouble(edtDialogAddKhoanthuCost.getText().toString().trim()));
            }
            if (kt.getTime() == null) {
                errorDialog("Chưa chọn ngày thu");
                return;
            }

            if (ktDao.insert(kt) > 0) {
                ktAdapter.addItem();
                Toast.makeText(getContext(), "Đã thêm khoản thu!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                checkListEmpty();
            }
        });
        dialog.show();
    }

    private void checkListEmpty() {
        if (ktAdapter.getItemCount() > 0) {
            tvEmpty.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_khoanthu, container, false);
    }
}
