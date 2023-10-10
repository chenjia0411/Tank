package com.msb.tank;

import java.awt.*;

/**
 * @Auther: chenjia
 * @Date: 2023/10/10 - 10 - 10 - 11:15
 * @Description: com.msb.tank
 * @version: 1.0
 */
public class Bullet {
    private int x,y;
    private Dir dir;
    private Group group;
    public static final int SPEED=5;

    public Bullet(int x, int y, Dir dir,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;
    }

    public Bullet() {
    }
    public void paint(Graphics g) {
        if (this.group==Group.GOOD){
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
            }}

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
    }

}
