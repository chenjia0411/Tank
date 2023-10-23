package com.msb.chainofresponsibility;

import com.msb.abstracts.AbstractGameObject;
import com.msb.tank.Bullet;
import com.msb.tank.Tank;
import com.msb.tank.Wall;

/**
 * @Auther: chenjia
 * @Date: 2023/10/22 - 10 - 22 - 1:10
 * @Description: com.msb.chainofresponsibility
 * @version: 1.0
 */
public class TankTankCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Tank&&go2 instanceof Tank){
            Tank t1 = (Tank) go1;
            Tank t2 = (Tank) go2;
            if (t1!=t2&&t1.islive()&&t2.isLive()){
                if (t1.getRect().intersects(t2.getRect())){
                    t1.back();
                    t2.back();
                }
            }
        }
        return true;
    }

}
