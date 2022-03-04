package android.example.sensorsurveyparttwo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get the sensor manager
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // individual light and proximity sensors
        Sensor mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Sensor mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        // TextViews to display current sensor values
        TextView mTextSensorLight = findViewById(R.id.label_light);
        TextView mTextSensorProximity = findViewById(R.id.label_proximity);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String sensor_error = getResources().getString(R.string.error_no_sensor);

        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }

        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }
    }

    @Override
    protected void onStart() {
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Sensor mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        super.onStart();

        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];
        TextView mTextSensorLight = findViewById(R.id.label_light);
        TextView mTextSensorProximity = findViewById(R.id.label_proximity);

        switch (sensorType) {
            case Sensor.TYPE_LIGHT: // Event came from the light sensor.
                mTextSensorLight.setText(getResources().getString(
                        R.string.label_light, currentValue));
                getWindow().getDecorView().setBackgroundColor(Color.argb(currentValue, currentValue * 1, currentValue * 2, currentValue * 3 ));
                // Handle light sensor
                break;
            case Sensor.TYPE_PROXIMITY: // Event came from the proximity sensor.
                mTextSensorProximity.setText(getResources().getString(
                        R.string.label_proximity, currentValue));
                //Handle Proximity sensor
                break;
            default:
                // do nothing
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}