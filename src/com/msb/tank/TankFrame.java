package com.msb.tank;

import com.msb.abstracts.AbstractGameObject;
import com.msb.chainofresponsibility.BulletTankCollider;
import com.msb.chainofresponsibility.BulletWallCollider;
import com.msb.chainofresponsibility.Collider;
import com.msb.chainofresponsibility.ColliderChain;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * @Auther: chenjia
 * @Date: 2023/10/5 - 10 - 05 - 14:53
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class TankFrame extends Frame {
    public static final int GAME_WIDTH=Integer.parseInt(PropertyMgr.get("GAME_WIDTH"));
    public static final int GAME_HEIGHT=Integer.parseInt(PropertyMgr.get("GAME_HEIGHT"));
    public static final TankFrame INSTANCE = new TankFrame();
   /* public static final int GAME_WIDTH =800;
    public static final int GAME_HEIGHT =600;*/
    public static final int a=10;
    Image offScreenImage = null;
    private Player myTank;
   /* private List<Bullet> bullets;
    private List<Bullet> bullets1;
    private List<Tank> tanks;
    private List<Explode> explodes;*/
    private List<AbstractGameObject>  objects;
    private Random r = new Random();
    private ColliderChain collider;

    private TankFrame() {
        this.setTitle("tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener());
        initGameObjiects(); //初始化物体
        collider = new ColliderChain();
    }




    private void initGameObjiects() {
        myTank = new Player(100, 100, Dir.R, Group.GOOD);
     /*   bullets = new ArrayList<Bullet>();
        bullets1 = new ArrayList<Bullet>();
        explodes = new ArrayList<Explode>();
        tanks = new ArrayList<Tank>();*/
        objects =new ArrayList<>();
        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));

        for (int i = 0; i < tankCount; i++) {
            objects.add(new Tank(r.nextInt(TankFrame.GAME_WIDTH-ResourceMgr.badTankD.getWidth()),
                    r.nextInt(TankFrame.GAME_HEIGHT-ResourceMgr.badTankU.getHeight()), Dir.randomDir(), Group.BAD));
        }
        this.add(new Wall(300,200,400,50));

    }


    public void add(AbstractGameObject go){
        objects.add(go);
    }

    /*Collider collider=new BulletTankCollider();//引入子弹和坦克碰撞检测
    Collider collider2 =new BulletWallCollider();//引入子弹和墙碰撞检测*/


    @Override
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
        for (int i=0;i<objects.size();i++){
            if (!objects.get(i).islive()){
                objects.remove(i);
                break;
            }
            AbstractGameObject go1 = objects.get(i);
            for (int j=0;j<objects.size();j++){
                AbstractGameObject go2 = objects.get(j);
                collider.collide(go1,go2);
            }
            if (objects.get(i).islive()){
            objects.get(i).paint(g);
            }
        }
/*
        for (int i = 0; i < tanks.size(); i++)
            if (!tanks.get(i).isLive()) {
                tanks.remove(i);
            } else {
                tanks.get(i).paint(g);
            }
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collidesWithTank(tanks.get(j));
            }
            if (!bullets.get(i).isLive()) {
                bullets.remove(i);
            } else {
                bullets.get(i).paint(g);
            }
        }
        for (int i = 0; i < bullets1.size(); i++) {
            for (int j = 0; j < tanks.size(); j++)
                bullets1.get(i).collidesWithTank(tanks.get(j));
            if (!bullets1.get(i).isLive()) {
                bullets1.remove(i);
            } else {
                bullets1.get(i).paint(g);
            }
        }
        for (int i = 0; i < explodes.size(); i++)
            if (!explodes.get(i).isLive()) {
                explodes.remove(i);
            } else {
                explodes.get(i).paint(g);
            }*/
    }

    @Override
    public void update(Graphics g) { //解决闪屏问题
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }


    private class TankKeyListener extends KeyAdapter { //监听

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }

    }
}
