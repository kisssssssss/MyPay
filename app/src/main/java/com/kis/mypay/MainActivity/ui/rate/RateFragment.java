package com.kis.mypay.MainActivity.ui.rate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kis.mypay.R;
import com.kis.mypay.databinding.FragmentRateBinding;

public class RateFragment extends Fragment {
    private FragmentRateBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RateViewModel RateViewModel = new ViewModelProvider(this).get(RateViewModel.class);

        binding = FragmentRateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        TabLayout tabLayout = root.findViewById(R.id.tab_layout);
        ViewPager2 viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new ExchangeFragment();
                    case 1:
                        return new TrendFragment();
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
                    tab.setText(R.string.exchange);
                    tab.setIcon(R.drawable.exchange);
                    break;
                case 1:
                    tab.setText(R.string.trend);
                    tab.setIcon(R.drawable.trend);
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
