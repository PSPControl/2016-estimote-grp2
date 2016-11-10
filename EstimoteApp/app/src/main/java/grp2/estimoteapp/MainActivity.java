package grp2.estimoteapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.DeviceId;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.telemetry.EstimoteTelemetry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends Activity {

    TextView printText;
    private BeaconManager beaconManager;
    private Region region;
    private String scanId;

    private DeviceId beaconId;
    private boolean newBeacon = true;
    private String textToPrint = "";

    public double temperature;
    public double brightness;
    public double pressure;

    private ArrayList<BeaconTelemetries> beacons = new ArrayList<BeaconTelemetries>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printText = (TextView) findViewById(R.id.printText);

        beaconManager = new BeaconManager(getApplicationContext());
        region = new Region("ranged region", UUID.fromString ("A9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List list) {
                if (!list.isEmpty() && list.get (0) instanceof Beacon) {
                    doSomethingWith((Beacon)list.get(0));
                }
            }
        });

        //Telemetry reading we supposedly don't need it
        /*beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                scanId = beaconManager.startTelemetryDiscovery();
            }
        });

        beaconManager.setTelemetryListener(new BeaconManager.TelemetryListener(){
            @Override
            public void onTelemetriesFound(List<EstimoteTelemetry> telemetries) {
                for (EstimoteTelemetry tlm : telemetries) {

                    //Gets data from the device
                    temperature = tlm.temperature;
                    brightness = tlm.ambientLight;
                    beaconId = tlm.deviceId;
                    pressure = tlm.pressure;
                    newBeacon = true;

                    //Checks if the read telemetry data comes from a previously read beacon
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

                    //Creates a new beacon
                    if(newBeacon == true){
                        Beacon b = new Beacon(beaconId);
                        b.brightness = brightness;
                        b.pressure = pressure;
                        b.temperature = temperature;
                        beacons.add(b);
                    }

                    //Prints all the saved beacons data
                    textToPrint = "";
                    for(int i = 0; i < beacons.size();i++){
                        textToPrint += "\n beaconID: " + beacons.get(i).beaconId +
                                ", temperature: " + beacons.get(i).temperature + " °C" +
                                ", light: " + beacons.get(i).brightness + " lux";

                    }
                    printText.setText(textToPrint);

                }
            }
        });*/
    }

    public void doSomethingWith(Beacon beacon){
        //Does something depending with what beacon is the nearest
        printText.setText(beacon.toString());
    }

    public void setText(String text){
        printText.setText(text);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //ask the user to turn Bluetooth or Location on, or ask for ACCESS_COARSE_LOCATION permission.
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback(){
            @Override
            public void onServiceReady(){
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);

        super.onPause();
    }

    @Override protected void onStart() {
        super.onStart();
        //Starts telemetry data reading
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                scanId = beaconManager.startTelemetryDiscovery();
            }
        });
    }


    @Override protected void onStop() {
        super.onStop();
        //Stops telemetry data reading
        beaconManager.stopTelemetryDiscovery(scanId);
    }

}
