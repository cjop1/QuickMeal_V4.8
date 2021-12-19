package com.grupo4.quickmeal_v28_def;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo4.quickmeal_v28_def.datos.DBHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    private final int REQUEST_CODE_GALLERY = 999;
    private TextView elemento;
    private EditText campo1, campo2, campo3, elementoId;
    private Button btnBuscar, btnInsertar, btnConsultar, btnBorrar, btnActualizar;
    private ImageView imgInsertar;

    String name="";

    private DBHelper dbHelper;

    String campo1Insertar;
    String campo2Insertar;
    String campo3Insertar;
    byte[] imageInsertar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        elemento =(TextView) findViewById(R.id.elemento);
        Intent intent = getIntent();
        name= intent.getStringExtra("name");

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnInsertar = (Button) findViewById(R.id.btnInsertar);
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        campo1 = (EditText)findViewById(R.id.editCampo1);
        campo2 = (EditText)findViewById(R.id.editCampo2);
        campo3 = (EditText)findViewById(R.id.editCampo3);
        elementoId = (EditText)findViewById(R.id.txtElementoId);
        imgInsertar = (ImageView) findViewById(R.id.imgFormulario);
        dbHelper = new DBHelper(getApplicationContext() );

        elemento.setText(name);

        if(name.equals("CARTAS")){
            campo1.setHint("Nombre");
            campo2.setHint("Descripción");
            campo3.setHint("Precio");

        }else if(name.equals("COMBOS")){
            campo1.setHint("Nombre");
            campo2.setHint("Descripción");
            campo3.setHint("Precio");

        }else if(name.equals("RESTAURANTES")){
            campo1.setHint("Restaurante");
            campo2.setHint("Dirección");
            campo3.setHint("Locación");
        }

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        FormActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    llenarCampos();
                    dbHelper.insertarData(campo1Insertar, campo2Insertar, campo3Insertar, imageInsertar, name);
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(), "Insert success", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.obtenerDataById(name, elementoId.getText().toString().trim());
                while (cursor.moveToNext()){
                    campo1.setText(cursor.getString(1));
                    campo2.setText(cursor.getString(2));
                    campo3.setText(cursor.getString(3));
                    byte[] img = cursor.getBlob(4);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                    imgInsertar.setImageBitmap(bitmap);
                }

            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenarCampos();
                try {
                    dbHelper.actualizarData(
                            name,
                            elementoId.getText().toString().trim(),
                            campo1Insertar,
                            campo2Insertar,
                            campo3Insertar,
                            imageInsertar
                    );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteData(name, elementoId.getText().toString().trim());
                limpiarCampos();
            }
        });

    }

    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void llenarCampos(){
        campo1Insertar = campo1.getText().toString().trim();
        campo2Insertar = campo2.getText().toString().trim();
        campo3Insertar = campo3.getText().toString().trim();
        imageInsertar = imageViewToByte(imgInsertar);
    }

    public void limpiarCampos(){
        campo1.setText("");
        campo2.setText("");
        campo3.setText("");
        imgInsertar.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
        } else{
            Toast.makeText(getApplicationContext(), "Sin permisos", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data!= null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgInsertar.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

