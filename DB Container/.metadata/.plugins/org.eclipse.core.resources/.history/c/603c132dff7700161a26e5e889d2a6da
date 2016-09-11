package pennapps.mootoo.co.sensor;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class VibrationSensor extends Sensor {

	private boolean vibrationStatus = false; 
	
	//Adafruit Huzza
	
	public VibrationSensor(String uniqueName, String location, boolean vib, MongoClient client) {
		super(uniqueName, location);
		this.vibrationStatus = vib;
	}

	public void addSensorInfoToDocument(String database, String roomName, MongoClient client) {
		Document doc = new Document("location", roomName)
				.append("Vibration Amount", vibrationStatus);
		
		MongoDatabase db = client.getDatabase(database);
		
		MongoCollection<Document> collections = db.getCollection("sensors");
		collections.insertOne(doc);
	}
	
	public void setVibrationStatus(boolean status) {
		vibrationStatus = status;
	}

}
