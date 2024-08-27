package Dominio;

import Excepciones.EtiquetaException;

public class Etiqueta {
    private String Nombre;
    
    public Etiqueta(String pNombre){
        this.Nombre = pNombre;
    }

    public void Validar() throws EtiquetaException{
        try{
            this.ValidarNombre();
        } catch (EtiquetaException e) {
            throw new EtiquetaException(e.getMessage());
        }
    }
    
    private void ValidarNombre() throws EtiquetaException {
        if (this.Nombre == null) {
            throw new EtiquetaException("El nombre no puede ser nulo.");
        }
    }
    
    
    /**
     * @return the Nombre
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    

}
