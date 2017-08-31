package tareacuatro;

import java.util.List;
import java.util.Scanner;

public class TareaCuatro {

    private static final String PATH = "./resources/";
    private static final String FILE_HOCKEY = "hockey.csv";
    private static final Long B = Long.parseLong("156");

    public static void main(String[] args) {

        PlayersFile pf = new PlayersFile(PATH, FILE_HOCKEY);

        List<Players> list = pf.Read();

        System.out.println("Lista Sacada por 'PlayerFile'(Primeros 3)");
        for (int i = 0; i < 3; i++) {
            System.out.println(list.get(i).toString());
        }
        System.out.println("");

        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre del archivo (no olvides el .dat):");
        String nombre = sc.nextLine();
        System.out.println("");

        PlayersRandomFile prf = new PlayersRandomFile(PATH, nombre);

        System.out.println("Guardando...");
        prf.Write(list);
        System.out.println("");

        do {
            System.out.print("Que First Nombre: ");
            String fn = sc.nextLine();
            System.out.print("Que Last Nombre: ");
            String ln = sc.nextLine();
            System.out.println("Leyendo archivo .dat ...");
            Long ti = System.currentTimeMillis();
            prf.Read(fn, ln);
            System.out.println("Tiempo Tardado: " + (System.currentTimeMillis() - ti) + " milisegundos");
            System.out.println("");
        } while (true);
    }

}
