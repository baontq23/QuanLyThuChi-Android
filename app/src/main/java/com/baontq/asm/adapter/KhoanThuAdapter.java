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

import com.baontq.asm.DAO.KhoanThuDAO;
import com.baontq.asm.DAO.LoaiThuDAO;
import com.baontq.asm.DTO.KhoanThu;
import com.baontq.asm.DTO.LoaiThu;
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

public class KhoanThuAdapter extends RecyclerView.Adapter<KhoanThuAdapter.KhoanThuVH> {
    private final Context context;
    private List<KhoanThu> ktList;
    private final KhoanThuDAO ktDao;
    public ItemChangeCallBack itemChangeCallBack;
    private final Calendar myCalendar = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
    private final LoaiThuDAO ltDao;

    public KhoanThuAdapter(Context context, List<KhoanThu> ktList, ItemChangeCallBack itemChangeCallBack) {
        this.context = context;
        this.ktList = ktList;
        ltDao = new LoaiThuDAO(context);
        ktDao = new KhoanThuDAO(context);
        this.itemChangeCallBack = itemChangeCallBack;
    }

    @NonNull
    @Override
    public KhoanThuVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_khoanthu_item, parent, false);
        return new KhoanThuVH(view);
    }

    public void setKtList(List<KhoanThu> ktList) {
        this.ktList = ktList;
        notifyDataSetChanged();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull KhoanThuVH holder, int position) {
        try {
            Date date = sdf.parse(ktList.get(position).getTime());
            holder.tvKhoanthuItemDate.setText(sdf1.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(view -> showInfoDialog(ktList.get(position)));
        holder.tvKhoanthuItemName.setText(ktList.get(position).getName());
        holder.tvKhoanthuItemCost.setText("+ " + String.format("%.0f", ktList.get(position).getCost()));
        holder.btnKhoanthuItemOption.setOnClickListener(view -> {
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
                            updateKhoanThuDialog(ktList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                            return true;
                        case R.id.option_delete:
                            deleteKhoanThuDialog(holder.getAdapterPosition());
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

    private void showInfoDialog(KhoanThu khoanThu) {
        LoaiThu loaiThu = ltDao.getById(khoanThu.getIdLt());
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
        tvDialogTitle.setText("Thông tin khoản thu");
        tvInfo.setText("Tên khoản thu");
        tvInfo2.setText("Loại thu");
        tvSubInfo2.setText(loaiThu.getName());
        tvCostInfo.setText("Số tiền");
        tvCostValue.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        tvCostValue.setText(String.format("%.0f", khoanThu.getCost()) + " VND");
        tvSubInfo.setText(khoanThu.getName());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnDialogClose.setOnClickListener(view -> dialog.dismiss());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void updateKhoanThuDialog(KhoanThu kt, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            myCalendar.setTime(sdf.parse(kt.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_add_khoanthu_dialog);
        TextView tvDateChoose, tvDialogTitle;
        TextInputLayout tilKhoanthuName, tilKhoanthuCost;
        TextInputEditText edtDialogAddKhoanthu, edtDialogAddKhoanthuCost;
        Spinner snLoaithu;
        MaterialButton btnDatePicker, btnDialogAddKhoanthuCancel, btnDialogAddKhoanthuSubmit;
        List<LoaiThu> ltList = ltDao.getAll();
        tilKhoanthuName = dialog.findViewById(R.id.til_khoanthu_name);
        tvDialogTitle = dialog.findViewById(R.id.tv_add_khoanthu_dialog_title);
        edtDialogAddKhoanthu = dialog.findViewById(R.id.edt_dialog_add_khoanthu);
        tilKhoanthuCost = dialog.findViewById(R.id.til_khoanthu_cost);
        edtDialogAddKhoanthuCost = dialog.findViewById(R.id.edt_dialog_add_khoanthu_cost);
        snLoaithu = dialog.findViewById(R.id.sn_loaithu);
        tvDateChoose = dialog.findViewById(R.id.tv_date_choose);
        btnDatePicker = dialog.findViewById(R.id.btn_date_picker);
        btnDialogAddKhoanthuCancel = dialog.findViewById(R.id.btn_dialog_add_khoanthu_cancel);
        btnDialogAddKhoanthuSubmit = dialog.findViewById(R.id.btn_dialog_add_khoanthu_submit);
        ArrayAdapter<LoaiThu> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, ltList);
        snLoaithu.setAdapter(adapter);
        //Custom dialog
        tvDialogTitle.setText("Cập nhật khoản thu");
        tvDateChoose.setText(kt.getTime());
        btnDialogAddKhoanthuSubmit.setText("Cập nhật");
        edtDialogAddKhoanthu.setText(kt.getName());
        edtDialogAddKhoanthuCost.setText(String.format("%.0f", kt.getCost()));
        int spinnerIndex = 0;
        for (LoaiThu lt : ltList) {
            if (lt.getId() == kt.getIdLt()) {
                snLoaithu.setSelection(spinnerIndex);
                break;
            }
            spinnerIndex++;
        }
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
                    tvDateChoose.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    kt.setTime(dayOfMonth + "/" + monthOfYear + "/" + year);
                }
            };
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
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

            if (ktDao.update(kt) > 0) {
                Toast.makeText(context, "Đã cập nhật khoản thu!", Toast.LENGTH_SHORT).show();
                ktList.set(i, kt);
                notifyItemChanged(i);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteKhoanThuDialog(int i) {
        new MDialog.Builder(context)
                .setTitle("Xác nhận")
                .setMessage("Khoản thu " + ktList.get(i).getName() + " sẽ bị xoá ?")
                .setNegativeButton("Huỷ bỏ", null)
                .setPositiveButton("Xoá", (dialogInterface, i1) -> {
                    if (ktDao.delete(ktList.get(i).getId()) > 0) {
                        ktList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, ktList.size());
                        Toast.makeText(context, "Đã xoá khoản thu!", Toast.LENGTH_SHORT).show();
                        itemChangeCallBack.checkListEmpty();
                    }
                }).create().show();
    }

    public void addItem() {
        ktList = ktDao.getAll();
        notifyItemInserted(ktList.size());
    }

    @Override
    public long getItemId(int position) {
        return ktList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return ktList == null ? 0 : ktList.size();
    }

    static class KhoanThuVH extends RecyclerView.ViewHolder {
        private final TextView tvKhoanthuItemName;
        private final TextView tvKhoanthuItemCost;
        private final TextView tvKhoanthuItemDate;
        private final MaterialButton btnKhoanthuItemOption;

        public KhoanThuVH(@NonNull View itemView) {
            super(itemView);
            tvKhoanthuItemName = itemView.findViewById(R.id.tv_khoanthu_item_name);
            tvKhoanthuItemDate = itemView.findViewById(R.id.tv_khoanthu_item_date);
            tvKhoanthuItemCost = itemView.findViewById(R.id.tv_khoanthu_item_cost);
            btnKhoanthuItemOption = itemView.findViewById(R.id.btn_khoanthu_item_option);

        }
    }
}
