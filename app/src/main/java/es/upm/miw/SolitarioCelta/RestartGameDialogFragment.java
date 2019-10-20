package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class RestartGameDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final MainActivity mainActivity = (MainActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.txtDialogoReiniciarPartidaTitulo));
        builder.setMessage(getString(R.string.txtDialogoReiniciarPartidaPregunta));
        builder.setPositiveButton(
                getString(R.string.txtDialogoReiniciarAfirmativo),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mainActivity.reiniciarJuego();
                    }
                }
        );
        builder.setNegativeButton(
                getString(R.string.txtDialogoReiniciarNegativo),
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
