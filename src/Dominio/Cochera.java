package Dominio;

import Excepciones.CocheraException;
import Excepciones.EtiquetaException;
import Excepciones.ParkingException;
import java.util.ArrayList;
import java.util.Objects;
import simuladortransito.Estacionable;

public class Cochera implements Estacionable {
    private static int lastCod = 1;
    private String Codigo;
    private boolean Ocupada;
    private Parking park;
    private ArrayList<Etiqueta> etiquetas;

    
    public Cochera(Etiqueta e, Parking p) {
        this.park = p;
        this.Codigo = "CCH" + String.valueOf(lastCod);
        this.Ocupada = false;
        this.etiquetas = new ArrayList<Etiqueta>();
        if(e != null){
            this.etiquetas.add(e);
        }
        lastCod++;
    }

    

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public boolean isOcupada() {
        return Ocupada;
    }

    public void setOcupada() {
        this.Ocupada = true;
    }
    public void setLibre() {
        this.Ocupada = false;
    }
    @Override
    public String getCodigo() {
        return this.Codigo;
    }

    @Override
    public boolean esDiscapacitado() {
        boolean encontrado = false;
        for(Etiqueta et : etiquetas){
            if(et.getNombre() == "Discapacitado"){
                encontrado = true;
            }
        }
        return encontrado;
    }

    @Override
    public boolean esElectrico() {
        boolean encontrado = false;
        for(Etiqueta et : etiquetas){
            if(et.getNombre() == "Electrico"){
                encontrado = true;
            }
        }
        return encontrado;
    }

    @Override
    public boolean esEmpleado() {
        boolean encontrado = false;
        for(Etiqueta et : etiquetas){
            if(et.getNombre() == "Empleado"){
                encontrado = true;
            }
        }
        return encontrado;
    }
    
    public Parking getParking(){
        return this.park;
    }
    
    public void agregarEtiqueta(Etiqueta e) throws EtiquetaException{
        try{
            e.Validar();
            this.etiquetas.add(e);
        } catch(EtiquetaException exEtq){
            throw new EtiquetaException(exEtq.getMessage());
        }
    }
    
    public ArrayList<Etiqueta> getEtiquetas(){
        return this.etiquetas;
    }
        
    
    public void Validar() throws CocheraException, ParkingException {
        try {
            this.ValidarParking();
        } catch (CocheraException e) {
             throw new CocheraException(e.getMessage());
        }
    }
    
    
    private void ValidarParking() throws CocheraException, ParkingException{
        if (this.park == null) {
            throw new CocheraException("El parking no puede ser nulo.");
        }else{
            this.park.Validar();
        }
    }
    
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cochera cochera = (Cochera) o;
        return Objects.equals(Codigo, cochera.Codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Codigo);
    }
}
