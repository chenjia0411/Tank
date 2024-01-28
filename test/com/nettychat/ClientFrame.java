package com.nettychat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Auther: chenjia
 * @Date: 2023/10/30 - 10 - 30 - 15:09
 * @Description: com.nettychat
 * @version: 1.0
 */
public class ClientFrame extends Frame {
    public static final ClientFrame INSTANCE = new ClientFrame();

    private TextArea  ta = new TextArea();//聊天室
    private TextField tf = new TextField();//输入框

    private Client c = null;

    private ClientFrame()  {
        this.setSize(300,400);
        this.setLocation(400,200);
        this.add(ta,BorderLayout.CENTER);
        this.add(tf,BorderLayout.SOUTH);
        this.setTitle("liaotianshi");

        tf.addActionListener(new ActionListener() { //按下回车键时触发
            @Override
            public void actionPerformed(ActionEvent e) {
                c.send(tf.getText());  //发送到聊天室
                //ta.setText(ta.getText() + tf.getText()+"\r\n");
                tf.setText(""); //输入框重置为空
            }
        });
        this.addWindowListener(new WindowAdapter() {  //点击窗口右上角关闭时候触发
            @Override
            public void windowClosing(WindowEvent e) {
                c.closeConnection();
                System.exit(0);
            }
        });
    }
        public void connectToServer(){
            c = new Client();
            c.connect();
        }

    //main方法，程序的入口：
    public static void main(String[] args) {
        ClientFrame f= ClientFrame.INSTANCE;
        f.setVisible(true);
        f.connectToServer();
    }

    public void updateText(String str) {
        ta.setText(ta.getText() + str+"\r\n"); //从窗口中打印出来
    }
}
