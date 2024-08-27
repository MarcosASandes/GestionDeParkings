package Servicios;
import Dominio.Anomalia;
import Dominio.Cochera;
import Dominio.Estadia;
import Dominio.Parking;
import Dominio.TipoVehiculo;
import Excepciones.ParkingException;
import java.util.ArrayList;
import simuladortransito.Estacionable;

public class ServicioParking{
    private ArrayList<Parking> listaParking;
    private static ServicioParking instancia = new ServicioParking();

    
    public static ServicioParking getInstancia() {
	return instancia;
    }

    private ServicioParking() {
        this.listaParking = new ArrayList<Parking>();
    }
    
    public ArrayList<Estacionable> getEstacionables(){
        ArrayList<Estacionable> estacionables = new ArrayList<Estacionable>();
        for(Parking p : this.listaParking){
            estacionables.addAll(p.getEstacionables());
        }
        return estacionables;
    }
    
    
    public void agregarParking(Parking p) throws ParkingException {
        if (p != null) {
            p.Validar();
            listaParking.add(p);
        } else {
            throw new ParkingException("El parking no puede ser nulo.");
        }   
    }
    
    public void agregarParking(ArrayList<Parking> parkings){
        for(Parking p : parkings){
            this.listaParking.add(p);
        }
    }

    public ArrayList<Parking> getListaParking() {
	return listaParking;
    }
    public ArrayList<Estadia> getListaEstadiasTotales() {
	ArrayList<Estadia> estadias = new ArrayList<Estadia>();
        for(Parking p : this.listaParking){
            estadias.addAll(p.getEstadias());
        }
        return estadias;
    }
    public Estadia getUltimaEstadiaDeCochera(Cochera c) {
        Estadia estadiaAux = null;
        for (Estadia est : getListaEstadiasTotales()) {
            if (est.getSalida() == null && est.getCochera().equals(c))
            {
                estadiaAux = est;
            }
        }
        return estadiaAux;
    }
    public ArrayList<Anomalia> getAnomalias() {
        ArrayList<Anomalia> anomalias = new ArrayList<Anomalia>();

        for (Parking p : listaParking) {
            if (!p.getAnomalias().isEmpty()) {
                anomalias.addAll(p.getAnomalias());
            }
        }
        return anomalias;
    }
    
    public double CalcularPromedioTarifa(TipoVehiculo tp){
        int cantParkings = this.listaParking.size();
        double suma = 0;
        for(Parking p: this.listaParking){
            suma += p.getTarifaPorTipoVehiculo(tp);
        }
        
        double promedio = 0;
        
        if(suma != 0){
            promedio = suma / cantParkings;
        }
        return promedio;
    }
    
}
