package com.Nio;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @Auther: chenjia
 * @Date: 2023/10/30 - 10 - 30 - 0:05
 * @Description: com.Nio
 * @version: 1.0
 */
public class TestClient {
    //main方法，程序的入口：
    public static void main(String[] args) throws Exception{
        Socket s=new Socket("localhost",8888);

        BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bw.write("chenjia");
        bw.newLine();
        bw.flush();

        BufferedReader reader =new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str =reader.readLine();

        System.out.println(str);

        bw.close();
    }
}
