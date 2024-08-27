package Main;
import Dominio.Cochera;
import Dominio.Etiqueta;
import Dominio.Parking;
import Dominio.Propietario;
import Dominio.Tarifario;
import Dominio.TipoVehiculo;
import Dominio.Vehiculo;
import Excepciones.CocheraException;
import Excepciones.EtiquetaException;
import Excepciones.ParkingException;
import Excepciones.PropietarioException;
import Excepciones.TipoVehiculoException;
import Excepciones.VehiculoException;
import Pantallas.UIConsola;
import Servicios.Fachada;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import simuladortransito.Estacionable;
import simuladortransito.SimuladorTransito;
import simuladortransito.Transitable;

public class DatosDePrueba {

    private static SimuladorTransito sim;

    public static void cargar() throws VehiculoException, ParkingException, PropietarioException, TipoVehiculoException, EtiquetaException, Exception {
        Fachada servicios = Fachada.getInstancia();
        
        generarTiposDeVehiculo();
        generarParkings(3);
        generarCocheras(servicios.getParkings());
        generarPropietarios(50);
        generarVehiculos(200, servicios.getPropietarios());
    }

    public static void console_test(String opcion) {
        Fachada f = Fachada.getInstancia();

        switch (opcion) {
            case "Parkings":
                test_MostrarParkings(f.getParkings());
                break;
            case "Cocheras":
                test_MostrarCocheras(f.getEstacionables());
                break;
            case "Etiquetas":
                UIConsola.mostrarListaEtiquetas(generarEtiquetas());
                break;
            case "Propietarios":
                test_MostrarPropietarios(f.getPropietarios());
                break;
            case "Vehiculos":
                test_MostrarVehiculos(f.getTransitables());
                break;
            default:
                test_MostrarParkings(f.getParkings());
                test_MostrarPropietarios(f.getPropietarios());
                test_MostrarVehiculos(f.getTransitables());
                test_MostrarCocheras(f.getEstacionables());
                UIConsola.mostrarListaEtiquetas(generarEtiquetas());
                break;
        }
    }

    private static ArrayList<Tarifario> generarTarifarios() {
        Fachada servicios = Fachada.getInstancia();
        ArrayList<Tarifario> tarifarios = new ArrayList<Tarifario>();
        ArrayList<TipoVehiculo> tipos = servicios.getTiposDeVehiculo();

        for (TipoVehiculo tp : tipos) {
            if (tp.getDescripcion() == "Motocicleta") {
                Tarifario nuevoTarifario = new Tarifario(0.1, tp);
                tarifarios.add(nuevoTarifario);
            } else {
                Tarifario nuevoTarifario = new Tarifario(0.05, tp);
                tarifarios.add(nuevoTarifario);
            }
        }
        return tarifarios;
    }

    private static void generarTiposDeVehiculo() throws TipoVehiculoException{
        try {
            Fachada servicios = Fachada.getInstancia();
            ArrayList<String> tiposString = new ArrayList<>(Arrays.asList("Motocicleta", "Standard", "Carga", "Pasajeros"));

            for (String t : tiposString) {
                TipoVehiculo tp = new TipoVehiculo(t);
                servicios.agregarTipoVehiculo(tp);
            }
        } catch (TipoVehiculoException e) {
            throw new TipoVehiculoException(e.getMessage());
        }

    }

    private static ArrayList<Etiqueta> generarEtiquetas() {
        ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
        ArrayList<String> tiposEtiquetas = new ArrayList<>(Arrays.asList("Discapacitado", "Electrico", "Empleado"));
        Random r = new Random();
        for (String t : tiposEtiquetas) {
            Etiqueta et = new Etiqueta(t);
            etiquetas.add(et);
        }

        return etiquetas;
    }

    public static void generarParkings(int cantidad) throws ParkingException {
        try {
            Fachada servicios = Fachada.getInstancia();
            ArrayList<Parking> creados = new ArrayList<Parking>();

            for (int i = 1; i <= cantidad; i++) {
                String nombre = "Parking nro" + String.valueOf(i);
                String dir = "Direccion " + String.valueOf(i);
                Parking park = new Parking(nombre, dir);
                park.agregarTarifarios(generarTarifarios());
                creados.add(park);
                servicios.agregarParking(park);
            }
        } catch (ParkingException e) {
            throw new ParkingException(e.getMessage());
        }

    }

