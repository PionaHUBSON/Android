package com.example.tastypizzav1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<TransactionList> productList;

    public TransactionAdapter(Context mCtx, List<TransactionList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.transaction_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final TransactionList product = productList.get(position);
        //loading the image


        holder.content2.setText(product.getContent2());
        holder.customer_phonenumber.setText(String.valueOf(product.getCustomer_phone_number()));
        holder.customer_postcode.setText(product.getCustomer_postcode());
        holder.customer_apartment_number.setText(product.getCustomer_apartment_number());
        holder.customer_name.setText(product.getCustomer_name());
        holder.customer_surname.setText(product.getCustomer_surname());
        holder.customer_email.setText(product.getCustomer_email());
        holder.customer_city.setText(product.getCustomer_city());
        holder.customer_street.setText(product.getCustomer_street());
        holder.total_order_value.setText(product.getTotal_order_value());
        holder.date.setText(product.getDate());


    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {



        TextView content2;
        TextView total_order_value;
        TextView customer_name;
        TextView customer_surname;
        TextView customer_email;
        TextView customer_city;
        TextView customer_street;
        TextView customer_apartment_number;
        TextView customer_postcode;
        TextView customer_phonenumber;
        TextView date;

        public ProductViewHolder(View itemView) {
            super(itemView);
            content2 = itemView.findViewById(R.id.name_res);
            total_order_value = itemView.findViewById(R.id.apartment_number_res);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_surname = itemView.findViewById(R.id.customer_surname);
            customer_email = itemView.findViewById(R.id.customer_email);
            customer_city = itemView.findViewById(R.id.customer_city);
            customer_street = itemView.findViewById(R.id.customer_street);
            customer_apartment_number = itemView.findViewById(R.id.customer_apartment_number);
            customer_postcode = itemView.findViewById(R.id.customer_postcode);
            customer_phonenumber = itemView.findViewById(R.id.customer_phone_number);
            date = itemView.findViewById(R.id.order_date);

        }
    }


}
