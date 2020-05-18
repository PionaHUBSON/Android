package com.example.matcar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import static com.example.matcar.Diagnostics.choose_mechanic;
import static com.example.matcar.Diagnostics.myDialog;
import static com.example.matcar.Overview.choose_mechanic_overview;
import static com.example.matcar.Overview.myDialog_overview;
import static com.example.matcar.Repairs_Final.choose_mechanic_repair;
import static com.example.matcar.Repairs_Final.myDialog_rep;

public class MechanicAdapter extends FirestoreRecyclerAdapter<Mechanic, MechanicAdapter.MechanicHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MechanicAdapter(@NonNull FirestoreRecyclerOptions<Mechanic> options, String act) {
        super(options);
        this.act = act;
    }

    String act;
    static String mechanic_id;

    @Override
    protected void onBindViewHolder(@NonNull final MechanicHolder holder, int position, @NonNull final Mechanic model) {
        holder.textViewName.setText(model.getName() + " " + model.getSurname());
        holder.textViewPhone_number.setText("Telefon: " + model.getPhone_number());
        holder.textViewEmail.setText("Email: " + model.getEmail());
        holder.textViewDescription.setText("Opis: " + model.getDescription());
        holder.btn_choose_mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mechanic_id = model.getId();
                if (act.equals("Diagnostics")) {
                    choose_mechanic.setText("Wybrano: " + holder.textViewName.getText());
                    myDialog.dismiss();
                } else if (act.equals("Overview")) {
                    choose_mechanic_overview.setText("Wybrano: " + holder.textViewName.getText());
                    myDialog_overview.dismiss();
                } else if (act.equals("Repairs")) {
                    choose_mechanic_repair.setText("Wybrano: " + holder.textViewName.getText());
                    myDialog_rep.dismiss();
                }
            }
        });
    }

    @NonNull
    @Override
    public MechanicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mechanik_item, parent, false);
        return new MechanicHolder(v);
    }

    class MechanicHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewSurname;
        TextView textViewPhone_number;
        TextView textViewEmail;
        TextView textViewDescription;
        TextView textViewId;
        Button btn_choose_mechanic;

        public MechanicHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.diagnostic_mechanic_name);
            textViewSurname = itemView.findViewById(R.id.diagnostic_mechanic_surname);
            textViewPhone_number = itemView.findViewById(R.id.diagnostic_mechanic_phone_number);
            textViewEmail = itemView.findViewById(R.id.diagnostic_mechanic_email);
            textViewDescription = itemView.findViewById(R.id.diagnostic_mechanic_description);
            textViewId = itemView.findViewById(R.id.diagnostic_mechanic_id);
            btn_choose_mechanic = itemView.findViewById(R.id.btn_choose_mechanic);
        }
    }
}
