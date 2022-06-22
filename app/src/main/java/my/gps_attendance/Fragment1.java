package my.gps_attendance;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

public class Fragment1 extends Fragment {
    DecimalFormat df = new DecimalFormat();
    TextView Pos_text;
    TextView GPS_text;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        df.applyLocalizedPattern("0.0000");
        GPS_text = view.findViewById(R.id.gps_text);
        Pos_text = view.findViewById(R.id.f1_pos_text);
        try {
            gpsData();
        }
        catch (Exception e){
            //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        init();
        return view;
    }

    void init() {
        if((GPS_Pos.longitude >= 127.4574 && GPS_Pos.longitude <= 127.4590) && (GPS_Pos.latitude >= 36.3349 && GPS_Pos.latitude <= 36.3370))
            Pos_text.setText("융합과학관");
        else if((GPS_Pos.longitude >= 127.4596 && GPS_Pos.longitude <= 127.4605) && (GPS_Pos.latitude >= 36.3359 && GPS_Pos.latitude <= 36.3365))
            Pos_text.setText("30주년기념관");
        else
            Pos_text.setText("알수 없음");
    }

    void gpsData() {
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
        else {
            String locationProvider = LocationManager.GPS_PROVIDER;
            Location location = lm.getLastKnownLocation(locationProvider);
            String longitude = df.format(location.getLongitude());
            String latitude = df.format(location.getLatitude());
            GPS_text.setText("위도 : " + longitude + "\n경도 : " + latitude);
            GPS_Pos.longitude = Double.parseDouble(longitude);
            GPS_Pos.latitude = Double.parseDouble(latitude);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);
        }
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            String longitude = df.format(location.getLongitude());
            String latitude = df.format(location.getLatitude());
            GPS_text.setText("위도 : " + longitude + "\n경도 : " + latitude);
            GPS_Pos.longitude = Double.parseDouble(longitude);
            GPS_Pos.latitude = Double.parseDouble(latitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    };
}
