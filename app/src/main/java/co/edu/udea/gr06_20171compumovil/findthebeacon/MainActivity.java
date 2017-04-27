package co.edu.udea.gr06_20171compumovil.findthebeacon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.Utils;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private Region region;
    private TextView beaconsInfo;
    private String message;
    private Utils utilsBeacons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utilsBeacons = new Utils();
        beaconsInfo = (TextView) findViewById(R.id.beaconsInfo);
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                Log.d("BEACONS", list.toString());
                message = "";
                for (Beacon beacon: list){
                    message += "Beacon #: " + String.valueOf(beacon.getMajor())+"\n";
                    message += "Distancia: "+ getDistance(beacon) + "\n\n";
                }
                beaconsInfo.setText("data: \n" + message);
            }
        });
        region = new Region("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    private String getDistance(Beacon beacon) {
       Double distance = utilsBeacons.computeAccuracy(beacon);
        return String.valueOf(distance);
    }


}
