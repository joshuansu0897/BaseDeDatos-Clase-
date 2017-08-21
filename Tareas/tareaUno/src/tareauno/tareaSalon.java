package tareauno;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author joshuansu
 */
public class tareaSalon {

    private static String RUTA = "./resources/hockey.csv";
    private static List<hockeyDatos> list;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Busca en 'LastName' y 'FirstName'");
        System.out.print("Palabra a buscar: ");
        String palabra = sc.nextLine();
        leer(RUTA, palabra);
        System.out.println("");
        mostrar(list);

    }

    private static void mostrar(List<hockeyDatos> l) {
        System.out.println("First Name, Last Name, Position, Team, Salary");
        l.forEach(p -> System.out.println(p.getFirstName()+", "+p.getLastName()+", "+p.getPosition()+", "+p.getTeam()+", "+p.getSalary()));
        System.out.println("");
    }

    private static void leer(String ruta, String palabra) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String line;
            list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (line.contains("Salary") || !line.toLowerCase().contains(palabra.toLowerCase())) {
                    continue;
                }
                String[] datos = line.split(",");
                hockeyDatos d = new hockeyDatos();
                d.setSalary(datos[0]);
                d.setBorn(datos[1]);
                d.setCity(datos[2]);
                d.setPrSt(datos[3]);
                d.setCntry(datos[4]);
                d.setNat(datos[5]);
                d.setHt(datos[6]);
                d.setWt(datos[7]);
                d.setDftYr(datos[8]);
                d.setDftRd(datos[9]);
                d.setOvrl(datos[10]);
                d.setHand(datos[11]);
                d.setLastName(datos[12]);
                d.setFirstName(datos[13]);
                d.setPosition(datos[14]);
                d.setTeam(datos[15]);
                d.setGP(datos[16]);
                list.add(d);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
