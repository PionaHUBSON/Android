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

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Restaurant> productList;

    public RestaurantsAdapter(Context mCtx, List<Restaurant> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.restaurants_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Restaurant product = productList.get(position);
        //loading the image
       Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);

        holder.textname.setText(product.getName());
        holder.textid.setText(String.valueOf(product.getId()));
        holder.textstreet.setText(product.getStreet());
        holder.textapartmentnumber.setText(product.getApartment_number()+",");
        holder.textpostcode.setText(product.getPostcode());
        holder.textcity.setText(product.getCity());
        holder.textminprice.setText(Double.parseDouble(product.getMin_price()) +"0 Zł");
        holder.textdeliverycost.setText(Double.parseDouble(product.getDelivery_cost())+"0 Zł");

        holder.ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToMenu= new Intent(v.getContext(),RestaurantMeals.class);
                GoToMenu.putExtra("id", String.valueOf(product.getId()));
                GoToMenu.putExtra("min_price", String.valueOf(product.getMin_price()));
                GoToMenu.putExtra("delivery_cost", String.valueOf(product.getDelivery_cost()));
                 mCtx.startActivity(GoToMenu);
            }
        });
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {


        public ImageView imageView;
        TextView relative_layout,textname,textid,textphone, textstreet, textapartmentnumber, textpostcode,textcity,textminprice,textdeliverycost,textlogoimg;
        LinearLayout ly;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textname = itemView.findViewById(R.id.name_res);
            ly = itemView.findViewById(R.id.ly);
            textid = itemView.findViewById(R.id.id_res);
            textphone = itemView.findViewById(R.id.phone_number_res);
            textstreet = itemView.findViewById(R.id.street_res);
            textapartmentnumber = itemView.findViewById(R.id.apartment_number_res);
            textpostcode = itemView.findViewById(R.id.postocde_res);
            textcity = itemView.findViewById(R.id.city_res);
            textminprice = itemView.findViewById(R.id.min_price_res);
            textdeliverycost = itemView.findViewById(R.id.delivert_cost_res);
           imageView = itemView.findViewById(R.id.logo_img_res);
        }
    }

    public void filterList(ArrayList<Restaurant> filteredList){
        productList = filteredList;
        notifyDataSetChanged();
    }

    //OnCreateOptionsMenu
}
