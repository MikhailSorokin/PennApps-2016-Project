package pennapps.mootoo.co.sensor;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import pennapps.mootoo.co.parsers.JsonContainer;

public class VibrationSensor extends Sensor {

	private int vibrationStatus; 
	
	//Adafruit Huzza
	
	public VibrationSensor(String uniqueName, String location, int vib, MongoClient client) {
		super(uniqueName, location);
		this.vibrationStatus = vib;
	}

	public void addSensorInfoToDocument(String database, String roomName, MongoClient client) {
		Document doc = new Document("location", roomName)
				.append("Vibration Amount", (vibrationStatus == 1) ? true : false);
		
		MongoDatabase db = client.getDatabase(database);
		
		MongoCollection<Document> collections = db.getCollection("sensors");
		collections.insertOne(doc);
		
		MongoCursor<Document> allDocs = collections.find().iterator();
		
		String jsonBuildUp = "";
		while (allDocs.hasNext()) {
			jsonBuildUp += allDocs.next().toJson();
		}
		
		JsonContainer.allJsonFormats.add(jsonBuildUp);
	}
	
	public void setVibrationStatus(int status) {
		vibrationStatus = status;
	}

}
