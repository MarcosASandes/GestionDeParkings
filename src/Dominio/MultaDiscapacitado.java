package Dominio;
public class MultaDiscapacitado extends Multa {

    public MultaDiscapacitado(String desc, Estadia pEst) {
        super(desc, pEst);
    }

    @Override
    public double CalcularMonto(Estadia pEstadia) {
        return 250;
    }

}
