package Dominio;
public class MultaVehiculoElectrico extends Multa {

    public MultaVehiculoElectrico(String desc, Estadia pEst) {
        super(desc, pEst);
    }

    @Override
    public double CalcularMonto(Estadia pEstadia) {
        return (pEstadia.CalcularValorEstadia() * 0.50);
    }

}
