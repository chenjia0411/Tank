package com.msb.net;

import com.sun.xml.internal.fastinfoset.stax.EventLocation;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Auther: chenjia
 * @Date: 2023/10/29 - 10 - 29 - 15:49
 * @Description: com.msb.net
 * @version: 1.0
 */
public class NettyServer {
    //main方法，程序的入口：
    public static void main(String[] args) throws Exception {
        //负责接客
        EventLoopGroup bossGroup =new NioEventLoopGroup(2);
        //负责服务
        EventLoopGroup workerGroup= new NioEventLoopGroup(4);
        //Server启动服务类
        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup,workerGroup);
        //异步全双工
        b.channel(NioServerSocketChannel.class);
        //netty帮我们内部处理了accept的过程
        b.childHandler(new MyChildInitializer()); //当有一个客户端连上的时候调用，-----回调函数
        ChannelFuture future = b.bind(8888).sync();
        future.channel().closeFuture().sync();
        System.out.println("关闭");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
class MyChildInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {  //客户端连上来自动调用
        socketChannel.pipeline().addLast(new MyChildHandler());
        //在客户端和服务器连接的管道中添加一个处理器专门用来处理之间的读写操作
    }
}

class MyChildHandler extends ChannelInboundHandlerAdapter{
    @Override  //读取客户段发送来的东西
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String buf = msg.toString();
        System.out.println(buf);
        System.out.println(msg.toString());
       // ctx.writeAndFlush(msg);
        ctx.writeAndFlush("我收到了");
    }

    @Override  //出异常的时候
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

    }
}