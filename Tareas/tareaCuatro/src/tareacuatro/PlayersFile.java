/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareacuatro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joshuansu
 */
public class PlayersFile {

    private final String PATH;
    private final String FILE_NAME;

    public PlayersFile(String path, String name) {
        this.PATH = path;
        this.FILE_NAME = name;
    }

    public List<Players> Read() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(PATH + FILE_NAME));
            String line;
            List<Players> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (line.contains("Salary")) {
                    continue;
                }
                String[] datos = line.split(",");
                Players d = new Players();
                d.setSalary(Integer.parseInt(datos[0]));
                d.setBorn(datos[1]);
                d.setCity(datos[2]);
                d.setPrSt(datos[3]);
                d.setCntry(datos[4]);
                d.setNat(datos[5]);
                d.setHt(Byte.valueOf(datos[6]));
                d.setWt(Short.parseShort(datos[7]));
                if (!datos[8].isEmpty() || !datos[8].trim().equals("")) {
                    d.setDftYr(Short.parseShort(datos[8]));
                } else {
                    d.setDftYr(Short.parseShort("0"));
                }
                if (!datos[9].isEmpty() || !datos[9].trim().equals("")) {
                    d.setDftRd(Byte.parseByte(datos[9]));
                } else {
                    d.setDftRd(Byte.parseByte("0"));
                }
                if (!datos[10].isEmpty() || !datos[10].trim().equals("")) {
                    d.setOvrl(Short.parseShort(datos[10]));
                } else {
                    d.setOvrl(Short.parseShort("0"));
                }

                d.setHand(datos[11].charAt(0));
                d.setLastName(datos[12]);
                d.setFirstName(datos[13]);
                d.setPosition(datos[14]);
                d.setTeam(datos[15]);
                if (!datos[16].isEmpty() || !datos[16].trim().equals("")) {
                    d.setGP(Byte.parseByte(datos[16]));
                } else {
                    d.setGP(Byte.parseByte("0"));
                }
                list.add(d);
            }
            br.close();
            return list;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
