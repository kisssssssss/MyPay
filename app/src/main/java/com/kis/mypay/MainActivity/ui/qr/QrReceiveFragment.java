package com.kis.mypay.MainActivity.ui.qr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kis.mypay.databinding.FragmentQrReceiveBinding;

public class QrReceiveFragment extends Fragment {

    private FragmentQrReceiveBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        QrViewModel qrViewModel = new ViewModelProvider(this).get(QrViewModel.class);

        binding = FragmentQrReceiveBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        return root;
    }
}