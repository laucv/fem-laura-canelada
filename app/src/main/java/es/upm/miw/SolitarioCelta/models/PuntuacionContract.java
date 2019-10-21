package es.upm.miw.SolitarioCelta.models;

import android.provider.BaseColumns;

final public class PuntuacionContract {

    private PuntuacionContract() {}

    public static abstract class tablaPuntuacion implements BaseColumns {
        static final String TABLE_NAME = "puntuaciones";

        static final String COL_NAME_ID = _ID;
        static final String COL_NAME_NOMBRE_JUGADOR = "nombreJugador";
        static final String COL_NAME_FECHA = "fecha";
        static final String COL_NAME_PIEZAS_RESTANTES = "piezasRestantes";
    }
}