package com.example.tastypizzav1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PizzaOrderAdapter extends RecyclerView.Adapter<PizzaOrderAdapter.ProductViewHolder3> {


        private Context mCtx;
        private List<PizzaOrderProducts> productList;
        public static boolean removed;

        public PizzaOrderAdapter(Context mCtx, List<PizzaOrderProducts> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
            removed=false;
        }


    @Override
        public ProductViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.order_product_list, null);
            return new ProductViewHolder3(view);
        }


    @Override
        public void onBindViewHolder(final ProductViewHolder3 holder5, int position) {
            final PizzaOrderProducts product = productList.get(position);


            holder5.name_order_product.setText("Name\n"+product.getName4());
            holder5.size_order_product.setText("Size\n"+product.getSize());
            holder5.price_order_product.setText("Price\n"+product.getPrice());

            holder5.delete_button_order_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<PizzaProductsAdapter.total_order.size();i++)
                    {
                        String temp=PizzaProductsAdapter.total_order.get(i);
                        String[] temp2=temp.split(";");

                        if(temp2[2].equals(product.getName4()) && temp2[0].equals(product.getSize()) && temp2[1].equals(product.getPrice()))
                        {
                            PizzaProductsAdapter.total_order.remove(i);
                            String temp3 = RestaurantMeals.total_order_price.getText().toString();
                            String[] temp4=temp3.split(":");
                            String[] temp5= temp4[1].split("z");
                            String[] temp6=temp5[0].split(",");
                                    temp6[0]+=".";
                                    temp6[0]+=temp6[1];
                            double delivery_cost = Double.parseDouble(temp6[0].toString().trim());
                            delivery_cost-=Double.parseDouble(product.getPrice());
                            RestaurantMeals.total_order_price.setText("Total order price: "+String.format("%.2f", delivery_cost)+"zł");
                            PizzaProductsAdapter.order_value1-=Double.parseDouble(product.getPrice());
                            RestaurantMeals.order_value.setText((String.format("%.2f", PizzaProductsAdapter.order_value1)+" zł"));
                            holder5.rl_order.setVisibility(View.GONE);
                            Toast.makeText(v.getContext(),"Meal was deleted!" , Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        class ProductViewHolder3 extends RecyclerView.ViewHolder {

            TextView name_order_product,size_order_product,price_order_product;
            Button delete_button_order_product;
            View backgroundview;
            RelativeLayout rl_order;
            public ProductViewHolder3(View itemView) {
                super(itemView);
                name_order_product = itemView.findViewById(R.id.name_order_product);
                size_order_product = itemView.findViewById(R.id.size_order_product);
                price_order_product = itemView.findViewById(R.id.price_order_product);
                delete_button_order_product = itemView.findViewById(R.id.delete_button_order_product);
                backgroundview = itemView.findViewById(R.id.view9);
                rl_order = itemView.findViewById(R.id.rl_order);

            }
        }
    }


