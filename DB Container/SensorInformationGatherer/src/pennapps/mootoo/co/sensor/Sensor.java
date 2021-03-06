package pennapps.mootoo.co.sensor;

import com.mongodb.MongoClient;

public abstract class Sensor {

	public String name;
	public String location;
	
	public Sensor(String uniqueName, String location) {
		name = uniqueName;
		this.location = location;
	}
	
	public abstract void addSensorInfoToDocument(String database, String roomName, MongoClient client);
}
