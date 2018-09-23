package com.akhutornoy.transformerswar.ui.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.akhutornoy.transformerswar.BuildConfig;
import com.akhutornoy.transformerswar.R;

public class AppDialog {

    private static ProgressDialog mProgressDialog;

    public static boolean isDialogShowing() {
        return mProgressDialog != null && mProgressDialog.isShowing();
    }


    public static void showProgressDialog(Context context) {
        if (mProgressDialog == null || !mProgressDialog.isShowing()) {
            mProgressDialog = new ProgressDialog(context, R.style.TransparentProgressDialog);
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.layout_dialog_progress);
            if (!BuildConfig.DEBUG) {
                mProgressDialog.setCancelable(false);
            } else {
                mProgressDialog.setCancelable(true);
            }
        }
    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }


    public static void showDialog(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.all_ok, okListener)
                .setNegativeButton(R.string.all_cancel, okListener)
                .create()
                .show();
    }

    public static void showDialog(Context context,
                                  String message,
                                  String positiveButtonName,
                                  DialogInterface.OnClickListener okListener,
                                  String negativeButtonName,
                                  DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveButtonName, okListener)
                .setNegativeButton(negativeButtonName, cancelListener)
                .create()
                .show();
    }

    public static void showDialogWithTitle(Context context, String title, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.all_ok, okListener)
                .setNegativeButton(R.string.all_cancel, okListener)
                .create()
                .show();
    }

    public static void showDialogOk(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
                .setTitle(message)
                .setCancelable(false)
                .setPositiveButton(R.string.all_ok, okListener)
                .create()
                .show();
    }
}
