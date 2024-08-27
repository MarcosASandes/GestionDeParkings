package Pantallas;

import Dominio.Estadia;
import Dominio.Etiqueta;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class UIConsola {

    protected static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void print(String s) {
        System.out.print(s);
    }

    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(int i) {
        System.out.print(i);
    }

    public static void println(int i) {
        System.out.println(i);
    }
public static void println(int i, int i2) {
        System.out.println(i+"-"+i2);
    }
    public static String leer() {
        try {
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    public static String leer(String s) {
        print(s);
        return leer();
    }

    public static int leerInt() {
        return leerInt(null);
    }

    public static int leerInt(String msg) {
        int num = -1;
        boolean ok = false;
        while (!ok) {
            try {
                if (msg != null) {
                    print(msg);
                }
                num = Integer.parseInt(leer());
                ok = true;
            } catch (Exception e) {
            }
        }
        return num;
    }

    public static void titulo(String titulo) {
        println(subrrayado(titulo.length() + 8));
        println(subrrayado(3) + " " + titulo + " " + subrrayado(3));
        println(subrrayado(titulo.length() + 8));
    }

    private static String subrrayado(int cant) {
        return "=".repeat(cant);
    }

    public static int menu(ArrayList opciones) {
        for (int x = 0; x < opciones.size(); x++) {
            println((x + 1) + "-" + opciones.get(x).toString());
        }
        int opcion;
        do {
            opcion = leerInt("opcion:") - 1;

        } while (opcion < 0 || opcion >= opciones.size());

        return opcion;
    }
    
    public static void mostrarLista(ArrayList opciones) {
        for (int x = 0; x < opciones.size(); x++) {
            Estadia est = (Estadia) opciones.get(x);
            int segundos = est.getEntrada().getSecond();
            int minutos = est.getEntrada().getMinute();
            print(est.getVehiculo().getPatente()+ "//"+ minutos +":"+ segundos+"////");
            
            if(est.getSalida()!=null){
            segundos = est.getSalida().getSecond();
            minutos = est.getSalida().getMinute();
            println(est.getVehiculo().getPatente()+ "//"+ minutos +":"+ segundos);}
            else
                println(est.getVehiculo().getPatente()+ "//sin salida");
        }
    }
    public static void mostrarListaSalida(ArrayList opciones) {
        for (int x = 0; x < opciones.size(); x++) {
            Estadia est = (Estadia) opciones.get(x);
            if(est.getSalida()!=null){
            int segundos = est.getSalida().getSecond();
            int minutos = est.getSalida().getMinute();
            println(est.getVehiculo().getPatente()+ "//"+ minutos +":"+ segundos);}
            else
                println(est.getVehiculo().getPatente()+ "//");
        }
    }
    
    public static void mostrarListaEtiquetas(ArrayList<Etiqueta> opciones){
        for(Etiqueta etq : opciones){
            String muestra = etq.getNombre();
            println(muestra);
        }
    }
}
