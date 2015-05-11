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
int INITIAL_SPEED = 20; //Initial speed of balls

class PacMan {
  //Location/Pac-Shape
  int px, py;
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

  PacMan() {
    //Initialization of Mouth Angles
    mouthDegreeStart = (PI/5);
    mouthDegreeEnd = (2*PI - PI/5);

    //Location of Initial Pac-Man
    px = sizex/2; 
    py = sizey/2 - sizey/100;
    PacWidth = width/20;
    PacHeight = height/20;

    //Color of Pac-Man
    colorPac = random(255);
    colorPac2 = random(255);
    colorPac3 = random(255);
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
      
      //Make Entire Pac-Man
      fill(colorPac, colorPac2, colorPac3, colorTransparency);
      stroke(0);

      //Animate Mouth Open/Close
      if (mouthOpen == 0) {
        arc(px, py, PacWidth, PacHeight, mouthDegreeStart, mouthDegreeEnd);
        mouthOpenCounter += 1;
      } else if (mouthOpen == 1) {
        arc(px, py, PacWidth, PacHeight, mouthDegreeStart - PI/8, mouthDegreeEnd + PI/8);
        mouthOpenCounter += 1;
        //    } else if (mouthOpen == 2) {
        //      ellipse(px, py, PacWidth, PacHeight);
        //      mouthOpenCounter += 1;
      } else if (mouthOpen == 2) {
        arc(px, py, PacWidth, PacHeight, mouthDegreeStart - PI/8, mouthDegreeEnd + PI/8);
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
        ellipse(px - width/40, py - height/20, PacWidth/5, PacHeight/5);
      }
      if (PacWidth >= 0) {
        PacWidth -= SHRINK_SPEED;
        PacHeight -= SHRINK_SPEED;
      } else {
        STATE = DEAD;
      }
    }
  }
  
}

