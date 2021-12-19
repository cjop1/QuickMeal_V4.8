package com.grupo4.quickmeal_v28_def.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupo4.quickmeal_v28_def.R;
import com.grupo4.quickmeal_v28_def.modelo.Carta;

import java.util.ArrayList;

public class CartaAdaptador extends BaseAdapter {

    Context context;
    ArrayList<Carta> cartas;
    LayoutInflater inflater;

    public CartaAdaptador(Context context, ArrayList<Carta> cartas) {
        this.context = context;
        this.cartas = cartas;
    }

    @Override
    public int getCount() {
        return cartas.size() ;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.grid_general, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imageGrid);
        TextView campoId = convertView.findViewById(R.id.txtGralId);
        TextView campoProducto = convertView.findViewById(R.id.txtGralProducto);
        TextView campoDescripcion = convertView.findViewById(R.id.txtGralDescripcion);
        TextView campoPrecio = convertView.findViewById(R.id.txtGralPrecio);

        Carta carta = cartas.get(position);
        byte[] image = carta.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,image.length);

        campoId.setText("ID:"+ carta.getId());
        campoProducto.setText(carta.getNombre());
        campoDescripcion.setText(carta.getDescripcion());
        campoPrecio.setText(carta.getPrecio());
        imageView.setImageBitmap(bitmap);

        return convertView;
    }
}
