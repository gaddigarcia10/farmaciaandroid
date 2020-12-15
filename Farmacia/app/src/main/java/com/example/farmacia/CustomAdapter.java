package com.example.farmacia;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivityMedicine listActivityMedicine;
    List<MedicineModel> mMedicineModelList;

    public CustomAdapter(ListActivityMedicine listActivity, List<MedicineModel> medicineModelList) {
        this.listActivityMedicine = listActivity;
        this.mMedicineModelList = medicineModelList;

    }


 @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String nombre = mMedicineModelList.get(position).getName();
                String datecad= mMedicineModelList.get(position).getDatecad();
                String  price= mMedicineModelList.get(position).getPrice();
                Toast.makeText(listActivityMedicine, nombre + " " + datecad + " " + price, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(listActivityMedicine);
                String[] options = {"Actualizar datos", "Eliminar"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            String id = mMedicineModelList.get(position).getId();
                            String name = mMedicineModelList.get(position).getName();
                            String datecad = mMedicineModelList.get(position).getDatecad();
                            String compoundactive = mMedicineModelList.get(position).getCompoundactive();
                            String content = mMedicineModelList.get(position).getContent();
                            String description = mMedicineModelList.get(position).getDescription();
                            String laboratory = mMedicineModelList.get(position).getLaboratory();
                            String price = mMedicineModelList.get(position).getPrice();

                            Intent actualizarDatos = new Intent(listActivityMedicine, MainActivity.class);
                            actualizarDatos.putExtra("updateid", id);
                            actualizarDatos.putExtra("updatename", name);
                            actualizarDatos.putExtra("updatedatecad", datecad);
                            actualizarDatos.putExtra("updatecompoundactive", compoundactive);
                            actualizarDatos.putExtra("updatecontent", content);
                            actualizarDatos.putExtra("updatedescription", description);
                            actualizarDatos.putExtra("updatelaboratory",laboratory);
                            actualizarDatos.putExtra("updateprice", price);

                            listActivityMedicine.startActivity(actualizarDatos);
                                                    }

                        if (which == 1) {
                            listActivityMedicine.eliminarRegistro(position);
                        }
                    }
                }).create().show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvNombre.setText(
                mMedicineModelList.get(i).getName()
                        + " " + mMedicineModelList.get(i).getDatecad()
                        + " " + mMedicineModelList.get(i).getPrice()
        );
    }

    @Override
    public int getItemCount() {
        return mMedicineModelList.size();
    }
}
