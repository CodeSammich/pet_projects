//Location of Mickey
int pos1x = 700, pos1y = 200;
int pos2x = 200, pos2y = 400;  
int newColor = 0;
boolean up = true;
boolean right = true;

void mickeyFace(int x, int y, int eyeColor) {
  fill(109, 123, 412);
  ellipse(x, y + 90, 300, 300); //Big White Circle
  fill(0);
  ellipse(x - 175, y - 85, 200, 200); //Left Ear
  ellipse(x + 175, y - 85, 200, 200);  // Right Ear
  fill(487, 234, 576);
  ellipse(x - 60, y + 40, 70, 100); // Left Eye
  ellipse(x + 55, y + 40, 70, 100); // Right Eye
  fill(200, 137, 147);
  arc(x, y + 125, 200, 175, 0, PI); //Mouth
  fill(0);
  ellipse(x - 2, y + 91, 60, 45); //Nose
  fill(103, eyeColor, 230);
  ellipse(x - 59, y + 40, 40, 75); //Left Pupil
  ellipse(x + 54, y + 40, 40, 75); //Right Pupil
}

void mickeyBody(int x, int y) {
  fill(0);
  arc(x, y, 400, 125, PI, 2 * PI); //Top of vest
  fill(255, 0, 0);
  rect(x - 200, y, 400, 100); // Actual Vest
  fill(200, 120, 75); //Button Color
  ellipse(x - 100, y + 50, 50, 50); //Left Vest Button
  ellipse(x + 100, y + 50, 50, 50); //Right Vest Button
}

void mickeyMouse(int x, int y, int eye) {
  mickeyFace(x, y, eye);
  mickeyBody(x, y + 304);
}

void setup() {
  size (1000, 1200);
  mickeyMouse(pos1x, pos1y, 287);
  mickeyMouse(pos2x, pos2y, 287);
}

void draw() {
  background(250, 150, 25); //keep making background
  textSize(70); //text size
  text("Mickey World", 50, 200); //continuous print text

  newColor = (int)random(255);

  //Move Right Mouse
  if (pos1y <= 10) { //Top Limit
    pos1x += 10; //Move left and back down once
    pos1y += 1; 
    up = false; //Change direction back down
  } else if (pos1y >= 610) { //Bottom Limit
    pos1x += 10; //Move left and up once
    pos1y -= 1;
    up = true; //Change direction back up
  } else if (up) { //Move back up continuously randomly
    pos1y -= random(5);
  } else if (!up) { //Move back down continuously randomly
    pos1y += random(5);
  }

  //Move Left Mouse
  if (pos2y <= 200) { //Top Limit
    pos2x -= 10; //Move right and back down once
    pos2y += 1;  
    up = false; //Change direction back down
  } else if (pos2y >= 800) { //Bottom Limit
    pos2x -=10; //Move right and up once
    pos2y -=1;
    up = true; //Change direction back up
  } else if (up) { //Move back up continuously
    pos2y -= random(5);
  } else if (!up) { //Move back down continuously
    pos2y += random(5);
  }    

  mickeyMouse(pos1x, pos1y, newColor);
  mickeyMouse(pos2x, pos2y, newColor);
}

// Make more Mice/Randomize Location! ****
//void keyPressed();
//   mickeyMouse(

