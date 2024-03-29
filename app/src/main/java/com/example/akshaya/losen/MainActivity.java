package com.example.akshaya.losen;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button b;
    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (TextView) findViewById(R.id.textView);
        b = (Button) findViewById(R.id.button);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location) {
               t.append("\n " + location.getLongitude() + " " + location.getLatitude());
              double lon =  location.getLongitude();
                double lat =  location.getLatitude();
                double homelong=78.10;
                double homelat=9.90;
              //  double clglat=9.893544;
                //double clglong=78.176084;
                if((lat>=homelat)&&(lon>= homelong))
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"Location fetched", Toast.LENGTH_SHORT);
                    toast.show();
                    final String phoneNo= "9442037876";
                    final String msg = "Reached Home";
                    sendSMS(phoneNo,msg);

                }
              /*  if ((lat>=clglat)&&(lon>= clglong))
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"Location fetched", Toast.LENGTH_SHORT);
                    toast.show();
                    final String phoneNo= "9442037876";
                    final String msg = "Reached college";
                    sendSMS(phoneNo,msg);

                }*/

            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
               // locationManager.requestLocationUpdates("gps", 5000, 0, listener);
                locationManager.requestSingleUpdate("gps",listener,null);

            }
        });

    }


   public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

}



