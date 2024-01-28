package com.Nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: chenjia
 * @Date: 2023/10/26 - 10 - 26 - 0:14
 * @Description: com.IO
 * @version: 1.0
 */
public class TestIo {
    //main方法，程序的入口：
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc=ServerSocketChannel.open(); //非阻塞通道
        ssc.socket().bind(new InetSocketAddress("127.0.0.1",8888));
        ssc.configureBlocking(false);
        System.out.println("server started,listening on:"+ssc.getLocalAddress());
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);


        while (true){
            selector.select();
            Set<SelectionKey> keys=selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()){
                SelectionKey key =it.next();
                it.remove();
                handle(key);
            }
        }
    }

    private static void handle(SelectionKey key) {
        if (key.isAcceptable()){
            try {
                ServerSocketChannel ssc=(ServerSocketChannel) key.channel();
                SocketChannel sc=ssc.accept();
                sc.configureBlocking(false);
                sc.register(key.selector(),SelectionKey.OP_READ);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
