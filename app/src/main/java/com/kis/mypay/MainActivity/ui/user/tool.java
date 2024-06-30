package com.kis.mypay.MainActivity.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.kis.mypay.AuthorityActivity.AuthorityActivity;
import com.kis.mypay.MainActivity.MainActivity;

public class tool {
    public static void reLogin(Activity activity) {
        if (activity != null) {
            // 清除当前用户信息
            SharedPreferences sharedPreferences = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("current_user_phone", "");
            editor.putBoolean("auto_login", false);
            editor.apply();

            // 跳转到登录界面
            Intent intent = new Intent(activity, AuthorityActivity.class);
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, "activity为空", Toast.LENGTH_SHORT).show();
        }

    }
}
