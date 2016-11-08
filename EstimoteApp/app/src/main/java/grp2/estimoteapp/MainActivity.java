package grp2.estimoteapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.DeviceId;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.telemetry.EstimoteTelemetry;

import java.util.List;

public class MainActivity extends Activity {

    TextView printText;
    private BeaconManager beaconManager;
    private String scanId;
    private DeviceId beaconId;
    private boolean newBeacon = true;
    public double temperature;
    public double brightness;
    public double pressure;


    private Beacon beacons[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printText = (TextView) findViewById(R.id.printText);

        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                scanId = beaconManager.startTelemetryDiscovery();
            }
        });

        beaconManager.setTelemetryListener(new BeaconManager.TelemetryListener(){
            @Override
            public void onTelemetriesFound(List<EstimoteTelemetry> telemetries) {
                for (EstimoteTelemetry tlm : telemetries) {

                    //THIS WORKS
                    //Gets data from the device
                    temperature = tlm.temperature;
                    brightness = tlm.ambientLight;
                    beaconId = tlm.deviceId;
                    pressure = tlm.pressure;

                    printText.setText("\n beaconID: " + beaconId +
                            ", temperature: " + temperature + " °C" +
                            ", light: " + brightness + " lux" +
                            ", pressure " + pressure + " ?");
                    //This first for doesnt work, couldnt test the other code below because of this
                   /* if(beacons.length > 0) {
                        for (int i = 0; i < beacons.length; i++) {
                            if (beaconId == beacons[i].beaconId) {
                                newBeacon = false;
                                beacons[i].temperature = temperature;
                                beacons[i].brightness = brightness;
                                beacons[i].pressure = pressure;
                            }
                        }
                    }
                    if(newBeacon == true){
                        Beacon b = new Beacon(beaconId);
                        b.brightness = brightness;
                        b.pressure = pressure;
                        b.temperature = temperature;
                        beacons[beacons.length] = b;
                    }
                    printText.setText("");
                    for(int i = 0; i < beacons.length;i++){
                        if(beaconId == beacons[i].beaconId){
                            printText.append("\n beaconID: " + beacons[i].beaconId +
                                    ", temperature: " + beacons[i].temperature + " °C" +
                                    ", light: " + beacons[i].brightness + " lux");
                        }
                    }*/

                }
            }
        });
    }

    //ask the user to turn Bluetooth or Location on, or ask for ACCESS_COARSE_LOCATION permission.
    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override protected void onStart() {
        super.onStart();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                scanId = beaconManager.startTelemetryDiscovery();
            }
        });
    }

    @Override protected void onStop() {
        super.onStop();
        beaconManager.stopTelemetryDiscovery(scanId);
    }

}
