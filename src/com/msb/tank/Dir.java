package com.msb.tank;

import java.util.Random;

/**
 * @Auther: chenjia
 * @Date: 2023/10/7 - 10 - 07 - 1:03
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public enum Dir {
    L,U,R,D;

    private static Random r =new Random();

    public static Dir randomDir(){
        return values()[r.nextInt(values().length)];
    }
}
