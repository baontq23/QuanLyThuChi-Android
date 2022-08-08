package com.baontq.asm.adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baontq.asm.DAO.LoaiChiDAO;
import com.baontq.asm.DTO.LoaiChi;
import com.baontq.asm.R;
import com.baontq.asm.dialog.MDialog;
import com.baontq.asm.interfaces.ItemChangeCallBack;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoaiChiAdapter extends RecyclerView.Adapter<LoaiChiAdapter.LoaiChiViewHolder> {
    private List<LoaiChi> list;
    private final LoaiChiDAO dao;
    private final Context context;
    public ItemChangeCallBack itemChangeCallBack;
    public LoaiChiAdapter(List<LoaiChi> list, Context context, ItemChangeCallBack itemChangeCallBack) {
        this.list = list;
        this.context = context;
        this.itemChangeCallBack = itemChangeCallBack;
        dao = new LoaiChiDAO(context);
    }

    @NonNull
    @Override
    public LoaiChiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loaichi_item, parent, false);
        return new LoaiChiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiChiViewHolder holder, int position) {
        holder.tvLoaichiItemName.setText(list.get(position).getName());
        holder.btnLoaiChiItemEdit.setOnClickListener(view -> editLoaiChiDialog(list.get(position), position));
        holder.btnLoaichiItemDelete.setOnClickListener(view -> {
            new MDialog.Builder(view.getContext())
                    .setTitle("Xác nhận")
                    .setMessage("Loại chi " + list.get(position).getName() + " và các khoản chi liên quan sẽ bị xoá ?")
                    .setNegativeButton("Huỷ bỏ", null)
                    .setPositiveButton("Xoá", (dialogInterface, i) -> {
                        if (dao.delete(list.get(position).getId()) > 0) {
                            list = dao.getAll();
                            notifyItemRemoved(position);
                            itemChangeCallBack.checkListEmpty();
                            Toast.makeText(view.getContext(), "Done", Toast.LENGTH_SHORT).show();
                        }else {

                        }
                    }).create().show();
        });
    }

    public void addItem() {
        list = dao.getAll();
        notifyItemInserted(list.size());
    }

    private void editLoaiChiDialog(LoaiChi lc, int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_add_loaichi_dialog);
        TextView tvTitleDialog;
        TextInputEditText edtDialogAddLoaichi;
        MaterialButton btnDialogAddLoaichiCancel, btnDialogAddLoaichiSubmit;
        TextInputLayout tilLoaichiName;
        tilLoaichiName = dialog.findViewById(R.id.til_loaichi_name);
        tvTitleDialog = dialog.findViewById(R.id.tv_dialog_title);
        edtDialogAddLoaichi = dialog.findViewById(R.id.edt_dialog_add_loaichi);
        btnDialogAddLoaichiCancel = dialog.findViewById(R.id.btn_dialog_add_loaichi_cancel);
        btnDialogAddLoaichiSubmit = dialog.findViewById(R.id.btn_dialog_add_loaichi_submit);
        tvTitleDialog.setText("Sửa loại chi");
        btnDialogAddLoaichiSubmit.setText("Cập nhật");
        edtDialogAddLoaichi.setText(lc.getName());
        btnDialogAddLoaichiCancel.setOnClickListener(view -> dialog.dismiss());
        btnDialogAddLoaichiSubmit.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtDialogAddLoaichi.getText().toString().trim())) {
                tilLoaichiName.setError("Vui lòng nhập loại chi");
                return;
            } else {
                tilLoaichiName.setErrorEnabled(false);
                lc.setName(edtDialogAddLoaichi.getText().toString().trim());
                if (dao.update(lc) > 0) {
                    Toast.makeText(context, "Cập nhật loại chi thành công!", Toast.LENGTH_SHORT).show();
                    itemChangeCallBack.checkListEmpty();
                    notifyItemChanged(position);
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật loại chi thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    public static class LoaiChiViewHolder extends RecyclerView.ViewHolder {
        TextView tvLoaichiItemName;
        MaterialButton btnLoaichiItemDelete, btnLoaiChiItemEdit;

        public LoaiChiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLoaichiItemName = itemView.findViewById(R.id.tv_loaichi_item_name);
            btnLoaichiItemDelete = itemView.findViewById(R.id.btn_loaichi_item_delete);
            btnLoaiChiItemEdit = itemView.findViewById(R.id.btn_loaichi_item_edit);
        }
    }
}
