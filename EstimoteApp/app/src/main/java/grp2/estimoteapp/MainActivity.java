package grp2.estimoteapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Contacts;
import android.widget.TextView;


public class MainActivity extends Activity {

    TextView telemetryText;

    BeaconReader beaconReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        telemetryText = (TextView) findViewById(R.id.telemetryText);

        beaconReader = new BeaconReader(getApplicationContext(),this);
    }


}
