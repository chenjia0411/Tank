package com.msb.tank;


import com.msb.abstracts.AbstractGameObject;
import com.msb.strategy.FireStrategy;
import com.msb.strategy.FourDirFireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * @Auther: chenjia
 * @Date: 2023/10/6 - 10 - 06 - 15:53
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class Player extends AbstractGameObject { //玩家
    public static final int SPEED = 5;
    //初始化子弹接口
    FireStrategy fireStrategy = null;
    private int x, y;
    private boolean bL, bU, bR, bD;
    private Dir dir;
    private boolean moving;
    private Group group;
    private boolean live = true;

    public Player(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        this.initFireStrategy();//初始化子弹
    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Player() {
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
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
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.goodTankL, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.goodTankR, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.goodTankU, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.goodTankD, x, y, null);
                break;
        }
        move();
    }

    @Override
    public boolean islive() {
        return live;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        serMainDir(); //按下键盘时判断键盘状态

    }

    private void serMainDir() {
        if (!bL && !bU && !bR && !bD) {
            moving = false;
        } else {
            moving = true;
        }
        if (bL && !bU && !bR && !bD) {
            dir = Dir.L;
        }
        if (!bL && bU && !bR && !bD) {
            dir = Dir.U;
        }
        if (!bL && !bU && bR && !bD) {
            dir = Dir.R;
        }
        if (!bL && !bU && !bR && bD) {
            dir = Dir.D;
        }


    }

    private void move() {
        if (!moving) {
            return;
        }
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
    }

    public void keyReleased(KeyEvent e) {
        int ker = e.getKeyCode();
        switch (ker) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }
        serMainDir(); //松开键盘时判断键盘状态
    }

    private void initFireStrategy() {
        ClassLoader classLoader = Player.class.getClassLoader();
        String className = PropertyMgr.get("tankFireStrategy"); //得到子弹的类
        try {
            Class aClass = classLoader.loadClass("com.msb.strategy." + className);  //第一种方法
            //Class aClass = Class.forName("com.msb.strategy." + className); //第二种方法
            fireStrategy = (FireStrategy) aClass.getDeclaredConstructor().newInstance(); //得到子弹对象

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fire() { //发射子弹
        fireStrategy.fire(this);
    }

    public void die() {
        this.setLive(false);
    }
}
