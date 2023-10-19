package com.msb.strategy;

import com.msb.tank.Player;

/**
 * @Auther: chenjia
 * @Date: 2023/10/19 - 10 - 19 - 15:50
 * @Description: com.msb.strategy
 * @version: 1.0
 */
public interface FireStrategy {  //定义发射子弹的接口
    public void fire(Player p); //发射子弹的方法

}
