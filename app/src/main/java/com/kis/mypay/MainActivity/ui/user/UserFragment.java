package com.kis.mypay.MainActivity.ui.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kis.mypay.AuthorityActivity.fragment.RegisterFragment;
import com.kis.mypay.MainActivity.MainActivity;
import com.kis.mypay.R;
import com.kis.mypay.SettingsActivity.SettingsActivity;
import com.kis.mypay.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {
    private FragmentUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        getUserInfo(view);

        ImageButton set = view.findViewById(R.id.user_set);
        set.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getUserInfo(View v) {
        // 获取当前用户的手机号
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString("current_user_phone", "");
        if (phone.isEmpty()) {
            tool.reLogin(getActivity());
        }
        ((TextView) v.findViewById(R.id.user_phone)).setText(phone);
    }
}
