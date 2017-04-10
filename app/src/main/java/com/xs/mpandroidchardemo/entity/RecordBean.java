package com.xs.mpandroidchardemo.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.w3c.dom.ProcessingInstruction;

import java.io.Serializable;

/**
 * @Description
 * @Author xs.lin
 * @Date 2017/4/5 11:50
 */

@DatabaseTable(tableName = RecordBean.TABLE_NAME)
public class RecordBean implements Serializable{

    public static final String TABLE_NAME = "tb_recordbean";

    public static final String ID = "id";//本地递增ID
    public static final String TIME = "time";
    public static final String VALUE = "value";
    public static final String FAHRENHEIT = "fahrenheitValue";//对应华氏度值
    public static final String DMIN = "dmin";//今天走过的分钟数

    @DatabaseField(generatedId = true,columnName = ID)
    private int id;
    @DatabaseField(columnName = TIME)
    private String time ;
    @DatabaseField(columnName = VALUE)
    private float value;
    @DatabaseField(columnName = FAHRENHEIT)
    private float fahrenheitValue;
    @DatabaseField(columnName = DMIN)
    private int min;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
        this.fahrenheitValue = 1.8f * value + 32;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public float getFahrenheitValue() {
        return fahrenheitValue;
    }

    public void setFahrenheitValue(float fahrenheitValue) {
        this.fahrenheitValue = fahrenheitValue;
    }


    @Override
    public String toString() {
        return "RecordBean{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", value=" + value +
                ", fahrenheitValue=" + fahrenheitValue +
                ", min=" + min +
                '}';
    }
}
