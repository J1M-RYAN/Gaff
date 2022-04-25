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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveBtnPostPropFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveBtnPostPropFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaveBtnPostPropFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaveBtnPostPropFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SaveBtnPostPropFrag newInstance(String param1, String param2) {
        SaveBtnPostPropFrag fragment = new SaveBtnPostPropFrag();
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
        return inflater.inflate(R.layout.fragment_save_btn_post_prop, container, false);
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle SavedInstanceState){ //Retrospective Method added - Once the view is created - method is called
        super.onViewCreated(view, SavedInstanceState);

        String addressLine1 = getArguments().getString("addressLine1");
        String longitude = getArguments().getString("longitude");
        String latitude = getArguments().getString("latitude");

        //Button 1
        ImageButton GoogleMapsButton = view.findViewById(R.id.googleMapsButton);
        GoogleMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // We imported the navigation package

                Bundle bundle = new Bundle();
                bundle.putString("addressLine1", addressLine1);
                bundle.putString("longitude", longitude);
                bundle.putString("latitude", latitude);

                Navigation.findNavController(v).navigate(R.id.action_propertyPostDescriptionFragment_to_googleMapPropertyLocationFragment, bundle); // navigating from the first fragment to the second fragment
            }
        });

        //Variable
        String price = getArguments().getString("pricePerMonth");
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

        //Button
        Button saveBtn = view.findViewById(R.id.saveBtn);

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

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print();
            }
        });
    }

    public void print(){
        Toast.makeText(getActivity(), "Button pressed",
                Toast.LENGTH_SHORT).show();
    }

}
