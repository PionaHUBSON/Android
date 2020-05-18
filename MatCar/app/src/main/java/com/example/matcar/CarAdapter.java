package com.example.matcar;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarHolder> {
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private Dialog car_delete_dialog;
    ArrayList<Car_Info> car_infos;

    public CarAdapter(@NonNull ArrayList<Car_Info> options) {
        this.car_infos = options;
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item2, parent, false);
        return new CarHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, final int position) {
        holder.brand.setText(car_infos.get(position).getBrand());
        holder.model2.setText(car_infos.get(position).getModel());
        holder.year.setText(car_infos.get(position).getYear());
        holder.vin.setText(car_infos.get(position).getVIN());

        holder.activity_your_cars_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                car_delete_dialog = new Dialog(v.getContext());
                ShowPopUp(v, position);
            }


        });

        holder.activity_your_cars_edit_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), Activity_Edit_Car.class);
                intent.putExtra("document_id", car_infos.get(position).getDocument_id());
                v.getContext().startActivity(intent);
            }
        });
    }

    public void ShowPopUp(View v, final int position) {
        car_delete_dialog.setContentView(R.layout.custompopup2);
        TextView close = car_delete_dialog.findViewById(R.id.close_popup2);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car_delete_dialog.dismiss();
            }
        });
        Button delete = car_delete_dialog.findViewById(R.id.custom_popup2_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                fstore.collection("cars").document(car_infos.get(position).getDocument_id()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                v.getContext().startActivity(new Intent(v.getContext(), YourCars.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
        });
        Button cancel = car_delete_dialog.findViewById(R.id.custom_popup2_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car_delete_dialog.dismiss();
            }
        });
        car_delete_dialog.show();
    }

    @Override
    public int getItemCount() {
        return car_infos.size();
    }

    class CarHolder extends RecyclerView.ViewHolder {
        TextView brand;
        TextView model2;
        TextView vin;
        TextView year;
        Button activity_your_cars_delete, activity_your_cars_edit_car;

        public CarHolder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.car_item_brand);
            model2 = itemView.findViewById(R.id.car_item_model);
            vin = itemView.findViewById(R.id.car_item_vin);
            year = itemView.findViewById(R.id.car_item_year);
            activity_your_cars_delete = itemView.findViewById(R.id.activity_your_cars_delete_car);
            activity_your_cars_edit_car = itemView.findViewById(R.id.activity_your_cars_edit_car);
        }
    }
}
