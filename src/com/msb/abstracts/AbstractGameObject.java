package com.msb.abstracts;

import java.awt.*;

/**
 * @Auther: chenjia
 * @Date: 2023/10/19 - 10 - 19 - 23:38
 * @Description: com.msb.abstracts
 * @version: 1.0
 */
public abstract class AbstractGameObject { //游戏物体的抽象方法


    public abstract void paint(Graphics p);

    public abstract boolean islive();
}
