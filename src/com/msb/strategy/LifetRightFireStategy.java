package com.msb.strategy;

import com.msb.tank.*;

/**
 * @Auther: chenjia
 * @Date: 2023/10/19 - 10 - 19 - 17:47
 * @Description: com.msb.strategy
 * @version: 1.0
 */
public class LifetRightFireStategy implements FireStrategy {
    @Override
    public void fire(Player p) {
        int bx =p.getX()+ ResourceMgr.goodTankU.getWidth()/2-ResourceMgr.bulletU.getWidth()/2;
        int by=p.getY()+ResourceMgr.goodTankU.getHeight()/2-ResourceMgr.bulletU.getHeight()/2;

        TankFrame.INSTANCE.add(new Bullet(bx,by, Dir.L,p.getGroup()));
        TankFrame.INSTANCE.add(new Bullet(bx,by, Dir.R,p.getGroup()));
    }
}
