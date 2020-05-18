package com.example.matcar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import static com.example.matcar.Repairs.no_orders_info;
import static com.example.matcar.Repairs.total_price;

public class Basket_Adapter extends RecyclerView.Adapter<Basket_Adapter.Basket_Holder> {

    private FirebaseAuth mAuth;
    private FirebaseFirestore history_for_users;
    ArrayList<String> basket_info;
    String[] all_item;

    public Basket_Adapter(@NonNull ArrayList<String> options) {
        this.basket_info = options;
        mAuth = FirebaseAuth.getInstance();
        history_for_users = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public com.example.matcar.Basket_Adapter.Basket_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_item, parent, false);
        return new Basket_Adapter.Basket_Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Basket_Adapter.Basket_Holder holder, final int position) {

        if (basket_info.size() == 0) {
        } else {
            all_item = basket_info.get(position).split(";");
            holder.basket_name.setText(all_item[0]);
            holder.basket_price.setText("Szacowana cena: " + all_item[1]);
            if (all_item.length == 2) holder.basket_options.setVisibility(View.GONE);
            if (all_item.length == 3) {
                holder.basket_options.setText("Opcje naprawy: " + all_item[2]);
            }
        }
        holder.basket_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basket_info.remove(position);
                notifyDataSetChanged();
                if (basket_info.size() == 0) {
                    total_price.setText("Całkowita szacowana cena zamówienia: 0 PLN");
                    no_orders_info.setVisibility(View.VISIBLE);
                }
            }

        });
        String[] split_price;
        String[] split_price2;
        int min_price = 0;
        int max_price = 0;

        for (int i = 0; i < basket_info.size(); i++) {
            all_item = basket_info.get(i).split(";");
            split_price = all_item[1].split("-");
            split_price2 = split_price[1].split(" ");
            min_price += Integer.valueOf(split_price[0]);
            max_price += Integer.valueOf(split_price2[0]);
        }
        total_price.setText("Całkowita szacowana cena zamówienia: " + String.valueOf(min_price) + "-" + max_price + " PLN");
    }

    @Override
    public int getItemCount() {
        return basket_info.size();
    }

    class Basket_Holder extends RecyclerView.ViewHolder {
        TextView basket_name, basket_price, basket_options;
        Button basket_delete;
        CardView all_basket;

        public Basket_Holder(@NonNull View itemView) {
            super(itemView);
            basket_name = itemView.findViewById(R.id.basket_name);
            basket_price = itemView.findViewById(R.id.basket_price);
            basket_options = itemView.findViewById(R.id.basket_options);
            basket_delete = itemView.findViewById(R.id.basket_delete);
            all_basket = itemView.findViewById(R.id.all_basket);

        }

    }
}
