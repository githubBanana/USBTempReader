package com.xs.mpandroidchardemo.entity;

import android.content.Context;
import android.util.Log;

import com.diy.dblib.util.DatabaseHelper;
import com.diy.dblib.util.GenericDao;
import com.diy.dblib.util.GenericDaoImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0 <数据库缓存>
 * @author: Xs
 * @date: 2016-03-26 14:49
 * @email Xs.lin@foxmail.com
 */
public class AppDatabaseCache {
    private static final String TAG = "AppDatabaseCache";

    private static AppDatabaseCache instance;
    private Context mContext;

    private DatabaseHelper mDataHelper;
    private GenericDao<RecordBean,Integer> recordBeanGenericDao;

    /**
     * 单例获取
     * @param context
     * @return
     */
    public static AppDatabaseCache getcache(Context context) {
        if (instance == null) {
            synchronized (AppDatabaseCache.class) {
                if(instance == null) {
                    instance = new AppDatabaseCache(context);
                }
            }
        }
        return instance;
    }

    private AppDatabaseCache(Context context){
        this.mContext = context;
        mDataHelper = AppDatabaseHelper.getHelper(mContext);
        recordBeanGenericDao = new GenericDaoImpl<>(mContext,mDataHelper,RecordBean.class);
    }

    public List<RecordBean> queryAllRecord() {
        return recordBeanGenericDao.queryForAll();
    }

    public List<RecordBean> queryRecordByTime(String day) {
        Map<String,Object> map = new HashMap<>();
        map.put(RecordBean.TIME,day);
        return recordBeanGenericDao.queryForFieldValues(map);
    }

    public void insertRecord(RecordBean recordBean) {
        recordBeanGenericDao.createOrUpdate(recordBean);
    }

    public void deleteRecord(String day) {
        Map<String,Object> map = new HashMap<>();
        map.put(RecordBean.TIME,day);
        recordBeanGenericDao.deleteByFieldValues(map);
    }

}
