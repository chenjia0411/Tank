package com.msb.tank;

import com.msb.abstracts.AbstractGameObject;

import java.awt.*;

/**
 * @Auther: chenjia
 * @Date: 2023/10/16 - 10 - 16 - 15:52
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class Explode extends AbstractGameObject {  //爆炸效果添加
    private int x,y;
   // private int width,height;
    private int step=0; //记录爆炸的帧数
    private boolean live =true;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;

     /*   this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();*/
    }

    public Explode() {
    }

    public void paint(Graphics g) {
        if (!live){
            return;
        }
         g.drawImage(ResourceMgr.explodes[step],x,y,null);
         step++;

        if (step>=ResourceMgr.explodes.length){
             live =false;
             step=0;//播完最后一张爆炸图片，爆炸动画消失
        }

    }

    @Override
    public boolean islive() {
        return live;
    }
}
