package com.basic.hive.util;

import org.junit.Test;

import java.sql.ResultSet;

/**
 * locate com.basic.hive.util
 * Created by 79875 on 2017/5/24.
 */
public class HiveUtilTest {
    private HiveUtil hiveUtil=new HiveUtil();

    @Test
    public void excuteQuery() throws Exception {
        ResultSet resultSet = hiveUtil.excuteQuery("select * from psn1");
        hiveUtil.printResultSet(resultSet);
    }

    @Test
    public void excuteHql() throws Exception {
        //drop and create table
        String tableName = "testHiveDriverTable";// "temp2";
        String DropTableSql="drop table if exists " + tableName;
        boolean b = hiveUtil.excuteHql(DropTableSql);
        System.out.println(b);
    }

}
