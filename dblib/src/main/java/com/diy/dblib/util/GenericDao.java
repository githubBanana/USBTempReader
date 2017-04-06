package com.diy.dblib.util;

import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0 <通用数据库操作方法>
 * @author: Xs
 * @date: 2016-03-23 21:40
 */
public interface GenericDao<T, PK extends Serializable> {

    // This is a convenience method for creating an item in the database if it does not exist.
    Dao.CreateOrUpdateStatus createOrUpdate(T t);

    void createOrUpdate(List<T> t) throws SQLException;

    // Retrieves an object associated with a specific ID.
    T queryForId(PK id);



    // Query for all of the items in the object table.
    List<T> queryForAll();

    // Query for all of the items in the object table.
    List<T> queryForAllOrderBy(String columnName, boolean ascending);

    // Query for the items in the object table that match a simple where with a
    // single field = value type of WHERE clause.
    List<T> queryForEq(String property, Object value);


    // Query for the rows in the database that matches all of the field to value
    // entries from the map passed in.
    List<T> queryForFieldValues(Map<String, Object> properties);

    // Query for the rows in the database that matches all of the field to value
    // entries from the map passed in.
    T queryForFieldValuesAndFirst(Map<String, Object> properties);

    T queryForFieldValuesAndFirstOrderBy(Map<String, Object> properties,
                                         String columnName, boolean ascending);

    // Delete the database row corresponding to the id from the data parameter.
    int delete(T object);

    // Delete an object from the database that has an id.
    int deleteById(PK id);

    //Delete all
    int deleteAll();

    //Delete by FieldValus
    int deleteByFieldValues(Map<String, Object> properties);

    //Update by FieldValue
    int updateByFieldValues(Map<String, Object> propertiesOld, Map<String, Object> propertiesNew);

    // Add a BETWEEN clause so the column must be between the low and high
    // parameters.
    List<T> between(String columnName, Object low, Object high);

    List<T> between(Map<String, Object> properties, String columnName,
                    Object low, Object high);

    List<T> ltAndGt(Map<String, Object> properties, String columnName,
                    Object low, Object high, String orderColumnName,
                    boolean ascending);

    T queryForOrderByFirst(Map<String, Object> properties, String columnName,
                           Object low, Object high, String orderColumnName, boolean ascending);

    // '<' clause so the column must be less-than the value.
    List<T> lt(String columnName, Object value);

    // Add a '<>' clause so the column must be not-equal-to the value.
    List<T> ne(String columnName, Object value);

    // Add a '<=' clause so the column must be less-than or equals-to the value.
    List<T> le(String columnName, Object value);

    // Add a LIKE clause so the column must mach the value using '%' patterns.
    List<T> like(String columnName, Object value);

    // Add a '>=' clause so the column must be greater-than or equals-to the
    // value.
    List<T> ge(String columnName, Object value);

    // Add a '>' clause so the column must be greater-than the value.
    List<T> gt(String columnName, Object value);

    // Add a IN clause so the column must be equal-to one of the objects passed
    // in.
    List<T> in(String columnName, Object... objects);

    // Same as in(String, Object...) except with a NOT IN clause.
    List<T> notIn(String columnName, Object... objects);
}
