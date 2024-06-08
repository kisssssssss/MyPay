package com.kis.mypay.AuthorityActivity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.divider.MaterialDivider;
import com.kis.mypay.MainActivity.MainActivity;
import com.kis.mypay.R;
import com.kis.mypay.databinding.FragmentRegisterBinding;
import com.kis.mypay.sql.SQLiteHelper;
import com.kis.mypay.sql.UserInfo;
import com.kis.mypay.utils.utils;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;

    private Activity activity;

    private View view;

    SQLiteHelper DBHelper;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        activity = getActivity();
        view = binding.getRoot();
        DBHelper = SQLiteHelper.getInstance(view.getContext());

        LottieAnimationView lottieAnimationView = view.findViewById(R.id.register_icon);
        lottieAnimationView.setOnClickListener(v -> lottieAnimationView.playAnimation());

        MaterialCardView cardPhoneView = view.findViewById(R.id.register_card_phone);
        MaterialCardView cardTokenView = view.findViewById(R.id.register_card_token);
        TextView textPhoneView = view.findViewById(R.id.register_title_phone);
        TextView textTokenView = view.findViewById(R.id.register_title_token);
        int defaultColor = ContextCompat.getColor(activity, R.color.zinc_600);
        int highlightColor = ContextCompat.getColor(activity, R.color.violet_600);
        view.setOnTouchListener((v, event) -> {
            // 隐藏软键盘和清楚输入框焦点
            utils.clearEditTextFocus(activity, view);

            // 重置输入框边框颜色
            cardPhoneView.setStrokeColor(defaultColor);
            textPhoneView.setTextColor(defaultColor);

            cardTokenView.setStrokeColor(defaultColor);
            textTokenView.setTextColor(defaultColor);
            return false;
        });

        EditText phoneEditText = view.findViewById(R.id.register_phone);
        phoneEditText.setOnTouchListener((v, event) -> {
            // 设置输入框边框颜色
            cardPhoneView.setStrokeColor(highlightColor);
            textPhoneView.setTextColor(highlightColor);

            cardTokenView.setStrokeColor(defaultColor);
            textTokenView.setTextColor(defaultColor);
            return false;
        });

        EditText tokenEditText = view.findViewById(R.id.register_token);
        tokenEditText.setOnTouchListener((v, event) -> {
            // 设置输入框边框颜色
            cardTokenView.setStrokeColor(highlightColor);
            textTokenView.setTextColor(highlightColor);

            cardPhoneView.setStrokeColor(defaultColor);
            textPhoneView.setTextColor(defaultColor);

            return false;
        });

        binding.registerButton.setOnClickListener(v -> {
            String phone = phoneEditText.getText().toString();
            String token = utils.Md5Decode32(tokenEditText.getText().toString());

            if (DBHelper.queryPhone(phone).isEmpty()) {
                if (DBHelper.insert(new UserInfo("User:" + phone, phone, token))) {
                    Toast.makeText(activity, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(activity, "注册失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, "该用户已存在", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}
