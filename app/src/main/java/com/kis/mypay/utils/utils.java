package com.kis.mypay.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class utils {
    public static void clearEditTextFocus(Activity activity, View v) {
        View focusedView = v.findFocus();
        if (focusedView instanceof EditText) {
            // 清除 EditText 的焦点
            EditText editText = (EditText) focusedView;
            editText.clearFocus();

            // 关闭软键盘
            if (activity != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }
        }
    }

    public static void requestEditTextFocus(Activity activity, EditText editText) {
        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void requestEditTextFocus(Context context, EditText editText) {
        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
