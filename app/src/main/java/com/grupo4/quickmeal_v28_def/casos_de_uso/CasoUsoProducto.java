package com.grupo4.quickmeal_v28_def.casos_de_uso;

import android.database.Cursor;

import com.grupo4.quickmeal_v28_def.modelo.Carta;

import java.util.ArrayList;

public class CasoUsoProducto {
    public ArrayList<Carta>llenarListaProductos(Cursor cursor){
        ArrayList<Carta>list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else{
            while (cursor.moveToNext()){
                Carta carta = new Carta(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4)
                );
                list.add(carta);
            }
            return list;
        }
    }

}
