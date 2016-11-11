package grp2.estimoteapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.DeviceId;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.telemetry.EstimoteTelemetry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends Activity {

    TextView printText;
    TextView telemetryText;
    private BeaconManager beaconManager;
    private Region region;
    private String scanId;
    private String textToPrint = "";
    private String UUIDToSearch = "A9407F30-F5F8-466E-AFF9-25556B57FE6D";
    static InputStream is = null;

    private ArrayList<BeaconTelemetries> beacons = new ArrayList<BeaconTelemetries>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printText = (TextView) findViewById(R.id.printText);
        telemetryText = (TextView) findViewById(R.id.telemetryText);

        beaconManager = new BeaconManager(getApplicationContext());
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

                    doSomethingWithTelemetries(beaconId,temperature,brightness,pressure);
                }
            }
        });

    }

    public void doSomethingWithNearest(Beacon beacon){
        //Prints UUID and mac address of the closest beacon
        printText.setText("Closest beacon with UUID of " + UUIDToSearch + " is beacon with mac address " + beacon.getMacAddress().toString());
        sendDataToUrl("http://jiska.picasson.fi/index.php/api/beacon/mac=" + beacon.getMacAddress().toString());
    }

    public void doSomethingWithTelemetries(DeviceId beaconId,double temperature, double brightness, double pressure){
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
            beacons.add(b);
        }

        //Prints all the saved beacons data
        textToPrint = "";
        for(int i = 0; i < beacons.size();i++){
            textToPrint += "\n beaconID: " + beacons.get(i).beaconId +
                    ", temperature: " + beacons.get(i).temperature + " °C" +
                    ", light: " + beacons.get(i).brightness + " lux";

                sendDataToUrl("http://jiska.picasson.fi/index.php/api/beaconvalues/beaconid=" + beacons.get(i).beaconId.toString() +
                        "&temperature=" + beacons.get(i).temperature +
                        "&brightness=" + beacons.get(i).brightness +
                        "&pressure=" + beacons.get(i).pressure);

        }
        telemetryText.setText(textToPrint);

    }

    public void sendDataToUrl(String url){

        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //HttpPost httpPost = new HttpPost(url);
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
