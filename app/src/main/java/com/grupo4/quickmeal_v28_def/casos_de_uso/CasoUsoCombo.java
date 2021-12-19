package com.grupo4.quickmeal_v28_def.casos_de_uso;

import android.database.Cursor;

import com.grupo4.quickmeal_v28_def.modelo.Combo;

import java.util.ArrayList;

public class CasoUsoCombo {
    public ArrayList<Combo>llenarListaCombos(Cursor cursor){
        ArrayList<Combo>list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else{
            while (cursor.moveToNext()){
                Combo combo = new Combo(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4)
                );
                list.add(combo);
            }
            return list;
        }
    }

}
