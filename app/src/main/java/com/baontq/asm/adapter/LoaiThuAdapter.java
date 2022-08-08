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

import com.baontq.asm.DAO.LoaiThuDAO;
import com.baontq.asm.DTO.LoaiThu;
import com.baontq.asm.R;
import com.baontq.asm.dialog.MDialog;
import com.baontq.asm.interfaces.ItemChangeCallBack;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoaiThuAdapter extends RecyclerView.Adapter<LoaiThuAdapter.LoaiThuVH> {
    private List<LoaiThu> list;
    private final LoaiThuDAO dao;
    private final Context context;
    public ItemChangeCallBack itemChangeCallBack;

    public LoaiThuAdapter(Context context, List<LoaiThu> list, ItemChangeCallBack itemChangeCallBack) {
        dao = new LoaiThuDAO(context);
        this.list = list;
        this.context =context;
        this.itemChangeCallBack = itemChangeCallBack;
    }

    @NonNull
    @Override
    public LoaiThuVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loaithu_item, parent, false);
        return new LoaiThuVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiThuVH holder, int position) {
        holder.tvLoaithuItemName.setText(list.get(position).getName());
        holder.btnLoaithuItemEdit.setOnClickListener(view -> editLoaiChiDialog(list.get(position), position));
        holder.btnLoaithuItemDelete.setOnClickListener(view -> {
            new MDialog.Builder(view.getContext())
                    .setTitle("Xác nhận")
                    .setMessage("Loại thu " + list.get(position).getName() + " và các khoản thu liên quan sẽ bị xoá ?")
                    .setNegativeButton("Huỷ bỏ", null)
                    .setPositiveButton("Xoá", (dialogInterface, i) -> {
                        if (dao.delete(list.get(position).getId()) > 0) {
                            list = dao.getAll();
                            notifyItemRemoved(position);
                            itemChangeCallBack.checkListEmpty();
                            Toast.makeText(view.getContext(), "Đã xoá loại thu!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                        }
                    }).create().show();
        });
    }

    private void editLoaiChiDialog(LoaiThu lt, int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_add_loaithu_dialog);
        TextView tvDialogTitle;
        TextInputLayout tilLoaithuName;
        TextInputEditText edtDialogAddLoaithu;
        MaterialButton btnDialogAddLoaithuCancel;
        MaterialButton btnDialogAddLoaithuSubmit;

        tvDialogTitle = dialog.findViewById(R.id.tv_dialog_title);
        tilLoaithuName = dialog.findViewById(R.id.til_loaithu_name);
        edtDialogAddLoaithu = dialog.findViewById(R.id.edt_dialog_add_loaithu);
        btnDialogAddLoaithuCancel = dialog.findViewById(R.id.btn_dialog_add_loaithu_cancel);
        btnDialogAddLoaithuSubmit = dialog.findViewById(R.id.btn_dialog_add_loaithu_submit);

        tvDialogTitle.setText("Sửa loại chi");
        btnDialogAddLoaithuSubmit.setText("Cập nhật");
        edtDialogAddLoaithu.setText(lt.getName());
        btnDialogAddLoaithuCancel.setOnClickListener(view -> dialog.dismiss());
        btnDialogAddLoaithuSubmit.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtDialogAddLoaithu.getText().toString().trim())) {
                tilLoaithuName.setError("Vui lòng nhập loại chi");
                return;
            } else {
                tilLoaithuName.setErrorEnabled(false);
                lt.setName(edtDialogAddLoaithu.getText().toString().trim());
                if (dao.update(lt) > 0) {
                    Toast.makeText(context, "Cập nhật loại thu thành công!", Toast.LENGTH_SHORT).show();
                    itemChangeCallBack.checkListEmpty();
                    notifyItemChanged(position);
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật loại thu thất bại!", Toast.LENGTH_SHORT).show();
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

    public void addItem() {
        list = dao.getAll();
        notifyItemInserted(list.size());
    }

    static class LoaiThuVH extends RecyclerView.ViewHolder {
        private final TextView tvLoaithuItemName;
        private final MaterialButton btnLoaithuItemDelete;
        private final MaterialButton btnLoaithuItemEdit;

        public LoaiThuVH(@NonNull View itemView) {
            super(itemView);
            tvLoaithuItemName = itemView.findViewById(R.id.tv_loaithu_item_name);
            btnLoaithuItemDelete = itemView.findViewById(R.id.btn_loaithu_item_delete);
            btnLoaithuItemEdit = itemView.findViewById(R.id.btn_loaithu_item_edit);
        }
    }
}
