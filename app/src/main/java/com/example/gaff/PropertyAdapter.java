package com.example.gaff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.Native;
import java.util.ArrayList;

public class PropertyAdapter extends ArrayAdapter<PropertyHelper> {
    private ArrayList<PropertyHelper> propertyList;

    public PropertyAdapter(@NonNull Context context,int resource,ArrayList<PropertyHelper> propertyList){
            super(context,resource);
            this.propertyList = propertyList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.landlord_property_list_item, parent,false);
        }
        ImageView propertyImage = convertView.findViewById(R.id.landlord_list_item_house_image);
        TextView addressLine1 = convertView.findViewById(R.id.landlord_list_item_address1);
        TextView pricePerMonth = convertView.findViewById(R.id.pricePerMonth);

       // propertyImage.setImageResource(propertyList.get(position).getImage());
        addressLine1.setText(propertyList.get(position).getAddressLine1());
        pricePerMonth.setText(propertyList.get(position).getPricePerMonth());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Toast.makeText(getContext(), "Item clicked is : " + propertyList.get(position).getAddressLine1(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
