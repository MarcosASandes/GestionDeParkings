
package Dominio;

import java.time.LocalDateTime;
import java.util.Objects;


/*
Esta clase se encarga de representar un evento (Ingreso/Egreso) del simulador,
la utilizamos para guardar cada evento que sucede en los distintos flujos asignados.
De esta clase hereda Ingreso y Egreso.
*/
public abstract class Evento {
    private LocalDateTime horaEvento;
    private Estadia estadiaEvento;
    
    public Evento(LocalDateTime pHora, Estadia pEstadia){
        this.horaEvento = pHora;
        this.estadiaEvento = pEstadia;
    }

    public abstract String getTipoEvento();
    
    
    /**
     * @return the horaEvento
     */
    public LocalDateTime getHoraEvento() {
        return horaEvento;
    }

    /**
     * @param horaEvento the horaEvento to set
     */
    public void setHoraEvento(LocalDateTime horaEvento) {
        this.horaEvento = horaEvento;
    }

    /**
     * @return the estadiaEvento
     */
    public Estadia getEstadiaEvento() {
        return estadiaEvento;
    }

    /**
     * @param estadiaEvento the estadiaEvento to set
     */
    public void setEstadiaEvento(Estadia estadiaEvento) {
        this.estadiaEvento = estadiaEvento;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(horaEvento, estadiaEvento);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return Objects.equals(horaEvento, evento.horaEvento) &&
               Objects.equals(estadiaEvento, evento.estadiaEvento);
    }
    
    
}
