package com.mv.attendance;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

public class CustomListViewAdapter extends BaseAdapter {

    List<Integer> present;
    List<Integer> absent;
    List<String> subjTitle;
    Context context;
    LayoutInflater inflter;

    public CustomListViewAdapter(Context applicationContext, List<Integer> present, List<Integer> absent, List<String> subjTitle) {
        this.context = applicationContext;
        this.present = present;
        this.absent = absent;
        this.subjTitle = subjTitle;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return subjTitle.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflter.inflate(R.layout.pie_chart_listview_element, viewGroup, false);
            //TextView tvPresent = view.findViewById(R.id.textView_Present_inListView);
            //TextView tvAbsent = view.findViewById(R.id.textView_Absent_inListView);
            TextView title = view.findViewById(R.id.textInCardViewAttendance_InListView);
            PieChart pieChart = view.findViewById(R.id.piechart_ListView);
            title.setText(subjTitle.get(i));
        Log.d("QWERT", "WHY NOT WORKINGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
            pieChart.addPieSlice(
                    new PieModel(
                            "Present",
                            present.get(i),
                            Color.parseColor("#00FB54")));
            pieChart.addPieSlice(
                    new PieModel(
                            "Absent",
                            absent.get(i),
                            Color.parseColor("#FF0000")));
            //pieChart.startAnimation();
            // To animate the pie chart
        return view;
    }
}
