package es.upm.miw.SolitarioCelta.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import es.upm.miw.SolitarioCelta.MainActivity;

import static es.upm.miw.SolitarioCelta.models.PuntuacionContract.tablaPuntuacion;

public class RepositorioPuntuaciones extends SQLiteOpenHelper {

    //nombre base de datos
    private static final String NOMBRE_FICHERO = tablaPuntuacion.TABLE_NAME + ".db";

    //número de versión
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + tablaPuntuacion.TABLE_NAME + " (" +
                    tablaPuntuacion._ID + " INTEGER PRIMARY KEY," +
                    tablaPuntuacion.COL_NAME_NOMBRE_JUGADOR + " TEXT," +
                    tablaPuntuacion.COL_NAME_FECHA + " TEXT," +
                    tablaPuntuacion.COL_NAME_PIEZAS_RESTANTES + " INT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + tablaPuntuacion.TABLE_NAME;

    public RepositorioPuntuaciones(Context context) {
        super(context, NOMBRE_FICHERO, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        Log.i(MainActivity.LOG_KEY, "SQL: " + SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long add(String nombreJugador, String fecha, int fichasRestantes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tablaPuntuacion.COL_NAME_NOMBRE_JUGADOR, nombreJugador);
        values.put(tablaPuntuacion.COL_NAME_FECHA, fecha);
        values.put(tablaPuntuacion.COL_NAME_PIEZAS_RESTANTES, fichasRestantes);
        return db.insert(tablaPuntuacion.TABLE_NAME, null, values);
    }
}
