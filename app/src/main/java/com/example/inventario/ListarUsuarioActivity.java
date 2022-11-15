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

public class ListarUsuarioActivity extends AppCompatActivity {

    RecyclerView recyclerUsuario;
    ArrayList<Usuario> listaUsuarios;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuario);

        recyclerUsuario = findViewById(R.id.rvListarUsuario);
        listaUsuarios = new ArrayList<>();
        recyclerUsuario.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerUsuario.setHasFixedSize(true);
        cargarUsuario();
    }

    private void cargarUsuario() {
        db.collection("usuarios")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Log.d(TAG, document.getId() + " => " + document.getData());
                            Usuario objUsuario = new Usuario();
                            objUsuario.setUsuario(document.getString("Usuario"));
                            objUsuario.setNombre(document.getString("Nombre"));
                            objUsuario.setCorreo(document.getString("Correo"));
                            objUsuario.setEstado(document.getString("Activo"));
                            listaUsuarios.add(objUsuario);
                        }
                        UsuarioAdapter adpaseo = new UsuarioAdapter(listaUsuarios);
                        recyclerUsuario.setAdapter(adpaseo);
                    } else {
                        //Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
    }


}