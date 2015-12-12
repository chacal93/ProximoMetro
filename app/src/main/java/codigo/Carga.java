package codigo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by GonzaloW7 on 14/11/2015.
 */
public class Carga {


    public static Red cargar(InputStream fichero, String nombre, String sentido, Red red) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(fichero));
        Estacion estacion = null;
        Estacion anterior = null;
        String line;
        int flag;
        String[] aux;
        TipoSentido tipo;
        if (sentido.equals("a")) tipo = TipoSentido.IDA;
        else tipo = TipoSentido.VUELTA;

        Linea linea = new Linea(tipo, Integer.parseInt(nombre)); //Se crea la linea
        red.annadeLinea(linea);
        while ((line = in.readLine()) != null) { //BUCLE POR CADA ESTACION
            flag = 1;
            //Comprobar si ya existe la estacion
            for (String dato : line.split(";")) {
                // Primero el nombre y luego los horarios
                if (flag == 1) {
                    flag = 0;
                   // dato = Normalizer.normalize(dato, Normalizer.Form.NFD); esto era para los acentos, pero no funciona y las eñes, puede que de problemas mas adelante, a la hora de buscar estaciones TODO
                   // dato = dato.replaceAll("[^\\p{ASCII}]", "");
                    estacion = red.annadeEstacion(dato);//añadirla a la red, comprueba si ya existe.
                    estacion.annadeLinea(linea);
                } else {
                    //Añadir horas
                    aux = dato.split(":");
                    Fecha fecha = new Fecha(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]));
                    linea.annadeHora(estacion, fecha);
                }
            }
            if (anterior != null) {
                estacion.annadeConexion(anterior); //ATENCION PELIGROSO: HE PUESTO QUE  LA CONEXION SEA RECIPROCA, ES DECIR TU AÑADES UNA CONEXION DE A a B y automaticamente se añade de B a A ç
                anterior.annadeConexion(estacion);
            }

            anterior = estacion;
        }
       // Log.e("CARGA", red.toString());
        return red;
    }



/*
    public static Linea cargar(InputStream fichero, Linea linea, String tipo) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(fichero));
        String line;
        int flag = 1;
        String[] aux;
        TipoSentido sent;
        Sentido sentido = new Sentido(linea.getLinea());
        Estacion anterior = null;
        int numLinea=linea.getLinea();
        if (tipo.equals("a")) {
            sent = TipoSentido.IDA;
        } else {
            sent = TipoSentido.VUELTA;
        }

        while ((line = in.readLine()) != null) {
            flag = 1;
            Estacion estacion = new Estacion(anterior);
            estacion.annadirlinea(numLinea);
            for (String dato : line.split(";")) {
                if (flag == 1) {
                    //Añadir nombre
                    estacion.setNombre(dato);
                    flag = 0;
                } else {
                    //Añadir horas
                    aux = dato.split(":");
                    Fecha fecha = new Fecha(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]));
                    estacion.annadirHora(fecha);
                }
            }
            sentido.annadirEstacion(estacion);
            if(anterior != null) anterior.setSiguiente(estacion);
            anterior = estacion;
        }
        // Log.e("CARGA","Nombre: "+dato[0]+" Horas: "+datos[1]);

        linea.annadirSentido(sentido, sent);
        Log.e("CARGA", linea.toString());
        return linea;
    }*/
}



