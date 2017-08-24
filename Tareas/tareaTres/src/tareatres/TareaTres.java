/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareatres;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author joshuansu
 */
public class TareaTres {

    private static final String RUTA_FOLDER = "./resources/";
    private static final String FILE_HOCKEY = "hockey.csv";
    private static Map<String, String> list;

    public static void main(String[] args) throws FileNotFoundException {
        leer(RUTA_FOLDER + FILE_HOCKEY);
        mostrar(list);
        guardar();
    }

    private static void leer(String ruta) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
                String line = br.readLine();
                list = new HashMap<>();
                while ((line = br.readLine()) != null) {
                    
                    String[] datos = line.split(",");
                    if(datos[15].contains("/")){
                        continue;
                    }
                    if (!list.containsKey(datos[15])) {
                        list.put(datos[15], datos[0]);
                    } else {
                        BigDecimal li = new BigDecimal(list.get(datos[15]));
                        BigDecimal csv = new BigDecimal(datos[0]);
                        list.replace(datos[15], li.add(csv).toString());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mostrar(Map<String, String> l) {
        System.out.println("Team, Salary");
        list.entrySet().forEach((entry) -> {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        });
        System.out.println("");
    }

    private static void guardar() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre del nuevo CSV: ");
        String nombre = sc.nextLine();
        PrintWriter pw = new PrintWriter(new File(RUTA_FOLDER + nombre));
        StringBuilder sb = new StringBuilder();

        sb.append("Team");
        sb.append(",");
        sb.append("Salary");
        sb.append("\n");

        list.entrySet().forEach((entry) -> {
            sb.append(entry.getKey());
            sb.append(",");
            sb.append(entry.getValue());
            sb.append("\n");
        });

        pw.write(sb.toString());
        pw.close();
        System.out.println("Se Guardo Exitosamente!");
    }

}
