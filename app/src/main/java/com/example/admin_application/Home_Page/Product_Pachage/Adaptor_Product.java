package com.example.admin_application.Home_Page.Product_Pachage;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.admin_application.Config;
import com.example.admin_application.Home_Page.Profile_Package.Adaptor;
import com.example.admin_application.Home_Page.Profile_Package.Model;
import com.example.admin_application.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptor_Product extends RecyclerView.Adapter<Adaptor_Product.MyHolder>
{
    List<Model_product> modelProducts;
    private Context mcontext;
    public Adaptor_Product(List<Model_product> modelProducts, Context context) {
        this.modelProducts = modelProducts;
        this.mcontext = context;

    }

    @NonNull
    @Override
    public Adaptor_Product.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_product, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_Product.MyHolder holder, int position) {
       // Model_product item = modelProducts.get(position);

        holder.single_row_product_name.setText(modelProducts.get(position).getProduct_name());
        holder.single_row_details_product.setText(modelProducts.get(position).getDetails_product());
        holder.single_row_price_product.setText(modelProducts.get(position).getPrice_product());
        holder.single_row_count_product.setText(modelProducts.get(position).getCount_product());

        String imageUrl = Config.url + modelProducts.get(position).getImage_product();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24)
                .into(holder.single_row_image_product);

    }

    @Override
    public int getItemCount() {
        if (modelProducts == null) {
            return 0;
        } else {
            return modelProducts.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView single_row_image_product;
        TextView single_row_count_product,single_row_price_product,single_row_details_product,single_row_product_name;
        public MyHolder(@NonNull View itemView) {

            super(itemView);
            single_row_product_name=itemView.findViewById(R.id.single_row_product_name);
            single_row_details_product=itemView.findViewById(R.id.single_row_details_product);
            single_row_price_product=itemView.findViewById(R.id.single_row_price_product);
            single_row_count_product=itemView.findViewById(R.id.single_row_count_product);
            single_row_image_product=itemView.findViewById(R.id.single_row_image_product);
        }
    }
    public void removeItem(int position) {
        modelProducts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}
