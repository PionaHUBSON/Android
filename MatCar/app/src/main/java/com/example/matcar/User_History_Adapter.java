package com.example.matcar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class User_History_Adapter extends RecyclerView.Adapter<User_History_Adapter.User_History_Holder> {
    private FirebaseAuth mAuth;
    private FirebaseFirestore history_for_users;
    ArrayList<User_History> user_history_infos;

    public User_History_Adapter(@NonNull ArrayList<User_History> options) {
        this.user_history_infos = options;
        mAuth = FirebaseAuth.getInstance();
        history_for_users = FirebaseFirestore.getInstance();
    }


    @NonNull
    @Override
    public com.example.matcar.User_History_Adapter.User_History_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new User_History_Adapter.User_History_Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final User_History_Adapter.User_History_Holder holder, final int position) {
        holder.name_of_service.setText(user_history_infos.get(position).getName_of_service());
        holder.date_of_order.setText(user_history_infos.get(position).getDate_of_order());
        holder.date_and_time.setText(user_history_infos.get(position).getDate_and_time());
        history_for_users.collection("mechanics").document(user_history_infos.get(position).getMechanic().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String name = task.getResult().get("name").toString();
                String surname = task.getResult().get("surname").toString();
                holder.mechanic.setText(name + " " + surname);
            }
        });
        if (user_history_infos.get(position).getRepair_price() == null) {
            holder.user_history_price.setVisibility(View.GONE);
            holder.user_history_price2.setVisibility(View.GONE);
        } else {
            holder.user_history_price.setText(user_history_infos.get(position).getRepair_price());
        }

        if (user_history_infos.get(position).getCar() == null) {
            holder.ll_user_history_car.setVisibility(View.GONE);
        } else {
            holder.user_history_car.setText(user_history_infos.get(position).getCar());
        }

        holder.status.setText(user_history_infos.get(position).getStatus());
        if (user_history_infos.get(position).getStatus().toString().equals("Przyjęto")) {
            holder.history_item_cancel_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    history_for_users.collection("mechanics_schedule").whereEqualTo("history_id", user_history_infos.get(position).getDocument_id())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    document.getReference().delete();
                                }
                            }
                        }
                    });
                    history_for_users.collection("diagnostics").whereEqualTo("history_id", user_history_infos.get(position).getDocument_id())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    document.getReference().delete();
                                }
                            }
                        }
                    });
                    history_for_users.collection("overview").whereEqualTo("history_id", user_history_infos.get(position).getDocument_id())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    document.getReference().delete();
                                }
                            }
                        }
                    });
                    history_for_users.collection("repairs_orders").whereEqualTo("history_id", user_history_infos.get(position).getDocument_id())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    document.getReference().delete();
                                }
                            }
                        }
                    });
                    history_for_users.collection("history").document(user_history_infos.get(position).getDocument_id()).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    v.getContext().startActivity(new Intent(v.getContext(), History.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });

                }
            });
        } else {
            holder.history_item_cancel_service.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return user_history_infos.size();
    }

    class User_History_Holder extends RecyclerView.ViewHolder {
        TextView date_and_time, date_of_order, mechanic, name_of_service, status;
        Button history_item_cancel_service;
        CardView history_item_card;
        TextView user_history_price;
        TextView user_history_price2;
        LinearLayout ll_user_history_car;
        TextView user_history_car;

        public User_History_Holder(@NonNull View itemView) {
            super(itemView);
            date_and_time = itemView.findViewById(R.id.user_history_termin);
            date_of_order = itemView.findViewById(R.id.user_history_date_of_order);
            mechanic = itemView.findViewById(R.id.user_history_mechanic);
            name_of_service = itemView.findViewById(R.id.user_history_service);
            status = itemView.findViewById(R.id.user_history_status);
            history_item_cancel_service = itemView.findViewById(R.id.history_item_cancel_service);
            history_item_card = itemView.findViewById(R.id.history_item_card);
            user_history_price = itemView.findViewById(R.id.user_history_price);
            user_history_price2 = itemView.findViewById(R.id.user_history_price_2);
            user_history_car = itemView.findViewById(R.id.user_history_car);
            ll_user_history_car = itemView.findViewById(R.id.ll_user_history_car);
        }
    }
}

