package my.gps_attendance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class Fragment2 extends Fragment {
    DecimalFormat df = new DecimalFormat();
    Hashtable<String, String> class_name_hash = new Hashtable<>();
    Hashtable<String, Integer> class_start_time_hash = new Hashtable<>();
    Hashtable<String, Integer> class_time_progress_hash = new Hashtable<>();
    Hashtable<String, String> class_pos_hash = new Hashtable<>();
    Date currentDate = new Date();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("k");
    String getHour;
    long now;
    int getWeek = 0;
    static boolean bAttendance = false;
    Button attendanceBtn;
    TextView Week_text;
    TextView Pos_text;
    TextView lec_pos_text;
    TextView lec_text;
    TextView lec_time_text;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        df.applyLocalizedPattern("0.0000");
        now = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        getHour = dateFormat.format(currentDate);

        getWeek = calendar.get(Calendar.DAY_OF_WEEK);
        attendanceBtn = view.findViewById(R.id.attendance_btn);
        Week_text = view.findViewById(R.id.f2_week_text);
        Pos_text = view.findViewById(R.id.f2_pos_text);
        lec_pos_text = view.findViewById(R.id.lecture_pos_text);
        lec_text = view.findViewById(R.id.lecture_text);
        lec_time_text = view.findViewById(R.id.lecture_time_text);

        init_class(getWeek);

        try {
            gpsData();
        }
        catch (Exception e) {
            //.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        init();
        //Toast.makeText(getContext(), "경도 " + GPS_Pos.longitude + "\n위도 " + GPS_Pos.latitude, Toast.LENGTH_SHORT).show();

        for(int i = 0; i < class_name_hash.size(); i++) {
            if (Integer.parseInt(getHour) >= class_start_time_hash.get("class"+(i + 1)) &&
                    Integer.parseInt(getHour) < (class_start_time_hash.get("class" + (i + 1)) + class_time_progress_hash.get("class" + (i + 1)))) {
                attendanceBtn.setEnabled(true);
                lec_pos_text.setText(class_pos_hash.get("class"+(i + 1)));
                lec_text.setText(class_name_hash.get("class"+(i + 1)));
                lec_time_text.setText(
                        class_start_time_hash.get("class" + (i + 1)) + ":00 ~ " + (class_start_time_hash.get("class" + (i + 1)) + class_time_progress_hash.get("class" + (i + 1))) + ":00"
                );
                break;
            }
            else {
                attendanceBtn.setEnabled(false);
                lec_pos_text.setText("수업 시간 아님");
                lec_text.setText("수업 시간 아님");
                lec_time_text.setText("수업 시간 아님");
            }
        }

        if(bAttendance)
            attendanceBtn.setEnabled(false);

        attendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((GPS_Pos.longitude >= 127.4574 && GPS_Pos.longitude <= 127.4590) && (GPS_Pos.latitude >= 36.3349 && GPS_Pos.latitude <= 36.3370) && Pos_text.getText().toString().equals(lec_pos_text.getText().toString())) {
                    Toast.makeText(getContext(), "출석 성공", Toast.LENGTH_SHORT).show();
                    attendanceBtn.setEnabled(false);
                    bAttendance = true;
                }
                else
                    Toast.makeText(getContext(), "출석 실패", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    void init(){
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

    void init_class(int week){
        switch (week){
            case 2:
                Week_text.setText("월요일");
                class_name_hash.put("class1", "임베디드소프트웨어");
                class_start_time_hash.put("class1", 9);
                class_time_progress_hash.put("class1", 2);
                class_pos_hash.put("class1", "융합과학관");

                class_name_hash.put("class2", "운영체제");
                class_start_time_hash.put("class2", 16);
                class_time_progress_hash.put("class2", 2);
                class_pos_hash.put("class2", "융합과학관");
                break;
            case 3:
                Week_text.setText("화요일");
                class_name_hash.put("class1", "데이터베이스 시스템");
                class_start_time_hash.put("class1", 9);
                class_time_progress_hash.put("class1", 2);
                class_pos_hash.put("class1", "융합과학관");

                class_name_hash.put("class2", "가상화폐기술");
                class_start_time_hash.put("class2", 15);
                class_time_progress_hash.put("class2", 3);
                class_pos_hash.put("class2", "30주년기념관");
                break;
            case 4:
                Week_text.setText("수요일");
                class_name_hash.put("class1", "운영체제");
                class_start_time_hash.put("class1", 9);
                class_time_progress_hash.put("class1", 1);
                class_pos_hash.put("class1", "융합과학관");

                class_name_hash.put("class2", "데이터베이스 시스템");
                class_start_time_hash.put("class2", 11);
                class_time_progress_hash.put("class2", 1);
                class_pos_hash.put("class2", "융합과학관");
                break;
            case 5:
                Week_text.setText("목요일");
                class_name_hash.put("class1", "임베디드소프트웨어");
                class_start_time_hash.put("class1", 9);
                class_time_progress_hash.put("class1", 2);
                class_pos_hash.put("class1", "융합과학관");

                class_name_hash.put("class2", "C#프로그래밍");
                class_start_time_hash.put("class2", 13);
                class_time_progress_hash.put("class2", 2);
                class_pos_hash.put("class2", "융합과학관");

                class_name_hash.put("class3", "캠퍼스멘토링");
                class_start_time_hash.put("class3", 19);
                class_time_progress_hash.put("class3", 1);
                class_pos_hash.put("class3", "융합과학관");
                break;
            case 6:
                Week_text.setText("금요일");
                class_name_hash.put("class1", "모바일프로그래밍");
                class_start_time_hash.put("class1", 9);
                class_time_progress_hash.put("class1", 3);
                class_pos_hash.put("class1", "융합과학관");

                class_name_hash.put("class2", "영어회화2");
                class_start_time_hash.put("class2", 13);
                class_time_progress_hash.put("class2", 2);
                class_pos_hash.put("class2", "혜화문화관");

                class_name_hash.put("class3", "C#프로그래밍");
                class_start_time_hash.put("class3", 17);
                class_time_progress_hash.put("class3", 1);
                class_pos_hash.put("class3", "융합과학관");
                break;
        }
    }

}
