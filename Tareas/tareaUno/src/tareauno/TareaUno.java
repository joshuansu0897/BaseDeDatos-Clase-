package tareauno;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TareaUno {

    private static List<DatosObj> list;
    private static String RUTA = "./resources/datos.csv";

    public static void main(String[] args) throws FileNotFoundException {
        leer(RUTA);
        Scanner sc = new Scanner(System.in);
        while (menu(sc)) {
        }
    }

    private static void leer(String ruta) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String line;
            list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                DatosObj d = new DatosObj();
                d.setNombre(datos[0]);
                d.setNumero(datos[1]);
                d.setCorreo(datos[2]);
                list.add(d);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean menu(Scanner sc) throws FileNotFoundException {
        System.out.println("Que quieres hacer?");
        System.out.println("1.- Mostrar el contenido del CSV");
        System.out.println("2.- Buscar en el CSV");
        System.out.println("3.- Modificar el contenido del CSV");
        System.out.println("4.- Agregar datos CSV");
        System.out.println("5.- Borrar datos CSV");
        System.out.println("0.- Salir y Guardar");
        System.out.println("99.- Salir SIN Guardar");
        String a = sc.nextLine();
        System.out.println("");
        switch (a) {
            case "1":
                mostrar(list);
                return true;
            case "2":
                buscarMenu();
                return true;
            case "3":
                modificarMenu();
                return true;
            case "4":
                agregarMenu();
                return true;
            case "5":
                borrarMenu();
                return true;
            case "0":
                guardar();
                return false;
            case "99":
                return false;
            default:
                System.out.println("No existe esa opcion");
                return true;
        }
    }

    private static void mostrar(List<DatosObj> l) {
        System.out.printf("%-35s %-20s %-20s\n", "Nombre", "Numero", "Correo");
        l.forEach(p -> System.out.printf("%-35s %-20s %-20s\n", p.getNombre(), p.getNumero(), p.getCorreo()));
        System.out.println("");
    }

    private static void buscarMenu() {
        Scanner sc = new Scanner(System.in);
        String opt;
        do {
            System.out.println("Mediante que quieres buscar?");
            System.out.println("1.- Todo");
            System.out.println("2.- Nombre");
            System.out.println("3.- Numero");
            System.out.println("4.- Correo");
            System.out.println("5.- Atras");
            opt = sc.nextLine();
            System.out.println("");
            if (opt.equals("5")) {
                break;
            }
        } while (buscar(opt, sc));
    }

    private static boolean buscar(String opt, Scanner sc) {
        List<DatosObj> l;
        System.out.print("Palabra a buscar: ");
        String palabra = sc.nextLine();
        System.out.println("");
        switch (opt) {
            case "2":
                l = list.stream()
                        .filter(c -> c.getNombre().toLowerCase().contains(palabra.toLowerCase()))
                        .collect(Collectors.toList());
                mostrar(l);
                return true;
            case "3":
                l = list.stream()
                        .filter(c -> c.getNumero().toLowerCase().contains(palabra.toLowerCase()))
                        .collect(Collectors.toList());
                mostrar(l);
                return true;
            case "4":
                l = list.stream()
                        .filter(c -> c.getCorreo().toLowerCase().contains(palabra.toLowerCase()))
                        .collect(Collectors.toList());
                mostrar(l);
                return true;
            default:
                System.out.println("No existe la opcion '" + opt + "', asi que se buscara mediante 'Todo'");
            case "1":
                l = list.stream()
                        .filter(c -> c.getNombre().toLowerCase().contains(palabra.toLowerCase())
                        || c.getNumero().toLowerCase().contains(palabra.toLowerCase())
                        || c.getCorreo().toLowerCase().contains(palabra.toLowerCase()))
                        .collect(Collectors.toList());
                mostrar(l);
                return true;
        }
    }

    private static void modificarMenu() {
        Scanner sc = new Scanner(System.in);
        String a;
        do {
            mostrarConIndice(list);
            System.out.println("'atras' \t Regresa al menu principal");
            System.out.println("");
            System.out.print("Numero del que decea modificar :");
            a = sc.nextLine();
            System.out.println("");
        } while (modificar(a, sc));
    }

    private static boolean modificar(String a, Scanner sc) {
        if (a.toLowerCase().equals("atras")) {
            return false;
        }

        if (a.matches("[0-9]*")) {
            int n = Integer.parseInt(a);
            if (n < list.size()) {
                DatosObj d = list.get(n);
                SetearDatos(d, sc);
                System.out.println("");
                return true;
            } else {
                System.out.println("Ese indice no existe en la lista");
                System.out.println("");
                return true;
            }
        } else {
            System.out.println("La Opcion '" + a + "' No existe");
            System.out.println("");
            return true;
        }
    }

    private static void guardar() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(RUTA));
        StringBuilder sb = new StringBuilder();

        list.forEach(d -> {
            sb.append(d.getNombre());
            sb.append(',');
            sb.append(d.getNumero());
            sb.append(',');
            sb.append(d.getCorreo());
            sb.append('\n');
        });

        pw.write(sb.toString());
        pw.close();
        System.out.println("Se Guardo Exitosamente!");
    }

    private static void agregarMenu() {
        Scanner sc = new Scanner(System.in);
        String a;
        do {
            System.out.println("Que decea hacer?");
            System.out.println("1.- Nuevo");
            System.out.println("2.- Atras");
            a = sc.nextLine();
            System.out.println("");
        } while (agregar(a, sc));

    }

    private static boolean agregar(String a, Scanner sc) {
        switch (a) {
            case "1":
                SetearDatos(null, sc);
                System.out.println("");
                return true;
            case "2":
                return false;
            default:
                System.out.println("La Opcion '" + a + "', No existe");
                return true;
        }
    }

    private static void borrarMenu() {
        Scanner sc = new Scanner(System.in);
        String a;
        do {
            mostrarConIndice(list);
            System.out.println(list.size() + " \t BORRAR TODO");
            System.out.println("'atras' \t Regresa al menu principal");
            System.out.println("");
            System.out.print("Indice1 del que decea Borrar :");
            a = sc.nextLine();
            System.out.println("");
        } while (borrar(a));
    }

    private static void mostrarConIndice(List<DatosObj> list) {       
        System.out.printf("%-10s %-35s %-20s %-20s\n", "Indice","Nombre", "Numero", "Correo");
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%-10s %-35s %-20s %-20s\n", i, list.get(i).getNombre(), list.get(i).getNumero(), list.get(i).getCorreo());
        }
        System.out.println("");
    }

    private static void SetearDatos(DatosObj d, Scanner sc) {
        if (d != null) {
            System.out.print("Cambiar " + d.getNombre() + " por:");
            d.setNombre(sc.nextLine());

            System.out.print("Cambiar " + d.getNumero() + " por:");
            d.setNumero(sc.nextLine());

            System.out.print("Cambiar " + d.getCorreo() + " por:");
            d.setCorreo(sc.nextLine());
        } else if (d == null) {
            d = new DatosObj();
            System.out.print("Nombre :");
            d.setNombre(sc.nextLine());

            System.out.print("Numero :");
            d.setNumero(sc.nextLine());

            System.out.print("Correo :");
            d.setCorreo(sc.nextLine());
            list.add(d);
        }
    }

    private static boolean borrar(String a) {
        if (a.toLowerCase().equals("atras")) {
            return false;
        }

        if (a.matches("[0-9]*")) {
            int n = Integer.parseInt(a);
            if (n == list.size()) {
                list.clear();
                return true;
            } else if (n < list.size()) {
                list.remove(n);
                return true;
            } else {
                System.out.println("Ese indice no existe en la lista");
                return true;
            }
        } else {
            System.out.println("La Opcion '" + a + "' No existe");
            return true;
        }
    }
}
