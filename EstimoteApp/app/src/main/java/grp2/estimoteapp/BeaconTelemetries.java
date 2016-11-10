package grp2.estimoteapp;

import com.estimote.sdk.DeviceId;

/**
 * Created by Tatu on 08/11/2016.
 */

public class BeaconTelemetries {
    //This class is used to store telemetry data for each beacon

    public DeviceId beaconId;
    public double temperature;
    public double brightness;
    public double pressure;

    BeaconTelemetries(DeviceId id){
        this.beaconId = id;
    }
}
