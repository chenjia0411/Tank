package com.msb.tank;

import com.msb.abstracts.AbstractGameObject;

import java.awt.*;

/**
 * @Auther: chenjia
 * @Date: 2023/10/20 - 10 - 20 - 0:39
 * @Description: com.msb.tank
 * @version: 1.0
 */
public class Wall extends AbstractGameObject {
    private int x, y, w, h;
    private Rectangle rect;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        rect = new Rectangle(x, y, w, h);
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

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }

    @Override
    public boolean islive() {
        return true;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }
}
