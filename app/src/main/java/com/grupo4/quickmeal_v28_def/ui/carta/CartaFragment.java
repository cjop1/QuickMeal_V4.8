package com.grupo4.quickmeal_v28_def.ui.carta;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.grupo4.quickmeal_v28_def.FormActivity;
import com.grupo4.quickmeal_v28_def.R;
import com.grupo4.quickmeal_v28_def.adaptadores.CartaAdaptador;
import com.grupo4.quickmeal_v28_def.casos_de_uso.CasoUsoProducto;
import com.grupo4.quickmeal_v28_def.datos.DBHelper;
import com.grupo4.quickmeal_v28_def.modelo.Carta;

import java.util.ArrayList;

public class CartaFragment extends Fragment {

    private String TABLE_NAME = "CARTAS";
    private CasoUsoProducto casoUsoProducto;
    private GridView gridView;
    private DBHelper DBHelper;
    private ArrayList<Carta> cartas;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_carta, container, false);
        try {
            casoUsoProducto = new CasoUsoProducto();
            DBHelper = new DBHelper(getContext());
            Cursor cursor = DBHelper.obtenerData(TABLE_NAME);
            cartas = casoUsoProducto.llenarListaProductos(cursor);
            gridView = (GridView) root.findViewById(R.id.gridCarta);
            CartaAdaptador cartaAdaptador = new CartaAdaptador(root.getContext(), cartas);
            gridView.setAdapter(cartaAdaptador);

        }catch (Exception e){
            Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("name", "CARTAS");
                getActivity().startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}