package com.kis.mypay.MainActivity.ui.rate;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kis.mypay.R;
import com.kis.mypay.utils.utils;

import java.util.List;


public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    private final List<DataModel> dataList;

    private double currentDollar = 1;

    private int selectedPosition = RecyclerView.NO_POSITION;

    public ListViewAdapter(List<DataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel data = dataList.get(position);

        holder.titleTextView.setText(data.getTitle());
        // 设置货币图片
        holder.countryIcon.setImageResource(data.getImageResId());
        // 设置 EditText 的初始值
        holder.numberEditText.setText("0");
        // 设置货币符号
        holder.moneyIcon.setImageResource(data.getMoneyIcon());

        // 设置 Item 的背景颜色
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.rgb(239, 239, 239));
        } else {
            holder.itemView.setBackgroundColor(Color.rgb(253, 251, 254));
        }
        // 设置 Item 的点击事件
        holder.itemView.setOnTouchListener((v, event) -> {
            focus(holder);
            utils.requestEditTextFocus(holder.itemView.getContext(), v.findViewById(R.id.numberEditText));
            return false;
        });
        holder.numberEditText.setOnTouchListener((v, event) -> {
            focus(holder);
            return false;
        });
        holder.numberEditText.setText(String.format("%.2f", currentDollar * data.getRate()));
        // 设置 EditText 的输入监听
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = () -> {
            currentDollar = Double.parseDouble(holder.numberEditText.getText().toString()) * data.getRate();
            for (int i = 0; i < getItemCount(); i++) {
                if (i != selectedPosition) {
                    notifyItemChanged(i);
                }
            }
        };
        holder.numberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (position == selectedPosition) {
                    // 移除上一次的Runnable
                    handler.removeCallbacks(runnable);
                    // 添加新的Runnable，延迟300毫秒执行
                    handler.postDelayed(runnable, 100);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void changePaddingSmoothly(final View view, final int newPaddingTop, final int newPaddingBottom) {
        int currentPaddingTop = view.getPaddingTop();
        int currentPaddingBottom = view.getPaddingBottom();

        ValueAnimator animatorTop = ValueAnimator.ofInt(currentPaddingTop, newPaddingTop);
        animatorTop.addUpdateListener(animation -> view.setPadding(view.getPaddingLeft(), (int) animation.getAnimatedValue(), view.getPaddingRight(), view.getPaddingBottom()));

        ValueAnimator animatorBottom = ValueAnimator.ofInt(currentPaddingBottom, newPaddingBottom);
        animatorBottom.addUpdateListener(animation -> view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), (int) animation.getAnimatedValue()));

        animatorTop.setDuration(300); // 设置动画持续时间
        animatorTop.start();

        animatorBottom.setDuration(300); // 设置动画持续时间
        animatorBottom.start();
    }

    // 初始化当前选中的Item
    public void resetSelectedPosition() {
        notifyItemChanged(selectedPosition);
        selectedPosition = RecyclerView.NO_POSITION;
    }

    public void focus(ViewHolder holder) {
        int previousPosition = selectedPosition;
        selectedPosition = holder.getBindingAdapterPosition();
        notifyItemChanged(previousPosition); // 刷新之前选中的Item
        notifyItemChanged(selectedPosition); // 刷新当前选中的Item

        currentDollar = Double.parseDouble(holder.numberEditText.getText().toString());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView countryIcon;
        public final TextView titleTextView;
        public final EditText numberEditText;
        public final ImageView moneyIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            countryIcon = itemView.findViewById(R.id.countryIcon);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            numberEditText = itemView.findViewById(R.id.numberEditText);
            moneyIcon = itemView.findViewById(R.id.money_icon);
        }
    }
}
