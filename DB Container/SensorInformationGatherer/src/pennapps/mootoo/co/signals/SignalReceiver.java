package pennapps.mootoo.co.signals;

import java.util.ArrayList;

import com.mongodb.MongoClient;

import pennapps.mootoo.co.sensor.MicSensor;
import pennapps.mootoo.co.sensor.PhotoSensor;
import pennapps.mootoo.co.sensor.Sensor;
import pennapps.mootoo.co.sensor.TemperatureSensor;
import pennapps.mootoo.co.sensor.VibrationSensor;

/**
 * This class gets whatever signal has been transmitted
 * and sets up the information about the sensor, based on
 * what sensor is being used currently.
 * @author Misha
 *
 */
public class SignalReceiver {
	

	public enum SensorType { TEMPERATURE, VIBRATOR, PHOTO, MIC };
	
	private boolean receivedSignal = false;
	private SensorType sensorType = SensorType.TEMPERATURE;
	
	private ArrayList<Sensor> allTypes = new ArrayList<Sensor>();

	public SignalReceiver(String sensorName) {
		receivedSignal = true;
		
		if (sensorName == "Temp") {
			sensorType = SensorType.TEMPERATURE;
		} else if (sensorName == "Vibe") {
			sensorType = SensorType.VIBRATOR;
		} else if (sensorName == "Photo") {
			sensorType = SensorType.PHOTO;
		} else if (sensorName == "Mic") {
			sensorType = SensorType.MIC;
		}
	}
	
	public boolean hasReceivedNewSignal() {
		return receivedSignal;
	}

	public SensorType getSensorType() {
		return sensorType;
	}
	
	public void transmitSensorInfo(String database, String roomName, double newValue, MongoClient client) {
		if (sensorType == SensorType.TEMPERATURE) {
			TemperatureSensor tempSensor = new TemperatureSensor(database, roomName, newValue, client);
			
			if (!hasExistingRoom(tempSensor)) {
				tempSensor.addSensorInfoToDocument(database, roomName, client);
				allTypes.add(tempSensor); 
			} else {
				tempSensor.setTemperature(newValue);
			}
		} else if (sensorType == SensorType.MIC) {
			MicSensor micSensor = new MicSensor(database, roomName, newValue, client);
			
			if (!hasExistingRoom(micSensor)) {
				micSensor.addSensorInfoToDocument(database, roomName, client);
				allTypes.add(micSensor); 
			} else {
				micSensor.setMicStatus(newValue);
			}
		}
	}
	
	public void transmitSensorInfo(String database, String roomName, boolean newValue, MongoClient client) {
		if (sensorType == SensorType.VIBRATOR) {
			VibrationSensor vibrationSensor = new VibrationSensor(database, roomName, newValue, client);
			
			if (!hasExistingRoom(vibrationSensor)) {
				vibrationSensor.addSensorInfoToDocument(database, roomName, client);
				allTypes.add(vibrationSensor);
			} else {
				vibrationSensor.setVibrationStatus(newValue);
			}
		} else if (sensorType == SensorType.PHOTO) {
			PhotoSensor photoSensor = new PhotoSensor(database, roomName, newValue, client);
			
			if (!hasExistingRoom(photoSensor)) {
				photoSensor.addSensorInfoToDocument(database, roomName, client);
				allTypes.add(photoSensor);
			} else {
				photoSensor.setPhotoStatus(newValue);
			}
		}
	}
	
	private boolean hasExistingRoom(Sensor sensor) {
		for (int i = 0 ; i < allTypes.size(); i++) {
			if (allTypes.get(i).name == sensor.name) {
				return true;
			}
		}
		
		return false;
	}
	
}