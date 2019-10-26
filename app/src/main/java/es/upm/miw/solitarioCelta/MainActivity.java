package es.upm.miw.solitarioCelta;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.upm.miw.solitarioCelta.models.Puntuacion;
import es.upm.miw.solitarioCelta.models.RepositorioPuntuaciones;

public class MainActivity extends AppCompatActivity {

    SCeltaViewModel miJuego;
    public static final String LOG_KEY = "MiW";
    static RepositorioPuntuaciones repositorioPuntuaciones;
    SharedPreferences preferences;
    Chronometer crono;
    TextView txtFichasRestantes;
    final String MI_CRONONOMETRO = "MI_CRONOMETRO";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        cambiarColoresAplicacion(obtenerColorAplicacion());

        setContentView(R.layout.activity_main);

        repositorioPuntuaciones = new RepositorioPuntuaciones(getApplicationContext());

        miJuego = ViewModelProviders.of(this).get(SCeltaViewModel.class);
        crono = findViewById(R.id.chronometer);
        txtFichasRestantes = findViewById(R.id.fichasRestantes);

        if (null != savedInstanceState) {
            long valorAnterior = savedInstanceState.getLong(MI_CRONONOMETRO);
            crono.setBase(valorAnterior);
            crono.start();
        }

        mostrarTablero();
    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre del recurso, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     *
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(@NotNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna

        Log.i(LOG_KEY, "fichaPulsada(" + i + ", " + j + ") - " + resourceName);
        miJuego.jugar(i, j);
        Log.i(LOG_KEY, "#fichas=" + miJuego.numeroFichas());

        if (miJuego.numeroFichas() == 32) {
            crono.setBase(SystemClock.elapsedRealtime());
            crono.start();
        }

        mostrarTablero();

        if (miJuego.juegoTerminado()) {
            crono.stop();
            guardarPuntuacionEnBaseDeDatos();
            crono.setBase(SystemClock.elapsedRealtime());
            new AlertDialogFragment().show(getFragmentManager(), "ALERT_DIALOG");
        }
    }

    public String obtenerNombreJugador() {
        String nombreJugador = preferences.getString(
                getString(R.string.nombreJugadorKey),
                getString(R.string.prefTituloNombreJugador)
        );

        Log.i(LOG_KEY, nombreJugador);

        return nombreJugador;
    }

    public String obtenerColorAplicacion() {
        String color = preferences.getString(
                getString(R.string.colorRadioButtonKey),
                getString(R.string.colorRadioButton)
        );

        Log.i(LOG_KEY, color);

        return color;
    }

    public void guardarPuntuacionEnBaseDeDatos() {
        String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss: ", Locale.getDefault()).format(new Date());
        repositorioPuntuaciones.add(obtenerNombreJugador(), fecha, miJuego.numeroFichas(), crono.getText().toString());
        Log.i(LOG_KEY, "Partida guardada");
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        String strRId;
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;

        txtFichasRestantes.setText(String.valueOf(miJuego.numeroFichas()));

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                strRId = prefijoIdentificador + i + j;
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = findViewById(idBoton);
                    button.setChecked(miJuego.obtenerFicha(i, j) == JuegoCelta.FICHA);
                }
            }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public void guardarPartida() {
        try {
            FileOutputStream fos = openFileOutput(getString(R.string.ficheroPartidaGuardada), Context.MODE_PRIVATE);
            fos.write(miJuego.serializaTablero().getBytes());
            fos.close();
            Log.i(LOG_KEY, "Partida guardada actualizada");
            Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.txtPartidaGuardada),
                    Snackbar.LENGTH_LONG
            ).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reiniciarJuego() {
        miJuego.reiniciar();
        crono.setBase(SystemClock.elapsedRealtime());
        mostrarTablero();
    }

    public void recuperarPartida() {
        boolean hayContenido = false;
        BufferedReader fin;
        String linea;
        try {
            fin = new BufferedReader(
                    new InputStreamReader(openFileInput(getString(R.string.ficheroPartidaGuardada))));
            linea = fin.readLine();
            while (linea != null) {
                hayContenido = true;
                miJuego.deserializaTablero(linea);
                linea = fin.readLine();
                Log.i(LOG_KEY, "Partida recuperada");
            }
            fin.close();
            mostrarTablero();
            Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.txtRecuperadaPartidaGuardada),
                    Snackbar.LENGTH_LONG
            ).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!hayContenido) {
            Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.txtNoExistePartidaGuardada),
                    Snackbar.LENGTH_LONG
            ).show();

        }
    }

    public static List<Puntuacion> getMejoresResultados() {
        return repositorioPuntuaciones.getBestPuntuaction();
    }

    public static List<Puntuacion> getMejoresResultadosOrdenadosPorTiempo() {
        return repositorioPuntuaciones.getBestPuntuactionOrderByTime();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                return true;
            case R.id.opcAcercaDe:
                startActivity(new Intent(this, AcercaDe.class));
                return true;
            case R.id.opcReiniciarPartida:
                RestartGameDialogFragment dialogFragment = new RestartGameDialogFragment();
                dialogFragment.show(getFragmentManager(), "RestartGameDialog");
                return true;
            case R.id.opcGuardarPartida:
                guardarPartida();
                return true;
            case R.id.opcRecuperarPartida:
                recuperarPartida();
                return true;
            case R.id.opcMejoresResultados:
                startActivity(new Intent(this, MejoresResultados.class));
            default:
                return true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(MI_CRONONOMETRO, crono.getBase());
    }


    public void cambiarColoresAplicacion(String currentTheme) {

        if (currentTheme.equals("rosa")) {
            setTheme(R.style.Theme_App_Rosa);
        } else if (currentTheme.equals("morado")) {
            setTheme(R.style.Theme_App_Morado);
        } else if (currentTheme.equals("rojo")) {
            setTheme(R.style.Theme_App_Rojo);
        } else if (currentTheme.equals("amarillo")) {
            setTheme(R.style.Theme_App_Amarillo);
        } else if (currentTheme.equals("naranja")) {
            setTheme(R.style.Theme_App_Naranja);
        } else if (currentTheme.equals("verde")) {
            setTheme(R.style.Theme_App_Verde);
        } else {
            setTheme(R.style.AppTheme);
        }
    }
}


