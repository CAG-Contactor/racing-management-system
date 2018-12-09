#include "Braccio.h"
#include <Servo.h> 

Servo base;
Servo shoulder;
Servo elbow;
Servo wrist_rot;
Servo wrist_ver;
Servo gripper;

void setup() {
  Braccio.begin();
  Serial.begin(9600);
}

void loop() {
  if (Serial.available()) {
    byte bytes[7];
    byte byteRead = Serial.readBytes(bytes, 7);
    int result = Braccio.ServoMovement(bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6] );
    Serial.println(result);
  }
}
