package com.kis.mypay.MainActivity.ui.rate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.*;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.kis.mypay.R;
import com.kis.mypay.databinding.FragmentRateTrendBinding;

import java.util.ArrayList;

public class TrendFragment extends Fragment {
    private FragmentRateTrendBinding binding;

    private LineChart lineChart;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRateTrendBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        // 创建描述信息
        Description description = new Description();
        description.setText("");
        description.setTextColor(R.color.violet_500);
        description.setTextSize(20);
        lineChart.setDescription(description);// 设置图表描述信息
        lineChart.setNoDataText("没有数据哦");// 没有数据时显示的文字
        lineChart.setNoDataTextColor(R.color.green_400);// 没有数据时显示文字的颜色
        lineChart.setDrawGridBackground(false);// chart 绘图区后面的背景矩形将绘制
        lineChart.setDrawBorders(false);// 禁止绘制图表边框的线

        initDate();
        initXY();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initDate() {
        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();

        values1.add(new Entry(1, 10));
        values1.add(new Entry(2, 15));
        values1.add(new Entry(3, 20));
        values1.add(new Entry(4, 5));
        values1.add(new Entry(5, 30));
        values1.add(new Entry(6, 15));
        values1.add(new Entry(7, 6));

        values2.add(new Entry(1, 20));
        values2.add(new Entry(2, 15));
        values2.add(new Entry(3, 13));
        values2.add(new Entry(4, 8));
        values2.add(new Entry(5, 9));
        values2.add(new Entry(6, 12));
        values2.add(new Entry(7, 15));

        // LineDataSet每一个对象就是一条连接线
        LineDataSet set1;
        LineDataSet set2;
        // 设置图例
        // 获取图例
        Legend legend = lineChart.getLegend();
        // 是否开启设置图例
        legend.setEnabled(true);
        // 设置图例文字大小
        legend.setTextSize(50f);
        // 设置图例文字颜色
        legend.setTextColor(R.color.blue_400);
        // 如果设置为true，那么当图例过多或者过长一行显示不下的时候就会自适应换行
        legend.setWordWrapEnabled(true);
        // 设置表格的最大相对大小，默认为0.95f(95%)
        legend.setMaxSizePercent(0.95f);
        // 设置图例位置
        // legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        // 设置图例形状:如SQUARE、CIRCLE、LINE、DEFAULT
        legend.setForm(Legend.LegendForm.CIRCLE);
        // 设置图例XY方向的间隔宽度
        legend.setXEntrySpace(20f);
        legend.setYEntrySpace(20f);
        // 设置图例标签到文字之间的距离
        legend.setFormToTextSpace(20f);
        // 设置文本包装
        legend.setWordWrapEnabled(true);
        // 判断图表中原来是否有数据
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            // 获取数据1
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
            set2.setValues(values2);
            // 刷新数据
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // 设置数据1  参数1：数据源 参数2：图例名称setValueFormatter
            set1 = new LineDataSet(values1, "美元");
            set1.setColor(R.color.green_500);// 两个点之间的距离连接线
            set1.setCircleColor(R.color.gray_200);// 数据展示的圆点颜色
            set1.setLineWidth(3f);// 设置线宽
            set1.setCircleRadius(3f);// 设置焦点圆心的大小
            set1.enableDashedHighlightLine(2f, 5f, 0f);// 点击后的高亮线的显示样式
            set1.setHighlightLineWidth(1f);// 设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(true);// 是否禁用点击高亮线
            set1.setHighLightColor(R.color.yellow_200);// 设置点击交点后显示交高亮线的颜色
            set1.setValueTextSize(9f);// 设置显示值的文字大小
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);// 直线 变成 曲线
            set1.setDrawValues(false);  // 不显示数值

