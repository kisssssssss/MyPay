package com.kis.mypay.SettingsActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.kis.mypay.MainActivity.MainActivity;
import com.kis.mypay.MainActivity.ui.user.tool;
import com.kis.mypay.R;
import com.kis.mypay.databinding.ActivitySettingsBinding;
import com.kis.mypay.sql.UserInfo;
import com.kis.mypay.sql.UserSQLHelper;
import com.kis.mypay.utils.utils;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;

    UserSQLHelper DBHelper;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        DBHelper = UserSQLHelper.getInstance(view.getContext());

        MaterialButton exit = view.findViewById(R.id.exit);
        exit.setOnTouchListener((v, event) -> {
            tool.reLogin(this);
            return false;
        });

        MaterialButton modify = view.findViewById(R.id.modify_password);
        EditText passwordInput = view.findViewById(R.id.new_password);
        modify.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            String phone = sharedPreferences.getString("current_user_phone", "");
            String password = passwordInput.getText().toString();
            if (!password.isEmpty() && !phone.isEmpty()) {
                UserInfo u = DBHelper.queryPhone(phone).get(0);
                u.token = utils.Md5Decode32(password);
                DBHelper.update(u);
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "密码不能为空/当前用户获取失败请重新登录", Toast.LENGTH_SHORT).show();
            }
        });

        setContentView(binding.getRoot());

    }

}