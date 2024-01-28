package com.msb.tank;


import com.msb.abstracts.AbstractGameObject;
import com.msb.net.TankJoinMsg;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Random;
import java.util.UUID;


/**
 * @Auther: chenjia
 * @Date: 2023/10/6 - 10 - 06 - 15:53
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class Tank extends AbstractGameObject  {//敌人坦克
    public static final int SPEED = 5;
    private int x, y;
    private boolean bL, bU, bR, bD;
    private Dir dir;
    private boolean moving = true;
    private Group group;
    private boolean live = true; //坦克是否存活
    private Random r = new Random();
    private int width,height;
    private int oldx,oldy;
    private Rectangle rect;

    private UUID id ;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        oldx =x;
        oldy =y;
        width =TankFrame.GAME_WIDTH-ResourceMgr.badTankD.getWidth();
        height=TankFrame.GAME_HEIGHT-ResourceMgr.badTankU.getHeight();

        this.rect =new Rectangle(x,y,ResourceMgr.badTankD.getWidth(),ResourceMgr.badTankU.getHeight());
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tank() {
    }

    public Tank(TankJoinMsg msg) {
        this.x =msg.getX();
        this.y = msg.getY();
        this.dir =msg.getDir();
        this.moving =msg.isMoving();
        this.group =msg.getGroup();
        this.id =msg.getId();

        oldx =x;
        oldy =y;
        width =TankFrame.GAME_WIDTH-ResourceMgr.badTankD.getWidth();
        height=TankFrame.GAME_HEIGHT-ResourceMgr.badTankU.getHeight();

        this.rect =new Rectangle(x,y,ResourceMgr.badTankD.getWidth(),ResourceMgr.badTankU.getHeight());

    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics g) {
        if (!live) {
            return;
        }
        switch (dir) {
            case L:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankL:ResourceMgr.goodTankL, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankR:ResourceMgr.goodTankR, x, y, null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankU:ResourceMgr.goodTankU, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankD:ResourceMgr.goodTankD, x, y, null);
                break;
        }

       // move();
        //更新坦克图片位置
        rect.x=x;
        rect.y=y;
    }

    @Override
    public boolean islive() {
        return live;
    }

    private void move() {
        //保留上一个位置
        this.oldx=x;
        this.oldy=y;
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
        boundsCheck(); //检查坦克是否出了边界
        randomDir();//坦克随机移动方向
        if (r.nextInt(100) > 90) fire(); //坦克开火
    }

    public void randomDir() {
        if (r.nextInt(100) > 90) {
            this.dir = Dir.randomDir();
        }
    }

    private void boundsCheck() {
        if (x < 0 || y < 30 || x > width || y > height) {  //出了边界回到原来的位置
          this.back();
        }
    }

    public void back() {
        x=oldx;
        y=oldy;
    }

    private void fire() {
        int bx = x + ResourceMgr.goodTankU.getWidth() / 2 - ResourceMgr.bulletU.getWidth() / 2;
        int by = y + ResourceMgr.goodTankU.getHeight() / 2 - ResourceMgr.bulletU.getHeight() / 2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bx, by, dir, group));
    }

    public void die() {
        this.setLive(false);
        TankFrame.INSTANCE.getGm().add(new Explode(x,y));
    }

    public Rectangle getRect() {
        return rect;
    }
}
