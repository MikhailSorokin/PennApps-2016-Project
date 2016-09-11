package pennapps.mootoo.co.sensor;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import pennapps.mootoo.co.parsers.JsonContainer;

public class MicSensor extends Sensor {

	private double micVoltage; //idk lol
	
	//Adafruit Huzza
	
	public MicSensor(String uniqueName, String location, int micVoltage, MongoClient client) {
		super(uniqueName, location);
		this.micVoltage = micVoltage;
	}

	public void addSensorInfoToDocument(String database, String roomName, MongoClient client) {
		Document doc = new Document("location", roomName)
				.append("Microphone Voltage", micVoltage);
		
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
	
	public void setMicStatus(double micVolt) {
		micVoltage = micVolt;
	}

}
