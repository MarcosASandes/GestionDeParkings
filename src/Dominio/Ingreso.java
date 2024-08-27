
package Dominio;

import java.time.LocalDateTime;


public class Ingreso extends Evento {
    
    public Ingreso(LocalDateTime pHora, Estadia pEstadia) {
        super(pHora, pEstadia);
    }

    @Override
    public String getTipoEvento() {
        return "Ingreso";
    }
    
    
}
