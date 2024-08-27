
package Dominio;

/*
El factor de demanda se
reduce lentamente.
*/
public class FormulaEstable implements FactorDemandaStrategy {
    @Override
    public double calcularFactorDemanda(Parking parking, int cantSegundos) {
        double cantRestante = 0.01 * cantSegundos;
        double totalRestante = parking.getFactorDemanda() - cantRestante;
        
        if(totalRestante < 0.25){
            totalRestante = 0.25;
            
        }
        return totalRestante;
    }
    
}
