package com.example.meteoriterace;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Fragment {


    GoogleMap gmap;

    FragmentContainerView frameMAP;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                gmap = googleMap;

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {


                        //create a point on map
                        MarkerOptions markerOptions = new MarkerOptions();

                        LatLng latLng1 = new LatLng(0,0);

                        markerOptions.position(latLng1);
                        //markerOptions.title("String type title");

                        //removes marker
                        //googleMap.clear();

                        //animating zoom to marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1,10));


                        googleMap.addMarker(markerOptions);
                    }

                });




            }
        });


        return view;
    }


    public void setLocation(double lat, double lon) {
        LatLng latLng = new LatLng(lat,lon);
        gmap.addMarker(new MarkerOptions().position(latLng));
        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
    }
}