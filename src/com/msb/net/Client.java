package com.msb.net;

import com.msb.tank.TankFrame;
import com.nettychat.ClientFrame;
import com.nettycodec.TankMsg;
import com.nettycodec.TankMsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * @Auther: chenjia
 * @Date: 2023/10/30 - 10 - 30 - 15:09
 * @Description: com.nettychat
 * @version: 1.0
 */
public class Client {
    public static Client INSTANCE= new Client();
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
                    socketChannel.pipeline()
                            .addLast(new MsgEncoder())  //添加tankMsg对象的编码处理器
                            .addLast(new MsgDecoder())  //添加解码处理器
                            .addLast(new Client.MyHandler());
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

    public void send(TankJoinMsg msg) {
            channel.writeAndFlush(msg);
    }

    public void closeConnection() {

        channel.close();
    }

    static class MyHandler extends SimpleChannelInboundHandler<TankJoinMsg> {
        //SimpleChannelInboundHandler<TankJoinMsg>，只处理 TankJoinMsg 类型的消息。
        //需要实现channelRead0()方法
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
        }


        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TankJoinMsg msg) throws Exception {
            //接收服务端传来的消息
            //已经帮我们把消息进行类型的转换
            System.out.println(msg.toString());
            msg.handle(msg);
        }


        @Override //出异常的时候
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
            ctx.close();
        }
    }

    //main方法，程序的入口：
    public static void main(String[] args) {
        Client c =new Client();
        c.connect();
    }
}

