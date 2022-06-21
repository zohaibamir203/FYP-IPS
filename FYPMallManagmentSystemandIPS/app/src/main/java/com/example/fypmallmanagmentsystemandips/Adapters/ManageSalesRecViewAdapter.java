package com.example.fypmallmanagmentsystemandips.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypmallmanagmentsystemandips.Models.Manage;
import com.example.fypmallmanagmentsystemandips.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ManageSalesRecViewAdapter extends RecyclerView.Adapter<ManageSalesRecViewAdapter.ViewHolder>{

    private ArrayList<Manage> shopOffers = new ArrayList<>();
    public  ManageSalesRecViewAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_sale_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSalesOff.setText(shopOffers.get(position).getShopOffer());
    }

    @Override
    public int getItemCount() {
        return shopOffers.size();
    }

    public void setShopOffers(ArrayList<Manage> shopOffers) {
        this.shopOffers = shopOffers;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtSalesOff;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSalesOff = itemView.findViewById(R.id.txtSalesOff);
        }
    }
}
