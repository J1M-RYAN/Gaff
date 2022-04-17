package com.example.gaff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
    FirebaseFirestore fStore;
    DatabaseReference myRef;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText addressLine1, addressLine2, pricePerMonth, eircode, propertyType, bedrooms, bathrooms, title, privateParking;
    Button addProperty;

    String user_id;
    //private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

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
        eircode = (EditText) rootView.findViewById(R.id.eircode);
        propertyType = (EditText) rootView.findViewById(R.id.propertyType);
        bedrooms = (EditText) rootView.findViewById(R.id.bedrooms);
        bathrooms = (EditText) rootView.findViewById(R.id.bathrooms);
        title = (EditText) rootView.findViewById(R.id.title);
        privateParking = (EditText) rootView.findViewById(R.id.privateParking);

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore = FirebaseFirestore.getInstance();
        //storageReference = FirebaseStorage.getInstance().getReference();

        addProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String address1 = addressLine1.getText().toString();
                final String address2 = addressLine2.getText().toString();
                final String price = pricePerMonth.getText().toString();
                final String eirCode = eircode.getText().toString();
                final String propertyT = propertyType.getText().toString();
                final String bedRooms = bedrooms.getText().toString();
                final String bathRooms = bathrooms.getText().toString();
                final String Title = title.getText().toString();
                final String privatePark = privateParking.getText().toString();
                //final int price = Integer.parseInt(pricePerMonth.getText().toString());
                if(!TextUtils.isEmpty(address1) && !TextUtils.isEmpty(address2) && !TextUtils.isEmpty(price)){
                    addProperty(address1,address2,price,eirCode,propertyT,bedRooms,bathRooms,Title,privatePark);
                }else{
                    Toast.makeText(getActivity(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    public boolean validPropertyInputs(String addressL1, String addressL2, String pricePerMonth){
        return true;
    }

    private void addProperty(String addressLine1, String addressLine2, String pricePerMonth, String eircode, String propertyType, String bedrooms,
                             String bathrooms, String title, String privateParking) {
        Map<String, Object> property = new HashMap<>();
        property.put("addressLine1", addressLine1);
        property.put("addressLine2", addressLine2);
        property.put("pricePerMonth", pricePerMonth);
        property.put("eircode", eircode);
        property.put("propertyType", propertyType);
        property.put("bedrooms", bedrooms);
        property.put("bathrooms", bathrooms);
        property.put("title", title);
        property.put("privateParking", privateParking);
        firebaseFirestore.collection("Properties").document(user_id).set(property).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Property Successfully Added",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Firestore Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}