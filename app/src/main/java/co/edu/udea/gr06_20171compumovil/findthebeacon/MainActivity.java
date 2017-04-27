package co.edu.udea.gr06_20171compumovil.findthebeacon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
    private ImageView imageViewBeacon;
    private TextView idNearestBeacon;
    private TextView distanceNearesBeacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utilsBeacons = new Utils();
        beaconsInfo = (TextView) findViewById(R.id.beaconsInfo);
        imageViewBeacon = (ImageView) findViewById(R.id.image_beacon);
        imageViewBeacon.setImageResource(R.drawable.white);
        idNearestBeacon = (TextView) findViewById(R.id.id_nearest_beacon);
        distanceNearesBeacon = (TextView) findViewById(R.id.distance_nearest_beacon);

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
//                Log.d("BEACONS", list.toString());
                message = "";

                for (int i = 0; i < list.size(); i++) {

                    if (i==0){
                        imageViewBeacon.setImageResource(getPictureBeacon(list.get(i).getMajor()));
                        idNearestBeacon.setText("Beacon #"+String.valueOf(list.get(i).getMajor()));
                        distanceNearesBeacon.setText("Distance: "+String.valueOf(utilsBeacons.computeAccuracy(list.get(i))) +"\n\n");
                    }else {
                        message += "\n Beacon #" + String.valueOf(list.get(i).getMajor())+"\n";
                        message += "Distancia: "+ String.valueOf(utilsBeacons.computeAccuracy(list.get(i))) + "\n\n";
                    }
                }
                beaconsInfo.setText("" + message);
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

    private int getPictureBeacon(int id){
        switch (id){
            case 34234:
                return R.drawable.blue;
            case 648:
                return R.drawable.purple;
            default:
                return Integer.parseInt(null);
        }
    }
}
