package com.xs.mpandroidchardemo.entity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.diy.dblib.util.DatabaseHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

/**
 * @version V1.0 <数据库Helper>
 * @author: Xs
 * @date: 2016-03-26 14:49
 * @email Xs.lin@foxmail.com
 */
public class AppDatabaseHelper extends DatabaseHelper {
    private static final String TAG = "AppDatabaseHelper";

    private static final String DB_NAME = "usbtempreader.db";
    private static int DB_VERSION = 1;

    private AppDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static AppDatabaseHelper instance;
    /**
     * 单例获取该Helper
     * @param context
     * @return
     */
    public static AppDatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (AppDatabaseHelper.class) {
                if (instance == null)
                    instance = new AppDatabaseHelper(context);
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RecordBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,RecordBean.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
