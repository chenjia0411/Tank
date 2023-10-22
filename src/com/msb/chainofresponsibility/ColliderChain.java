package com.msb.chainofresponsibility;

import com.msb.abstracts.AbstractGameObject;
import com.msb.tank.PropertyMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chenjia
 * @Date: 2023/10/22 - 10 - 22 - 22:32
 * @Description: com.msb.chainofresponsibility
 * @version: 1.0
 */
public class ColliderChain implements Collider{
    private List<Collider> colliders;

    public ColliderChain() {
        initColliders(); //碰撞器初始化
    }

    private void initColliders() {
        colliders=new ArrayList<>();
        String[] collidersNames = PropertyMgr.get("colliders").split(","); //得到的属性值按 “,” 进行分割
        try {
            for (String name: collidersNames) {
                //往碰撞容器中添加对象
                Class<?> aClass = Class.forName("com.msb.chainofresponsibility." + name);
                Collider c = (Collider) aClass.getConstructor().newInstance();
                colliders.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean collide(AbstractGameObject go1,AbstractGameObject go2){
        for (Collider collider:colliders){
            if (!collider.collide(go1,go2)){
                return false;
            }
        }
        return true;
    }
}