    public static void generarVehiculos(int cantidad, ArrayList<Propietario> props) throws VehiculoException, PropietarioException, TipoVehiculoException, EtiquetaException {
        try {
            Fachada f = Fachada.getInstancia();
            generarTiposDeVehiculo();
            ArrayList<TipoVehiculo> tipos = f.getTiposDeVehiculo();
            ArrayList<Etiqueta> etiquetas = generarEtiquetas();
            Random random = new Random();
            for (int i = 0; i < cantidad; i++) {
                TipoVehiculo tp = tipos.get(random.nextInt(tipos.size()));
                Propietario prop = props.get(random.nextInt(props.size()));
                Vehiculo veh = new Vehiculo("VVV-" + String.format("%03d", i), tp, prop);

                // Solo el 20% de los vehículos tendrán etiquetas
                if (random.nextDouble() < 0.2) {
                    Etiqueta etq = etiquetas.get(random.nextInt(etiquetas.size()));
                    veh.agregarEtiqueta(etq);
                }

                prop.addVehiculo(veh);
                f.agregarVehiculo(veh);
            }

            ArrayList<Etiqueta> faltantes = f.verificarEtiquetasFaltantes(etiquetas);
            if (faltantes.size() > 0) {
                for (Etiqueta eq : faltantes) {
                    boolean etiquetaAgregada = false;
                    while (!etiquetaAgregada) {
                        Vehiculo vehiculoAleatoria = f.getVehiculos().get(random.nextInt(f.getVehiculos().size()));
                        if (!vehiculoAleatoria.getEtiquetas().contains(eq)) {
                            vehiculoAleatoria.agregarEtiqueta(eq);
                            etiquetaAgregada = true;
                        }
                    }
                }
            }
        } catch (VehiculoException e) {
            throw new VehiculoException(e.getMessage());
        }
    }

