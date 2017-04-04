package com.xs.mpandroidchardemo;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Administrator on 2017/4/2.
 */
public class YValueFormat implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return value+"℃";
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
