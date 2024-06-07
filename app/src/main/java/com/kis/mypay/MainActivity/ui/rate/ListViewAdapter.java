package com.kis.mypay.MainActivity.ui.rate;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kis.mypay.R;
import com.kis.mypay.utils.utils;

import java.util.List;


public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    private final List<DataModel> dataList;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel dataModel = dataList.get(position);

        holder.titleTextView.setText(dataModel.getTitle());
        // 设置货币图片
        holder.countryIcon.setImageResource(dataModel.getImageResId());
        // 设置 EditText 的初始值
        holder.numberEditText.setText("0");
        // 设置货币符号
        holder.moneyIcon.setImageResource(dataModel.getMoneyIcon());

        float density = holder.itemView.getContext().getResources().getDisplayMetrics().density;
        int padding8 = (int) (8 * density);
        int padding24 = (int) (16 * density);
        if (position == selectedPosition) {
            changePaddingSmoothly(holder.itemView, padding24, padding24);
            holder.itemView.setBackgroundColor(Color.rgb(239, 239, 239));
        } else {
            holder.itemView.setPadding(padding8, padding8, padding8, padding8);
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        holder.itemView.setOnTouchListener((v, event) -> {
            v.performClick();

            // 高亮当前 Item
            int previousPosition = selectedPosition;
            selectedPosition = holder.getBindingAdapterPosition();
            notifyItemChanged(previousPosition); // 刷新之前选中的Item
            notifyItemChanged(selectedPosition); // 刷新当前选中的Item

            // 聚焦输入框并弹出键盘
            utils.requestEditTextFocus(holder.itemView.getContext(), v.findViewById(R.id.numberEditText));
            return false;
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
