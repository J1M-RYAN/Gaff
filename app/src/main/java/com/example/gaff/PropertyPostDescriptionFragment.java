package com.example.gaff;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PropertyPostDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertyPostDescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PropertyPostDescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PropertyPostDescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PropertyPostDescriptionFragment newInstance(String param1, String param2) {
        PropertyPostDescriptionFragment fragment = new PropertyPostDescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_property_post_description, container, false);
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle SavedInstanceState){ //Retrospective Method added - Once the view is created - method is called
        super.onViewCreated(view, SavedInstanceState);

        //Variable
        String price = getArguments().getString("pricePerMonth");
        String addressLine1 = getArguments().getString("addressLine1");
        String addressLine2 = getArguments().getString("addressLine2");

        String eircode = getArguments().getString("eircode");
        String propertyType = getArguments().getString("propertyType");
        String bedrooms = getArguments().getString("bedrooms");
        String bathrooms = getArguments().getString("bathrooms");
        String title = getArguments().getString("title");
        String privateParking = getArguments().getString("privateParking");

        //View
        TextView priceTextView = view.findViewById(R.id.AAprice);
        TextView addressLine1TextView = view.findViewById(R.id.AAaddressline1);
        TextView addressLine2TextView = view.findViewById(R.id.AAaddressline2);

        TextView eircodeTextView = view.findViewById(R.id.AAeircode);
        TextView propertyTypeTextView = view.findViewById(R.id.AApropertyType);
        TextView bedroomsTextView = view.findViewById(R.id.AAbedrooms);
        TextView bathroomsTextView = view.findViewById(R.id.AAbathrooms);
        TextView titleTextView = view.findViewById(R.id.AAtitle);
        TextView privateParkingTextView = view.findViewById(R.id.AAprivateParking);

        //Set
        priceTextView.setText(price);
        addressLine1TextView.setText(addressLine1);
        addressLine2TextView.setText(addressLine2);
        eircodeTextView.setText(eircode);
        propertyTypeTextView.setText(propertyType);
        bedroomsTextView.setText(bedrooms);
        bathroomsTextView.setText(bathrooms);
        titleTextView.setText(title);
        privateParkingTextView.setText(privateParking);
    }

}