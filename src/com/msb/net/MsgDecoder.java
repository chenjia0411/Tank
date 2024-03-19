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
        if(buf.readableBytes() <8) return;  //消息头是八个字节，如果不够直接返回

        buf.markReaderIndex();

        MsgType msgType = MsgType.values()[buf.readInt()]; //读取消息的类型
        int length = buf.readInt(); //得到消息长度

        if (buf.readableBytes() < length) {
            buf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        Msg msg =null;
        msg = (Msg) Class.forName("com.msb.net."+msgType.toString()+"Msg")
                .getDeclaredConstructor()
                .newInstance();  //效率低， 但是添加新的消息对象不用改代码

        //方法二：
    /*    switch (msgType){
            case TankJoin:
                msg =new TankJoinMsg();
                msg.toBytes();
                break;
            case TankStartMoving:
                msg = new TankStartMovingMsg();
                msg.toBytes()
        }*/
        msg.parse(bytes);
        list.add(msg);
    }
}
