package com.example.gaff;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
public class LandlordsProperties extends Fragment {

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
    ProgressDialog progressDialog;

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

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Initialize firebase/fire store DB
        db = FirebaseFirestore.getInstance();
        propertyArrayList = new ArrayList<Property>();
        //Initialize adapter
        propertyAdapter = new PropertyAdapter(getActivity(),propertyArrayList);
        //Set recycler view use to the adapter
        recyclerView.setAdapter(propertyAdapter);
        //Get Data from the fire store
        EventChangeListener();
    }

    private void EventChangeListener() {
        //Refer to the collection
        db.collection("Properties").orderBy("addressLine2", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        // Call back method
                        // Called when new data is added, modified, removed data
                        // When the application runs - Data from firebase is retrieved

                        //Order fetched data from the fire store
                        if(error != null){

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                propertyArrayList.add(dc.getDocument().toObject(Property.class));
                            }
                            propertyAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }

                    }
                });
    }
}