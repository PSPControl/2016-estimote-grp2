package grp2.estimoteapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.widget.TextView;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.DeviceId;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.telemetry.EstimoteTelemetry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    TextView printText;
    private BeaconManager beaconManager;
    private String scanId;
    private DeviceId beaconId;
    private boolean newBeacon = true;
    private String textToPrint = "";

    public double temperature;
    public double brightness;
    public double pressure;

    private ArrayList<Beacon> beacons = new ArrayList<Beacon>();
    //private Beacon beacons[];
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
                    newBeacon = true;

                    /*printText.setText("\n beaconID: " + beaconId +
                            ", temperature: " + temperature + " °C" +
                            ", light: " + brightness + " lux" +
                            ", pressure " + pressure + " ?");*/

                    //This first for doesnt work, couldnt test the other code below because of this
                    if(beacons.size() > 0) {
                        for (int i = 0; i < beacons.size(); i++) {
                            if (beaconId == beacons.get(i).beaconId) {
                                newBeacon = false;
                                beacons.get(i).temperature = temperature;
                                beacons.get(i).brightness = brightness;
                                beacons.get(i).pressure = pressure;
                            }
                        }
                    }
                    if(newBeacon == true){
                        Beacon b = new Beacon(beaconId);
                        b.brightness = brightness;
                        b.pressure = pressure;
                        b.temperature = temperature;
                        beacons.add(b);
                    }
                    textToPrint = "empty";
                    for(int i = 0; i < beacons.size();i++){
                        textToPrint += "\n beaconID: " + beacons.get(i).beaconId +
                                ", temperature: " + beacons.get(i).temperature + " °C" +
                                ", light: " + beacons.get(i).brightness + " lux";

                    }
                    printText.setText(textToPrint);

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
