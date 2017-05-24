package com.basic.hive.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * locate com.basic.hive.util
 * Created by 79875 on 2017/5/24.
 */
public class HiveUtil {
    // 定义日志对象
    private static Logger log = LoggerFactory.getLogger(HiveUtil.class);

    private static Connection conn = null;

    public HiveUtil() {
        try {
            conn=getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 定义hive连接信息
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    // url,user,pasword要写在配置文件中，并且只加载一次
    // 静态代码块不可以抛异常。
    static {
        try {
            // 从资源文件中读取配置信息
            loadProperties("/hive.properties");
            // 注册驱动
//            String driverClass = "org.apache.hive.jdbc.HiveDriver";
            Class.forName(driver);

        } catch (Exception e) {
            log.error(e.getMessage());
            // 抛出初始化异常。
            throw new ExceptionInInitializerError(e);
        }
    }

    // handle the properties file to get the informations for connection
    private static void loadProperties(String fileName) {
        Properties props = new Properties();
        try {
            InputStream in = Object. class .getResourceAsStream( fileName );
            props.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = props.getProperty("driverClassName");
        url = props.getProperty("url");
        username = props.getProperty("username");
        password = props.getProperty("password");
    }

    // 获得数据库的连接
    public Connection getConnection() throws SQLException {
        if (conn == null)
            conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    // 释放资源的代码
    public static void release(Statement stmt, Connection conn, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
        }
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

    // 执行查询语句
    public ResultSet excuteQuery(String QueryString) {
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(QueryString);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;

    }


    //输出查询的记过
    public void printResultSet(ResultSet rs){
        if(rs==null){
            System.out.println("ResultSet is empty!");
            return;
        }
        try {
            while(rs.next()){
                for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
                    System.out.println(rs.getMetaData().getColumnName(i)+"--->"+rs.getObject(i));
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     *
     * @param hql
     * @return boolean
     * @throws SQLException
     */
    public boolean excuteHql(String hql)
            throws SQLException {
        // 创建hive数据库操作基本对象
        Statement stmt = null;
        try {
            // 获取hive数据库连接
            stmt = conn.createStatement();
            log.info("excute hql：" + hql);
            stmt.execute(hql);
            log.info("hql ：" + hql + "excute finished");
            return true;
        } catch (SQLException e) {
            log.info(e.getMessage());
            throw e;
        }
    }



}
