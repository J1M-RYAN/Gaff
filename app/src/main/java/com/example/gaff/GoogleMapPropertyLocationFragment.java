package com.example.gaff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoogleMapPropertyLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoogleMapPropertyLocationFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    String addressLine1, longitude, latitude;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GoogleMapPropertyLocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoogleMapPropertyLocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoogleMapPropertyLocationFragment newInstance(String param1, String param2) {
        GoogleMapPropertyLocationFragment fragment = new GoogleMapPropertyLocationFragment();
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
        addressLine1 = getArguments().getString("addressLine1");
        longitude = getArguments().getString("longitude");
        latitude = getArguments().getString("latitude");

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return inflater.inflate(R.layout.fragment_google_map_property_location, container, false);
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle SavedInstanceState){ //Retrospective Method added - Once the view is created - method is called
        super.onViewCreated(view, SavedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;

        float lat = Float.parseFloat(latitude);
        float lng = Float.parseFloat(longitude);

        LatLng county = new LatLng(lat,-lng);
        map.addMarker(new MarkerOptions().position(county).title(addressLine1));
        map.moveCamera(CameraUpdateFactory.newLatLng(county));
    }
}
