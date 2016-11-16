package grp2.estimoteapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Contacts;
import android.widget.TextView;


public class MainActivity extends Activity {

    TextView telemetryText;
    TextView songText;
    BeaconReader beaconReader;
    DataGetter dataGetter;
    //url for getting the songs
    String songUrl = "http://www.students.oamk.fi/~t3paji00/estimote/index.php/api/songs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        telemetryText = (TextView) findViewById(R.id.telemetryText);
        songText = (TextView) findViewById(R.id.songtext);
        beaconReader = new BeaconReader(getApplicationContext(),this);
        dataGetter = new DataGetter(this,songUrl);
        dataGetter.execute();

    }


}
