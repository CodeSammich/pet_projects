int numOfBalls =   77;
int numOfShots = 3;

Ball[] bagOfBalls = new Ball[numOfBalls];
Bomb userControl = new Bomb(); //User's Explosion
Ball trigger = new Ball(); //User's Trigger
PImage winImage; //Win's Smile

void setup() {
  size(3000, 1500);
  background(0);
  bagOfBalls = new Ball[numOfBalls];
  Bomb userControl; //User's Explosion
  Ball trigger; //User's Trigger
  //balls
  for (int i = 0; i < bagOfBalls.length; i++) {
    bagOfBalls[i] = new Ball();
  }
  //print(bagOfBalls.length);
  //User
  userControl = new Bomb();
  trigger = new Ball();

  //Restart Settings
  Score = 0;
  numDead = 0;
  numOfShots = 3;
  winImage = loadImage("Smiley-face-1600x1200-wallpaper-775678.jpg");
}

void draw() {
  background(0);

  customBackground();
  customScoreboard();

  noCursor();
  
  //Explodes on Click
  if (mouseClick) { 
    if (trigger.state == GROWING) {
      trigger.draw();
      trigger.triggerExplode();
    } else if (trigger.state == SHRINKING) {
      trigger.draw();
      trigger.triggerExplode();
    }
  }

  //Creates Balls
  for (int i = 0; i < bagOfBalls.length; i++) {
    bagOfBalls[i].draw();
    bagOfBalls[i].move();
    //    if (userControl.isOverlapping(bagOfBalls[i])) {
    //      bagOfBalls[i].explode();
    //    }
    for (int j = 0; j < bagOfBalls.length; j++) {
      if (bagOfBalls[i].isColliding(bagOfBalls[j])) {
        bagOfBalls[j].explode();
        //bagOfBalls[j].move();
      }
    }
    if (trigger.isColliding(bagOfBalls[i])) {
      bagOfBalls[i].explode();
      //    bagOfBalls[i].move();
    }
  }

  //Win!
  if (numOfShots >= 0 && numDead == numOfBalls) {
    background(0);
    customScoreboard();
    fill(#2A7BDB);
    textSize(width/10);
    text("You Win!", 4*width/20, 12*height/20);
    winImage.resize(6*width/20, 6*height/20);
    image(winImage, 13*width/20, 8*height/20);

    //Play Again Button
    fill(#233DA0);
    rect(16*width/20, 17*height/20, 3*width/20, 2*height/20);
    fill(255);
    textSize(width/40);
    text("Play Again?", 129*width/160, 147*height/160);
  }

  //Lose Only
  if (numOfShots == 0 && numDead != numOfBalls) {
    fill(#233DA0);
    rect(16*width/20, 17*height/20, 3*width/20, 2*height/20);
    fill(255);
    textSize(width/40);
    text("Play Again?", 129*width/160, 147*height/160);
    userControl.draw();
    userControl.move();
  } else {

    //User Cursor
    userControl.draw(); //Create Cursor  r
    userControl.move();
  }
}

//Sets Trigger Explosion to True for draw to call
boolean mouseClick = false;

void mouseClicked() {
  if (numOfShots > 0) {
    mouseClick = true;
    trigger = new Ball();  
    trigger.x = mouseX;
    trigger.y = mouseY;
    trigger.state = GROWING;
    numOfShots -= 1;

    //Restart Button
  } else if (mouseX > 16*width/20 && mouseX < 19*width/20 && mouseY > 17*height/20 && mouseY < 19*height/20) {
    setup();
  }
}

void customBackground() {
  strokeWeight(3);
  stroke(#2A7BDB);
  for (int k = 0; k < 20; k++) {
    line(0, k*height/20, width, k*height/20);
    line(k*width/20, 0, k*width/20, height);
  }
}

void customScoreboard() {
  //Scoreboard!
  fill(#2EAF23);
  rect(0, 0, width, height/20);

  //Score!
  fill(0);
  textSize(width/40);
  text("Score:", width/20, height/20 - height/80);
  text(Score, 3*width/20, height/20 - height/80);

  //Number Dead
  text("Dead:", 7*width/20, height/20 - height/80);
  text(numDead, 9*width/20, height/20 - height/80);
  text("/", 10*width/20, height/20 - height/80);
  text(numNeedDead, 11*width/20, height/20 - height/80);

  //Number Of Shots
  text("Ammo:", 14*width/20, height/20 - height/80);
  text(numOfShots, 16*width/20, height/20 - height/80);
}

////FIX SIZE OF EXPLOSION, ETC. for all screen sizes!

