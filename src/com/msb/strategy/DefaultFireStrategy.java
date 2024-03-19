package com.msb.strategy;

import com.msb.net.BulletNewMsg;
import com.msb.net.Client;
import com.msb.tank.Bullet;
import com.msb.tank.Player;
import com.msb.tank.ResourceMgr;
import com.msb.tank.TankFrame;

/**
 * @Auther: chenjia
 * @Date: 2023/10/19 - 10 - 19 - 15:53
 * @Description: com.msb.strategy
 * @version: 1.0
 */
public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Player p) {
        int bx =p.getX()+ ResourceMgr.goodTankU.getWidth()/2-ResourceMgr.bulletU.getWidth()/2;
        int by=p.getY()+ResourceMgr.goodTankU.getHeight()/2-ResourceMgr.bulletU.getHeight()/2;

        //把子弹加入到物体中
        Bullet b = new Bullet(p.getId(), bx, by, p.getDir(), p.getGroup());
        TankFrame.INSTANCE.getGm().add(b);

        //发送子弹的消息
        Client.INSTANCE.send(new BulletNewMsg(b));

    } //默认子弹

}
