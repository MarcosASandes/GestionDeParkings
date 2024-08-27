package Dominio;
public class MultaEmpleado extends Multa {

    public MultaEmpleado(String desc, Estadia pEst) {
        super(desc, pEst);
    }

    
    
    @Override
    public double CalcularMonto(Estadia pEstadia) {
        return pEstadia.getDuracionEstadiaEnUT() / 10.0;
    }

}
