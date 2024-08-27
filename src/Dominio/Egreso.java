
package Dominio;

import java.time.LocalDateTime;


public class Egreso extends Evento {
    
    public Egreso(LocalDateTime pHora, Estadia pEstadia) {
        super(pHora, pEstadia);
    }

    @Override
    public String getTipoEvento() {
        return "Egreso";
    }
    
}
