/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareacuatro;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author joshuansu
 */
public class PlayersRandomFile {

    private final String PATH;
    private final String FILE_NAME;
    private HashMap<String, Long> indexTable;

    //Colores en ansi, creo que solo soporta desde 30 hasta 37 (creo)
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_RED = "\033[31m";
    public static final String ANSI_LIGHT_BLUE = "\033[34m";

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
            indexTable = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                escribeEn = out.length();
                out.seek(escribeEn);
                Players p = list.get(i);
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

                indexTable.put((p.getFirstName().trim().toLowerCase() + p.getLastName().trim().toLowerCase()), new Long(i));
            }
            out.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return escribeEn;
    }

    public Players Read(String firstName, String lastName) {
        return Read(indexTable.get(firstName.trim().toLowerCase() + lastName.trim().toLowerCase()));
    }

    public Players Read(Long position) {
        try {
            if (position == null) {
                System.out.println("");
                System.out.println("Hola, la " + ANSI_LIGHT_BLUE + "Posicion " + ANSI_RESET + "llega como " + ANSI_RED + "Nullo " + ANSI_RESET + "...");
                System.out.println("Es posible que " + ANSI_LIGHT_BLUE + "First Name " + ANSI_RESET + "o " + ANSI_LIGHT_BLUE + "Last Name " + ANSI_RESET + "Sean Datos " + ANSI_RED + "ERRONEOS." + ANSI_RESET);
                System.out.println("");
                return null;
            }
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
            String LastN = new String(lname);
            p.setLastName(LastN);

            byte[] fname = new byte[30];
            out.read(fname);
            String FirstN = new String(fname);
            p.setFirstName(FirstN);

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
