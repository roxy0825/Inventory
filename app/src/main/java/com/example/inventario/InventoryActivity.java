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

public class InventoryActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView etCodigo, etNombre, etCantidad, etValor;
    CheckBox cbActivo;

    String codigo, nombre, cantidad, valor,codigoId;

    byte sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        getSupportActionBar().hide();
        etCodigo = findViewById(R.id.tvCodigo);
        etNombre = findViewById(R.id.tvNombre);
        etCantidad = findViewById(R.id.tvCantidad);
        etValor = findViewById(R.id.tvValor);
        cbActivo = findViewById(R.id.cbActivo);
        etCodigo.requestFocus();
        sw = 0;
    }

    public void adicionar(View view) {
        codigo = etCodigo.getText().toString();
        nombre = etNombre.getText().toString();
        cantidad = etCantidad.getText().toString();
        valor = etValor.getText().toString();
        if (codigo.isEmpty() || nombre.isEmpty() || cantidad.isEmpty()
                || valor.isEmpty()) {
            Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
            etCodigo.requestFocus();
        } else {
            Map<String, Object> invetory = new HashMap<>();
            invetory.put("Codigo", codigo);
            invetory.put("Nombre", nombre);
            invetory.put("Cantidad", cantidad);
            invetory.put("Valor", valor);
            invetory.put("Activo", "si");

            System.out.println("*** " + invetory);

            // Add a new document with a generated ID
            db.collection("inventory")
                    .add(invetory)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(InventoryActivity.this, "Datos guardados!", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InventoryActivity.this, "Error, guardando campos!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void consultar(View view) {
        codigo = etCodigo.getText().toString();
        if (codigo.isEmpty()) {
            Toast.makeText(this, "La placa es requerido", Toast.LENGTH_SHORT).show();
            etCodigo.requestFocus();
        } else {
            db.collection("inventory")
                    .whereEqualTo("Codigo", codigo)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                sw = 1;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    codigoId = document.getId();
                                    etCodigo.setText(document.getString("Codigo"));
                                    etNombre.setText(document.getString("Nombre"));
                                    etCantidad.setText(document.getString("Cantidad"));
                                    etValor.setText(document.getString("Valor"));

                                    if (document.getString("Activo").equals("si")) {
                                        cbActivo.setChecked(Boolean.TRUE);
                                    } else {
                                        cbActivo.setChecked(Boolean.FALSE);
                                    }
                                }
                            } else {
                                Toast.makeText(InventoryActivity.this, "Error consultando datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void modificar(View view) {
        if (sw == 0) {
            Toast.makeText(this, "Para modificar debe primero consultar", Toast.LENGTH_SHORT).show();
            etCodigo.requestFocus();
        } else {
            codigo = etCodigo.getText().toString();
            nombre = etNombre.getText().toString();
            cantidad = etCantidad.getText().toString();
            valor = etValor.getText().toString();
            if (codigo.isEmpty() || nombre.isEmpty() || nombre.isEmpty()
                    || valor.isEmpty() || valor.isEmpty()) {
                Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
                etCodigo.requestFocus();
            } else {
                // Create a new user with a first and last name
                Map<String, Object> vehiculo = new HashMap<>();
                vehiculo.put("Codigo", codigo);
                vehiculo.put("Nombre", nombre);
                vehiculo.put("Cantidad", cantidad);
                vehiculo.put("Valor", valor);
                vehiculo.put("Activo", "si");
                db.collection("inventory").document(codigoId)
                        .set(vehiculo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(InventoryActivity.this, "Vehiculo actualizado correctmente...", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(InventoryActivity.this, "Error actualizando vehiculo...", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }

    public void Anular(View view) {
        if (sw == 0) {
            Toast.makeText(this, "Para anular debe primero consultar", Toast.LENGTH_SHORT).show();
            etCodigo.requestFocus();
        } else {
            codigo = etCodigo.getText().toString();
            nombre = etNombre.getText().toString();
            cantidad = etCantidad.getText().toString();
            valor = etValor.getText().toString();
            if (codigo.isEmpty() || cantidad.isEmpty() || nombre.isEmpty()
                    || valor.isEmpty() || valor.isEmpty()) {
                Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
                etCodigo.requestFocus();
            } else {
                // Create a new user with a first and last name
                Map<String, Object> vehiculo = new HashMap<>();
                vehiculo.put("Codigo", codigo);
                vehiculo.put("Nombre", nombre);
                vehiculo.put("Cantidad", cantidad);
                vehiculo.put("Valor", valor);
                vehiculo.put("Activo", "no");
                db.collection("inventory").document(codigoId)
                        .set(vehiculo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(InventoryActivity.this, "Vehiculo anulado correctmente...", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                                cbActivo.setClickable(false);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(InventoryActivity.this, "Error anulando vehiculo...", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }

    public void cancelar(View view) {
        limpiarCampos();
    }

    public void listar(View view){
        Intent intlistar = new Intent(this, ListarIventoryActivity.class);
       startActivity(intlistar);
    }

    private void limpiarCampos() {
        etCodigo.setText("");
        etNombre.setText("");
        etCantidad.setText("");
        etValor.setText("");
        cbActivo.setChecked(false);
        sw = 0;
        etCodigo.requestFocus();
    }
}