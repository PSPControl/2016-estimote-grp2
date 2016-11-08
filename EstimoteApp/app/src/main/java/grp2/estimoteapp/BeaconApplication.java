package grp2.estimoteapp;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.telemetry.EstimoteTelemetry;

import java.util.List;
import java.util.UUID;

//With this we can use the beacon in different activities
public class BeaconApplication extends Application{

    private BeaconManager beaconManager;
    private String scanId;
    public double temperature;
    public double brightness;
    @Override
    public void onCreate(){
        super.onCreate();

        /*beaconManager = new BeaconManager(getApplicationContext());

        Log.d("CONNECT","tried to connect");
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                scanId = beaconManager.startTelemetryDiscovery();
            }
        });
        Log.d("CONNECT","connection doned");
       /* beaconManager.connect(new BeaconManager.ServiceReadyCallback(){
            @Override
            public void onServiceReady(){
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), //Estimote things UUID and major/minor values
                        11111,11111));
            }
        });

        Log.d("CONNECT","telemetry listenerstarted");
        beaconManager.setTelemetryListener(new BeaconManager.TelemetryListener(){
            @Override
            public void onTelemetriesFound(List<EstimoteTelemetry> telemetries) {
                for (EstimoteTelemetry tlm : telemetries) {
                    temperature = tlm.temperature;
                    brightness = tlm.ambientLight;
                    Log.d("TELEMETRY","beaconID: " + tlm.deviceId +
                            ", temperature: " + temperature + " °C" +
                            ", light: " + brightness + " lux"

                    );
                }
            }
        });
        Log.d("CONNECT","telemetry listener doned");*/

    }


}