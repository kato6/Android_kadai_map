package jp.android.myapp.googlemapsapplication01;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Circle;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.]
   private UiSettings mUiSettings;//マップの各種設定を指定するための変数
    private LatLng center;
    private double radius;

    private Address getLatLongFromLocationName(String locationName) throws IOException{
        Geocoder gcoder = new Geocoder(this, Locale.getDefault());

        List<Address> addressesList = gcoder.getFromLocationName(locationName,1);
        Address address = addressesList.get(0);

        return address;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();


    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }



    private void setUpMapIfNeeded() {

        if (mMap == null) {

            //googleMapの作成に必要↓
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            if (mMap != null) {
                setUpMap();
               mUiSettings = mMap.getUiSettings();

                mUiSettings.setCompassEnabled(true); //北を指すコンパス表示の有無

                mUiSettings.setMyLocationButtonEnabled(true);//現在地ボタンの有無

                mUiSettings.setRotateGesturesEnabled(true);//回転ジェスチャー有無
            }
        }
    }

    private void setUpMap() {

        double latitude = 43.0675;
        double longitude = 141.350784;

        Intent intent = getIntent();
        String address1 = intent.getStringExtra("address");

        try {
            Address address = getLatLongFromLocationName(address1);
            latitude = address.getLatitude();
            longitude = address.getLongitude();


        } catch (IOException e) {
            e.printStackTrace();
        }

        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("クリックされた座標").snippet(address1).alpha(90));
       /* CircleOptions circleOptions = new CircleOptions().center(center).radius(radius);
        Circle circle = mMap.addCircle(circleOptions);
        circle.setStrokeColor(Color.argb(0x99,0x33,0x99,0xFF));
        circle.setStrokeWidth(10.0f);*/

        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10);
        mMap.moveCamera(cu);




        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

}
