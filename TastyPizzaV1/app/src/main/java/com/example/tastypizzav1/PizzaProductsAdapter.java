package com.example.tastypizzav1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.LinkedList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;


public class PizzaProductsAdapter extends RecyclerView.Adapter<PizzaProductsAdapter.ProductViewHolder2>{

    private Context mCtx;
    private List<PizzaProducts> productList2;
    static double order_value1=0.0;
    boolean clicked=false;
    public static List<String> total_order = new LinkedList<>();
    public PizzaProductsAdapter(Context mCtx, List<PizzaProducts> productList2) {
        this.mCtx = mCtx;
        this.productList2 = productList2;
    }

    @Override
    public PizzaProductsAdapter.ProductViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new ProductViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(final PizzaProductsAdapter.ProductViewHolder2 holder, int position) {
        final PizzaProducts product2 = productList2.get(position);
        holder.textname2.setText(product2.getName2());
        holder.textingredients2.setText(product2.getIngredients());
        holder.textmin_pizza_price2.setText(Double.parseDouble(product2.getMin_pizza_price())+"0 zł");
        holder.textmedium_pizza_price2.setText(Double.parseDouble(product2.getMedium_pizza_price())+"0 zł");
        holder.textmax_pizza_price2.setText(Double.parseDouble(product2.getMax_pizza_price())+"0 zł");

        holder.ly2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            }
        });
    holder.button_delete.setOnClickListener(new View.OnClickListener() {
        @Override

        public void onClick(View v) {

            if (holder.rb1.isChecked()) {

                if (holder.order_radio_1.size() > 0) {

                    String tempstring = holder.order_radio_1.get(holder.order_radio_1.size()-1).trim();
                    String[] s5 = tempstring.split(";");
                    for(int i=0;i<total_order.size();i++)
                    {
                        String tempstring2 = total_order.get(i).trim();
                        String[] s6 =tempstring2.split(";");
                        if(s6[0].equals(s5[0]) && s6[1].equals(s5[1]) && s6[2].equals(s5[2]))
                        {
                            total_order.remove(i);
                        }
                    }
                    order_value1 -= Math.round(Double.valueOf(s5[1])*100.0)/100.0;
                    RestaurantMeals.order_value.setText((float)Math.round(order_value1 * 100.0) / 100.0+"0 zł");
                    holder.order_radio_1.remove(holder.order_radio_1.size() - 1);
                    if (holder.order_radio_1.size() == 0) {
                        //holder.button_delete.setVisibility(View.GONE);
                    } else {
                       // holder.button_delete.setText("- (" + holder.order_radio_1.size() + ")");
                    }
                }
            } else if (holder.rb2.isChecked()) {

                if (holder.order_radio_2.size() > 0) {
                    String tempstring = holder.order_radio_2.get(holder.order_radio_2.size()-1);
                    String[] s5 = tempstring.split(";");
                    for(int i=0;i<total_order.size();i++)
                    {
                        String tempstring2 = total_order.get(i).trim();
                        String[] s6 =tempstring2.split(";");
                        if(s6[0].equals(s5[0]) && s6[1].equals(s5[1]) && s6[2].equals(s5[2]))
                        {
                            total_order.remove(i);
                        }
                    }
                    order_value1 -= Math.round(Double.valueOf(s5[1])*100.0)/100.0;
                    RestaurantMeals.order_value.setText((float)Math.round(order_value1 * 100.0) / 100.0+"0 zł");
                    holder.order_radio_2.remove(holder.order_radio_2.size() - 1);
                    if (holder.order_radio_2.size() == 0) {
                       // holder.button_delete.setVisibility(View.GONE);
                    } else {
                       // holder.button_delete.setText("- (" + holder.order_radio_2.size() + ")");
                    }
                }
            } else if (holder.rb3.isChecked()) {

                if (holder.order_radio_3.size() > 0) {
                    String tempstring = holder.order_radio_3.get(holder.order_radio_3.size()-1);
                    String[] s5 = tempstring.split(";");
                    for(int i=0;i<total_order.size();i++)
                    {
                        String tempstring2 = total_order.get(i).trim();
                        String[] s6 =tempstring2.split(";");
                        if(s6[0].equals(s5[0]) && s6[1].equals(s5[1]) && s6[2].equals(s5[2]))
                        {
                            total_order.remove(i);
                        }
                    }
                    order_value1 -= Math.round(Double.valueOf(s5[1])*100.0)/100.0;
                    RestaurantMeals.order_value.setText((float)Math.round(order_value1 * 100.0) / 100.0+"0 zł");
                    holder.order_radio_3.remove(holder.order_radio_3.size() - 1);
                    if (holder.order_radio_3.size() == 0) {
                       // holder.button_delete.setVisibility(View.GONE);
                    } else {
                      //  holder.button_delete.setText("- (" + holder.order_radio_3.size() + ")");
                    }
                }
            }
        }
    });
       holder.rb1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(RestaurantMeals.order_value.getText()=="0.00")
               {
                   holder.order_radio_1.clear();
                   holder.button_delete.setVisibility(View.GONE);

               }
               if(holder.order_radio_1.size()==0)
               {
                   holder.button_delete.setVisibility(View.GONE);
               }
                else {
                   //holder.button_delete.setVisibility(View.VISIBLE);
                  // holder.button_delete.setText("- (" + holder.order_radio_1.size() + ")");
               }
           }
       });
        holder.rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RestaurantMeals.order_value.getText()=="0.00")
                {
                    holder.order_radio_2.clear();
                    holder.button_delete.setVisibility(View.GONE);

                }
                if(holder.order_radio_2.size()==0)
                {
                    holder.button_delete.setVisibility(View.GONE);
                }
                else {
                  //  holder.button_delete.setVisibility(View.VISIBLE);
                  //  holder.button_delete.setText("- (" + holder.order_radio_2.size() + ")");
                }
            }
        });
        holder.rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RestaurantMeals.order_value.getText()=="0.00")
                {
                    holder.order_radio_3.clear();
                    holder.button_delete.setVisibility(View.GONE);

                }
                if(holder.order_radio_3.size()==0)
                {
                    holder.button_delete.setVisibility(View.GONE);
                }
                else {
                   // holder.button_delete.setVisibility(View.VISIBLE);
                  //  holder.button_delete.setText("- (" + holder.order_radio_3.size() + ")");
                }
            }
        });

        holder.add_order_to_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.rb1.isChecked()) {
                    String s1 = product2.getMin_pizza_price();
                    order_value1 +=(Double.valueOf(s1));
                    Toast.makeText(v.getContext(), "Meal was added to bucket\n"+String.format("Total order price:  %.2f", PizzaProductsAdapter.order_value1)+"PLN", Toast.LENGTH_SHORT).show();
                    RestaurantMeals.order_value.setText(String.format("%.2f", PizzaProductsAdapter.order_value1)+" zł");
                    StringBuilder s3=new StringBuilder();
                    s3.append("Small");
                    s3.append(";");
                    s3.append(product2.getMin_pizza_price());
                    s3.append(";");
                    s3.append(product2.getName2());
                    s3.toString().trim();

                    holder.order_radio_1.add(s3.toString());
                    //holder.button_delete.setText("- ("+holder.order_radio_1.size()+")");
                   // holder.button_delete.setVisibility(View.VISIBLE);
                    total_order.add(s3.toString());
                }
                else if(holder.rb2.isChecked())
                {
                    String s2 = product2.getMedium_pizza_price();
                    order_value1 += (Double.valueOf(s2));
                    Toast.makeText(v.getContext(), "Meal was added to bucket\n"+String.format("Total order price:  %.2f", PizzaProductsAdapter.order_value1)+"PLN", Toast.LENGTH_SHORT).show();
                    RestaurantMeals.order_value.setText(String.format("%.2f", PizzaProductsAdapter.order_value1)+" zł");
                    StringBuilder s3=new StringBuilder();
                    s3.append("Medium");
                    s3.append(";");
                    s3.append(product2.getMedium_pizza_price());
                    s3.append(";");
                    s3.append(product2.getName2());
                    s3.toString().trim();

                    holder.order_radio_2.add(s3.toString());
                    //holder.button_delete.setText("- ("+holder.order_radio_2.size()+")");
                    //holder.button_delete.setVisibility(View.VISIBLE);

                    total_order.add(s3.toString());
                }
                else if(holder.rb3.isChecked())
                {
                    String s33 = product2.getMax_pizza_price();
                    order_value1 += (Double.valueOf(s33));
                    Toast.makeText(v.getContext(), "Meal was added to bucket\n"+String.format("Total order price:  %.2f", PizzaProductsAdapter.order_value1)+"PLN", Toast.LENGTH_SHORT).show();
                    RestaurantMeals.order_value.setText(String.format("%.2f", PizzaProductsAdapter.order_value1)+" zł");
                    StringBuilder s3=new StringBuilder();
                    s3.append("Huge");
                    s3.append(";");
                    s3.append(product2.getMax_pizza_price());
                    s3.append(";");
                    s3.append(product2.getName2());
                    s3.toString().trim();

                    holder.order_radio_3.add(s3.toString());
                    //holder.button_delete.setText("- ("+holder.order_radio_3.size()+")");
                  //  holder.button_delete.setVisibility(View.VISIBLE);

                    total_order.add(s3.toString());
                }
                else if(!holder.rb1.isChecked() && !holder.rb2.isChecked() && !holder.rb3.isChecked())
                {
                    Toast.makeText(v.getContext(), "First choose the size of pizza!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList2.size();
    }

    class ProductViewHolder2 extends RecyclerView.ViewHolder {

        LinearLayout ly2;
        TextView textid2,textname2,textingredients2,textmin_pizza_price2,textmedium_pizza_price2,textmax_pizza_price2,text_restaurant_id2;
        Button add_order_to_pizza,button_delete;
        public List<String> order_radio_1 = new LinkedList<>();
        public List<String> order_radio_2 = new LinkedList<>();
        public List<String> order_radio_3 = new LinkedList<>();
        RadioButton rb1,rb2,rb3;
        public ProductViewHolder2(View itemView) {
            super(itemView);
            textid2 = itemView.findViewById(R.id.id_product_list);
            textname2 = itemView.findViewById(R.id.name_product_list);
            textingredients2 = itemView.findViewById(R.id.ingredients_product_list);
            textmin_pizza_price2 = itemView.findViewById(R.id.min_pizza_price_product_list);
            textmedium_pizza_price2 = itemView.findViewById(R.id.medium_pizza_price_product_list);
            textmax_pizza_price2 = itemView.findViewById(R.id.max_pizza_price_product_list);
            text_restaurant_id2 = itemView.findViewById(R.id.restaurant_id_product_list);
            ly2 = itemView.findViewById(R.id.ly2);
            add_order_to_pizza = itemView.findViewById(R.id.button50);
            button_delete = itemView.findViewById(R.id.button_delete);
            rb1 = itemView.findViewById(R.id.radioButton8);
            rb2 = itemView.findViewById(R.id.radioButton10);
            rb3 = itemView.findViewById(R.id.radioButton9);
            button_delete.setVisibility(View.GONE);

        }

    }




}