            // set1.setValueFormatter(new LargeValueFormatter("℃"));//显示数值的样式
            // 阴影填充
            // set1.setDrawFilled(true);//设置禁用范围背景填充 阴影
            // set1.setFillDrawable(getResources().getDrawable(R.drawable.line_gradient_bg_shape));
            // 设置数据2
            set2 = new LineDataSet(values2, "人民币");
            set2.setColor(R.color.red_500);// 两个点之间的距离连接线
            set2.setCircleColor(R.color.white);// 数据展示的圆点颜色
            set2.setLineWidth(3f);// 设置线宽
            set2.setCircleRadius(3f);// 设置焦点圆心的大小
            set2.enableDashedHighlightLine(2f, 5f, 0f);// 点击后的高亮线的显示样式
            set2.setHighlightLineWidth(1f);// 设置点击交点后显示高亮线宽
            set2.setHighlightEnabled(true);// 是否禁用点击高亮线
            set2.setHighLightColor(R.color.yellow_300);// 设置点击交点后显示交高亮线的颜色
            set2.setValueTextSize(9f);// 设置显示值的文字大小
            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);// 直线 变成 曲线
            set2.setDrawValues(false);  // 不显示数值

            // 阴影填充
            // set2.setDrawFilled(true);//设置禁用范围背景填充 阴影
            // set2.setFillDrawable(getResources().getDrawable(R.drawable.line_gradient_bg_shape2));

            // 保存LineDataSet集合
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // 添加数据集
            dataSets.add(set2);// 添加数据集
            // 创建LineData对象 属于LineChart折线图的数据集合
            LineData data = new LineData(dataSets);
            // 添加到图表中
            lineChart.setData(data);
            // 绘制图表
            lineChart.invalidate();
        }
    }

    private void initXY() {
        ArrayList<String> xvalue = new ArrayList<>();// x轴时间
        xvalue.add("10-1");// 当然这样可以把X轴的数据随便设置成啥都行。
        xvalue.add("10-2");
        xvalue.add("10-3");
        xvalue.add("10-4");
        xvalue.add("10-5");
        xvalue.add("10-6");
        xvalue.add("10-7");

        // 获取此图表的x轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(true);// 设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(true);// 是否绘制轴线
        xAxis.setDrawGridLines(true);// 设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);// 绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置x轴的显示位置
        xAxis.setGranularity(1);// 让x轴上自定义的值和折线上相对应
        xAxis.setAxisLineColor(R.color.white);// 设置横轴线的颜色
        xAxis.setTextColor(R.color.white);// 设置横轴字体颜色

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xvalue.get((int) value - 1);
            }
        });

         // 获取右边的轴线
        YAxis rightAxis = lineChart.getAxisRight();
        // 设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        // 获取左边的轴线
        YAxis leftAxis = lineChart.getAxisLeft();
        // 设置网格线为虚线效果
        leftAxis.enableGridDashedLine(0f, 0f, 0f);
        // 是否绘制0所在的网格线
        leftAxis.setDrawZeroLine(false);
        leftAxis.setEnabled(true);// 设置轴启用或禁用 如果禁用以下的设置全部不生效
        leftAxis.setDrawAxisLine(true);// 是否绘制轴线
        leftAxis.setDrawGridLines(true);// 设置x轴上每个点对应的线
        leftAxis.setDrawLabels(true);// 绘制标签  指x轴上的对应数值
        leftAxis.setGranularity(1);// 让y轴上自定义的值和折线上相对应
        leftAxis.setAxisLineColor(R.color.white);// 设置纵轴线的颜色
        leftAxis.setTextColor(R.color.white);    // 设置纵轴字体颜色

        lineChart.setTouchEnabled(true); // 设置是否可以触摸
        lineChart.setDragEnabled(false); // 是否可以拖拽
        lineChart.setScaleEnabled(false);// 是否可以缩放 x和y轴, 默认是true
        lineChart.setScaleXEnabled(false); // 是否可以缩放 仅x轴
        lineChart.setScaleYEnabled(false); // 是否可以缩放 仅y轴
        lineChart.setPinchZoom(false);  // 设置x轴和y轴能否同时缩放。默认是否
        lineChart.setDoubleTapToZoomEnabled(false);// 设置是否可以通过双击屏幕放大图表。默认是true
        lineChart.setHighlightPerDragEnabled(true);// 能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        lineChart.setDragDecelerationEnabled(false);// 拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        lineChart.setDragDecelerationFrictionCoef(0.99f);// 与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。
        lineChart.getXAxis().setDrawGridLines(false);// 设置网格线
        lineChart.getAxisLeft().setDrawGridLines(false);// 去掉左右边线
        lineChart.getAxisRight().setDrawGridLines(false);// 去掉左右边线


        final MarkerViews mv = new MarkerViews(getContext(), R.layout.fragment_rate_trend_markview, lineChart, xvalue);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);

    }
}
