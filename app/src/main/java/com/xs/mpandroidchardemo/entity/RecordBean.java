package com.xs.mpandroidchardemo.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
    public static final String DMIN = "dmin";//今天走过的分钟数

    @DatabaseField(generatedId = true,columnName = ID)
    private int id;
    @DatabaseField(columnName = TIME)
    private String time ;
    @DatabaseField(columnName = VALUE)
    private float value;
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
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "RecordBean{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", value=" + value +
                ", min=" + min +
                '}';
    }
}
