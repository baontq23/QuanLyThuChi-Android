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

import com.baontq.asm.DAO.LoaiThuDAO;
import com.baontq.asm.DTO.LoaiThu;
import com.baontq.asm.R;
import com.baontq.asm.adapter.LoaiThuAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoaiThuFragment extends Fragment {
    private FloatingActionButton fabThemLoaiThu;
    private RecyclerView rvLoaithu;
    private LoaiThuAdapter adapter;
    private LoaiThuDAO dao;
    private List<LoaiThu> listLt;
    private TextView tvEmpty;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = new LoaiThuDAO(getContext());
        listLt = dao.getAll();
        fabThemLoaiThu = view.findViewById(R.id.fab_themloaithu);
        rvLoaithu = view.findViewById(R.id.rv_loaithu);
        tvEmpty = view.findViewById(R.id.tv_empty);
        adapter = new LoaiThuAdapter(getContext(), listLt, () -> checkListEmpty());
        rvLoaithu.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        rvLoaithu.addItemDecoration(decoration);
        rvLoaithu.setAdapter(adapter);
        checkListEmpty();
        fabThemLoaiThu.setOnClickListener(view1 -> addLoaiThuDialog());
    }

    private void checkListEmpty() {
        if (adapter.getItemCount() > 0) {
            tvEmpty.setVisibility(View.GONE);
        }else {
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void addLoaiThuDialog() {
        LoaiThu lc = new LoaiThu();
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_add_loaithu_dialog);
        TextInputLayout tilLoaithuName;
        TextInputEditText edtDialogAddLoaithu;
        MaterialButton btnDialogAddLoaithuCancel, btnDialogAddLoaithuSubmit;

        tilLoaithuName = dialog.findViewById(R.id.til_loaithu_name);
        edtDialogAddLoaithu = dialog.findViewById(R.id.edt_dialog_add_loaithu);
        btnDialogAddLoaithuCancel = dialog.findViewById(R.id.btn_dialog_add_loaithu_cancel);
        btnDialogAddLoaithuSubmit = dialog.findViewById(R.id.btn_dialog_add_loaithu_submit);
        btnDialogAddLoaithuCancel.setOnClickListener(view -> dialog.dismiss());
        btnDialogAddLoaithuSubmit.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtDialogAddLoaithu.getText().toString().trim())) {
                tilLoaithuName.setError("Chưa nhập loại thu");
                return;
            } else {
                tilLoaithuName.setErrorEnabled(false);
                lc.setName(edtDialogAddLoaithu.getText().toString().trim());
            }
            if (dao.insert(lc) > 0) {
                adapter.addItem();
                Toast.makeText(getContext(), "Đã thêm loại thu", Toast.LENGTH_SHORT).show();
                checkListEmpty();
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loaithu, container, false);
    }
}
