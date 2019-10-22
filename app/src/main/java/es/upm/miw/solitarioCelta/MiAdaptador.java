package es.upm.miw.solitarioCelta;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import es.upm.miw.solitarioCelta.databinding.ItemLayoutBinding;
import es.upm.miw.solitarioCelta.models.Puntuacion;


public class MiAdaptador extends ArrayAdapter {

    private Context context;
    private int idLayout;
    private List<Puntuacion> misDatos;
    private static LayoutInflater layoutInflater;


    /**
     * Constructor. This constructor will result in the underlying data collection being
     * immutable, so methods such as {@link #clear()} will throw an exception.
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param data     The data to represent in the ListView.
     */
    public MiAdaptador(@NonNull Context context, int resource, @NonNull List<Puntuacion> data) {
        super(context, resource, data);
        this.context = context;
        this.idLayout = resource;
        this.misDatos = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        ItemLayoutBinding itemLayoutBinding = DataBindingUtil.getBinding(convertView);

        if (itemLayoutBinding == null) {
            itemLayoutBinding = DataBindingUtil.inflate(layoutInflater, idLayout, parent, false);
        }

        itemLayoutBinding.setPuntuacion(misDatos.get(position));
        itemLayoutBinding.executePendingBindings();

        return itemLayoutBinding.getRoot();
    }
}
