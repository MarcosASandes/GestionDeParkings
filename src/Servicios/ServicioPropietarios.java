package Servicios;
import Dominio.Propietario;
import Excepciones.PropietarioException;
import java.util.ArrayList;

public class ServicioPropietarios {
    private ArrayList<Propietario> propietarios;
    private static ServicioPropietarios instancia = new ServicioPropietarios();
    
    public static ServicioPropietarios getInstancia() {
	return instancia;
    }
    
    private ServicioPropietarios(){
        this.propietarios = new ArrayList<Propietario>();
    }
    
    
    public void agregarPropietario(Propietario pProp) throws PropietarioException {
        if (pProp != null) {
            pProp.Validar();
            this.propietarios.add(pProp);
        } else {
            throw new PropietarioException("El propietario no puede ser nulo.");
        }  
    }
    
    public void agregarPropietario(ArrayList<Propietario> props){
        for(Propietario p : props){
            this.propietarios.add(p);
        }
    }

    public ArrayList<Propietario> getListaPropietario() {
	return propietarios;
    }

}
