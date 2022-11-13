package com.example.inventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListarIventoryActivity extends AppCompatActivity {

    RecyclerView recyclerpaseo;
    ArrayList<Inventory> listaParking;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_iventory);

        recyclerpaseo = findViewById(R.id.rvListarInventory);
        listaParking = new ArrayList<>();
        recyclerpaseo.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerpaseo.setHasFixedSize(true);
        cargarParking();
    }

    private void cargarParking() {
        db.collection("inventory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                Inventory objParking = new Inventory();
                                objParking.setCodigo(document.getString("Codigo"));
                                objParking.setNombre(document.getString("Nombre"));
                                objParking.setCantidad(document.getString("Cantidad"));
                                objParking.setValor(document.getString("Valor"));
                                objParking.setActivo(document.getString("Activo"));
                                listaParking.add(objParking);
                            }
                            InventoryAdapter adpaseo = new InventoryAdapter(listaParking);
                            recyclerpaseo.setAdapter(adpaseo);
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}