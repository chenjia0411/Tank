package com.msb.tank;

import java.io.IOException;
import java.util.Properties;

/**
 * @Auther: chenjia
 * @Date: 2023/10/18 - 10 - 18 - 14:21
 * @Description: com.msb.tank
 * @version: 1.0
 */
public class PropertyMgr { //引用配置文件
    private static Properties properties =new Properties();

    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key){
        return (String)properties.get(key);
    }
}
