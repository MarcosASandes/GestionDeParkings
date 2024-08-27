
package Controller;

import Dominio.SensorSim;
import static Main.DDAOBL.tiempoInicio;
import Pantallas.VistaIngresoEgreso;
import Servicios.Fachada;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import simuladortransito.ConfiguracionException;
import simuladortransito.Estacionable;
import simuladortransito.FlujoEgreso;
import simuladortransito.FlujoIngreso;
import simuladortransito.Periodo;
import simuladortransito.SimuladorTransito;
import simuladortransito.Transitable;


public class ControladorVistaIngresoEgreso {
    private final VistaIngresoEgreso vista;
    private final Fachada servicios = Fachada.getInstancia();
    private static SimuladorTransito sim= SimuladorTransito.getInstancia();
    private ArrayList<Estacionable> cocheras = new ArrayList<>();
    private ArrayList<Transitable> vehiculos = new ArrayList<>();
    
    public ControladorVistaIngresoEgreso(VistaIngresoEgreso vista) {
        this.vista = vista;
        cocheras = servicios.getEstacionables();
        vehiculos = servicios.getTransitables();
        generarVista(); 
    }
    
    private void generarVista() {
       vista.mostrarVehiculosCocheras(cocheras, vehiculos);
    }
        
    public void cargarIngreso(int vehiculoSeleccionado, int cocheraSeleccionada) {
       LinkedHashMap<Transitable, Estacionable> preasignacionManualIng = new LinkedHashMap();
       preasignacionManualIng.put(vehiculos.get(vehiculoSeleccionado), cocheras.get(cocheraSeleccionada));
       long segundosTranscurridos = Duration.between(tiempoInicio, Instant.now()).getSeconds();
       try{
       sim.programar(new FlujoIngreso("IngresoManual", new Periodo((int)segundosTranscurridos, 1), preasignacionManualIng));
       sim.iniciar(new SensorSim());
       }
       catch(ConfiguracionException ex){}
    }

    public void cargarEgreso(int vehiculoSeleccionado, int cocheraSeleccionada) {
       LinkedHashMap<Transitable, Estacionable> preasignacionManualEgr = new LinkedHashMap();
       preasignacionManualEgr.put(vehiculos.get(vehiculoSeleccionado), cocheras.get(cocheraSeleccionada));
       long segundosTranscurridos = Duration.between(tiempoInicio, Instant.now()).getSeconds();
       try{
       sim.programar(new FlujoEgreso("EgresoManual", new Periodo((int)segundosTranscurridos, 1), preasignacionManualEgr));
       sim.iniciar(new SensorSim());
       }
       catch(ConfiguracionException ex){}
    }
    
}
