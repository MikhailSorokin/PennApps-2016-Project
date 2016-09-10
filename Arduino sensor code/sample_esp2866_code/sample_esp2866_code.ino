#include <EEPROM.h>
#include <ESP8266WiFi.h> //library for the esp8266
#include <PubSubClient.h> //library for pubsubclient
#include <Wire.h>
//#include <Adafruit_INA219.h> //library for using I2C

int sensorPin = 2;//whatever pin the sensor will connect to
float voltage;

// Connect to the WiFi
const char* ssid = "AirPennNet-Guest";
//const char* password = " "; //commented out the password
const char* mqtt_server = "iot.eclipse.org:1883";

WiFiClient espClient;
PubSubClient client(espClient);

const byte ledPin = 0; // Pin with LED on Adafruit Huzzah

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) { 
    char receivedChar = (char)payload[i];
    Serial.print(receivedChar);
    if (receivedChar == '0')
      // ESP8266 Huzzah outputs are "reversed"
      digitalWrite(ledPin, HIGH);
    if (receivedChar == '1')
      digitalWrite(ledPin, LOW);
  }
  Serial.println();
}


void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("ESP8266 Client")) {
      Serial.println("connected");
      //do some calculations:
      int reading = analogRead(sensorPin);
      float voltage = (reading * 5.0)/1024.0;
      // ... and subscribe to topic
      client.publish("Temperature1","analogRead(voltage)");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void setup()
{
  Serial.begin(9600);

  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);

  pinMode(ledPin, OUTPUT);
}

void loop()
{
  if (!client.connected()) {
    reconnect();
  }
  client.loop();
}

