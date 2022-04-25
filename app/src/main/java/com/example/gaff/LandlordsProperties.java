package com.example.gaff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LandlordsProperties#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LandlordsProperties extends Fragment implements RecyclerViewInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<Property> propertyArrayList;
    PropertyAdapter propertyAdapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String user_id;
    private NavController navController;

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

        return  inflater.inflate(R.layout.fragment_landlords_properties, container, false);
    }
    
    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle SavedInstanceState){ //Retrospective Method added - Once the view is created - method is called
        super.onViewCreated(view, SavedInstanceState);
        navController = Navigation.findNavController(view);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        //Initialize firebase/fire store DB
        db = FirebaseFirestore.getInstance();
        propertyArrayList = new ArrayList<Property>();
        //Initialize adapter
        propertyAdapter = new PropertyAdapter(getActivity(),propertyArrayList, this);
        //Set recycler view use to the adapter
        recyclerView.setAdapter(propertyAdapter);
        //Get Data from the fire store
        EventChangeListener();
    }

    private void EventChangeListener() {
        //Refer to the collection
        db.collection("AllProperties").document(user_id)
                .collection("UserAddedProperties").orderBy("addressLine1", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        // Call back method
                        // Called when new data is added, modified, removed data
                        // When the application runs - Data from firebase is retrieved

                        //Order fetched data from the fire store
                        if(error != null){
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                propertyArrayList.add(dc.getDocument().toObject(Property.class));
                            }
                            propertyAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        String addressLine1 = propertyArrayList.get(position).getAddressLine1();
        String addressLine2 = propertyArrayList.get(position).getAddressLine2();
        String pricePerMonth = propertyArrayList.get(position).getPricePerMonth();
        String eircode = propertyArrayList.get(position).getEircode();
        String longitude = propertyArrayList.get(position).getLongitude();
        String latitude = propertyArrayList.get(position).getLatitude();
        String propertyType = propertyArrayList.get(position).getPropertyType();
        String bedrooms = propertyArrayList.get(position).getBedrooms();
        String bathrooms = propertyArrayList.get(position).getBathrooms();
        String title = propertyArrayList.get(position).getTitle();
        String privateParking = propertyArrayList.get(position).getPrivateParking();

        Bundle bundle = new Bundle();
        bundle.putString("addressLine1", addressLine1);
        bundle.putString("addressLine2", addressLine2);
        bundle.putString("pricePerMonth", pricePerMonth);
        bundle.putString("eircode", eircode);
        bundle.putString("longitude", longitude);
        bundle.putString("latitude", latitude);
        bundle.putString("propertyType", propertyType);
        bundle.putString("bedrooms", bedrooms);
        bundle.putString("bathrooms", bathrooms);
        bundle.putString("title", title);
        bundle.putString("privateParking", privateParking);

        navController.navigate(R.id.action_landlordsProperties_to_propertyPostDescriptionFragment, bundle);
    }
}