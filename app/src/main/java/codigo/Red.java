package codigo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by GonzaloW7 on 15/11/2015.
 */
public class Red {

    public HashMap<String, Estacion> estaciones;
    public List<Linea> lineas;

    public Red() {
        estaciones = new HashMap<>();
        lineas = new ArrayList<>();
    }

    public void annadeLinea(Linea linea) {
        lineas.add(linea);
    }

    public boolean estacionExiste(String nombre) {
        return estaciones.containsKey(nombre);
    }

    public Linea getLinea(TipoSentido tipo, int id) {
        int index;
        if ((index = lineas.indexOf(new Linea(tipo, id))) == -1) {
            return null;
        } else {
            return lineas.get(index);
        }
    }



    public boolean mismaLinea(Estacion est1, Estacion est2) { //Comprueba si dos estaciones estan en la misma linea, TODO ATENCION ejemplo Avenida de America - Diego de Leon
        LinkedList<Linea> ll = est1.getLineas();
        ll.retainAll(est2.getLineas()); // en ll solo estaran las Lineas que esten tambien en est2, es decir lineas comunes
        if ((ll.isEmpty())) return false;
        return true;
    }

    public Linea seleccionarSentido(LinkedList<Linea> ll, Estacion ori, Estacion dest) { //A partir de una lista de lineas y el origen y el destinodevuelve el sentido adecuado
        //Primero comprobar que efectivamente es la misma linea y solo queda el sentido
        int antiguo, nuevo = -1;
        boolean test = false;
        Linea lineaSentido = null;
        for (Linea line : ll) {
            antiguo = line.getId();
            if (nuevo != -1 && antiguo == nuevo) {
                test = true;
                break;
            }
            nuevo = antiguo;
        }

        if (test) {
            //Sabemos la linea (id en nuevo) pero no el sentido.
            lineaSentido = ori.buscarLinea(nuevo);
            LinkedHashMap<Estacion, List<Fecha>> estaciones = lineaSentido.getEstaciones();
            for (Map.Entry<Estacion, List<Fecha>> entry : estaciones.entrySet()) {
                if (entry.getKey().equals(ori)) { //Si encontramos primero el origen es que vamos en la direccion de linea correcta
                    return lineaSentido;
                } else if (entry.getKey().equals(dest)) {
                    break;
                }
            }
            //Si llega aqui hay que buscar el otro sentido
            if (lineaSentido.tipo == TipoSentido.IDA) {
                lineaSentido = ori.buscarLinea(nuevo, TipoSentido.VUELTA);
            } else {
                lineaSentido = ori.buscarLinea(nuevo, TipoSentido.IDA);
            }
            return lineaSentido;
        } else {
            return lineaSentido;
        }
    }

    // Esta funcion crea una lista de objeto de clase Viaje, cada viaje contiene la linea y las estaciones de origen y destino con sus horas (que se añadiran posteriormente)
    //Esta funcion solo añade las linea y las estaciones de origen y destino
    public List<Viaje> listaViajes(LinkedList<Estacion> lista) throws MetroException {
        /*
        Obetener las lineas de la primera estacion
         */
        List<Viaje> viaje = new ArrayList<>();
        Estacion primera = lista.getFirst();
        LinkedList<Linea> ll = new LinkedList<>(primera.getLineas());
        ll.retainAll(lista.getLast().getLineas());
        Linea lineaNueva = null;
        Linea lineaVieja = null;

        if (!(ll.isEmpty())) { // Si ll no esta vacia es que estan en la misma linea, pero queda averiguar el sentido
            viaje.add(new Viaje(seleccionarSentido(ll, primera, lista.getLast()), primera, lista.getLast()));
            return viaje;
        }


        //Usamos una estacion pivote que ira avanzado para saber la linea
        //caso lineas distintas
        //Viaje v = new Viaje(primera.getLineas().getFirst(), primera); //Se crea el viaje sin el destino
        Viaje v = new Viaje(primera);

        Estacion pivote = null;
        while ((lista.size() > 1)) { //primera es la estacion mas atras posible
            lista.removeFirst();
            pivote = lista.getFirst();       //Si no estan en la misma linea, se crea un viaje para "primera" y se comienza con pivote

            ll = new LinkedList<>(primera.getLineas());
            ll.retainAll(pivote.getLineas());
            lineaNueva = seleccionarSentido(ll, primera, pivote);
            if (lineaNueva == null){
                throw new MetroException();
            }
            // lineaComun(primera, pivote);
            if (lineaVieja != null && (lineaNueva != lineaVieja)) { //TODO el fallo por aqui
                //Si las lineas son distintas se acaba el viaje en primera y se inicia otro
                v.annadeDestino(primera);
                v.annadeLinea(lineaVieja);
                viaje.add(v);
                v = new Viaje(lineaNueva, primera);
            }
            lineaVieja = lineaNueva;
            primera = pivote;
             /*
                TODO continuar aqui: -1 crear variable linea, que representa la linea en la que estamos ahora(hacer funcion que devuelva la linea comun a dos estaciones),
                TODO Entonces queda asi estaciones: 1->2->3->4   primero comparamos 1 y 2, nos da que tienen la L9 luego 2 y 3 nos da que tienen la L9 (igual) y luego 3 y 4 y nos da que tiene
                TODO la L6 (distinta), pues se crea un viaje que abarque desde la 1 hasta la 3 se sustituye la variable linea actual por la L6 y se continua...
                 */
        }
        //Solo queda un elemento
        v.annadeDestino(primera);
        viaje.add(v);
        return viaje;
    }

    public Linea lineaComun(Estacion est1, Estacion est2) {
        LinkedList<Linea> ll = est1.getLineas();
        ll.retainAll(est2.getLineas()); // en ll solo estaran las Lineas que esten tambien en est2, es decir lineas comunes
        return ll.getFirst();
    }

    public Estacion annadeEstacion(String nombre) {
        Estacion estacion = null;
        if ((estacion = estaciones.get(nombre)) == null) {
            estacion = new Estacion(nombre);
            estaciones.put(nombre, estacion);
        }
        return estacion;
    }

    public void reseteaBusqueda() {

        for (Map.Entry<String, Estacion> e : estaciones.entrySet()) {
            e.getValue().reset();
        }
    }


    public LinkedList<Estacion> calculaRutaStr(String ori, String dest) {
        if (estacionExiste(ori) && estacionExiste(dest)) {
            reseteaBusqueda();
            return calcularRuta(obtenerEstacion(ori), obtenerEstacion(dest), null);
        } else return null;
    }


    private LinkedList<Estacion> calcularRuta(Estacion actual, Estacion destino, LinkedList<Estacion> lista) {
        if (actual.equals(destino)) {
            lista = new LinkedList<Estacion>();
            lista.add(destino);
            return lista;
        }
        if (actual.esVisitada()) {
            return null;
        }
        LinkedList<Estacion> listaAux;
        actual.visita();
        for (Estacion e : actual.getConexiones()) {
            if ((listaAux = calcularRuta(e, destino, lista)) != null) {
                lista = listaAux;
                lista.addFirst(actual);
                break;
            }
        }
        return lista;


    }

    public Estacion obtenerEstacion(String nombre) {
        return estaciones.get(nombre);
    }

    @Override
    public String toString() {
        return "Red{" +
                "Lineas=" + '\n' + lineas.toString() +
                '}';
    }
}
