package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class RestartGameDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final MainActivity mainActivity = (MainActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.txtDialogoReiniciarPartidaTitulo))
                .setMessage(getString(R.string.txtDialogoReiniciarPartidaPregunta))
                .setPositiveButton(
                        getString(R.string.txtDialogoReiniciarAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = mainActivity.getIntent();
                                mainActivity.finish();
                                startActivity(intent);
                            }
                        }
                )
                .setNegativeButton(
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
