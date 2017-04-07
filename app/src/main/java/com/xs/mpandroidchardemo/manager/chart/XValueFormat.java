package com.xs.mpandroidchardemo.manager.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by Simon on 2017/4/4.
 */
public class XValueFormat implements IAxisValueFormatter {

    DecimalFormat df=new DecimalFormat("0000");

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int hour = (int) (value / 60);
        int min = (int) (value % 60);
        String hourStr = (hour+"").length() == 1 ? "0"+hour : ""+hour;
        String minStr = (min+"").length() == 1 ? "0"+min : ""+min;
        return hourStr + ":" + minStr;
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
