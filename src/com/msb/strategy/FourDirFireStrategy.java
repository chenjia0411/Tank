package com.msb.strategy;

import com.msb.tank.*;

/**
 * @Auther: chenjia
 * @Date: 2023/10/19 - 10 - 19 - 17:04
 * @Description: com.msb.strategy
 * @version: 1.0
 */
public class FourDirFireStrategy implements FireStrategy{

    @Override
    public void fire(Player p) {
        int bx =p.getX()+ ResourceMgr.goodTankU.getWidth()/2-ResourceMgr.bulletU.getWidth()/2;
        int by=p.getY()+ResourceMgr.goodTankU.getHeight()/2-ResourceMgr.bulletU.getHeight()/2;

        Dir[] dirs =Dir.values();
        for (Dir dir:dirs)
        TankFrame.INSTANCE.add(new Bullet(bx,by,dir,p.getGroup()));
    } ///发射四个方向的子弹
}
