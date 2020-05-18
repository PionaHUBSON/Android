package com.example.matcar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static com.example.matcar.Repairs_Final.choose_car_button_repair;
import static com.example.matcar.Repairs_Final.myDialog_final;

public class CarAdapter_Final_Repair extends RecyclerView.Adapter<CarAdapter_Final_Repair.CarHolder> {
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    ArrayList<Car_Info> car_infos;
    public static String final_car;
    public static String final_id;

    public CarAdapter_Final_Repair(@NonNull ArrayList<Car_Info> options) {
        this.car_infos = options;
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item3, parent, false);
        return new CarHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, final int position) {
        holder.brand.setText(car_infos.get(position).getBrand());
        holder.model2.setText(car_infos.get(position).getModel());
        holder.year.setText(car_infos.get(position).getYear());
        holder.vin.setText(car_infos.get(position).getVIN());
        holder.final_repair_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_car_button_repair.setText("Wybrano: " + car_infos.get(position).getBrand());
                final_car = car_infos.get(position).getBrand() + " " + car_infos.get(position).getModel();
                final_id = car_infos.get(position).getDocument_id();
                myDialog_final.dismiss();
            }
        });
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
        Button final_repair_confirm;

        public CarHolder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.final_repair_car_item_brand);
            model2 = itemView.findViewById(R.id.final_repair_car_item_model);
            vin = itemView.findViewById(R.id.final_repair_car_item_vin);
            year = itemView.findViewById(R.id.final_repair_car_item_year);
            final_repair_confirm = itemView.findViewById(R.id.final_repair_choose_car);
        }
    }
}
