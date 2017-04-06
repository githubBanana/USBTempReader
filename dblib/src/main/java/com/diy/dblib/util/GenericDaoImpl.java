package com.diy.dblib.util;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0 <数据库操作方法具体调用接口>
 * @author: Xs
 * @date: 2016-03-23 21:54
 */
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
    private Dao<T, PK> daoOpe;

    @SuppressWarnings("rawtypes")
    public GenericDaoImpl(Context context, DatabaseHelper helper, Class clazz) {
        try {
            daoOpe = helper.getDao(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加对象
     * @param t
     * @return
     */
    public Dao.CreateOrUpdateStatus createOrUpdate(T t) {
		/*
		 * //事务操作
		 * TransactionManager.callInTransaction(helper.getConnectionSource(),
		 * new Callable<Void>() {
		 *
		 * @Override public Void call() throws Exception { return null; } });
		 */
        try {
            return daoOpe.createOrUpdate(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 添加一个列表的对象
     * @param t
     * @throws SQLException
     */
    @Override
    public void createOrUpdate(List<T> t) throws SQLException {
        for (int i = 0; i < t.size(); i++) {
            daoOpe.createOrUpdate(t.get(i));
        }
    }

    /**
     * 根据ID获取对象
     * @param id
     * @return
     */
    public T queryForId(PK id) {
        try {
            return daoOpe.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取所有对象
     * @return
     */
    @Override
    public List<T> queryForAll() {
        List<T> tList = null;
        try {
            tList = daoOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tList;
    }

    @Override
    public List<T> queryForAllOrderBy(String columnName, boolean ascending) {
        try {
            return daoOpe.queryBuilder().orderBy(columnName, ascending).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据field字段 获取List对象
     * @param property
     * @param value
     * @return
     */
    @Override
    public List<T> queryForEq(String property, Object value) {
        try {
            return daoOpe.queryForEq(property, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据properties(多条件限制查询) 获取对应的List对象
     * @param properties
     * @return
     */
    @Override
    public List<T> queryForFieldValues(Map<String, Object> properties) {
        try {
            return daoOpe.queryForFieldValues(properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据properties(多条件限制查询：与操作) 获取对应的第一个对象
     * @param properties
     * @return
     */
    @Override
    public T queryForFieldValuesAndFirst(Map<String, Object> properties) {
        try {
            Where<T, PK> where = daoOpe.queryBuilder().where();
            boolean isAnd = true;
            for (String key : properties.keySet()) {
                if (!isAnd) {
                    where = where.and();
                }
                isAnd = false;
                where = where.eq(key, properties.get(key));
            }
            return where.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T queryForFieldValuesAndFirstOrderBy(Map<String, Object> properties,
                                                String columnName, boolean ascending) {
        try {
            Where<T, PK> where = daoOpe.queryBuilder()
                    .orderBy(columnName, ascending).where();
            boolean isAnd = true;
            for (String key : properties.keySet()) {
                if (!isAnd) {
                    where = where.and();
                }
                isAnd = false;
                where = where.eq(key, properties.get(key));

            }
            return where.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(T object) {
        try {
            return daoOpe.delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据ID删除对象
     * @param id
     * @return
     */
    @Override
    public int deleteById(PK id) {
        try {
            return daoOpe.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除对应所有记录
     * @return
     */
    @Override
    public int deleteAll(){
        DeleteBuilder<T, PK> deleteBuilder= daoOpe.deleteBuilder();
        try {
             return deleteBuilder.delete();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据properties(多条件限制:与删除)删除对应的记录
     * @param properties
     * @return
     */
    @Override
    public int deleteByFieldValues(Map<String, Object> properties) {
        DeleteBuilder<T, PK> deleteBuilder= daoOpe.deleteBuilder();
        Where<T, PK> where = deleteBuilder.where();
        try {
            boolean isAnd = true;
            for (String key : properties.keySet()) {
                if (!isAnd) {
                    where = where.and();
                }
                isAnd = false;
                where = where.eq(key, properties.get(key));
            }
            return deleteBuilder.delete();
        }catch (SQLException e) {
                e.printStackTrace();
            }
        return 0;
    }

    /**
     * 根据propertiesOld筛选后 由propertiesNew更新相应的记录
     * @param propertiesOld
     * @param propertiesNew
     * @return
     */
    @Override
    public int updateByFieldValues(Map<String, Object> propertiesOld,Map<String, Object> propertiesNew) {
        UpdateBuilder<T, PK> updateBuilder= daoOpe.updateBuilder();
        Where<T, PK> whereOld = updateBuilder.where();
        try {
            boolean isAndOld = true;
            for (String key : propertiesOld.keySet()) {
                if (!isAndOld) {
                    whereOld = whereOld.and();
                }
                isAndOld = false;
                whereOld = whereOld.eq(key, propertiesOld.get(key));
            }
            for (String key : propertiesNew.keySet()) {
                updateBuilder.updateColumnValue(key, propertiesNew.get(key));
            }
            return updateBuilder.update();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public List<T> between(String columnName, Object low, Object high) {
        try {
            return daoOpe.queryBuilder().where().between(columnName, low, high)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> between(Map<String, Object> properties, String columnName,
                           Object low, Object high) {
        try {
            Where<T, PK> where = daoOpe.queryBuilder().where()
                    .between(columnName, low, high);
            for (String key : properties.keySet()) {
                where = where.and().eq(key, properties.get(key));
            }
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> ltAndGt(Map<String, Object> properties, String columnName,
                           Object low, Object high, String orderColumnName, boolean ascending) {
        try {
            Where<T, PK> where = daoOpe.queryBuilder()
                    .orderBy(orderColumnName, ascending).where();
            where.or(where.lt(columnName, low), where.gt(columnName, high));

            for (String key : properties.keySet()) {
                where = where.and().eq(key, properties.get(key));
            }
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T queryForOrderByFirst(Map<String, Object> properties,
                                  String columnName, Object low, Object high, String orderColumnName,
                                  boolean ascending) {
        try {
            Where<T, PK> where = daoOpe.queryBuilder()
                    .orderBy(orderColumnName, ascending).where()
                    .between(columnName, low, high);
            for (String key : properties.keySet()) {
                where = where.and().eq(key, properties.get(key));
            }
            return where.queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> lt(String columnName, Object value) {
        try {
            return daoOpe.queryBuilder().where().lt(columnName, value).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> ne(String columnName, Object value) {
        try {
            return daoOpe.queryBuilder().where().ne(columnName, value).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> le(String columnName, Object value) {
        try {
            return daoOpe.queryBuilder().where().le(columnName, value).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> like(String columnName, Object value) {
        try {
            return daoOpe.queryBuilder().where().like(columnName, value)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> ge(String columnName, Object value) {
        try {
            return daoOpe.queryBuilder().where().ge(columnName, value).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> gt(String columnName, Object value) {
        try {
            return daoOpe.queryBuilder().where().gt(columnName, value).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> in(String columnName, Object... objects) {
        try {
            return daoOpe.queryBuilder().where().in(columnName, objects)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> notIn(String columnName, Object... objects) {
        try {
            return daoOpe.queryBuilder().where().notIn(columnName, objects)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

