package com.kis.mypay.AuthorityActivity;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.kis.mypay.AuthorityActivity.fragment.LoginFragment;
import com.kis.mypay.R;
import com.kis.mypay.databinding.ActivityAuthorityBinding;

public class AuthorityActivity extends AppCompatActivity {
    private ActivityAuthorityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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