package com.example.hm1;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private IntentIntegrator integrator;
	private SensorManager sensorManager;
	
	private SensorEventListener linearAccelerationSensorEventListener;
	private SensorEventListener orientationSensorEventListener;
	private SensorEventListener lightSensorEventListener;
	private SensorEventListener pressureSensorEventListener;
	
	private Sensor linearAccelerationSensor;
	private Sensor orientationSensor;
	private Sensor lightSensor;
	private Sensor pressureSensor;
	
	private TextView xValTV;
	private TextView yValTV;
	private TextView zValTV;
	private TextView azimuthValTV;
	private TextView lightValTV;
	private TextView pressureValTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		integrator = new IntentIntegrator(this);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
		
		xValTV = (TextView) findViewById(R.id.xValTV);
		yValTV = (TextView) findViewById(R.id.yValTV);
		zValTV = (TextView) findViewById(R.id.zValTV);
		azimuthValTV = (TextView) findViewById(R.id.azimuthValTV);
		lightValTV = (TextView) findViewById(R.id.lightValTV);
		pressureValTV = (TextView) findViewById(R.id.pressureValTV);
		
		
		Button qrScan = (Button) findViewById(R.id.qrScan);
		
		qrScan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				integrator.initiateScan();
			}
		});
		
		Button qrGenerate = (Button) findViewById(R.id.qrGenerate);
		
		qrGenerate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText qrGenerateET = (EditText) findViewById(R.id.generateET);
				integrator.shareText(qrGenerateET.getText());
			}
		});
		
		addSensorListeners();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
			IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
			
			if (scanResult != null) {
				String result = scanResult.getContents();
				Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
			}
	}
	
	private void addSensorListeners() {
		
		linearAccelerationSensorEventListener = new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				
				float[] values = event.values;

				float x = values[0];
				float y = values[1];
				float z = values[2];
				
				xValTV.setText(String.valueOf(x));
				yValTV.setText(String.valueOf(y));
				zValTV.setText(String.valueOf(z));
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				//SensorManager.SENSOR_STATUS_ACCURACY_LOW/MEDIUM/HIGH
			}
		};
		
		orientationSensorEventListener = new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				
				float[] values = event.values;

				float azimuth = values[0];
				
				azimuthValTV.setText(String.valueOf(azimuth));
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				//SensorManager.SENSOR_STATUS_ACCURACY_LOW/MEDIUM/HIGH
			}
		};
		
		lightSensorEventListener = new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				
				float[] values = event.values;

				float light = values[0];
				
				lightValTV.setText(String.valueOf(light));
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				//SensorManager.SENSOR_STATUS_ACCURACY_LOW/MEDIUM/HIGH
			}
		};
		
		pressureSensorEventListener = new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				
				float[] values = event.values;

				float pressure = values[0];
				
				pressureValTV.setText(String.valueOf(pressure));
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				//SensorManager.SENSOR_STATUS_ACCURACY_LOW/MEDIUM/HIGH
			}
		};
	}
	
	@Override
    public void onResume() {
		
		super.onResume();
		sensorManager.registerListener(linearAccelerationSensorEventListener, linearAccelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(orientationSensorEventListener, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(lightSensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(pressureSensorEventListener, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
		// Available Delays: SensorManager.SENSOR_DELAY_NORMAL/UI/GAME/FASTEST
    }
	
	@Override
    public void onPause() {
		
		super.onPause();
		sensorManager.unregisterListener(linearAccelerationSensorEventListener);
		sensorManager.unregisterListener(orientationSensorEventListener);
		sensorManager.unregisterListener(lightSensorEventListener);
		sensorManager.unregisterListener(pressureSensorEventListener);
    }
}
