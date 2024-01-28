package com.nettycodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Auther: chenjia
 * @Date: 2024/1/24 - 01 - 24 - 15:38
 * @Description: com.nettycodec
 * @version: 1.0
 */
public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        //list的原因，是因为服务器可能一下读取到一堆消息，类型不同。
        if(buf.readableBytes() <8) return;  //判断字节数组的长度是不是自己需要的

        int x =buf.readInt();  //先写先读
        int y =buf.readInt();

        out.add(new TankMsg(x,y));  //x =5 ,y =8
        out.add(new TankMsg(2,4)); //表示之后消息都是传递在out中，后续msg接收到的也是out数组妆化后的object对象
    }
}
