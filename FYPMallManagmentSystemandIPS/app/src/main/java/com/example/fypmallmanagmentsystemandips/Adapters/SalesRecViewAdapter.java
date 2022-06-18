package com.example.fypmallmanagmentsystemandips.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fypmallmanagmentsystemandips.Models.Sales;
import com.example.fypmallmanagmentsystemandips.R;

import java.util.ArrayList;

public class SalesRecViewAdapter extends RecyclerView.Adapter<SalesRecViewAdapter.ViewHolder> {

    private ArrayList<Sales> sales = new ArrayList<>();

    public SalesRecViewAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSalesShop.setText(sales.get(position).getShopName());
        holder.txtSalesOffer.setText(sales.get(position).getShopOffers());
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    public void setSales(ArrayList<Sales> sales) {
        this.sales = sales;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtSalesShop,txtSalesOffer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSalesShop = itemView.findViewById(R.id.txtSalesShop);
            txtSalesOffer = itemView.findViewById(R.id.txtSalesOffer);
        }
    }
}
