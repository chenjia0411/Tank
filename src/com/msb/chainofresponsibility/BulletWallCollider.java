package com.msb.chainofresponsibility;

import com.msb.abstracts.AbstractGameObject;
import com.msb.tank.Bullet;
import com.msb.tank.Wall;

/**
 * @Auther: chenjia
 * @Date: 2023/10/22 - 10 - 22 - 1:10
 * @Description: com.msb.chainofresponsibility
 * @version: 1.0
 */
public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Bullet&&go2 instanceof Wall){
            Bullet b = (Bullet)go1;
            Wall w = (Wall)go2;
            if (b.islive()){
                if (b.getRect().intersects(w.getRect())){
                    b.die();
                    return false;
                }
            }
        }else if (go1 instanceof Wall&&go2 instanceof Bullet){
            return this.collide(go2,go1);
        }
        return true;
    }

}
