package com.nettycodec;

/**
 * @Auther: chenjia
 * @Date: 2024/1/23 - 01 - 23 - 21:20
 * @Description: com.nettycodec
 * @version: 1.0
 */
public class TankMsg {
    public int x,y;

    public TankMsg() {
    }

    public TankMsg(int x, int y) {
        this.x = x;
        this.y = y;
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

    @Override
    public String toString() {
        return "TankMsg{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
