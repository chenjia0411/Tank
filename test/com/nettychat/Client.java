package com.nettychat;

import com.msb.net.NettyClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;

/**
 * @Auther: chenjia
 * @Date: 2023/10/30 - 10 - 30 - 15:09
 * @Description: com.nettychat
 * @version: 1.0
 */
public class Client {

    private Channel channel =null;

    public  void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        Bootstrap b=new Bootstrap();
        try {


            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel = socketChannel;//初始化通道
                    socketChannel.pipeline().addLast(new Client.MyHandler());
                }
            });
            ChannelFuture future = b.connect("localhost", 8888).sync();

            System.out.println("连接成功");
            future.channel().closeFuture().sync();/*拿到future关联的channel;如果调用了channel中的close方法，会产生一个Future
        closeFuture会拿到这个Future,如果没有拿到Future,则会阻塞，一直到有人调用close方法，才会继续执行下面的代码*/
            System.out.println("客户端通道关闭");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void send(String text) {
            channel.writeAndFlush(Unpooled.copiedBuffer(text.getBytes()));//netty的底层都是用ByteBuf封装的
        //所以用辅助类Unpooled.copiedBuffer，返回值是一个ByteBuf
    }

    public void closeConnection() {
        send("_bye_");
        channel.close();
    }

    static class MyHandler extends ChannelInboundHandlerAdapter {
        @Override //读取服务端发送来的消息
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf =null;
            try {
                buf = (ByteBuf) msg;
                byte[] bytes = new byte[buf.readableBytes()]; //bytes数组的大小为获取到文件中的可读字节数
                buf.getBytes(buf.readerIndex(), bytes);  //把文件中可读字节数复制到bytes中
                String str = new String(bytes);
                ClientFrame.INSTANCE.updateText(str);  //传入服务器的值到窗口中
                /*System.out.println(str);
                System.out.println(buf.refCnt());*///获取当前对象的引用计数值
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (buf!=null){
                    ReferenceCountUtil.release(buf); // 释放引用计数值----实际上是减1，当引用计数值为0时释放内存
                }
            }
        }


        @Override //出异常的时候
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
            ctx.close();
        }
    }
}

