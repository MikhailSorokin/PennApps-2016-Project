
int sensor;
int n;
//value for sensor and for the counter

void setup()
{
  Serial.begin(9600);    
}

void loop()
{
  sensor = analogRead(A0);
  //While sensor is not moving, just wait
  if (sensor<1022){
    delay(10);
  }
  else{
    //once it starts moving, print the number of times it has been actuated
    Serial.println(n);
    n = n + 1; //add another value to the counter
    delay(10);
  }
}

