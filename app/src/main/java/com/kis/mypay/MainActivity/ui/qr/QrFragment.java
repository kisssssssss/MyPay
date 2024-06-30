package com.kis.mypay.MainActivity.ui.qr;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kis.mypay.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.kis.mypay.databinding.FragmentQrBinding;

public class QrFragment extends Fragment {
    private FragmentQrBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        QrViewModel qrViewModel = new ViewModelProvider(this).get(QrViewModel.class);

        binding = FragmentQrBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        TabLayout tabLayout = root.findViewById(R.id.pay_tab);
        ViewPager2 viewPager = root.findViewById(R.id.pay_view_pager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 当tab从选择到未选择
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 已经选中tab后的重复点击tab
            }
        });
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new QrPayFragment();
                    case 1:
                        return new QrReceiveFragment();
                    default:
                        throw new IllegalStateException("Unexpected position: " + position);
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.pay);
                    break;
                case 1:
                    tab.setText(R.string.receive);
                    break;
            }
            tab.view.setOnLongClickListener(v -> false);
        }).attach();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
