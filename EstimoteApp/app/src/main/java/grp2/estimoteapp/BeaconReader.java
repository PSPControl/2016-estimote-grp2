package grp2.estimoteapp;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.DeviceId;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.telemetry.EstimoteTelemetry;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by t4pita00 on 15.11.2016.
 */

public class BeaconReader extends Activity {

    MainActivity mainActivity;
    DataSender dataSender;
    private BeaconManager beaconManager;
    private Region region;
    private String scanId;
    private String textToPrint = "";
    private String UUIDToSearch = "A9407F30-F5F8-466E-AFF9-25556B57FE6D";

    private ArrayList<BeaconTelemetries> beacons = new ArrayList<BeaconTelemetries>();

    BeaconReader(Context context, MainActivity activity){

        mainActivity = activity;
        beaconManager = new BeaconManager(context);
        dataSender = new DataSender();
        //Proximity detecting, detects beacons which UUID matches UUIDToSearch
        region = new Region("ranged region", UUID.fromString (UUIDToSearch), null, null);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List list) {
                //Checks what beacon is the closest and does something with it
                if (!list.isEmpty() && list.get (0) instanceof Beacon) {
                    doSomethingWithNearest((Beacon)list.get(0));
                }
            }
        });

        //TELEMETRY READING
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                scanId = beaconManager.startTelemetryDiscovery();
            }
        });

        beaconManager.setTelemetryListener(new BeaconManager.TelemetryListener(){
            @Override //This function is called whenever new telemetry is read
            public void onTelemetriesFound(List<EstimoteTelemetry> telemetries) {
                for (EstimoteTelemetry tlm : telemetries) {

                    //Gets telemetrydata from the device
                    double temperature = tlm.temperature;
                    double brightness = tlm.ambientLight;
                    DeviceId beaconId = tlm.deviceId;
                    double pressure = tlm.pressure;
                    int distance = tlm.rssi;
                    doSomethingWithTelemetries(beaconId,temperature,brightness,pressure,distance);
                }
            }
        });


    }

   public void doSomethingWithNearest(Beacon beacon){

        //Prints UUID and mac address of the closest beacon
        //dataSender.sendDataToUrl("http://jiska.picasson.fi/index.php/api/beacon/mac=" + beacon.getMacAddress().toString());
        textToPrint = "Closest beacon with UUID of " + beacon.getProximityUUID() + " is beacon with mac address " + beacon.getMacAddress().toString();
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
               // mainActivity.printText.setText(textToPrint);
            }
        });
    }

    public void doSomethingWithTelemetries(DeviceId beaconId,double temperature, double brightness, double pressure,int distance){
        boolean newBeacon = true;

        //Checks if the read telemetry data comes from a previously read beacon
        if(beacons.size() > 0) {
            for (int i = 0; i < beacons.size(); i++) {
                DeviceId beaconId2 = beacons.get(i).beaconId;
                if (beaconId.equals(beaconId2)) {
                    newBeacon = false;
                    beacons.get(i).temperature = temperature;
                    beacons.get(i).brightness = brightness;
                    beacons.get(i).pressure = pressure;
                    beacons.get(i).distance = distance;
                    break;
                }
            }
        }

        //Creates a new beacon
        if(newBeacon){
            BeaconTelemetries b = new BeaconTelemetries(beaconId);
            b.brightness = brightness;
            b.pressure = pressure;
            b.temperature = temperature;
            b.distance = distance;
            beacons.add(b);
        }

        //Prints all the saved beacons data
        textToPrint = "";
        for(int i = 0; i < beacons.size();i++){
            textToPrint += "\n beaconID: " + beacons.get(i).beaconId +
                    ", temperature: " + beacons.get(i).temperature + " °C" +
                    ", light: " + beacons.get(i).brightness + " lux" +
                    ", distance " + beacons.get(i).distance + ".";

                /*dataSender.sendDataToUrl("http://jiska.picasson.fi/index.php/api/beaconvalues/beaconid=" + beacons.get(i).beaconId.toString() +
                        "&temperature=" + beacons.get(i).temperature +
                        "&brightness=" + beacons.get(i).brightness +
                        "&pressure=" + beacons.get(i).pressure +
                        "&distance=" + beacons.get(i).distance);*/

        }
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.telemetryText.setText(textToPrint);
            }
        });
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
