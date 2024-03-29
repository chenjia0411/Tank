package com.msb.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Auther: chenjia
 * @Date: 2024/1/26 - 01 - 26 - 1:32
 * @Description: com.msb.net
 * @version: 1.0
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) throws Exception {
        buf.writeInt(msg.getMsgType().ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);  //消息头： 类型加长度，占八个字节
        buf.writeBytes(bytes);
    } //消息编码器
}
