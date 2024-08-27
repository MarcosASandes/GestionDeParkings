package Dominio;
public class UT {

    private int ValorUT = 1;
    private static UT instancia = new UT();

    public static UT getInstancia() {
        return instancia;
    }

    private UT() {

    }

    public int getValorUT() {
        return ValorUT;
    }

    public void setValorUT(int ValorUT) {
        this.ValorUT = ValorUT;
    }

    public int convertir_a_ut(int segundos) {
        return segundos * ValorUT;
    }

}
