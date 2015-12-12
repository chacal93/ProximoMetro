package codigo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by GonzaloW7 on 14/11/2015.
 */
public class Estacion {

    List<Estacion> conexion;
    public String nombre;
    boolean visitada;

    LinkedList<Linea> linea;
    /*
    * Incluir campo id, que se saque la id a partir del nombre, normalizando String
    *
    *
     */


    public Estacion(String nombre) {
        conexion=new ArrayList<>();
        this.nombre=nombre;
        this.visitada=false;
        this.linea = new LinkedList<>();
    }


    public Linea buscarLinea(int id) {
    for (Linea l : this.linea){
        if(l.getId() == id){
            return l;
        }
    }
        return null;
    }

    public Linea buscarLinea(int id,TipoSentido tipo) {
        for (Linea l : this.linea){
            if(l.getId() == id && l.tipo == tipo){
                return l;
            }
        }
        return null;
    }

    public LinkedList<Linea> getLineas(){
        return this.linea;
    }
    public void annadeLinea(Linea lin){
        this.linea.add(lin);
    }

    public void reset(){
        this.visitada=false;
    }
    public boolean esVisitada(){
        return this.visitada;
    }
    public void visita(){
        this.visitada=true;
    }

    public void annadeConexion(Estacion ee){
        conexion.add(ee);
    }

    public List<Estacion> getConexiones(){ return this.conexion;}


    @Override
    public String toString() {
        /*return "Estacion{" +
                "nombre='" + nombre + '\'' +
                '}';*/
        return nombre;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estacion estacion = (Estacion) o;

        return nombre.equals(estacion.nombre);

    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }


    /*
    String nombre;
    List<Integer> lineas;
    List<Fecha> horas;
    Estacion siguiente;
    Estacion anterior;
    Integer id;

    public Estacion(Estacion anterior) {
        this.lineas = new ArrayList<>();
        this.horas = new ArrayList<>();
        this.anterior=anterior;

    }

    public void annadirHora(Fecha ff) {
        this.horas.add(ff);
    }
    public void annadirlinea(int lin){
        lineas.add(lin);
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSiguiente(Estacion siguiente) {
        this.siguiente = siguiente;
    }

    public void setAnterior(Estacion anterior) {
        this.anterior = anterior;
    }


    @Override
    public String toString() {
        return "Estacion{" +
                "nombre='" + nombre + '\'' +
                '}';
    }*/
}
