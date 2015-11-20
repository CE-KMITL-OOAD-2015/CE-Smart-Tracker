package com.kaleido.cesmarttracker.adapter;

/**
 * Created by monkiyes on 11/1/2015 AD.
 */

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.kaleido.cesmarttracker.R;

import java.util.ArrayList;

public class TeacherListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] itemid;
    protected HorizontalBarChart mChart;

    public TeacherListAdapter(Activity context, String[] itemname, Integer[] imgid, String[] itemid) {
        super(context, R.layout.progress4, itemname);
        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.itemid = itemid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.students_list, null, true);

        TextView stdName = (TextView) rowView.findViewById(R.id.student_name);
        TextView stdId = (TextView) rowView.findViewById(R.id.student_id);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        stdName.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        stdId.setText(itemid[position]);

        mChart = (HorizontalBarChart) rowView.findViewById(R.id.chart1);
        // mChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        mChart.setDrawBarShadow(false);

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(33.0f, 0));

        BarDataSet dataset = new BarDataSet(entries, "");
        dataset.setColor(Color.parseColor("#d11141"));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("");

        BarData bardata = new BarData(labels, dataset);
        mChart.setData(bardata);
        bardata.setValueTextSize(20f);
        mChart.setDescription("");

        //mChart.setDrawValueAboveBar(true);

        //mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        //mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart.setDrawGridBackground(false);

        Legend legend = mChart.getLegend();
        legend.setEnabled(false);

        YAxis topAxis = mChart.getAxisLeft();
        topAxis.setDrawLabels(false);
        topAxis.setDrawGridLines(false);
        topAxis.setDrawAxisLine(false);

        YAxis bottomAxis = mChart.getAxisRight();
        bottomAxis.setDrawLabels(false);
        bottomAxis.setDrawGridLines(false);
        bottomAxis.setDrawAxisLine(false);

        XAxis rightAxis = mChart.getXAxis();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);

        mChart.setPadding(-1, -1, -1, -1);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        // mChart.setDrawYLabels(false);
    //    XAxis xl = mChart.getXAxis();
    //    xl.setPosition(XAxis.XAxisPosition.BOTTOM);
    //    xl.setDrawAxisLine(true);
    //    xl.setDrawGridLines(true);
    //    xl.setGridLineWidth(0.3f);

    //    YAxis yl = mChart.getAxisLeft();
    //    yl.setDrawAxisLine(true);
    //    yl.setDrawGridLines(true);
    //    yl.setGridLineWidth(0.3f);
//        yl.setInverted(true);

   //     YAxis yr = mChart.getAxisRight();
   //     yr.setDrawAxisLine(true);
   //     yr.setDrawGridLines(false);
//        yr.setInverted(true);

        mChart.animateY(2500);

        return rowView;
    }
/*
    private void setData(int count, float range) {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add(mMonths[0]);
        yVals1.add(new BarEntry(86.0f, 0));

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet 1");

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        mChart.setData(data);
    }
    */

}
