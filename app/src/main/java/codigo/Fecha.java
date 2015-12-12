package codigo;

/**
 * Created by GonzaloW7 on 14/11/2015.
 */
public class Fecha {

    public int hora;
    public int minuto;
    public int indice;

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public Fecha(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public String toString() {
        return "Fecha{" + hora +":" + minuto +
                '}';
    }
}
