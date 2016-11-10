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

    @Override
    public void onCreate(){
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback(){
            @Override
            public void onServiceReady(){
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("A9407F30-F5F8-466E-AFF9-25556B57FE6D"), //Estimotes UUID and major/minor values
                        11111,11111));
            }
        });


    }


}