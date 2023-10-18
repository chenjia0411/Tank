import com.msb.tank.Dir;
import com.msb.tank.PropertyMgr;
import com.msb.tank.TankFrame;

import java.awt.*;
import java.util.Random;

/**
 * @Auther: chenjia
 * @Date: 2023/10/12 - 10 - 12 - 23:31
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class Test extends Frame {
    public static final int GAME_WIDTH = Integer.parseInt(PropertyMgr.get("GAME_WIDTH"));
    public static final int GAME_HEIGHT = Integer.parseInt(PropertyMgr.get("GAME_HEIGHT"));
    //main方法，程序的入口：
    public static void main(String[] args) {
        System.out.println(GAME_HEIGHT);
        System.out.println(GAME_WIDTH);
        a(GAME_HEIGHT,GAME_WIDTH);
    }
    public static void a(int GAME_HEIGHT,int GAME_WIDTH){
        System.out.println(GAME_HEIGHT);
        System.out.println(GAME_WIDTH);
    }
}
