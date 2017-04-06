package com.xs.mpandroidchardemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntegerRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.xs.mpandroidchardemo.entity.AppDatabaseCache;
import com.xs.mpandroidchardemo.entity.RecordBean;
import com.xs.mpandroidchardemo.utils.TimeHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartActivity extends AppCompatActivity {

    @Bind(R.id.chart1)
    LineChart mChart;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    private static final String DAY = "day";

    public static void start(Activity activity,String day) {
        Intent intent = new Intent();
        intent.putExtra(DAY,day);
        intent.setComponent(new ComponentName(activity,ChartActivity.class));
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initLineChart();

        String day = getIntent().getStringExtra(DAY);
        List<RecordBean> list = AppDatabaseCache.getcache(this).queryRecordByTime(day);
        new MyThread(list).start();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initLineChart() {
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(false);
        // 不显示数据描述
        mChart.getDescription().setEnabled(false);


        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineWidth(2f);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(24 * 60);
        xAxis.setValueFormatter(new XValueFormat());
        xAxis.setAxisLineColor(R.color.colorPrimary);
        xAxis.setGranularity(2);

        Matrix matrix = new Matrix();
        matrix.postScale(120f, 1f);
//        matrix.set(mChart.getViewPortHandler().getMatrixTouch());
//        matrix.setTranslate(-100,1);
         //在图表动画显示之前进行缩放
        mChart.getViewPortHandler().refresh(matrix, mChart, false);
//         x轴执行动画
        mChart.animateX(1000);

        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisMaximum(42f);
        yAxis.setAxisMinimum(34f);
        yAxis.setLabelCount(5);
        yAxis.setValueFormatter(new YValueFormat());

        mChart.getAxisRight().setEnabled(false);


    }

    private void setData(ArrayList<Entry> values) {

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "");
            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 0f, 0.5f);
            set1.enableDashedHighlightLine(10f, 0f, 0.5f);
            set1.setColor(this.getResources().getColor(R.color.colorBlue));
            set1.setCircleColor(this.getResources().getColor(R.color.colorBlue));

            set1.setLabel("温度值");
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);

        }
    }

    public void myTouch(View view) {
        test(TimeHelper.getBetweenMinutes());
    }


    class MyThread extends Thread {

        private List<RecordBean> recordBeanList;
        public MyThread(List<RecordBean> recordBeanList) {
            this.recordBeanList = recordBeanList;
        }

        ArrayList<Entry> values ;
        boolean isFirstSetData = true;
        @Override
        public void run() {
            if (recordBeanList == null || recordBeanList.size() == 0) {
            } else {
                values = new ArrayList<>();
                for (RecordBean rb :
                        recordBeanList) {
                    values.add(new Entry(rb.getMin(),rb.getValue()));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setData(values);
                        if (isFirstSetData) {
                            test(recordBeanList.get(0).getMin());
                            isFirstSetData = false;
                        }
                    }
                });
            }
        }
    }


    private void test(int min) {
        mChart.getViewPortHandler().getMatrixTouch().postTranslate(-mChart.getWidth() / 6.6f / 2 * min,1);
//         Y轴执行动画
        mChart.animateY(1000);
        mChart.invalidate();
    }
}
