import com.msb.tank.PropertyMgr;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

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
       /* System.out.println(PropertyMgr.get("initTankCount"));
        System.out.println(PropertyMgr.get("GAME_WIDTH"));
        System.out.println(PropertyMgr.get("GAME_HEIGHT"));
        int GAME_HEIGHT =Integer.parseInt(PropertyMgr.get("GAME_HEIGHT"));
        System.out.println(GAME_HEIGHT);*/
        List<Object> a =new ArrayList();
        a.add(new a());
        a.add(new a());

        a a1= (a) a;
        System.out.println(a.toString());

    }

}
class a{
    int a =0;
    int b = 1;

    @Override
    public String toString() {
        return "a{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}