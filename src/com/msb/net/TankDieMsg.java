package com.msb.net;

import com.msb.tank.Bullet;
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
public class TankDieMsg extends Msg{
    private UUID id; //坦克id
    private UUID bulletId; //子弹id

    public TankDieMsg(UUID id, UUID bulletId) {
        this.id = id;
        this.bulletId = bulletId;
    }

    public UUID getBulletId() {
        return bulletId;
    }

    public void setBulletId(UUID bulletId) {
        this.bulletId = bulletId;
    }

    public TankDieMsg() {
    }


    @Override
    public byte[] toBytes() {

        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());
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
    public String toString() {
        return "TankDieMsg{" +
                "id=" + id +
                ", bulletId=" + bulletId +
                '}';
    }

    @Override
    public void handle() {
        Bullet b = TankFrame.INSTANCE.getGm().findBulletByUUID(bulletId);
        if (b !=null)
            b.die();
        Tank t =TankFrame.INSTANCE.getGm().findTankByUUID(id);  //查找物体集合有没有

        if(this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())){
                TankFrame.INSTANCE.getGm().getMyTank().die();
        } else {
            if(t != null)
                t.die();
        }





    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }





    @Override
    public MsgType getMsgType() {
        return MsgType.TankDie;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            this.bulletId= new UUID(dis.readLong(), dis.readLong());
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


}
