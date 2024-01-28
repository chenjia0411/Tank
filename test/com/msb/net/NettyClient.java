package com.msb.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @Auther: chenjia
 * @Date: 2023/10/30 - 10 - 30 - 0:49
 * @Description: com.msb.net
 * @version: 1.0
 */
    public class NettyClient {
        //main方法，程序的入口：
        public static void main(String[] args) throws Exception {
            EventLoopGroup workerGroup = new NioEventLoopGroup(1);
            Bootstrap b=new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new MyHandler());
                }
            });
            ChannelFuture future = b.connect("localhost", 8888).sync();
            future.channel().closeFuture().sync();/*拿到future关联的channel;如果调用了channel中的close方法，会产生一个Future
            closeFuture会拿到这个Future,如果没有拿到Future,则会阻塞，一直到有人调用close方法，才会继续执行下面的代码*/
            System.out.println("go on");
            workerGroup.shutdownGracefully();
        }

    static class MyHandler extends ChannelInboundHandlerAdapter {
        @Override //读取服务端发送来的消息
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println(msg.toString());
        }

        @Override  //第一次连上的时候
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ByteBuf buf = Unpooled.copiedBuffer("mashibing".getBytes());
            ctx.writeAndFlush(buf);
        }

        @Override //出异常的时候
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();

        }
    }
    }
