package com.example.marwa.mymap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LocationManager mgr;
    TextView txt;
    String locationData;
    double longt;
    double latit;
    Button add;
    Geocoder geocoder;
    List<Address> geo;
    private static final int LOCATION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnLocation = findViewById(R.id.myloc);

        txt=findViewById(R.id.mytxt);
        geocoder = new Geocoder(this);
        mgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions

                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                } else {
                    mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener());
                }
            }
        });

        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String result="";
                   geo= geocoder.getFromLocation(
                            latit, longt, 1);
                    if (geo != null && geo.size() > 0) {
                        Address address = geo.get(0);
                        result = address.getAddressLine(0) ;
                        txt.setText(result);
                    }
                    else{
                        txt.setText("Not connected");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }

        class MyLocationListener implements LocationListener {

            @Override
            public void onLocationChanged(Location location) {
                longt=location.getLongitude();
                latit=location.getLatitude();

                locationData = "Longitude: " + location.getLongitude();
                locationData += "\nLatitude: " + location.getLatitude();
                Toast.makeText(MainActivity.this, locationData, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MainActivity.LOCATION_REQUEST_CODE:
                if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED)

                    mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener());
                else{
                    ;
                }
                break;
        }
    }

}

