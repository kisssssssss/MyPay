package com.kis.mypay.AuthorityActivity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.divider.MaterialDivider;
import com.kis.mypay.MainActivity.MainActivity;
import com.kis.mypay.R;
import com.kis.mypay.databinding.FragmentLoginBinding;
import com.kis.mypay.sql.UserSQLHelper;
import com.kis.mypay.sql.UserInfo;
import com.kis.mypay.utils.utils;

import java.util.List;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;

    private Activity activity;

    private View view;

    UserSQLHelper DBHelper;

    @SuppressLint({"ClickableViewAccessibility"})
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        activity = getActivity();
        view = binding.getRoot();
        DBHelper = UserSQLHelper.getInstance(view.getContext());

        LottieAnimationView lottieAnimationView = view.findViewById(R.id.login_icon);
        lottieAnimationView.setOnClickListener(v -> lottieAnimationView.playAnimation());

        MaterialCardView cardView = view.findViewById(R.id.login_card);
        MaterialCardView cardView2 = view.findViewById(R.id.login_card_2);
        MaterialDivider dividerView = view.findViewById(R.id.login_divider);
        view.setOnTouchListener((v, event) -> {
            // 隐藏软键盘和清楚输入框焦点
            utils.clearEditTextFocus(activity, view);

            // 重置输入框边框颜色
            cardView.setStrokeColor(ContextCompat.getColor(activity, R.color.zinc_600));
            cardView2.setStrokeColor(ContextCompat.getColor(activity, R.color.zinc_600));
            dividerView.setDividerColor(ContextCompat.getColor(activity, R.color.zinc_500));

            return false;
        });

        EditText phoneEditText = view.findViewById(R.id.login_phone);
        EditText tokenEditText = view.findViewById(R.id.login_token);
        int violet_600 = ContextCompat.getColor(activity, R.color.violet_600);
        int zinc_600 = ContextCompat.getColor(activity, R.color.zinc_600);
        phoneEditText.setOnTouchListener((v, event) -> {
            // 设置输入框边框颜色
            cardView.setStrokeColor(violet_600);
            cardView2.setStrokeColor(zinc_600);
            dividerView.setDividerColor(violet_600);
            return false;
        });
        tokenEditText.setOnTouchListener((v, event) -> {
            // 设置输入框边框颜色
            cardView.setStrokeColor(zinc_600);
            dividerView.setDividerColor(zinc_600);
            cardView2.setStrokeColor(violet_600);
            return false;
        });

        CheckBox autoLoginCheckBox = view.findViewById(R.id.auto_login);

        binding.loginButton.setOnClickListener(v -> {
            // 获取用户输入
            String phone = phoneEditText.getText().toString();
            String token = utils.Md5Decode32(tokenEditText.getText().toString());
            boolean isChecked = autoLoginCheckBox.isChecked();

            // 判断用户输入是否为空
            if (!phone.isEmpty() && !token.isEmpty()) {
                UserInfo info = new UserInfo();
                info.name = "User:" + phone;
                info.phone = phone;
                info.token = token;
                info.autoLogin = isChecked;

                List<UserInfo> searchRes = DBHelper.queryPhone(phone);

                // 判断用户是否存在
                if (searchRes.isEmpty()) {
                    Toast.makeText(activity, "该用户不存在，请先注册", Toast.LENGTH_SHORT).show();
                } else {
                    // 判断用户密码是否正确
                    if (searchRes.get(0).token.equals(token)) {
                        Toast.makeText(activity, "登录成功", Toast.LENGTH_SHORT).show();

                        // 保存用户信息
                        SharedPreferences sharedPreferences = activity.getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("current_user_phone", phone);
                        editor.putBoolean("auto_login", isChecked);
                        editor.commit();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(activity, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(activity, "手机号或密码不能为空", Toast.LENGTH_SHORT).show();
            }
        });

        binding.registerButton.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_authority, new RegisterFragment()).addToBackStack(null)
                    .commit();
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
