/**
 * @Auther: chenjia
 * @Date: 2023/10/24 - 10 - 24 - 0:05
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class Test2 {
    //main方法，程序的入口：

    public static void main(String[] args) {
         int a =0;
        int b=0;
       for (int i=0;i<10;i++){
            a=a+i;
            if (i==2){
                System.out.println("i"+i);
                System.out.println("a"+a);
                break;
            }
            for (int j=0;j<2;j++){
                b++;
                System.out.println("b"+b);
            }
        }
    }
}
