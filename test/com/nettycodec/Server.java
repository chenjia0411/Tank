package com.nettycodec;


import com.msb.tank.Tank;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Auther: chenjia
 * @Date: 2023/10/29 - 10 - 29 - 15:49
 * @Description: com.msb.net
 * @version: 1.0
 */
public class Server {

    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE); //channel(管道)组
    static ChannelFuture future =null;
    //main方法，程序的入口：
    public static void serverStart() {
        //负责接客
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        //负责服务
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        //Server启动服务类
        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup, workerGroup);

        try {
            //异步全双工
            Server.future = b.channel(NioServerSocketChannel.class)
                    //netty帮我们内部处理了accept的过程
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new TankMsgDecoder())
                                    .addLast(new ServerChildHandler());
                        }

                    }) //当有一个客户端连上的时候调用，-----回调函数

                    .bind(8888)
                    .sync();
            ServerFrame.INSTANCE.updateServerMsg("server started!");

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("正常关闭");
        bossGroup.shutdownGracefully();  //关闭
        workerGroup.shutdownGracefully();
    }

    static class ServerChildHandler extends ChannelInboundHandlerAdapter {
        @Override   //确认channel已经连接上
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Server.clients.add(ctx.channel()); //添加channel到clients组中
        }

        @Override  //读取客户段发送来的东西
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            TankMsg tm = (TankMsg) msg;
            System.out.println(msg);
            System.out.println(tm.toString());
          // ServerFrame.INSTANCE.updateClientMsg(msg.toString());
           //ServerFrame.INSTANCE.updateClientMsg(tm.toString());
        }

        @Override  //出异常的时候
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            Server.clients.remove(ctx);
            ctx.close();
        }
    }
}

