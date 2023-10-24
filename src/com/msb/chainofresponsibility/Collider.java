package com.msb.chainofresponsibility;

import com.msb.abstracts.AbstractGameObject;

import java.io.Serializable;

/**
 * @Auther: chenjia
 * @Date: 2023/10/22 - 10 - 22 - 0:08
 * @Description: com.msb.chainofresponsibility
 * @version: 1.0
 */
public interface Collider extends Serializable { //碰撞检测
    //返回true继续，返回false终止
    public abstract boolean collide(AbstractGameObject go1,AbstractGameObject go2);

}
