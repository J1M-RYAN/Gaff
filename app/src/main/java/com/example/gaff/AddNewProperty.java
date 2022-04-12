package com.example.gaff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewProperty#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewProperty extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference myRef;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText addressLine1, addressLine2, pricePerMonth;
    Button addProperty;

    public AddNewProperty() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewProperty.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewProperty newInstance(String param1, String param2) {
        AddNewProperty fragment = new AddNewProperty();
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
        View rootView = inflater.inflate(R.layout.fragment_add_new_property, container, false);
        addressLine1 = (EditText)  rootView.findViewById(R.id.editTextTextPostalAddress1);
        addressLine2 = (EditText)  rootView.findViewById(R.id.editTextTextPostalAddressLine2);
        pricePerMonth = (EditText) rootView.findViewById(R.id.pricePerMonth);
        addProperty = (Button) rootView.findViewById(R.id.add_property_btn);


        addProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProperty();
                Toast.makeText(getActivity(), "Property added.",
                        Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }
    public boolean validPropertyInputs(String addressL1, String addressL2, int pricePerMonth){
        return true;
    }

    private void addProperty() {
        if(validPropertyInputs(addressLine1.getText().toString(), addressLine2.getText().toString(),Integer.parseInt(pricePerMonth.getText().toString()))){

            mAuth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance("https://gaff-14394-default-rtdb.firebaseio.com");
            myRef = database.getReference("properties");
            PropertyHelper propertyHelper = new PropertyHelper(addressLine1.getText().toString(), addressLine2.getText().toString(),Integer.parseInt(pricePerMonth.getText().toString()));

            myRef.child(mAuth.getUid()).setValue(propertyHelper);
            addressLine2.setText("");
            addressLine1.setText("");
            pricePerMonth.setText("");
        }
    }


}