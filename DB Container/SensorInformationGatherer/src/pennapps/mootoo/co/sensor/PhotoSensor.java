package pennapps.mootoo.co.sensor;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import pennapps.mootoo.co.parsers.JsonContainer;

public class PhotoSensor extends Sensor {

	private int cameraOn;
	
	//Adafruit Huzza
	
	public PhotoSensor(String uniqueName, String location, int cameraStatus, MongoClient client) {
		super(uniqueName, location);
		this.cameraOn = cameraStatus;
	}

	public void addSensorInfoToDocument(String database, String roomName, MongoClient client) {
		Document doc = new Document("location", roomName)
				.append("Camera Power Value", (cameraOn == 1) ? true : false);
		
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

	public void setPhotoStatus(int newValue) {
		cameraOn = newValue;
	}

}