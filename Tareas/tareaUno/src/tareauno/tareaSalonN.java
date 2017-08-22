/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * Esta es para un CSV gernearl, se ponen los campos que quieres y palabras claves a buscaer,
 * tambien lo puede ignorar
 * el importante es tareaSalon
 */
public class tareaSalonN {

    private static String RUTA = "./resources/hockey.csv";
    private static List<String> list;
    private static String[] CAMPOS;
    private static List<Integer> INDEX;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Que campos quiere de CSV (separelos por una ',' ejemplo: nombre,numero,correo)");
        System.out.print("Campos:");
        CAMPOS = sc.nextLine().split(",");
        System.out.println("Busca en el CSV");
        System.out.print("Palabra a buscar: ");
        String palabra = sc.nextLine();
        leer(RUTA, palabra);
        System.out.println("");
        mostrar(list);
    }

    private static void mostrar(List<String> l) {
        for (int i = 0; i < CAMPOS.length; i++) {
            if (i == 0) {
                System.out.print(CAMPOS[i].trim());
            } else {
                System.out.print(", " + CAMPOS[i].trim());
            }
        }
        System.out.println("");
        l.forEach((txt) -> {
            System.out.println(txt.replaceAll(",", ", "));
        });
        System.out.println("");
    }

    private static void leer(String ruta, String palabra) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
                String line = br.readLine();
                list = new ArrayList<>();
                if (line != null) {
                    String[] cam = line.split(",");
                    INDEX = new ArrayList<>();
                    for (String campo : CAMPOS) {
                        for (int i = 0; i < cam.length; i++) {
                            if (cam[i].toLowerCase().trim().equals(campo.toLowerCase().trim())) {
                                if (!INDEX.contains(i)) {
                                    INDEX.add(i);
                                    break;
                                }
                            }

                            if (i == cam.length - 1) {
                                INDEX.add(-100);
                            }
                        }
                    }
                }

                while ((line = br.readLine()) != null) {
                    String[] datos = line.split(",");
                    String d = "";
                    boolean paso = false;

                    for (int i : INDEX) {
                        if (i == -100) {
                            continue;
                        }
                        if (datos[i].toLowerCase().contains(palabra.trim().toLowerCase())) {
                            paso = true;
                            break;
                        }
                    }

                    if (!paso) {
                        continue;
                    }

                    for (int i : INDEX) {
                        if (i != -100) {
                            d += datos[i] + ",";
                        } else {
                            d += "CampoNoEncontrado,";
                        }
                    }
                    d = d.substring(0, d.length() - 1);
                    list.add(d);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
