package codigo;

/**
 * Created by GonzaloW7 on 19/11/2015.
 */
public class Viaje {

    Fecha fechaSalida;
    Fecha fechaLLegada;

    Linea linea;

    Estacion origen;
    Estacion destino;

    public Viaje(Linea linea,Estacion origen,Estacion destino){
        this.linea=linea;
        this.origen=origen;
        this.destino=destino;
    }

    public Viaje(Linea linea,Estacion origen){
        this.linea=linea;
        this.origen=origen;
    }

    public Viaje(Estacion origen){
        this.origen=origen;
    }

    public void annadeDestino(Estacion destino){
        this.destino=destino;
    }
    public Linea getLinea(){
        return this.linea;
    }

    public void setFechaSalida(Fecha fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void setFechaLLegada(Fecha fechaLLegada) {
        this.fechaLLegada = fechaLLegada;
    }

    public Fecha getFechaLLegada() {
        return fechaLLegada;
    }

    public Fecha getFechaSalida() {
        return fechaSalida;
    }
    public Estacion getDestino(){
        return this.destino;
    }
    public Estacion getOrigen(){
        return this.origen;
    }
    public void annadeLinea(Linea linea){
        this.linea=linea;
    }


}
