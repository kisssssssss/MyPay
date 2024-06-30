package com.kis.mypay.AuthorityActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.kis.mypay.AuthorityActivity.fragment.LoginFragment;
import com.kis.mypay.MainActivity.MainActivity;
import com.kis.mypay.R;
import com.kis.mypay.databinding.ActivityAuthorityBinding;

public class AuthorityActivity extends AppCompatActivity {
    private ActivityAuthorityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 自动登录
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("auto_login", false)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        binding = ActivityAuthorityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_authority, new LoginFragment()).addToBackStack(null)
                .commit();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
