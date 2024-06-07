package com.kis.mypay.MainActivity.ui.rate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kis.mypay.R;
import com.kis.mypay.databinding.FragmentRateExchangeBinding;
import com.kis.mypay.utils.utils;

import java.util.ArrayList;
import java.util.List;

public class ExchangeFragment extends Fragment {
    private FragmentRateExchangeBinding binding;
    private RecyclerView recyclerView;
    private ListViewAdapter listViewAdapter;

    private View exchangeFragmentview;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRateExchangeBinding.inflate(inflater, container, false);
        exchangeFragmentview = binding.getRoot();

        createRecyclerView();

        exchangeFragmentview.setOnTouchListener((v, event) -> {
            v.performClick();

            utils.clearEditTextFocus(getActivity(), v);

            listViewAdapter.resetSelectedPosition();
            return false;
        });

        return exchangeFragmentview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // 创建 RecyclerView
    @SuppressLint("ClickableViewAccessibility")
    public void createRecyclerView() {
        recyclerView = exchangeFragmentview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(createListViewAdapter());
    }

    // 创建列表适配器
    public ListViewAdapter createListViewAdapter() {
        List<DataModel> dataList = new ArrayList<>();
        dataList.add(new DataModel("USD", R.drawable.country_usd, R.drawable.money_usd, 1));
        dataList.add(new DataModel("星琼", R.drawable.money_xinqiong, R.drawable.money_usd, 1));
        dataList.add(new DataModel("原石", R.drawable.money_yuanshi, R.drawable.money_usd, 1));
        dataList.add(new DataModel("CNY", R.drawable.country_cn, R.drawable.money_cny, 7.2415));
        dataList.add(new DataModel("EUR", R.drawable.country_eu, R.drawable.money_eur, 0.9216));

        listViewAdapter = new ListViewAdapter(dataList);
        return listViewAdapter;
    }
}
