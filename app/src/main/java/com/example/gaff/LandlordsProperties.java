package com.example.gaff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LandlordsProperties#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LandlordsProperties extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    ListView listOfProperties;
    private ArrayList<PropertyHelper> landLordsProperties;

    public LandlordsProperties() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LandlordsProperties.
     */
    // TODO: Rename and change types and number of parameters
    public static LandlordsProperties newInstance(String param1, String param2) {
        LandlordsProperties fragment = new LandlordsProperties();
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
        View rootView = inflater.inflate(R.layout.fragment_landlords_properties, container, false);

        listOfProperties = (ListView) rootView.findViewById(R.id.list_of_properties);
        landLordsProperties = new ArrayList<>();
        updateProperties();

        PropertyAdapter propertyAdapter = new PropertyAdapter(getActivity(),R.layout.landlord_property_list_item, landLordsProperties);
        listOfProperties.setAdapter(propertyAdapter);


        return rootView;
    }

    private void updateProperties() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://gaff-14394-default-rtdb.firebaseio.com");
        myRef = database.getReference("properties");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PropertyHelper> properties = new ArrayList<>();
                for (DataSnapshot property : dataSnapshot.getChildren()) {
                    PropertyHelper p = property.getValue(PropertyHelper.class);
                    properties.add(p);
                    Toast.makeText(getActivity(), p.getAddressLine1(), Toast.LENGTH_SHORT).show();

                }
                landLordsProperties = properties;

            }


            @Override
            public void onCancelled(DatabaseError error) {
                //
            }
        });


    }
}