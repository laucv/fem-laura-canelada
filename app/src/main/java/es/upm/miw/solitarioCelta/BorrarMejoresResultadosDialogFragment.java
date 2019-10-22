package es.upm.miw.solitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class BorrarMejoresResultadosDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final MejoresResultados mejoresResultados = (MejoresResultados) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.txtDialogoMejoresResultados));
        builder.setMessage(getString(R.string.txtDialogoMejoresResultadosPregunta));
        builder.setPositiveButton(
                getString(R.string.txtDialogoMejoresResultadosAfirmativo),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mejoresResultados.borrarMejoresResultados();
                    }
                }
        );
        builder.setNegativeButton(
                getString(R.string.txtDialogoMejoresResultadosNegativo),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Acción opción No
                    }
                }
        );

        return builder.create();
    }

}
