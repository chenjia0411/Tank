package com.msb.tank;

import java.awt.*;

/**
 * @Auther: chenjia
 * @Date: 2023/10/10 - 10 - 10 - 11:15
 * @Description: com.msb.tank
 * @version: 1.0
 */
public class Bullet {
    public static final int SPEED=5;
    private int x,y;
    private Dir dir;
    private Group group;
    private boolean live=true;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;
    }

    public Bullet() {
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void paint(Graphics g) {
            switch (dir){
                case L:
                    g.drawImage(ResourceMgr.bulletL,x,y,null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.bulletR,x,y,null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.bulletU,x,y,null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.bulletD,x,y,null);
                    break;
            }

        move();
    }

    private void move() {
        switch (dir){
            case L:
                x -=SPEED;
                break;
            case U:
                y -=SPEED;
                break;
            case R:
                x +=SPEED;
                break;
            case D:
                y +=SPEED;
                break;
        }

        boundsCheck();          //判断子弹是否飞出边界

    }

    public void collidesWithTank(Tank tank) {
        if (!tank.isLive()){return;}
        if(this.group==tank.getGroup()){return;}
        //把子弹和坦克变为两个方块
        Rectangle   rect=new Rectangle(x,y,ResourceMgr.bulletU.getWidth(),ResourceMgr.bulletU.getHeight());//子弹的方块
        Rectangle   recttank=new Rectangle(tank.getX(),tank.getY(),
                ResourceMgr.badTankU.getWidth(),ResourceMgr.badTankU.getHeight());       //坦克的方块

        //判断方块是否相交
        if(rect.intersects(recttank)){
            this.die();   //相交子弹消失
            tank.die();   //相交坦克死亡
        }
    }

    private void boundsCheck() {
        if(x<0 || y<30 || x>TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT){
            live=false;
        }
    }
    public void die(){
        this.setLive(false);
    }
}
