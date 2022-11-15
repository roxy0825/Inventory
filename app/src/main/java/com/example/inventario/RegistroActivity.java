package com.example.inventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView etUsuario, etNombre, etCorreo, etClave;
    CheckBox cbActivo;

    String usuario, nombre, correo, clave, usuarioId;

    byte sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        getSupportActionBar().hide();
        etUsuario = findViewById(R.id.etUsuario);
        etNombre = findViewById(R.id.etNombreUsuario);
        etCorreo = findViewById(R.id.etCorreo);
        etClave = findViewById(R.id.etClave);
        cbActivo = findViewById(R.id.cbActivoUsuario);
        etUsuario.requestFocus();
        sw = 0;
    }

    public void regresar(View view) {
        Intent intMain = new Intent(this, MainActivity.class);
        startActivity(intMain);
    }

    public void adicionar(View view) {
        usuario = etUsuario.getText().toString();
        nombre = etNombre.getText().toString();
        correo = etCorreo.getText().toString();
        clave = etClave.getText().toString();
        if (usuario.isEmpty() || nombre.isEmpty() || correo.isEmpty()
                || clave.isEmpty()) {
            Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
            etUsuario.requestFocus();
        } else {
            Map<String, Object> usuarios = new HashMap<>();
            usuarios.put("Usuario", usuario);
            usuarios.put("Nombre", nombre);
            usuarios.put("Correo", correo);
            usuarios.put("Clave", clave);
            usuarios.put("Activo", "si");

            // Add a new document with a generated ID
            db.collection("usuarios")
                    .add(usuarios)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegistroActivity.this, "Datos guardados!", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistroActivity.this, "Error, guardando campos!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void consultar(View view) {
        usuario = etUsuario.getText().toString();
        if (usuario.isEmpty()) {
            Toast.makeText(this, "El usuario es requerido", Toast.LENGTH_SHORT).show();
            etUsuario.requestFocus();
        } else {
            db.collection("usuarios")
                    .whereEqualTo("Usuario", usuario)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                sw = 1;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    usuarioId = document.getId();
                                    etUsuario.setText(document.getString("Usuario"));
                                    etNombre.setText(document.getString("Nombre"));
                                    etCorreo.setText(document.getString("Correo"));
                                    etClave.setText(document.getString("Clave"));
                                    if (document.getString("Activo").equals("si")) {
                                        cbActivo.setChecked(Boolean.TRUE);
                                    } else {
                                        cbActivo.setChecked(Boolean.FALSE);
                                    }
                                }
                            } else {
                                Toast.makeText(RegistroActivity.this, "Error consultando datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void modificar(View view) {
        if (sw == 0) {
            Toast.makeText(this, "Para modificar debe primero consultar", Toast.LENGTH_SHORT).show();
            etUsuario.requestFocus();
        } else {
            usuario = etUsuario.getText().toString();
            nombre = etNombre.getText().toString();
            correo = etCorreo.getText().toString();
            clave = etClave.getText().toString();
            if (usuario.isEmpty() || nombre.isEmpty() || correo.isEmpty()
                    || clave.isEmpty()) {
                Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
                etUsuario.requestFocus();
            } else {
                // Create a new user with a first and last name
                Map<String, Object> usuarios = new HashMap<>();
                usuarios.put("Usuario", usuario);
                usuarios.put("Nombre", nombre);
                usuarios.put("Correo", correo);
                usuarios.put("Clave", clave);
                usuarios.put("Activo", "si");

                db.collection("usuarios").document(usuarioId)
                        .set(usuarios)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RegistroActivity.this, "Usuario actualizado correctmente...", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegistroActivity.this, "Error actualizando usuario...", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }

    public void anular(View view) {
        if (sw == 0) {
            Toast.makeText(this, "Para anular debe primero consultar", Toast.LENGTH_SHORT).show();
            etUsuario.requestFocus();
        } else {
            usuario = etUsuario.getText().toString();
            nombre = etNombre.getText().toString();
            correo = etCorreo.getText().toString();
            clave = etClave.getText().toString();
            if (usuario.isEmpty() || nombre.isEmpty() || correo.isEmpty()
                    || clave.isEmpty()) {
                Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
                etUsuario.requestFocus();
            } else {
                // Create a new user with a first and last name
                Map<String, Object> usuarios = new HashMap<>();
                usuarios.put("Usuario", usuario);
                usuarios.put("Nombre", nombre);
                usuarios.put("Correo", correo);
                usuarios.put("Password", clave);
                usuarios.put("Activo", "no");

                db.collection("usuarios").document(usuarioId)
                    .set(usuarios)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegistroActivity.this, "Usuario anulado correctmente...", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                            cbActivo.setClickable(false);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistroActivity.this, "Error anulando usuario...", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        }

    }

    public void listarUsuario(View view) {
        Intent intListarUsuario =new Intent(this, ListarUsuarioActivity.class);
        startActivity(intListarUsuario);
    }

    public void cancelar(View view) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        etUsuario.setText("");
        etNombre.setText("");
        etCorreo.setText("");
        etClave.setText("");
        cbActivo.setChecked(false);
        sw = 0;
        etUsuario.requestFocus();
    }
}