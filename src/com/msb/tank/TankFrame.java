package com.msb.tank;


import com.msb.abstracts.AbstractGameObject;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;


/**
 * @Auther: chenjia
 * @Date: 2023/10/5 - 10 - 05 - 14:53
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class TankFrame extends Frame {
    public static final int GAME_WIDTH=Integer.parseInt(PropertyMgr.get("GAME_WIDTH"));
    public static final int GAME_HEIGHT=Integer.parseInt(PropertyMgr.get("GAME_HEIGHT"));
    public static final TankFrame INSTANCE = new TankFrame();//使用了单列模式
    Image offScreenImage = null;  //闪屏问题的解决
    private GameModel gm=new GameModel();



    private TankFrame() {
        this.setTitle("tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener());


    }


    @Override
    public void paint(Graphics g) {
        gm.paint(g);
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


    private class TankKeyListener extends KeyAdapter { //监听器

        @Override
        public void keyPressed(KeyEvent e) { //按下键盘
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_S){  //按下s键保持
              save();
            }else if (key==KeyEvent.VK_L){
                load();
            }
                else {
            gm.getMyTank().keyPressed(e);
            }
        }

        private void save()  {
            File f=new File("tanksave.dat");
            ObjectOutputStream oos = null;
            try {
                FileOutputStream fos = new FileOutputStream(f);
                oos=new ObjectOutputStream(fos);
                oos.writeObject(gm);
                oos.flush();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    if (oos!=null)
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        private void load() {
            File f =new File("tanksave.dat");
            ObjectInputStream ois =null;
            try {
                FileInputStream fis = new FileInputStream(f);
                ois = new ObjectInputStream(fis);
                gm = (GameModel) ois.readObject();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) { //抬起键盘
            gm.getMyTank().keyReleased(e);
        }
    }

  /* 添加object对象，第一种方法：
        虽然简单但是没有做到数据与视图分离
   public void add(AbstractGameObject object){
        this.gm.add(object);
    }*/
  //第二种方法：
    public GameModel getGm(){
        return this.gm;
    }
}
