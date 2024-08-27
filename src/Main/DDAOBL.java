
package Main;

import Dominio.Parking;
import Dominio.Propietario;
import Dominio.SensorSim;
import Pantallas.UIMenu;
import Servicios.Fachada;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import simuladortransito.ConfiguracionException;
import simuladortransito.Estacionable;
import simuladortransito.FlujoEgreso;
import simuladortransito.FlujoIngreso;
import simuladortransito.Modo;
import simuladortransito.PerfilEgreso;
import simuladortransito.PerfilIngreso;
import simuladortransito.Periodo;
import simuladortransito.SimuladorTransito;
import simuladortransito.Transitable;

public class DDAOBL {

    private static SimuladorTransito sim;
    public static Instant tiempoInicio;
    public static void main(String[] args) {
        // TODO code application logic here
        tiempoInicio = Instant.now();
        try {
            DatosDePrueba.cargar();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DDAOBL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Para testing, ingresar en el string lo que se desea testear. Consultar metodo
        //console_test en DatosDePrueba.
        //DatosDePrueba.console_test("Cocheras");
        
        Fachada servicios = Fachada.getInstancia();
        UIMenu menu = new UIMenu();
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);

        sim = SimuladorTransito.getInstancia();

        ArrayList<Parking> parkings = servicios.getParkings();
        ArrayList<Estacionable> cocheras = servicios.getEstacionables();
        ArrayList<Propietario> propietarios = servicios.getPropietarios();
        ArrayList<Transitable> vehiculos = servicios.getTransitables();

        PerfilEgreso perfilConAnomalia;

        perfilConAnomalia = new PerfilEgreso.Builder()
                .generarAnomalia(Modo.SIEMPRE)
                .build();

        PerfilIngreso perfilConInfracciones = new PerfilIngreso.Builder()
                .invadirEstacionableDiscapacitado(Modo.ALEATORIO)
                .invadirEstacionableElectrico(Modo.ALEATORIO)
                .invadirEstacionableEmpleadoInterno(Modo.ALEATORIO)
                .build();

        
        LinkedHashMap<Transitable, Estacionable> preasignacion1 = new LinkedHashMap();
        preasignacion1.put(vehiculos.get(0), cocheras.get(0));

        LinkedHashMap<Transitable, Estacionable> preasignacion2 = new LinkedHashMap();
        preasignacion2.put(vehiculos.get(1), cocheras.get(1));

        LinkedHashMap<Transitable, Estacionable> preasignacion3 = new LinkedHashMap();
        preasignacion3.put(vehiculos.get(3), cocheras.get(0));
        
        LinkedHashMap<Transitable, Estacionable> preasignacion6 = new LinkedHashMap();
        preasignacion6.put(vehiculos.get(6), cocheras.get(1));

        LinkedHashMap<Transitable, Estacionable> preasignacion4 = new LinkedHashMap();
        preasignacion4.put(vehiculos.get(4), cocheras.get(4));

        LinkedHashMap<Transitable, Estacionable> preasignacion5 = new LinkedHashMap();
        preasignacion5.put(vehiculos.get(5), cocheras.get(4));
        
        LinkedHashMap<Transitable, Estacionable> preasignacion7 = new LinkedHashMap();
        preasignacion7.put(vehiculos.get(7), cocheras.get(7));

        LinkedHashMap<Transitable, Estacionable> preasignacion8 = new LinkedHashMap();
        preasignacion8.put(vehiculos.get(8), cocheras.get(7));
        

        sim.addTransitables(vehiculos);
        sim.addEstacionables(cocheras);
        try {
            sim.pausarLogger();
            sim.programar(new FlujoIngreso("ingreso1", new Periodo(2, 1), preasignacion1));
            sim.programar(new FlujoIngreso("ingreso2", new Periodo(4, 1), preasignacion2));
            sim.programar(new FlujoIngreso("ingreso3", new Periodo(6, 1), preasignacion3));//ANOMALIA HOUDINI Caso1
            sim.programar(new FlujoIngreso("ingreso4", new Periodo(8, 1), preasignacion6));//ANOMALIA HOUDINI Caso2
            sim.programar(new FlujoIngreso("ingreso5", new Periodo(10, 1), preasignacion4));
            sim.programar(new FlujoEgreso("egreso6", new Periodo(12, 1), preasignacion5));//ANOMALIA T1 Y T2 Caso1
            sim.programar(new FlujoIngreso("ingreso7", new Periodo(14, 1), preasignacion7));
            sim.programar(new FlujoEgreso("egreso8", new Periodo(16, 1), preasignacion8));//ANOMALIA T1 Y T2 Caso2

            sim.programar(new FlujoIngreso("Ingreso de prueba", new Periodo(18, 7), 30));
            sim.programar(new FlujoEgreso("Egreso de prueba", new Periodo(26, 4), 10));
            sim.programar(new FlujoEgreso("anomalias", new Periodo(31, 5), 5, perfilConAnomalia));//ANOMALIA MISTERY
            
            sim.iniciar(new SensorSim());
        }catch (ConfiguracionException ex) {

        }
    }

}
