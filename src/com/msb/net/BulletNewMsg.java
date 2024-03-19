package com.msb.net;

import com.msb.tank.Bullet;
import com.msb.tank.Dir;
import com.msb.tank.Group;
import com.msb.tank.TankFrame;


import java.io.*;
import java.util.UUID;

/**
 * @Auther: chenjia
 * @Date: 2024/2/8 - 02 - 08 - 20:30
 * @Description: com.msb.net
 * @version: 1.0
 */
public class BulletNewMsg extends Msg{
    private UUID piayerId;
    private UUID id;
    private int x,y;
    private Dir dir;
    private Group group;

    public BulletNewMsg() {
    }

    public BulletNewMsg(Bullet bullet){
        this.piayerId = bullet.getPlayerId();
        this.id =bullet.getId();
        this.x =bullet.getX();
        this.y =bullet.getY();
        this.dir =bullet.getDir();
        this.group =bullet.getGroup();
        }


    @Override
    public byte[] toBytes() {

        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            //玩家坦克id
            dos.writeLong(piayerId.getMostSignificantBits()); //得到id的高位long
            dos.writeLong(piayerId.getLeastSignificantBits());

            //子弹id
            dos.writeLong(id.getMostSignificantBits()); //得到id的高位long
            dos.writeLong(id.getLeastSignificantBits());

            dos.writeInt(x);
            dos.writeInt(y);

            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
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
        if (this.piayerId.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())) return; //如果是自己发的消息返回

        //不是则处理
        Bullet bullet = new Bullet(this.piayerId,x,y,dir,group);
        bullet.setId(this.id);
        TankFrame.INSTANCE.getGm().add(bullet);
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "piayerId=" + piayerId +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                '}';
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.piayerId = new UUID(dis.readLong(),dis.readLong());
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir =Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];

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
