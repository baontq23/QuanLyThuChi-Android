package com.baontq.asm.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.baontq.asm.R;
import com.google.android.material.button.MaterialButton;

public class ConfirmDialog extends Dialog {


    @SuppressLint("ResourceType")
    public ConfirmDialog(Context context) {
        super(context, R.layout.dialog_confirm);
    }

    public static class Builder {
        private final Context mContext;
        private ConfirmDialog mConfirmDialog;
        private CharSequence mTitle;
        private CharSequence mMessage;
        private View mContentView;
        private OnClickListener mCancelClickListener;
        private OnClickListener mConfirmClickListener;
        private boolean mCancelable = true;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(int titleId) {
            this.mTitle = mContext.getText(titleId);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public Builder setMessage(int messageId) {
            this.mMessage = mContext.getText(messageId);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.mMessage = message;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder setContentView(View contentView) {
            this.mContentView = contentView;
            return this;
        }

        public Builder setCancelOnClickListener(OnClickListener listener) {
            this.mCancelClickListener = listener;
            return this;
        }

        public Builder setConfirmOnClickListener(OnClickListener listener) {
            this.mConfirmClickListener = listener;
            return this;
        }

        public ConfirmDialog create() {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View dialogView = inflater.inflate(R.layout.dialog_confirm, null);
            mConfirmDialog = new ConfirmDialog(mContext);
            mConfirmDialog.setCancelable(mCancelable);
            TextView tvDialogTitle, tvDialogMessage;
            MaterialButton btnDialogCancel, btnDialogSubmit;

            tvDialogTitle = dialogView.findViewById(R.id.tv_dialog_title);
            tvDialogMessage = dialogView.findViewById(R.id.tv_dialog_message);
            btnDialogCancel = dialogView.findViewById(R.id.btn_dialog_cancel);
            btnDialogSubmit = dialogView.findViewById(R.id.btn_dialog_submit);
            dialogView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (mTitle == null) {
                tvDialogTitle.setText("Thông báo");
            } else {
                tvDialogTitle.setText(mTitle);
            }

            if (mMessage == null) {
                tvDialogMessage.setText("Thông báo");
            } else {
                tvDialogMessage.setText(mMessage);
            }

            if (mCancelClickListener != null) {
                btnDialogCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCancelClickListener.onClick(mConfirmDialog, DialogInterface.BUTTON_NEGATIVE);
                        mConfirmDialog.dismiss();
                    }
                });
            }
            dialogView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int dialogHeight = dialogView.getMeasuredHeight();
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);
            int maxHeight = (int) (metrics.heightPixels * 0.8);
            ViewGroup.LayoutParams dialogParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if(dialogHeight >= maxHeight) {
                dialogParams.height = maxHeight;
            }
            mConfirmDialog.setContentView(dialogView, dialogParams);

            return mConfirmDialog;
        }

        public ConfirmDialog show() {
            mConfirmDialog = create();
            mConfirmDialog.show();
            return mConfirmDialog;
        }
    }


}
