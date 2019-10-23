package es.upm.miw.solitarioCelta.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Puntuacion implements Parcelable {

    private int id;

    private String nombreJugador;

    private String fecha;

    private int piezasRestantes;

    private String tiempo;

    public Puntuacion(int id, String nombreJugador, String fecha, int piezasRestantes, String tiempo) {
        this.id = id;
        this.nombreJugador = nombreJugador;
        this.fecha = fecha;
        this.piezasRestantes = piezasRestantes;
        this.tiempo = tiempo;
    }


    public int getId() {
        return id;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPiezasRestantes() {
        return piezasRestantes;
    }

    public void setPiezasRestantes(int piezasRestantes) {
        this.piezasRestantes = piezasRestantes;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    protected Puntuacion(Parcel in) {
        id = in.readInt();
        nombreJugador = in.readString();
        fecha = in.readString();
        piezasRestantes = in.readInt();
        tiempo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombreJugador);
        dest.writeString(fecha);
        dest.writeInt(piezasRestantes);
        dest.writeString(tiempo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Puntuacion> CREATOR = new Parcelable.Creator<Puntuacion>() {
        @Override
        public Puntuacion createFromParcel(Parcel in) {
            return new Puntuacion(in);
        }

        @Override
        public Puntuacion[] newArray(int size) {
            return new Puntuacion[size];
        }
    };

    @Override
    public String toString() {
        return "Puntuacion{" +
                "id=" + id +
                ", nombreJugador='" + nombreJugador + '\'' +
                ", fecha='" + fecha + '\'' +
                ", piezasRestantes=" + piezasRestantes +
                ", tiempo='" + tiempo + '\'' +
                '}';
    }
}