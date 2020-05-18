package com.example.matcar;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Repair_Data_Adapter extends RecyclerView.Adapter<Repair_Data_Adapter.Repair_Data_Holder> {

    private FirebaseAuth mAuth;
    private FirebaseFirestore repairs_for_users;
    List<Repair_Data> repair_data;
    protected Context context;
    StorageReference imageReference;
    private Dialog add_dialog;
    private CheckBox repairs_checkbox_left_front, repairs_checkbox_left_rear, repairs_checkbox_right_front, repairs_checkbox_right_rear;
    public static ArrayList<String> services = new ArrayList<>();
    private RecyclerView repairs_total_order_recycler_view;

    public Repair_Data_Adapter(@NonNull List<Repair_Data> options, Context context) {
        this.repair_data = options;
        mAuth = FirebaseAuth.getInstance();
        repairs_for_users = FirebaseFirestore.getInstance();
        this.context = context;
    }


    @NonNull
    @Override
    public com.example.matcar.Repair_Data_Adapter.Repair_Data_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.repair_item, parent, false);
        return new Repair_Data_Adapter.Repair_Data_Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Repair_Data_Holder holder, final int position) {
        holder.name.setText(repair_data.get(position).getName());
        holder.description.setText(repair_data.get(position).getDescription());
        holder.repair_price.setText("Szacowana cena naprawy\n" + repair_data.get(position).getRepair_price());
        StorageReference storageReference;
        imageReference = FirebaseStorage.getInstance().getReference().child(repair_data.get(position).getImg());
        storageReference = FirebaseStorage.getInstance().getReference().child(repair_data.get(position).getImg());
        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Glide.with(context)
                            .load(task.getResult())
                            .into(holder.photo);

                } else {
                }
            }
        });
        holder.repair_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_dialog = new Dialog(v.getContext());
                add_dialog.setContentView(R.layout.custompopup_add_repair);
                add_dialog.getWindow().setBackgroundDrawable(v.getResources().getDrawable(R.drawable.rounded_background));
                TextView close = add_dialog.findViewById(R.id.close_repair_add_repair);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_dialog.dismiss();
                    }
                });
                TextView name = add_dialog.findViewById(R.id.custompopup_add_repair_name);
                TextView price = add_dialog.findViewById(R.id.custompopup_add_repair_price);
                LinearLayout popup_repairs_all_options = add_dialog.findViewById(R.id.popup_repairs_all_options);
                popup_repairs_all_options.setVisibility(View.GONE);
                LinearLayout popup_repairs_front = add_dialog.findViewById(R.id.popup_repairs_front);
                popup_repairs_front.setVisibility(View.GONE);
                LinearLayout popup_repairs_rear = add_dialog.findViewById(R.id.popup_repairs_rear);
                popup_repairs_rear.setVisibility(View.GONE);
                name.setText(holder.name.getText());
                price.setText(holder.repair_price.getText());
                if (repair_data.get(position).getIsFrontLeft().equals("true") && repair_data.get(position).getIsFrontRight().equals("true")) {
                    popup_repairs_all_options.setVisibility(View.VISIBLE);
                    popup_repairs_front.setVisibility(View.VISIBLE);
                }
                if (repair_data.get(position).getIsRearLeft().equals("true") && repair_data.get(position).getIsRearRight().equals("true")) {
                    popup_repairs_all_options.setVisibility(View.VISIBLE);
                    popup_repairs_rear.setVisibility(View.VISIBLE);
                }
                repairs_checkbox_left_front = add_dialog.findViewById(R.id.popup_checkbox_left_front);
                repairs_checkbox_right_front = add_dialog.findViewById(R.id.popup_checkbox_right_front);
                repairs_checkbox_left_rear = add_dialog.findViewById(R.id.popup_checkbox_left_rear);
                repairs_checkbox_right_rear = add_dialog.findViewById(R.id.popup_checkbox_right_rear);
                final Button repair_add_button = add_dialog.findViewById(R.id.repair_add_button);

                repairs_total_order_recycler_view = add_dialog.findViewById(R.id.repairs_total_order_recycler_view);
                repair_add_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (repair_data.get(position).getIsFrontLeft().equals("true") && repair_data.get(position).getIsFrontRight().equals("true")
                                && repair_data.get(position).getIsRearLeft().equals("true") && repair_data.get(position).getIsRearRight().equals("true")) {
                            if (!repairs_checkbox_left_front.isChecked() && !repairs_checkbox_right_front.isChecked()
                                    && !repairs_checkbox_right_rear.isChecked() && !repairs_checkbox_left_rear.isChecked()) {
                                Toast.makeText(v.getContext(), "Musisz zaznaczyć przynajmniej jedną z opcji", Toast.LENGTH_SHORT).show();
                            } else {
                                if (repairs_checkbox_left_front.isChecked() && !repairs_checkbox_right_front.isChecked() &&
                                        !repairs_checkbox_left_rear.isChecked() && !repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy przód");
                                } else if (!repairs_checkbox_left_front.isChecked() && repairs_checkbox_right_front.isChecked() &&
                                        !repairs_checkbox_left_rear.isChecked() && !repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Prawy przód");
                                } else if (!repairs_checkbox_left_front.isChecked() && !repairs_checkbox_right_front.isChecked() &&
                                        repairs_checkbox_left_rear.isChecked() && !repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy tył");
                                } else if (!repairs_checkbox_left_front.isChecked() && !repairs_checkbox_right_front.isChecked() &&
                                        !repairs_checkbox_left_rear.isChecked() && repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Prawy tył");
                                } else if (repairs_checkbox_left_front.isChecked() && repairs_checkbox_right_front.isChecked() &&
                                        !repairs_checkbox_left_rear.isChecked() && !repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy przód, Prawy przód");
                                } else if (repairs_checkbox_left_front.isChecked() && !repairs_checkbox_right_front.isChecked() &&
                                        repairs_checkbox_left_rear.isChecked() && !repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy przód, Lewy tył");
                                } else if (repairs_checkbox_left_front.isChecked() && !repairs_checkbox_right_front.isChecked() &&
                                        !repairs_checkbox_left_rear.isChecked() && repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy przód, Prawy tył");
                                } else if (!repairs_checkbox_left_front.isChecked() && repairs_checkbox_right_front.isChecked() &&
                                        repairs_checkbox_left_rear.isChecked() && !repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Prawy przód, Lewy tył");
                                } else if (!repairs_checkbox_left_front.isChecked() && repairs_checkbox_right_front.isChecked() &&
                                        !repairs_checkbox_left_rear.isChecked() && repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Prawy przód, Prawy tył");
                                } else if (!repairs_checkbox_left_front.isChecked() && !repairs_checkbox_right_front.isChecked() &&
                                        repairs_checkbox_left_rear.isChecked() && repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy tył, Prawy tył");
                                } else if (repairs_checkbox_left_front.isChecked() && repairs_checkbox_right_front.isChecked() &&
                                        repairs_checkbox_left_rear.isChecked() && !repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy przód, Prawy przód, Lewy tył");
                                } else if (repairs_checkbox_left_front.isChecked() && repairs_checkbox_right_front.isChecked() &&
                                        !repairs_checkbox_left_rear.isChecked() && repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy przód, Prawy przód, Prawy tył");
                                } else if (repairs_checkbox_left_front.isChecked() && !repairs_checkbox_right_front.isChecked() &&
                                        repairs_checkbox_left_rear.isChecked() && repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy przód, Lewy tył, Prawy tył");
                                } else if (!repairs_checkbox_left_front.isChecked() && repairs_checkbox_right_front.isChecked() &&
                                        repairs_checkbox_left_rear.isChecked() && repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Prawy przód, Lewy tył, Prawy tył");
                                } else if (repairs_checkbox_left_front.isChecked() && repairs_checkbox_right_front.isChecked() &&
                                        repairs_checkbox_left_rear.isChecked() && repairs_checkbox_right_rear.isChecked()) {
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy przód, Prawy przód, Lewy tył, Prawy tył");
                                }
                                add_dialog.dismiss();
                            }
                        } else if (repair_data.get(position).getIsFrontLeft().equals("true") && repair_data.get(position).getIsFrontRight().equals("true") &&
                                repair_data.get(position).getIsRearLeft().equals("false") && repair_data.get(position).getIsRearRight().equals("false")) {
                            if (!repairs_checkbox_left_front.isChecked() && !repairs_checkbox_right_front.isChecked()) {
                                Toast.makeText(v.getContext(), "Musisz zaznaczyć przynajmniej jedną z opcji", Toast.LENGTH_SHORT).show();
                            } else {
                                if (repairs_checkbox_left_front.isChecked() && !repairs_checkbox_right_front.isChecked())
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy przód");
                                else if (repairs_checkbox_right_front.isChecked() && !repairs_checkbox_left_front.isChecked())
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Prawy przód");
                                else if (repairs_checkbox_right_front.isChecked() && repairs_checkbox_left_front.isChecked())
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy przód, Prawy przód");
                                add_dialog.dismiss();
                            }
                        } else if (repair_data.get(position).getIsRearLeft().equals("true") && repair_data.get(position).getIsRearRight().equals("true")
                                && repair_data.get(position).getIsFrontLeft().equals("false") && repair_data.get(position).getIsFrontRight().equals("false")) {
                            if (!repairs_checkbox_left_rear.isChecked() && !repairs_checkbox_right_rear.isChecked()) {
                                Toast.makeText(v.getContext(), "Musisz zaznaczyć przynajmniej jedną z opcji", Toast.LENGTH_SHORT).show();
                            } else {
                                if (repairs_checkbox_left_rear.isChecked() && !repairs_checkbox_right_rear.isChecked())
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy tył");
                                else if (repairs_checkbox_right_rear.isChecked() && !repairs_checkbox_left_rear.isChecked())
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Prawy tył");
                                else if (repairs_checkbox_right_rear.isChecked() && repairs_checkbox_left_rear.isChecked())
                                    services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price() +
                                            ";" + "Lewy tył, Prawy tył");
                                add_dialog.dismiss();
                            }
                        } else {
                            services.add(repair_data.get(position).getName() + ";" + repair_data.get(position).getRepair_price());
                            add_dialog.dismiss();
                        }
                    }
                });
                add_dialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return repair_data.size();
    }

    class Repair_Data_Holder extends RecyclerView.ViewHolder {
        TextView name, description, repair_price, img;
        ImageView photo;
        Button repair_add_button;

        public Repair_Data_Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.repair_name);
            description = itemView.findViewById(R.id.repair_description);
            repair_price = itemView.findViewById(R.id.repair_price);
            photo = itemView.findViewById(R.id.repair_photo);
            repair_add_button = itemView.findViewById(R.id.repair_add_button);


        }

    }
}

