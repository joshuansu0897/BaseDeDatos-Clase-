/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author joshuansu
 */
public class TareaDos {

    private static final String RUTA_FOLDER = "./resources/";
    private static final String FILE_HOCKEY = "hockey.csv";
    private static List<String> list;

    public static void main(String[] args) throws FileNotFoundException {
        leer(RUTA_FOLDER + FILE_HOCKEY);
        mostrar(list);
        guardar();
    }

    private static void leer(String ruta) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
                String line = br.readLine();
                list = new ArrayList<>();
                int i = 0;
                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(",");
                    String d = "";

                    d += datos[12] + ", ";
                    d += datos[13] + ", ";
                    d += datos[15] + ", ";

                    BigDecimal salary = new BigDecimal(datos[0].trim());
                    BigDecimal gp = new BigDecimal(datos[16].trim());
                    
                    d += salary.divide(gp, 2, RoundingMode.HALF_UP).toString();

                    System.out.println(i++);
                    list.add(d);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mostrar(List<String> l) {
        System.out.println("Last Name, First Name, Team, SPG");
        for (int i = 0; i < l.size(); i++) {
            System.out.println(i + " " + l.get(i));
        }
        System.out.println("");
    }

    private static void guardar() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre del nuevo CSV: ");
        String nombre = sc.nextLine();
        PrintWriter pw = new PrintWriter(new File(RUTA_FOLDER + nombre));
        StringBuilder sb = new StringBuilder();

        list.forEach(d -> {
            sb.append(d);
            sb.append("\n");
        });

        pw.write(sb.toString());
        pw.close();
        System.out.println("Se Guardo Exitosamente!");
    }

}