    public static void generarCocheras(ArrayList<Parking> parks) throws EtiquetaException, EtiquetaException, CocheraException, ParkingException, Exception {
        try {
            ArrayList<Etiqueta> tipos = generarEtiquetas();
            Random random = new Random();

            for (Parking pPark : parks) {
                int cocherasACrear = random.nextInt(51) + 50; // Generar un número entre 50 y 100
                int cocherasConEtiqueta = (int) (cocherasACrear * 0.20); // 20% de las cocheras

                for (int i = 0; i < cocherasACrear; i++) {
                    Etiqueta e = null;

                    if (i < cocherasConEtiqueta) {
                        e = tipos.get(random.nextInt(tipos.size()));
                    }

                    Cochera c = new Cochera(e, pPark);
                    pPark.agregarCochera(c);
                }

                ArrayList<Etiqueta> faltantes = pPark.verificarEtiquetasFaltantes(tipos);
                if (faltantes.size() > 0) {
                    for (Etiqueta eq : faltantes) {
                        boolean etiquetaAgregada = false;
                        while (!etiquetaAgregada) {
                            Cochera cocheraAleatoria = pPark.getCocheras().get(random.nextInt(pPark.getCocheras().size()));
                            if (!cocheraAleatoria.getEtiquetas().contains(eq)) {
                                cocheraAleatoria.agregarEtiqueta(eq);
                                etiquetaAgregada = true;
                            }
                        }
                    }
                }
            }
        } catch (EtiquetaException e) {
            throw new EtiquetaException(e.getMessage());
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static void generarPropietarios(int cantidad) throws PropietarioException {
        try {
            Fachada f = Fachada.getInstancia();
            Random r = new Random();
            for (int x = 0; x < cantidad; x++) {
                String cedula = generarCedula();
                String nombre = generarNombre();
                Propietario prop = new Propietario(cedula, nombre);
                prop.setCuentaCorriente(r.nextInt((100 - (-10)) + 1) + (-10));
                f.agregarPropietario(prop);
            }
        } catch (PropietarioException e) {
            throw new PropietarioException(e.getMessage());
        }

    }

    private static String generarCedula() {
        Random random = new Random();
        int[] numeros = new int[7];
        int[] secuencia = {2, 9, 8, 7, 6, 3, 4};

        // Generar los primeros siete dígitos aleatoriamente
        for (int i = 0; i < 7; i++) {
            numeros[i] = random.nextInt(10); // Genera un número entre 0 y 9
        }

        // Calcular el dígito verificador
        int suma = 0;
        for (int i = 0; i < 7; i++) {
            suma += numeros[i] * secuencia[i];
        }

        int residuo = suma % 10;
        int digitoVerificador = (residuo == 0) ? 0 : 10 - residuo;

        // Construir la cédula completa
        StringBuilder cedula = new StringBuilder();
        for (int numero : numeros) {
            cedula.append(numero);
        }
        cedula.append(digitoVerificador);

        return cedula.toString();
    }

    private static String generarNombre() {
        String[] nombres = {
            "Juan", "María", "José", "Ana", "Luis", "Laura", "Carlos", "Marta",
            "Pedro", "Lucía", "Fernando", "Sofía", "Javier", "Elena", "Diego",
            "Clara", "Manuel", "Gabriela", "Ricardo", "Valentina", "Andrés",
            "Daniela", "Santiago", "Camila", "David", "Victoria", "Francisco",
            "Martina", "Joaquín", "Alejandra", "Sebastián", "Isabel", "Miguel",
            "Paula", "Alejandro", "Rosa", "Rafael", "Natalia", "Tomás", "Emilia",
            "Hugo", "Pilar", "Adrián", "Raquel", "Gonzalo", "Olga", "Enrique",
            "Teresa", "Ignacio", "Beatriz", "Pablo", "Julia", "Alberto", "Irene",
            "Antonio", "Inés", "Roberto", "Eva", "Marcos", "Alicia", "Sergio",
            "Cristina", "Felipe", "Patricia", "Eduardo", "Sandra", "Rodrigo",
            "Lorena", "Vicente", "Mónica", "Jorge", "Silvia", "Alfonso", "Carmen"
        };

        String[] apellidos = {
            "González", "Rodríguez", "Pérez", "García", "Fernández", "López",
            "Martínez", "Sánchez", "Díaz", "Ramírez", "Torres", "Flores",
            "Rivera", "Gómez", "Vargas", "Jiménez", "Mendoza", "Ruiz", "Castro",
            "Ortiz", "Morales", "Herrera", "Medina", "Aguilar", "Pacheco",
            "Soto", "Cruz", "Delgado", "Guerrero", "Navarro", "Ramos", "Reyes",
            "Mora", "Salazar", "Rojas", "Maldonado", "Vega", "Santana", "Escobar",
            "Peña", "Montoya", "Cabrera", "Miranda", "Silva", "Suárez", "Valenzuela",
            "Romero", "Parra", "Araya", "Figueroa", "Zamora", "Bustos", "Contreras",
            "Campos", "Carrillo", "Correa", "Muñoz", "Espinoza", "Arroyo", "Mejía",
            "Villalobos", "Solís", "Ortega", "Acosta", "Lozano", "Chávez", "Rosales",
            "Núñez", "Hernández", "Paredes", "Álvarez", "Del Valle", "Del Río"
        };

        Random random = new Random();
        String nombre = nombres[random.nextInt(nombres.length)];
        String apellido = apellidos[random.nextInt(apellidos.length)];
        return nombre + " " + apellido;
    }

    private static void test_MostrarParkings(ArrayList<Parking> parks) {
        for (Parking p : parks) {
            String muestra = p.getNombre() + " - "
                    + p.getDireccion() + " - "
                    + p.getEstado() + " - "
                    + "CantCocheras: " + p.getCapacidad();

            for (Cochera c : p.getCocheras()) {
                muestra += "|" + c.getCodigo();
            }

            UIConsola.println(muestra);
        }
    }

    private static void test_MostrarCocheras(ArrayList<Estacionable> cocheras) {
        for (Estacionable est : cocheras) {
            Cochera c = (Cochera) est;
            String muestra = c.getCodigo() + " / ";

            if (c.getEtiquetas() != null) {
                for (Etiqueta etq : c.getEtiquetas()) {
                    muestra += etq.getNombre() + " | ";
                }
            }

            UIConsola.println(muestra);
        }
    }

    private static void test_MostrarPropietarios(ArrayList<Propietario> props) {
        for (Propietario p : props) {
            String muestra = p.getCedula() + " - "
                    + p.getNombreCompleto() + " - "
                    + p.getCuentaCorriente() + " - "
                    + "CantVehiculos: " + p.getVehiculos().size();

            UIConsola.println(muestra);
        }
    }

    private static void test_MostrarVehiculos(ArrayList<Transitable> vehiculos) {
        for (Transitable v : vehiculos) {
            Vehiculo veh = (Vehiculo) v;
            String muestra = v.getPatente() + " - "
                    + veh.getTipoVehiculo().getDescripcion() + " - "
                    + veh.getPropietario().getNombreCompleto() + " - "
                    + "EsDiscapacitado: " + veh.esDiscapacitado() + " - "
                    + "EsElectrico: " + veh.esElectrico() + " - "
                    + "EsEmpleado: " + veh.esEmpleado();

            for (Etiqueta e : veh.getEtiquetas()) {
                muestra += "|" + e.getNombre();
            }

            UIConsola.println(muestra);
        }
    }

}
