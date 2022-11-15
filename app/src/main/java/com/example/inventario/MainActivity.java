package com.example.inventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView etCorreo, etClave;

    String correo, clave, usuarioId;

    byte sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        etCorreo = findViewById(R.id.etcorreo);
        etClave = findViewById(R.id.etclave);
        etCorreo.requestFocus();

        usuarioId = "";
        sw = 0;
    }

    public void registrarse(View view){
        Intent intRegistrar=new Intent(this, RegistroActivity.class);
        startActivity(intRegistrar);
    }

    public void login(View view) {
        correo = etCorreo.getText().toString();
        clave = etClave.getText().toString();
        if (correo.isEmpty() || clave.isEmpty()) {
            Toast.makeText(MainActivity.this, "Todos los campos son requeridos!", Toast.LENGTH_SHORT).show();
            etCorreo.requestFocus();
        } else {
            db.collection("usuarios")
                .whereEqualTo("Correo", correo)
                .whereEqualTo("Clave", clave)
                .whereEqualTo("Activo", "si")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                usuarioId = document.getId();
                            }

                            if (!usuarioId.isEmpty()) {
                                Toast.makeText(MainActivity.this, "Session iniciada", Toast.LENGTH_SHORT).show();
                                Intent intParking = new Intent(MainActivity.this, InventoryActivity.class);
                                startActivity(intParking);
                            } else {
                                Toast.makeText(MainActivity.this, "Corro o Clave incorrectos", Toast.LENGTH_SHORT).show();
                            }

                            limpiarCampos();

                        } else {
                            Toast.makeText(MainActivity.this, "Error consultando datos", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        }
                    }
                });
        }
    }

    private void limpiarCampos() {
        etCorreo.setText("");
        etClave.setText("");
        sw = 0;
        etCorreo.requestFocus();
    }

}