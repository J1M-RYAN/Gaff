package com.example.gaff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PropertyAdapter  extends RecyclerView.Adapter<PropertyAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Property> propertyArrayList;

    public PropertyAdapter(Context context, ArrayList<Property> propertyArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.propertyArrayList = propertyArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public PropertyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_property_post,parent,false);
        return new MyViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyAdapter.MyViewHolder holder, int position) {
        Property property = propertyArrayList.get(position);
        holder.addressLine1.setText(property.getAddressLine1());
        holder.addressLine2.setText(property.getAddressLine2());
        holder.pricePerMonth.setText(property.getPricePerMonth());
        //holder.pricePerMonth.setText(String.valueOf(property.getPricePerMonth()));
    }

    @Override
    public int getItemCount() {
        return propertyArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView addressLine1, addressLine2, pricePerMonth;



        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            addressLine1 = itemView.findViewById(R.id.tvaddressLine1);
            addressLine2 = itemView.findViewById(R.id.tvaddressLine2);
            pricePerMonth = itemView.findViewById(R.id.tvpricePerMonth);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
