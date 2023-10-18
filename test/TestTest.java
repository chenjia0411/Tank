import com.msb.tank.PropertyMgr;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther: chenjia
 * @Date: 2023/10/18 - 10 - 18 - 14:41
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
class TestTest {
    //main方法，程序的入口：
    public static void main(String[] args) {
        System.out.println(PropertyMgr.get("initTankCount"));
        System.out.println(PropertyMgr.get("GAME_WIDTH"));
        System.out.println(PropertyMgr.get("GAME_HEIGHT"));
        int GAME_HEIGHT =Integer.parseInt(PropertyMgr.get("GAME_HEIGHT"));
        System.out.println(GAME_HEIGHT);
    }

}