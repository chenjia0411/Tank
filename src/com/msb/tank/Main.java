package com.msb.tank;

/**
 * @Auther: chenjia
 * @Date: 2023/10/5 - 10 - 05 - 21:32
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class Main {
    //main方法，程序的入口：
    public static void main(String[] args) {

        TankFrame.INSTANCE.setVisible(true);
        for (;;){
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TankFrame.INSTANCE.repaint();
        }
    }

}
