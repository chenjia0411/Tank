package com.msb.tank;

import com.msb.abstracts.AbstractGameObject;

import java.awt.*;
import java.io.Serializable;

/**
 * @Auther: chenjia
 * @Date: 2023/10/10 - 10 - 10 - 11:15
 * @Description: com.msb.tank
 * @version: 1.0
 */
public class Bullet extends AbstractGameObject  {
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public static final int SPEED = 5;
    Rectangle rect; //子弹方块
    Rectangle recttank; //坦克方块
    private int x, y;
    private int w = ResourceMgr.bulletU.getWidth();//子弹的宽度
    private int h = ResourceMgr.bulletU.getHeight();//子弹的高度
    private Dir dir;
    private Group group;
    private boolean live = true;


    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        rect = new Rectangle(x, y, w, h);
        recttank = new Rectangle();
    }

    public Bullet() {
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void paint(Graphics g) {
        if (!live) {
            return;
        }
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }

        move();
    }

    @Override
    public boolean islive() {
        return live;
    }


    private void move() {
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }

        boundsCheck();          //判断子弹是否飞出边界
        //移动子弹方块
        rect.x = x;
        rect.y = y;
    }


    public void collidesWithTank(Tank tank) {  //碰撞

    }

    public Rectangle getRect() {
        return rect;
    }

    private void boundsCheck() {
        if (x < 0 || y < 30 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            live = false;
        }
    }

    public void die() {
        this.setLive(false);
    }
}
