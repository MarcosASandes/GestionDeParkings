package Dominio;

import Excepciones.TipoVehiculoException;

public class TipoVehiculo {
    private String descripcion;

    public TipoVehiculo(String pDesc){
        this.descripcion = pDesc;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void Validar() throws TipoVehiculoException{
        try{
            this.ValidarDesc();
        } catch (TipoVehiculoException e) {
            throw new TipoVehiculoException(e.getMessage());
        }
    }
    
    private void ValidarDesc() throws TipoVehiculoException{
        if (this.descripcion == null) {
            throw new TipoVehiculoException("La descripcion no puede ser nula.");
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Si es el mismo objeto
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Si el objeto es nulo o de diferente clase
        }
        TipoVehiculo that = (TipoVehiculo) obj; // Cast al tipo correcto
        return descripcion != null ? descripcion.equals(that.descripcion) : that.descripcion == null; // Comparación de las descripciones
    }

    @Override
    public int hashCode() {
        return descripcion != null ? descripcion.hashCode() : 0; // Generar hashCode basado en descripcion
    }
}
