package es.upm.miw.solitarioCelta.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.solitarioCelta.MainActivity;

import static es.upm.miw.solitarioCelta.models.PuntuacionContract.tablaPuntuacion;

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
                    tablaPuntuacion.COL_NAME_PIEZAS_RESTANTES + " INT," +
                    tablaPuntuacion.COL_NAME_TIEMPO + " TEXT)";

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

    public long add(String nombreJugador, String fecha, int fichasRestantes, String tiempo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tablaPuntuacion.COL_NAME_NOMBRE_JUGADOR, nombreJugador);
        values.put(tablaPuntuacion.COL_NAME_FECHA, fecha);
        values.put(tablaPuntuacion.COL_NAME_PIEZAS_RESTANTES, fichasRestantes);
        values.put(tablaPuntuacion.COL_NAME_TIEMPO, tiempo);
        return db.insert(tablaPuntuacion.TABLE_NAME, null, values);
    }

    public List<Puntuacion> getBestPuntuaction() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = tablaPuntuacion.COL_NAME_PIEZAS_RESTANTES + " ASC";

        Cursor cursor = db.query(
                tablaPuntuacion.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List<Puntuacion> listaPuntuacion = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Puntuacion puntuacion = new Puntuacion(
                        cursor.getInt(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_NOMBRE_JUGADOR)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_FECHA)),
                        cursor.getInt(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_PIEZAS_RESTANTES)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_TIEMPO))
                );
                listaPuntuacion.add(puntuacion);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return listaPuntuacion;
    }

    public List<Puntuacion> getBestPuntuactionOrderByTime() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = tablaPuntuacion.COL_NAME_TIEMPO + " ASC";

        Cursor cursor = db.query(
                tablaPuntuacion.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List<Puntuacion> listaPuntuacion = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Puntuacion puntuacion = new Puntuacion(
                        cursor.getInt(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_NOMBRE_JUGADOR)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_FECHA)),
                        cursor.getInt(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_PIEZAS_RESTANTES)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_TIEMPO))
                );
                listaPuntuacion.add(puntuacion);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return listaPuntuacion;
    }

    public int deleteBestPuntuations() {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(tablaPuntuacion.TABLE_NAME, null, null);
        db.close();
        Log.i("MiW", "Registros borrados: " + deletedRows);
        return deletedRows;
    }
}
