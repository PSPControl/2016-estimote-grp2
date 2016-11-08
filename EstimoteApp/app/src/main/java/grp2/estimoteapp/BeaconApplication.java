package grp2.estimoteapp;

import android.app.Application;
import android.util.Log;

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
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), //Estimote things UUID and major/minor values
                        38043,41846));
            }
        });

        beaconManager.setTelemetryListener(new BeaconManager.TelemetryListener(){
            @Override
            public void onTelemetriesFound(List<EstimoteTelemetry> telemetries) {
                for (EstimoteTelemetry tlm : telemetries) {
                    Log.d("TELEMETRY","beaconID: " + tlm.deviceId +
                            ", temperature: " + tlm.temperature + " °C" +
                            ", light: " + tlm.ambientLight + " lux"

                    );
                }
            }
        });


    }


}