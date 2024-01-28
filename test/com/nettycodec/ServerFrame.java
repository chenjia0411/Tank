package com.nettycodec;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Auther: chenjia
 * @Date: 2024/1/23 - 01 - 23 - 19:20
 * @Description: com.nettychat
 * @version: 1.0
 */
public class ServerFrame extends Frame {
    public static final ServerFrame INSTANCE =new ServerFrame();

    TextArea taServer =new TextArea();
    TextArea taClient =new TextArea();

    private Server server =new Server();

    public ServerFrame(){
        this.setSize(800,600);
        this.setLocation(300,30);
        Panel p = new Panel(new GridLayout(1,2));
        p.add(taServer);
        p.add(taClient);
        this.add(p);
        this.setTitle("服务器");

        taServer.setFont(new Font("Consolas",Font.PLAIN,25));  //设置字体
        taClient.setFont(new Font("Consolas",Font.PLAIN,25));

        this.updateServerMsg("server:");
        this.updateClientMsg("client:");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Server.future.channel().close();
                System.exit(0);
            }
        });

        // server.serverStart();  --注意会阻塞
    }

    public void updateServerMsg(String str) {  //更新Server窗口中的内容
        this.taServer.setText(taServer.getText() +str +System.getProperty("line.separator"));
        //System.getProperty("line.separator")能在不同的系统上拿到不同的换行服
    }

    public void updateClientMsg(String str) { //更新Client窗口中的内容
        this.taClient.setText(taClient.getText() +str +System.getProperty("line.separator"));
    }

    //main方法，程序的入口：
    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();
    }
}
