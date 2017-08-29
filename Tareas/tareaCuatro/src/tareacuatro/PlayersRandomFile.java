/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareacuatro;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

/**
 *
 * @author joshuansu
 */
public class PlayersRandomFile {

    private final String PATH;
    private final String FILE_NAME;

    public PlayersRandomFile(String PATH, String FILE_NAME) {
        this.PATH = PATH;
        this.FILE_NAME = FILE_NAME;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public long Write(List<Players> list) {
        long escribeEn = 0;

        try {
            File f = new File(PATH + FILE_NAME);
            if (f.exists()) {
                f.delete();
            }

            RandomAccessFile out = new RandomAccessFile(PATH + FILE_NAME, "rw");
            for (Players p : list) {
                escribeEn = out.length();
                out.seek(escribeEn);
                out.writeInt(p.getSalary());
                out.writeBytes(p.getBorn());
                out.writeBytes(p.getCity());
                out.writeBytes(p.getPrSt());
                out.writeBytes(p.getCntry());
                out.writeBytes(p.getNat());
                out.writeByte(p.getHt());
                out.writeShort(p.getWt());
                out.writeShort(p.getDftYr());
                out.writeByte(p.getDftRd());
                out.writeShort(p.getOvrl());
                out.writeChar(p.getHand());
                out.writeBytes(p.getLastName());
                out.writeBytes(p.getFirstName());
                out.writeBytes(p.getPosition());
                out.writeBytes(p.getTeam());
                out.writeShort(p.getGP());
            }
            out.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return escribeEn;
    }

    public Players Read(Long position) {
        try {
            RandomAccessFile out = new RandomAccessFile(PATH + FILE_NAME, "rw");
            Players p = new Players();

            out.seek(position);
            p.setSalary(out.readInt());

            byte[] born = new byte[10];
            out.read(born);
            p.setBorn(new String(born));

            byte[] city = new byte[30];
            out.read(city);
            p.setCity(new String(city));

            byte[] prst = new byte[4];
            out.read(prst);
            p.setPrSt(new String(prst));

            byte[] cntry = new byte[3];
            out.read(cntry);
            p.setCntry(new String(cntry));

            byte[] nat = new byte[3];
            out.read(nat);
            p.setNat(new String(nat));

            p.setHt(out.readByte());
            p.setWt(out.readShort());
            p.setDftYr(out.readShort());
            p.setDftRd(out.readByte());
            p.setOvrl(out.readShort());
            p.setHand(out.readChar());

            byte[] lname = new byte[30];
            out.read(lname);
            p.setLastName(new String(lname));

            byte[] fname = new byte[30];
            out.read(fname);
            p.setFirstName(new String(fname));

            byte[] pos = new byte[10];
            out.read(pos);
            p.setPosition(new String(pos));

            byte[] tema = new byte[20];
            out.read(tema);
            p.setTeam(new String(tema));

            p.setGP(out.readByte());

            System.out.println(p.toString());
            return p;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
