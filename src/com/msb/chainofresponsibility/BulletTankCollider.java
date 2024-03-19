package com.msb.chainofresponsibility;

import com.msb.abstracts.AbstractGameObject;
import com.msb.net.Client;
import com.msb.net.TankDieMsg;
import com.msb.tank.Bullet;
import com.msb.tank.Tank;

import java.awt.*;

/**
 * @Auther: chenjia
 * @Date: 2023/10/22 - 10 - 22 - 0:20
 * @Description: com.msb.chainofresponsibility
 * @version: 1.0
 */
public class BulletTankCollider implements Collider {

    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Bullet && go2 instanceof Tank) {
            Bullet b = (Bullet) go1;
            Tank t = (Tank) go2;
            if (!t.isLive() || !b.islive()) {
                return false;
            }
            if (b.getGroup() == t.getGroup()) {
                return true;
            }
            //坦克方块
            Rectangle recttank = t.getRect();
            //判断方块是否相交
            if (b.getRect().intersects(recttank)) {
                b.die();   //相交子弹消失
                t.die();   //相交坦克死亡
                Client.INSTANCE.send(new TankDieMsg(t.getId(),b.getId()));
                return false;
            }
        } else if (go1 instanceof Tank && go2 instanceof Bullet) {
            return this.collide(go2, go1);
        }
        return true;
    }

}
