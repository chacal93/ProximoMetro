package ghm.proximometro;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import codigo.Carga;
import codigo.Estacion;
import codigo.Fecha;
import codigo.Linea;
import codigo.MetroException;
import codigo.Red;
import codigo.TipoSentido;
import codigo.Viaje;

public class inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void prueba(View view) {
        try {
            String[] partir;
            Red red = new Red();
            //NOTA: nomenclatura de los ficheros:  <NUMERO DE LINEA>-<SENTIDO a, b>.txt
            for (String fichero : getAssets().list("datos")) {
                //bucle por cada fichero en la carpeta datos
                Linea linea;
                partir = fichero.split("-|\\.");
                int lin = Integer.parseInt(partir[0]);
                //Partir[0] contiene la linea y partir[1] el sentido (partir 2 tiene la extension)
                red = Carga.cargar(getAssets().open("datos/" + partir[0] + "-" + partir[1] + "." + partir[2]), partir[0], partir[1], red);
            }


            Log.e("TEST", "Desde Baunatal a Cuzco");
            Log.e("TEST", red.calculaRutaStr("Baunatal","Cuzco").toString());
            Log.e("TEST", "Desde Hospital Infanta Sofia a Puerta del Sur");
            Log.e("TEST", red.calculaRutaStr("Hospital Infanta Sofia", "Puerta del Sur").toString());

            List<Viaje> viajes=principal(red,"Hospital Infanta Sofia", "Puerta del Sur");
            viajes.get(0).getDestino();
            Log.e("TEST", "aa");

           // Log.e("TEST", red.getLinea(TipoSentido.IDA,101));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Viaje> principal(Red red,String origen, String destino){ //FUNCION PRINCIPAL; A PARTIR DEL ORIGEN Y EL DESTINO CALCULA TODO
        try {
            //Hora actual
            Calendar c = Calendar.getInstance();
            Fecha fecha= new Fecha(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE));
            List<Viaje> viajes= red.listaViajes(red.calculaRutaStr(origen, destino)); //Me devuelve el viaje


            for(Viaje v: viajes){ //Para cada viaje sacar horas origen y destino y compararlas con la actual
                Estacion orig=v.getOrigen();
                List<Fecha> fechasPosibles= v.getLinea().getEstaciones().get(orig);
                v.setFechaSalida(encuentraFecha(fechasPosibles,fecha));
                //La hora de llegada se calcula con el indice de la hora

                Estacion dest=v.getDestino();
                fechasPosibles= v.getLinea().getEstaciones().get(dest);
                v.setFechaLLegada(fechasPosibles.get(v.getFechaSalida().getIndice()));
            }

            return viajes;
        } catch (MetroException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Fecha encuentraFecha(List<Fecha> fechasPosibles,Fecha actual){
        int indice=0;
        for(Fecha f : fechasPosibles){
            if(f.getHora() >= actual.getHora()){ //esa hora nos vale
                if(f.getMinuto() >= actual.getMinuto()){
                    f.setIndice(indice);
                    return f;
                }
            }
            indice ++;
        }
        indice=0;
    return fechasPosibles.get(0);
    }

}
