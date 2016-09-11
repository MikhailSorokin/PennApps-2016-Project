package pennapps.mootoo.co.sensor;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import pennapps.mootoo.co.parsers.JsonContainer;

public class TemperatureSensor extends Sensor {

	private double temperature;
	
	public TemperatureSensor(String uniqueName, String location, double temperature, MongoClient client) {
		super(uniqueName, location);
		this.temperature = temperature;
	}

	public void addSensorInfoToDocument(String database, String roomName, MongoClient client) {
		Document doc = new Document("location", roomName)
				.append("Temperature", temperature);
		
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

	public void setTemperature(double temp) {
		temperature = temp;
	}
	
}
