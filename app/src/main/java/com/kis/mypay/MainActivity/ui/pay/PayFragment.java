package com.kis.mypay.MainActivity.ui.pay;

import com.kis.mypay.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kis.mypay.databinding.FragmentPayBinding;

public class PayFragment extends Fragment {
    private FragmentPayBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PayViewModel payViewModel = new ViewModelProvider(this).get(PayViewModel.class);

        binding = FragmentPayBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        ImageView qrCodeImageView = root.findViewById(R.id.qrCodeImageView);
        Bitmap qrCodeBitmap = payViewModel.generateQRCode("https", 320, 320);
        qrCodeImageView.setImageBitmap(qrCodeBitmap);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
