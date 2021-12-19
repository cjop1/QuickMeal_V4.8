package com.grupo4.quickmeal_v28_def.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    /**
     * Declaración del objeto de la base de datos
     */
    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, "BDQuickMeal.db", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * CREACION DE TABLAS DE BASE DE DATOS
         */

        db.execSQL("CREATE TABLE CARTAS(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "PRODUCTO VARCHAR," +
                "DESCRIPCION VARCHAR," +
                "PRECIO VARCHAR," +
                "IMAGE BLOB" +
                ")");

        db.execSQL("CREATE TABLE COMBOS(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "PRODUCTO VARCHAR," +
                "DESCRIPCION VARCHAR," +
                "PRECIO VARCHAR," +
                "IMAGE BLOB" +
                ")");

        db.execSQL("CREATE TABLE RESTAURANTES(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "RESTAURANTE VARCHAR," +
                "DIRECCI0N VARCHAR," +
                "LOCACION VARCHAR," +
                "IMAGE BLOB" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CARTAS");
        db.execSQL("DROP TABLE IF EXISTS COMBOS");
        db.execSQL("DROP TABLE IF EXISTS RESTAURANTES");
    }

    /**
     * CRUD
     */

    //1. Obtener Producto

    public Cursor obtenerData(String table){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+table, null);
        return cursor;
    }

    //2. Obtener Producto by Id

    public Cursor obtenerDataById(String table, String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM" + table + "WHERE ID = "+id, null);
        return cursor;
    }


    //3. Insertar Producto

    public void insertarData(String campo1, String campo2, String campo3, byte[] image, String table){
        String sql = "INSERT INTO "+ table +" VALUES(null, ?, ?, ?, ?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, campo1);
        statement.bindString(2, campo2);
        statement.bindString(3, campo3);
        statement.bindBlob(4, image);

        statement.executeInsert();
    }


    //4. Actualizar Producto

    public void actualizarData(String table, String ID, String campo1, String campo2, String campo3, byte[] IMAGE){
        ContentValues contentValues = new ContentValues();
        contentValues.put("producto", campo1);
        contentValues.put("descripcion", campo2);
        contentValues.put("precio", campo3);
        contentValues.put("image", IMAGE);
        sqLiteDatabase.update(table, contentValues, "id=?", new String[]{ID});
    }


    //5. Borrar Producto

    public void deleteData(String table, String id){
        sqLiteDatabase.execSQL("DELETE FROM " + table + " WHERE ID = "+id);
    }

}

