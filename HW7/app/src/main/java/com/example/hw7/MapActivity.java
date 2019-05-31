package com.example.hw7;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mLocationClient;
    private boolean mLocationPermissionGranted = false;
    private final static String LOGTAG = "LOGGER...";
    private Marker mUser;
    private Marker mtrafficCam;

    @Override
    protected void onStart() {
        super.onStart();
    }

    // The below methods are from class notes/lecture videos. I have every reason
    // to believe the location permission works, however, I set a default location
    // for my emulator since it doesn't seem to get the emulator's default location.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        mLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    getLocation();
                }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        if(mLocationPermissionGranted) {
            Task location = mLocationClient.getLastLocation();

            location.addOnCompleteListener(new OnCompleteListener<Location>() {

                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location actualLocation = task.getResult();
                    if (actualLocation != null) {

                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(true);

                        LatLng currentLocation = new LatLng(actualLocation.getLatitude(),
                                actualLocation.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));

                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

                    } else {
                        // default location for emulator
                        LatLng homeCoordinates = new LatLng(47.691024, -122.299596);

                        mMap.addMarker(new MarkerOptions().position(homeCoordinates).title("Current location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeCoordinates, 10));

                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));


                    }
                }
            });
            location.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });

        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        this.getLocationPermission();

        this.getLocation();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TrafficCamera trafficCamera = bundle.getParcelable("TRAFFICCAM");
        Log.i(LOGTAG, trafficCamera.getDescription());

        String camDescription = trafficCamera.getDescription();
        Double latitude = trafficCamera.getLatitude();
        Double longitude = trafficCamera.getLongitude();


        LatLng trafficCamLocation = new LatLng(latitude, longitude);

        mtrafficCam = mMap.addMarker(new MarkerOptions()
                .position(trafficCamLocation).title(camDescription));

    }
}
