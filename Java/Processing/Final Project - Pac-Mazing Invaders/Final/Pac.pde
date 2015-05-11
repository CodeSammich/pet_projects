//Life States
int STATE;
int MOVING = 0;
int EXPANDING = 1;
int SHRINKING = 2;
int DEAD = 3;
int STARTING_SIZE = 50;
int MAX_SIZE = 400;
int colorTransparency;
int BOOM_SPEED = 20;
int SHRINK_SPEED = 40;
int EXPLOSION_DELAY = 30; //Frames per second, computer processes 60 fps; so, 30 = .5 second delay
int Brightness = 150;

class PacMan {
  //Location/Pac-Shape
  int xcor, ycor;
  float colorPac, colorPac2, colorPac3;
  float mouthDegreeStart, mouthDegreeEnd;
  float PacWidth;
  float PacHeight;

  //Animation Frames/Delay;
  int mouthOpen;
  int mouthOpenCounter = 0;

  //Health Level to be put on Health Bar
  int health; 

  //Dead?
  boolean Dead;

  //Directions Allowed for Pac
  boolean right = true;
  boolean left = true;
  boolean down = true;
  boolean up = true;

  PacMan() {
    //Initialization of Mouth Angles
    mouthDegreeStart = (PI/5);
    mouthDegreeEnd = (2*PI - PI/5);

    //Location of Initial Pac-Man
    //    xcor = sizex/2; 
    //    ycor = sizey/2 - sizey/100;
    xcor = SPACE;
    ycor = SPACE + SPACE/2;

    PacWidth = width/32;
    PacHeight = PacWidth;

    //Color of Pac-Man
    colorPac = random(105) + Brightness;
    colorPac2 = random(105) + Brightness;
    colorPac3 = random(105) + Brightness;
    colorTransparency = 255;
    mouthOpen = 0;

    //Life Proficiency
    Dead = false;
    health = 1000;

    STATE = MOVING;
  }

  void draw() {
    if (STATE != DEAD) {
      if (STATE == EXPANDING || STATE == SHRINKING) {
        explode();
      }

      winCheck();

      //Make Entire Pac-Man
      fill(colorPac, colorPac2, colorPac3, colorTransparency);
      noStroke();

      //Animate Mouth Open/Close
      if (mouthOpen == 0) {
        arc(xcor, ycor, PacWidth, PacHeight, mouthDegreeStart, mouthDegreeEnd);
        mouthOpenCounter += 1;
      } else if (mouthOpen == 1) {
        arc(xcor, ycor, PacWidth, PacHeight, mouthDegreeStart - PI/8, mouthDegreeEnd + PI/8);
        mouthOpenCounter += 1;
        //    } else if (mouthOpen == 2) {
        //      ellipse(xcor, ycor, PacWidth, PacHeight);
        //      mouthOpenCounter += 1;
      } else if (mouthOpen == 2) {
        arc(xcor, ycor, PacWidth, PacHeight, mouthDegreeStart - PI/8, mouthDegreeEnd + PI/8);
        mouthOpenCounter += 1;
        mouthOpen = 0;
      }
    } else if (STATE == DEAD) {
      die();
    }
  }

  void die() {
    //Set Color to Black
    colorPac = 0;
    colorPac2 = 0;
    colorPac3 = 0;

    //Stop Animating
    mouthOpen = 10;

    screen=6;
  }

  void explode() {
    //Explode
    if (STATE == EXPANDING) {
      colorTransparency = 100;
      if (PacWidth < MAX_SIZE) {
        PacWidth += BOOM_SPEED;
        PacHeight += BOOM_SPEED;
      }
      if (PacWidth >= MAX_SIZE || PacHeight >= MAX_SIZE) {
        STATE = SHRINKING;
      }
    }

    if (STATE == SHRINKING) {
      if (EXPLOSION_DELAY > 0) {
        EXPLOSION_DELAY -= 1;
        PacWidth += SHRINK_SPEED; //Cancels Out to make Freeze Effect
        PacHeight += SHRINK_SPEED;
        //Makes Eye
        fill(random(255), random(255), random(255));
        ellipse(xcor - width/40, ycor - height/20, PacWidth/5, PacHeight/5);
      }
      if (PacWidth >= 0) {
        PacWidth -= SHRINK_SPEED;
        PacHeight -= SHRINK_SPEED;
      } else {
        STATE = DEAD;
      }
    }
  }
//  boolean canMove(int k) {
//    for (Wall wall : w) {
//      if (wall.block(k)) {
//        print(false);
//        return false;
//      }
//    } 
//    print(true);
//    return true;
//  }

  void winCheck() {
    if (xcor>=width-SPACE && ycor>=8*SPACE && ycor<=9*SPACE) {
      screen=5;
    }
  }

  boolean canMove(int k) {
    //    for (Wall wall : w) {
    //      if (wall.block(k)) {
    //        print(false);
    //        return false;
    //      }
    //    } 
    //    print(true);
    PImage frame = get();
    for (int i = 0; i < 6; i++) {      
      if (k == UP) {
        color c = frame.get(xcor, (int)(ycor - PacWidth - i));
        if (c == color(255, 0, 0) || c == color(0, 0, 255))
          return false;
      } else if (k == DOWN) {  
        color c = frame.get(xcor, (int)(ycor + PacWidth - i));
        if (c == color(255, 0, 0) || c == color(0, 0, 255))
          return false;
      } else if (k == RIGHT) {
        color c = frame.get((int)(xcor + PacWidth + i), ycor);
        if (c == color(255, 0, 0) || c == color(0, 0, 255))
          return false;
      } else if (k == LEFT) {
        color c = frame.get((int)(xcor - PacWidth - i), ycor);
        if (c == color(255, 0, 0) || c == color(0, 0, 255))
          return false;
      }
    }
    return true;
  }
}

