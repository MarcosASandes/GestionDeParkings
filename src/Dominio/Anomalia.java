package Dominio;

import Excepciones.AnomaliaException;
import Excepciones.CocheraException;
import Excepciones.EstadiaException;
import Excepciones.ParkingException;
import Excepciones.PropietarioException;
import Excepciones.TipoVehiculoException;
import Excepciones.VehiculoException;
import java.time.LocalDateTime;

public class Anomalia {

    private LocalDateTime Fecha;
    private String CodigoError;
    private Estadia Estadia;

    public Anomalia(LocalDateTime pFecha, String pCodError, Estadia estadia) {
        this.Fecha = pFecha;
        this.CodigoError = pCodError;
        this.Estadia = estadia;
    }

    public Estadia getEstadia() {
        return Estadia;
    }

    public LocalDateTime getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDateTime Fecha) {
        this.Fecha = Fecha;
    }

    public String getCodigoError() {
        return CodigoError;
    }

    public void setCodigoError(String CodigoError) {
        this.CodigoError = CodigoError;
    }

    public void Validar() throws VehiculoException, EstadiaException, CocheraException, AnomaliaException, ParkingException, PropietarioException, TipoVehiculoException {
        try {
            this.ValidarFecha();
            this.ValidarCodigo();
            this.ValidarEstadia();
        } catch (IllegalArgumentException e) {
            throw new AnomaliaException(e.getMessage());
        }
    }

    private void ValidarFecha() throws AnomaliaException {
        if (this.Fecha == null) {
            throw new AnomaliaException("La fecha no puede ser nula.");
        }
    }

    private void ValidarCodigo() throws AnomaliaException {
        if (this.CodigoError == null) {
            throw new AnomaliaException("El codigo no puede ser nulo.");
        }
    }

    private void ValidarEstadia() throws VehiculoException, EstadiaException, CocheraException, ParkingException, PropietarioException, TipoVehiculoException, TipoVehiculoException {
        if (this.Estadia != null) {
            this.Estadia.Validar();
        } else {
            throw new EstadiaException("La estadia es nula.");
        }
    }

}
