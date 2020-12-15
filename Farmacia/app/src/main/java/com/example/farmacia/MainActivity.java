package com.example.farmacia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class MainActivity extends AppCompatActivity {

    EditText  etname, etdatecad, etcompoundactive, etcontent, etdescription, etlaboratory, etprice;
    FloatingActionButton fabGuardar, fabListar;

    ProgressDialog progressDialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String updateid, updatename, updatedatecad, updatecompoundactive, updatecontent, updatedescription, updatelaboratory, updateprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etname = findViewById(R.id.etname);
        etdatecad = findViewById(R.id.etdatecad);
        etcompoundactive = findViewById(R.id.etcompoundactive);
        etcontent = findViewById(R.id.etcontent);
        etdescription = findViewById(R.id.etdescription);
        etlaboratory = findViewById(R.id.etlaboratory);
        etprice = findViewById(R.id.etprice);

        fabGuardar = findViewById(R.id.fabGuardar);
        fabListar = findViewById(R.id.fabListar);

        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Agregar registro");


        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actionBar.setTitle("Actualizar Datos");

            updateid = bundle.getString("updateid");
            updatename = bundle.getString("updatename");
            updatedatecad = bundle.getString("updatedatecad");
            updatecompoundactive = bundle.getString("updatecompoundactive");
            updatecontent= bundle.getString("updatecontent");
            updatedescription = bundle.getString("updatedescription");
            updatelaboratory= bundle.getString("updatelaboratory");
            updateprice = bundle.getString("updateprice");

            etname.setText(updatename);
            etdatecad.setText(updatedatecad);
            etcompoundactive.setText(updatecompoundactive);
            etcontent.setText(updatecontent);
            etdescription.setText(updatedescription);
            etlaboratory.setText(updatelaboratory);
            etprice.setText(updateprice);

        } else {
            actionBar.setTitle("Agregar");
        }


        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = updateid;
                    String nombre = etname.getText().toString().trim();
                    String datecad = etdatecad.getText().toString().trim();
                    String compoundactive = etcompoundactive.getText().toString().trim();
                    String content = etcontent.getText().toString().trim();
                    String description = etdescription.getText().toString().trim();
                    String laboratory = etlaboratory.getText().toString().trim();
                    String price = etprice.getText().toString().trim();

                    actualizarDatos(id, nombre, datecad, compoundactive, content, description, laboratory, price);

                } else {
                    String nombre = etname.getText().toString().trim();
                    String datecad = etdatecad.getText().toString().trim();
                    String compoundactive = etcompoundactive.getText().toString().trim();
                    String content = etcontent.getText().toString().trim();
                    String description = etdescription.getText().toString().trim();
                    String laboratory = etlaboratory.getText().toString().trim();
                    String price = etprice.getText().toString().trim();

                    cargarDatos(nombre, datecad, compoundactive, content, description, laboratory, price);
                }
            }
        });


        fabListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivityMedicine.class));
                finish();
            }
        });

    }


    private void cargarDatos( String name, String compoundactive, String datecad, String content, String description, String laboratory, String price) {
        progressDialog.setTitle("Agregar datos");
        progressDialog.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("name", name);
        doc.put("compoundactive", compoundactive);
        doc.put("datecad", datecad);
        doc.put("content",content );
        doc.put("description", description);
        doc.put("laboratory", laboratory);
        doc.put("price", price);


        db.collection("Documents").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Datos almacenados con éxito...", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void actualizarDatos(String id, String name, String compoundactive, String datecad, String content, String description, String laboratory, String price) {
        progressDialog.setTitle("Actualizando datos a Firebase");
        progressDialog.show();



        db.collection("Documents")
                .document(id).update(
                "nombre", name,
                "compoundactive", compoundactive,
                "datecad", datecad,
                "content", content,
                "descriptio", description,
                "laboratory", laboratory,
                "price", price
        )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Actualización exitosa...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}