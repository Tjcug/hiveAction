package com.basic.hive.custom;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * locate com.basic.hive.custom
 * Created by 79875 on 2017/5/24.
 * add jar /root/TJ/original-hiveAction-1.0-SNAPSHOT.jar; 添加jar包到hive客户端
 * create temporary function sentensive as 'com.basic.hive.custom.sentensive'; 创建自定义函数
 * select id,sentensive(name),likes,address from psn1; 使用自定义函数
 * drop temporary function sentensive 销毁自定义函数
 *
 * 自定义UDF函数
 */
public class sentensive extends UDF{
    public Text evaluate(final Text s){
        if(s==null){
            return null;
        }
        String str=s.toString();
        str=str.substring(0,1)+"***"+str.substring(str.length()-1,str.length());
        return new Text(str);
    }
}
