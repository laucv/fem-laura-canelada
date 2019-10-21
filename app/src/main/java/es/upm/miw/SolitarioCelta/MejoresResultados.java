package es.upm.miw.SolitarioCelta;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MejoresResultados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mejores_resultados);

        ListView lista = findViewById(R.id.listaLV);
        MiAdaptador miAdaptador = new MiAdaptador(this, R.layout.item_layout, MainActivity.getMejoresResultados());
        lista.setAdapter(miAdaptador);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mejores_resultados_menu, menu);
        return true;
    }

    public void borrarMejoresResultados(){
        MainActivity mActivity= new MainActivity();
        mActivity.borrarMejoresResultados();
        //TODO finish();
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.txtBorrarMejoresResultados),
                Snackbar.LENGTH_LONG
        ).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcBorrarMejoresPuntuaciones:
                BorrarMejoresResultadosDialogFragment dialogFragment = new BorrarMejoresResultadosDialogFragment();
                dialogFragment.show(getFragmentManager(), "BorrarMejoresResultados");
                return true;
            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.txtSinImplementar),
                        Snackbar.LENGTH_LONG
                ).show();
        }
        return true;
    }
}
