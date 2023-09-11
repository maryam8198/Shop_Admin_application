package com.example.admin_application.Home_Page.Profile_Package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_application.Home_Page.Profile_Package.Model;
import com.example.admin_application.R;

import java.util.List;

public class Adaptor extends RecyclerView.Adapter<Adaptor.MyHolder> {

    List<Model> data;
    private Context mcontext;

    public Adaptor(List<Model> data, Context context) {
        this.data = data;
        this.mcontext = context;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_profile, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Model item = data.get(position);

        holder.show_username.setText(data.get(position).getUsername());
        holder.show_name.setText(data.get(position).getName());
        holder.show_lastname.setText(data.get(position).getLastname());
        holder.show_phone.setText(data.get(position).getPhone());

    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {


        private TextView show_username,show_name,show_lastname,show_phone;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            show_username = itemView.findViewById(R.id.profile_show_username);
            show_name = itemView.findViewById(R.id.profile_show_name);
            show_lastname = itemView.findViewById(R.id.profile_show_lastname);
            show_phone = itemView.findViewById(R.id.profile_show_phone);

        }

    }
}