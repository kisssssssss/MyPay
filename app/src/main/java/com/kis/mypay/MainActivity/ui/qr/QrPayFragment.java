package com.kis.mypay.MainActivity.ui.qr;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.imageview.ShapeableImageView;
import com.kis.mypay.R;
import com.kis.mypay.databinding.FragmentQrPayBinding;

public class QrPayFragment extends Fragment {

    private FragmentQrPayBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        QrViewModel qrViewModel = new ViewModelProvider(this).get(QrViewModel.class);

        binding = FragmentQrPayBinding.inflate(inflater, container, false);

        View root = binding.getRoot();


        ShapeableImageView qr = root.findViewById(R.id.pay_qr);
        Bitmap qrCodeBitmap = qrViewModel.generateQRCode("成功向XXX支付1000000000,余额100000000000000", 320, 320);
        qr.setImageBitmap(qrCodeBitmap);

        return root;
    }
}
