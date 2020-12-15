package com.example.farmacia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import android.app.ListActivity;

import java.util.List;

import com.example.farmacia.CustomAdapter;

public class ListActivityMedicine extends AppCompatActivity {

    java.util.List<MedicineModel> mmedicineModelList = new ArrayList<>();
    RecyclerView recyclerview;

    //Crear instancia de FirebaseFirestore
    FirebaseFirestore db;

    CustomAdapter customAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    Context context;
    FloatingActionButton fabAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_actiity_medicine);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ver registros");

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        fabAgregar = findViewById(R.id.fabAgregar);

        // Obtener instancia de Firebase
        db = FirebaseFirestore.getInstance();

        verDatos();

        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivityMedicine.this, MainActivity.class));
                finish();
            }
        });
    }

    public void eliminarRegistro(int index) {
        db.collection("Documents").document(mmedicineModelList.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ListActivityMedicine.this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                        verDatos();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListActivityMedicine.this, "No se ha completado la operación" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void verDatos() {
        db.collection("Documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        mmedicineModelList.clear();
                        for (DocumentSnapshot doc : task.getResult()) {
                            MedicineModel MedicineModel = new MedicineModel(
                                    doc.getString("id"),
                                    doc.getString("name"),
                                    doc.getString("datecad"),
                                    doc.getString("compoundactive"),
                                    doc.getString("content"),
                                    doc.getString("description"),
                                    doc.getString("laboratory"),
                                    doc.getString("price")
                            );

                            mmedicineModelList.add(MedicineModel);
                        }
                        customAdapter = new CustomAdapter(ListActivityMedicine.this, mmedicineModelList);
                        recyclerview.setAdapter(customAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ListActivityMedicine.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}