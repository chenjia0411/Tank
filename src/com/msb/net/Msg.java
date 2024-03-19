package com.msb.net;

/**
 * @Auther: chenjia
 * @Date: 2024/1/29 - 01 - 29 - 15:40
 * @Description: com.msb.net
 * @version: 1.0
 */
public abstract class Msg {
    public abstract byte[] toBytes();

    public abstract void handle();

    public abstract MsgType getMsgType();

    public abstract void parse(byte[] bytes);
}
