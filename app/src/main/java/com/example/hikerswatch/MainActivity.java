package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView latitudeTextView;
    TextView longitudeTextView;
       TextView addressTextView;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitudeTextView =findViewById(R.id.latitudeTextView);
        longitudeTextView=findViewById(R.id.longitudeTextView);
         addressTextView = findViewById(R.id.addressTextView);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener =new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {

                    List<Address> listAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    if (listAddress != null && listAddress.size() > 0) {
                        Log.i("Address", listAddress.get(0).toString());
                        String address = "";
                        latitudeTextView.setText("Latitude: " +String.valueOf(location.getLatitude()));
                        longitudeTextView.setText("Longitude " +String.valueOf(location.getLongitude()));

                      /*if (listAddress.get(0).getThoroughfare()!= null) {
                            address += listAddress.get(0).getThoroughfare()+" ";
                        }*/

                        if (listAddress.get(0).getLocality() != null) {
                            address += listAddress.get(0).getLocality() + " ";
                        }

                        if (listAddress.get(0).getPostalCode() != null) {
                            address += listAddress.get(0).getPostalCode() + " ";
                        }


                        if (listAddress.get(0).getAdminArea() != null) {
                            address += listAddress.get(0).getAdminArea();
                        }
                        addressTextView.setText("Address:" + address);


                    }
                }catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
            };
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }


    }
}
