package com.baontq.asm.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.baontq.asm.DAO.KhoanChiDAO;
import com.baontq.asm.DAO.LoaiChiDAO;
import com.baontq.asm.DTO.KhoanChi;
import com.baontq.asm.DTO.LoaiChi;
import com.baontq.asm.R;
import com.baontq.asm.dialog.MDialog;
import com.baontq.asm.interfaces.ItemChangeCallBack;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KhoanChiAdapter extends RecyclerView.Adapter<KhoanChiAdapter.KhoanChiVH> {
    private List<KhoanChi> list;
    private KhoanChiDAO dao;
    private Context context;
    private LoaiChiDAO lcDao;
    private List<LoaiChi> loaiChiList;
    private Calendar myCalendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
    public ItemChangeCallBack itemChangeCallBack;

    public KhoanChiAdapter(List<KhoanChi> list, Context context, ItemChangeCallBack itemChangeCallBack) {
        this.list = list;
        this.itemChangeCallBack = itemChangeCallBack;
        this.context = context;
        dao = new KhoanChiDAO(context);
        lcDao = new LoaiChiDAO(context);
    }

    @NonNull
    @Override
    public KhoanChiVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_khoanchi_item, parent, false);
        return new KhoanChiVH(view);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull KhoanChiVH holder, int position) {
        try {
            Date date = sdf.parse(list.get(position).getTime());
            holder.tvKhoanchiItemDate.setText(sdf1.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfoDialog(list.get(position));
            }
        });
        holder.tvKhoanchiItemName.setText(list.get(position).getName());
        holder.tvKhoanchiItemCost.setText("- " + String.format("%.0f", list.get(position).getCost()));
        holder.btnKhoanchiItemOption.setOnClickListener(view -> {
            MenuBuilder menuBuilder = new MenuBuilder(context);
            MenuInflater inflater = new MenuInflater(context);
            inflater.inflate(R.menu.option_chi_thu, menuBuilder);
            MenuPopupHelper optionMenu = new MenuPopupHelper(context, menuBuilder, view);
            optionMenu.setForceShowIcon(true);
            menuBuilder.setCallback(new MenuBuilder.Callback() {
                @Override
                public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.option_edit:
                            updateKhoanChiDialog(list.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                            return true;
                        case R.id.option_delete:
                            deleteKhoanChiDialog(holder.getAdapterPosition());
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onMenuModeChange(@NonNull MenuBuilder menu) {
                }
            });
            optionMenu.show();
        });

    }

    private void showInfoDialog(KhoanChi khoanChi) {
        LoaiChi loaiChi = lcDao.getById(khoanChi.getIdLc());
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_infomation);
        TextView tvDialogTitle, tvInfo, tvSubInfo, tvInfo2, tvSubInfo2, tvCostInfo, tvCostValue;
        MaterialButton btnDialogClose;

        tvDialogTitle = dialog.findViewById(R.id.tv_dialog_title);
        tvInfo = dialog.findViewById(R.id.tv_info);
        tvSubInfo = dialog.findViewById(R.id.tv_sub_info);
        tvInfo2 = dialog.findViewById(R.id.tv_info_2);
        tvSubInfo2 = dialog.findViewById(R.id.tv_sub_info_2);
        tvCostInfo = dialog.findViewById(R.id.tv_cost_info);
        tvCostValue = dialog.findViewById(R.id.tv_cost_value);
        btnDialogClose = dialog.findViewById(R.id.btn_dialog_close);
        tvDialogTitle.setText("Thông tin khoản chi");
        tvInfo.setText("Tên khoản chi");
        tvInfo2.setText("Loại chi");
        tvSubInfo2.setText(loaiChi.getName());
        tvCostInfo.setText("Số tiền");
        tvCostValue.setTextColor(context.getResources().getColor(R.color.red_dark_200));
        tvCostValue.setText(String.format("%.0f", khoanChi.getCost())+ " VND");
        tvSubInfo.setText(khoanChi.getName());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnDialogClose.setOnClickListener(view -> dialog.dismiss());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void setList(List<KhoanChi> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItem() {
        list = dao.getAll();
        notifyItemInserted(list.size());
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class KhoanChiVH extends RecyclerView.ViewHolder {
        private TextView tvKhoanchiItemName, tvKhoanchiItemDate;
        private MaterialButton btnKhoanchiItemOption;
        private TextView tvKhoanchiItemCost;


        public KhoanChiVH(@NonNull View itemView) {
            super(itemView);
            tvKhoanchiItemName = itemView.findViewById(R.id.tv_khoanchi_item_name);
            tvKhoanchiItemDate = itemView.findViewById(R.id.tv_khoanchi_item_date);
            btnKhoanchiItemOption = itemView.findViewById(R.id.btn_khoanchi_item_option);
            tvKhoanchiItemCost = itemView.findViewById(R.id.tv_khoanchi_item_cost);

        }
    }

    private void deleteKhoanChiDialog(int position) {
        new MDialog.Builder(context)
                .setTitle("Xác nhận")
                .setMessage("Khoản chi " + list.get(position).getName() + " sẽ bị xoá ?")
                .setNegativeButton("Huỷ bỏ", null)
                .setPositiveButton("Xoá", (dialogInterface, i) -> {
                    if (dao.delete(list.get(position).getId()) > 0) {
                        list = dao.getAll();
                        notifyItemRemoved(position);
                        itemChangeCallBack.checkListEmpty();
                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
    }

    private void updateKhoanChiDialog(KhoanChi kc, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            myCalendar.setTime(sdf.parse(kc.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_add_khoanchi_dialog);
        TextInputLayout tilKhoanchiName, tilKhoanchiCost;
        TextInputEditText edtDialogAddKhoanchi, edtDialogAddKhoanchiCost;
        Spinner snLoaichi;
        TextView tvDateChoose, tvDialogTitle;
        MaterialButton btnDatePicker, btnDialogAddKhoanchiCancel, btnDialogAddKhoanchiSubmit;
        loaiChiList = lcDao.getAll();
        ArrayAdapter<LoaiChi> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, loaiChiList);
        tilKhoanchiName = dialog.findViewById(R.id.til_khoanchi_name);
        edtDialogAddKhoanchi = dialog.findViewById(R.id.edt_dialog_add_khoanchi);
        tilKhoanchiCost = dialog.findViewById(R.id.til_khoanchi_cost);
        edtDialogAddKhoanchiCost = dialog.findViewById(R.id.edt_dialog_add_khoanchi_cost);
        snLoaichi = dialog.findViewById(R.id.sn_loaichi);
        tvDateChoose = dialog.findViewById(R.id.tv_date_choose);
        tvDialogTitle = dialog.findViewById(R.id.tv_add_khoanchi_dialog_title);
        btnDatePicker = dialog.findViewById(R.id.btn_date_picker);
        btnDialogAddKhoanchiCancel = dialog.findViewById(R.id.btn_dialog_add_khoanchi_cancel);
        btnDialogAddKhoanchiSubmit = dialog.findViewById(R.id.btn_dialog_add_khoanchi_submit);
        snLoaichi.setAdapter(adapter);
        //Custom dialog
        int spinnerIndex = 0;
        for (LoaiChi lc : loaiChiList) {
            if (lc.getId() == kc.getIdLc()) {
                snLoaichi.setSelection(spinnerIndex);
                break;
            }
            spinnerIndex++;
        }
        tvDialogTitle.setText("Cập nhật khoản chi");
        edtDialogAddKhoanchi.setText(kc.getName());
        edtDialogAddKhoanchiCost.setText(String.format("%.0f", list.get(position).getCost()));
        tvDateChoose.setText(kc.getTime());
        btnDialogAddKhoanchiSubmit.setText("Cập nhật");
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
                        kc.setTime(dayOfMonth + "/" + monthOfYear + "/" + year);
                        Date selectedDate = myCalendar.getTime();
                        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        kc.setTime(dateFormatter.format(selectedDate));
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
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
            if (dao.update(kc) > 0) {
                notifyItemChanged(position);
                Toast.makeText(context, "Đã cập nhật khoản chi!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
