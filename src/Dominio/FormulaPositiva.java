
package Dominio;


/*
El factor de demanda
aumenta moderadamente
en función de la ocupación.
*/
public class FormulaPositiva implements FactorDemandaStrategy {

    @Override
    public double calcularFactorDemanda(Parking parking, int cantSegundos) {
        double porcentajeOcupacion = parking.calcularPorcentajeOcupacion();
        double cantRestante;
        double factorActual = parking.getFactorDemanda();
        
        if(porcentajeOcupacion < 33){
            cantRestante = 0.5 * cantSegundos;
            factorActual += cantRestante;
        }
        else if(porcentajeOcupacion > 66){
            cantRestante = 0.15 * cantSegundos;
            factorActual += cantRestante;
        }
        else{
            cantRestante = 0.1 * cantSegundos;
            factorActual += cantRestante;
        }
        
        if(factorActual > 10){
            factorActual = 10;
        }
        return factorActual;
    }
    
}
