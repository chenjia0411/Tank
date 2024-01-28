package com.msb.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Auther: chenjia
 * @Date: 2024/1/27 - 01 - 27 - 14:02
 * @Description: com.msb.net
 * @version: 1.0
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        if(buf.readableBytes() <37) return;

        int length = buf.readInt();

        byte[] bytes = new byte[length];

        buf.readBytes(bytes);

        TankJoinMsg tjm = new TankJoinMsg();
        tjm.parse(bytes);

        list.add(tjm);
    }
}
