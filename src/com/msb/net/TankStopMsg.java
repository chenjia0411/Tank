package com.msb.net;

import com.msb.tank.Dir;
import com.msb.tank.Tank;
import com.msb.tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * @Auther: chenjia
 * @Date: 2024/1/29 - 01 - 29 - 15:09
 * @Description: com.msb.net
 * @version: 1.0
 */
public class TankStopMsg extends Msg{
    private UUID id;
    private int x,y;


    public TankStopMsg(UUID id, int x, int y) {
        this.id =id;
        this.x=x;
        this.y=y;

    }

    public TankStopMsg() {
    }

    @Override
    public String toString() {
        return "TankStopMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public byte[] toBytes() {

        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits()); //得到id的高位long
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (dos != null)
                    dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }


    @Override
    public void handle() {
        if(this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())){
            return;    //如果是自己发的消息不做处理
        }

        Tank t =TankFrame.INSTANCE.getGm().findTankByUUID(this.id);  //查找物体集合有没有

        if(t!=null){
            t.setMoving(false);
            t.setX(this.x);
            t.setY(this.y);
        }

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
