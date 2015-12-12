package codigo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by GonzaloW7 on 14/11/2015.
 */
public class Linea {


  //  List<Estacion> orden; // ESTA POR SI ACASO CON EL MAPA ESE NO FUNCIONA EN ORDEN, O NO SE PUEDE ITERAR EN ORDEN.
    public LinkedHashMap<Estacion, List<Fecha>> estaciones;
    public int id;
    public String nombre;
    TipoSentido tipo;


    public Linea(TipoSentido tipo, int id) {
        estaciones = new LinkedHashMap<>();
        this.tipo = tipo;
        this.id = id;
        this.nombre = "Linea" + id;
       // orden=new ArrayList<>();
    }

    public LinkedHashMap<Estacion, List<Fecha>> getEstaciones(){
        return this.estaciones;
    }


    public int getId(){
        return this.id;
    }




    public List<Fecha> obtenerHoras(Estacion nombre) {
        return estaciones.get(nombre);
    }

    public void annadeHora(Estacion nombre, Fecha fecha) { //NOTA IMPORTANTE, SI YO PIDO LA LISTA DE FECHAS EN UN MAPA Y AÑADO ALGO, TENGO QUE HACER UN PUT EN EL MAPA O SE ACTUALIZA SOLO??¿? Ç

        List<Fecha> lista = estaciones.get(nombre);
        if (lista == null) {
            lista=new ArrayList<Fecha>();
            estaciones.put(nombre,lista);
        }
        lista.add(fecha);
    }


    @Override
    public String toString() {
        return "Linea{" +
                "nombre='" + nombre + '\'' +'\n'+
                estaciones.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Linea linea = (Linea) o;

        if (id != linea.id) return false;
        if (!nombre.equals(linea.nombre)) return false;
        return tipo == linea.tipo;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nombre.hashCode();
        result = 31 * result + tipo.hashCode();
        return result;
    }




/*
    public String nombre;
    public int linea;
    public Sentido[] sentidos;

    public Linea(int id) {
        this.nombre = "Linea " + id;
        this.linea = id;
        sentidos = new Sentido[2];
    }


    public void annadirSentido(Sentido sentido, TipoSentido tipo) {
        if (tipo == TipoSentido.IDA) {
            sentidos[0] = sentido;
        } else {
            sentidos[1] = sentido;
        }
    }

    public int getLinea() {
        return linea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Linea linea1 = (Linea) o;

        return linea == linea1.linea;

    }

    @Override
    public int hashCode() {
        return linea;
    }

    @Override
    public String toString() {
        return "Linea{" +
                ", nombre='" + nombre + '\'' +
                "sentidos=" + Arrays.toString(sentidos) +
                '}';
    }*/
}
