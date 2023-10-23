package com.msb.tank;

import com.msb.abstracts.AbstractGameObject;
import com.msb.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Auther: chenjia
 * @Date: 2023/10/23 - 10 - 23 - 16:19
 * @Description: com.msb.tank
 * @version: 1.0
 */
public class GameModel {
    private Player myTank;  //玩家
    private List<AbstractGameObject> objects; //物体集合
    private ColliderChain collider; //碰撞器
    private Random r = new Random();

    public GameModel() {
        collider = new ColliderChain();
        initGameObjiects(); //初始化物体
    }

    private void initGameObjiects() {
        myTank = new Player(100, 100, Dir.R, Group.GOOD);  //初始化玩家坦克

        objects = new ArrayList<>(); //初始化游戏物体
        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));  //在配置文件中得到敌人数量

        for (int i = 0; i < tankCount; i++) {
            objects.add(new Tank(r.nextInt(TankFrame.GAME_WIDTH - ResourceMgr.badTankD.getWidth()),
                    r.nextInt(TankFrame.GAME_HEIGHT - ResourceMgr.badTankU.getHeight()), Dir.randomDir(), Group.BAD));
        }
        this.add(new Wall(300, 200, 400, 50));
    }

    public void add(AbstractGameObject go) {
        objects.add(go);
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.white);
      /*  g.drawString("bullets1" + bullets1.size(), 10, 50);
        g.drawString("bullets" + bullets.size(), 10, 60);
        g.drawString("tanks" + tanks.size(), 10, 70);
        g.drawString("explodes" + explodes.size(), 10, 80);*/
        g.drawString("objects"+objects.size(),10,50);
        g.setColor(color);
        myTank.paint(g);
        for (int i = 0; i < objects.size(); i++) {
            if (!objects.get(i).islive()) {
                objects.remove(i);
                break;
            }
            AbstractGameObject go1 = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObject go2 = objects.get(j);
                collider.collide(go1, go2);
            }
            if (objects.get(i).islive()) {
                objects.get(i).paint(g);
            }
        }
    }

    public List<AbstractGameObject> getObjects() {
        return objects;
    }

    public void setObjects(List<AbstractGameObject> objects) {
        this.objects = objects;
    }

    public Player getMyTank() {
        return myTank;
    }

    public void setMyTank(Player myTank) {
        this.myTank = myTank;
    }

}