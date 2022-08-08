package com.baontq.asm.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baontq.asm.DAO.LoaiChiDAO;
import com.baontq.asm.DTO.LoaiChi;
import com.baontq.asm.R;
import com.baontq.asm.adapter.LoaiChiAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoaiChiFragment extends Fragment {
    private RecyclerView rvLoaichi;
    private FloatingActionButton fabThemloaichi;
    private LoaiChiDAO dao;
    private LoaiChiAdapter adapter;
    private TextView tvEmpty;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = new LoaiChiDAO(getContext());
        tvEmpty = view.findViewById(R.id.tv_empty);
        adapter = new LoaiChiAdapter(dao.getAll(), getContext(), () -> checkListEmpty());
        rvLoaichi = view.findViewById(R.id.rv_loaichi);
        fabThemloaichi = view.findViewById(R.id.fab_themloaichi);
        rvLoaichi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        rvLoaichi.addItemDecoration(decoration);
        rvLoaichi.setAdapter(adapter);
        fabThemloaichi.setOnClickListener(view1 -> {
            addLoaiChiDialog();
        });
        checkListEmpty();
    }

    public void addLoaiChiDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_add_loaichi_dialog);
        TextInputEditText edtDialogAddLoaichi;
        MaterialButton btnDialogAddLoaichiCancel, btnDialogAddLoaichiSubmit;
        TextInputLayout tilLoaichiName;
        tilLoaichiName = dialog.findViewById(R.id.til_loaichi_name);
        edtDialogAddLoaichi = dialog.findViewById(R.id.edt_dialog_add_loaichi);
        btnDialogAddLoaichiCancel = dialog.findViewById(R.id.btn_dialog_add_loaichi_cancel);
        btnDialogAddLoaichiSubmit = dialog.findViewById(R.id.btn_dialog_add_loaichi_submit);
        btnDialogAddLoaichiCancel.setOnClickListener(view -> dialog.dismiss());
        btnDialogAddLoaichiSubmit.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtDialogAddLoaichi.getText().toString().trim())) {
                tilLoaichiName.setError("Vui lòng nhập loại chi");
                return;
            } else {
                tilLoaichiName.setErrorEnabled(false);
                LoaiChi newLc = new LoaiChi(0, edtDialogAddLoaichi.getText().toString().trim());
                if (dao.insert(newLc) > 0) {
                    adapter.addItem();
                    Toast.makeText(getContext(), "Thêm loại chi thành công!", Toast.LENGTH_SHORT).show();
                    checkListEmpty();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm loại chi thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void checkListEmpty() {
        if (adapter.getItemCount() > 0) {
            tvEmpty.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loaichi, container, false);
    }
}