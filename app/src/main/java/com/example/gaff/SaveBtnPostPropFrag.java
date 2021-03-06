package com.example.gaff;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

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

        //Changed Save button to unsave if property is saved already
        String isPropSaved = getArguments().getString("savedProp");
        if(isPropSaved == "saved"){
            saveBtn.setText("Unsave");
        }

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
                if(isPropSaved == "saved"){
                    unSaveProp(eircode);
                }else{
                    saveProp(eircode);
                }
            }
        });
    }

    public void unSaveProp(String propEircode){
        String user_id = mAuth.getCurrentUser().getUid();
        db.collection("SavedProperties").document(user_id)
                .collection("UserSavedProperties").whereEqualTo("eircode", propEircode)
                .get().
            addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        //if there is no document to delete the loop will exit without iterating even once
                        for (QueryDocumentSnapshot document : task.getResult()){
                            Toast.makeText(getActivity(), "Property unsaved",
                                    Toast.LENGTH_SHORT).show();
                            document.getReference().delete();
                        }
                    }
                }
            });
    }

    public void saveProp(String propEircode){
        db.collectionGroup("UserAddedProperties")
                .orderBy("eircode", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        //Order fetched data from the fire store
                        if(error != null){

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                Property prop = dc.getDocument().toObject(Property.class);
                                if(prop.getEircode().compareTo(propEircode) == 0){
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    db.collection("SavedProperties").document(user_id)
                                            .collection("UserSavedProperties").add(prop)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(getActivity(), "Property Successfully Saved",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getActivity(), "Firestore Error: "+
                                                            e,Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        }

                    }
                });
    }

}
