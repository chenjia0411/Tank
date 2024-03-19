package com.msb.net;

import com.msb.tank.*;

import java.io.*;
import java.util.UUID;

/**
 * @Auther: chenjia
 * @Date: 2024/1/25 - 01 - 25 - 13:34
 * @Description: com.msb.net
 * @version: 1.0
 */
public class TankJoinMsg extends Msg{  //坦克加入的信息
    private int x, y;
    private Dir dir;
    private boolean moving;
    private Group group;

    private UUID id; // 玩家id UUid是由两个long类型长的数合到一起。

    public TankJoinMsg(Player p) {
        this.x = p.getX();
        this.y = p.getY();
        this.dir = p.getDir();
        this.moving = p.isMoving();
        this.group = p.getGroup();
        this.id = p.getId();
    }

    public TankJoinMsg() {
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
    }

    public byte[] toBytes() {

        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits()); //得到id的高位long
            dos.writeLong(id.getLeastSignificantBits());
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
    public MsgType getMsgType() {
        return MsgType.TankJoin;
    }

    public void parse(byte[] bytes) {

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void handle() {
        //如果是自己的消息就不进行处理
        if(this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId()))  return;
        if(TankFrame.INSTANCE.getGm().findTankByUUID(this.id) != null) return; //判断游戏物体集合中有没有这个坦克
        //加入这个坦克
        Tank t = new Tank(this);
        TankFrame.INSTANCE.getGm().add(t);//添加到物体中

        //新加入一个物体之后，把自己信息发送给服务器，目的是让后面的客户端得到自己的坦克信息
        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
    }
}
