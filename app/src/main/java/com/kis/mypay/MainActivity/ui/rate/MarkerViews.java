package com.kis.mypay.MainActivity.ui.rate;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.kis.mypay.R;

import java.util.ArrayList;

public class MarkerViews extends MarkerView {
    private TextView tvContent;//自己定义的xmL
    LineChart lineChart;//图表控件
    ArrayList<String> xvalue;//X轴数据源
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MarkerViews(Context context, int layoutResource, LineChart lineChart, ArrayList<String> xvalue) {
        super(context,layoutResource);
        tvContent = (TextView) findViewById(R.id.tvContent1);
        this.lineChart=lineChart;
        this.xvalue=xvalue;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {//重写refreshContent方法（注意，在  //MPAndroidChart早期版本里这里有三个参数，这里有两个，我这是MPAndroidChart 3.0.2版本
        //下面这里是关键的
        LineData lineData=lineChart.getLineData();//得到已经绘制成型的折线图的数据
        LineDataSet set=(LineDataSet)lineData.getDataSetByIndex(0);//获取第一条折线图Y轴数据
        LineDataSet set1=(LineDataSet)lineData.getDataSetByIndex(1);//获取第二条折线图Y轴数据

        int DataSetIndex=highlight.getDataSetIndex();//获取点击的是哪条折线上的交叉点，0就是第一条，以此类推
        int index;
        if (DataSetIndex==0){
            index= set.getEntryIndex(e);//根据点击的该条折线的点，获取当前Y轴数据对应的index值，
        }else {
            index= set1.getEntryIndex(e);//根据点击的该条折线的点，获取当前Y轴数据对应的index值，
        }
        //根据index值，分别获取当前X轴上对应的两条折线的Y轴的值
        // Log.i("x,y轴","/"+xvalue.get(index)+"/"+e.getY());

        tvContent.setText("时间："+xvalue.get(index)+"\n利率："+e.getY());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());

    }
}
