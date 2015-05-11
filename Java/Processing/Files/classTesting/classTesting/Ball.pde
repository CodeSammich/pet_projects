//State Constants
int MOVING = 0;
int GROWING = 1;
int SHRINKING = 2;
int DEAD = 3;
int STARTING_SIZE = 50;
int MAX_SIZE = 400;
int EXPLOSION_DELAY = 30; //Frames per second, computer processes 60 fps; so, 30 = .5 second delay
int INITIAL_SPEED = 20; //Initial speed of balls
int BOOM_SPEED = 20;
int SHRINK_SPEED = 40;
int Score = 0;
int Multipler = 20;
int numDead; //Number Dead
int numNeedDead = numOfBalls; //Number Needed to Pass a level

class Ball {
  //Each Ball's Explosion Delay
  int EXPLODE_DELAY;

  //Basic Properties
  float diameter; //Diameter
  float x, y; //Location
  int dx, dy; //Speed
  int boomSpeed = BOOM_SPEED; //Growing Speed

  //Color Params
  int colorRed;
  int colorGreen;
  int colorBlue;
  int colorTransparency;
  int state;

  //Makes initial ball
  Ball() {
    EXPLODE_DELAY = EXPLOSION_DELAY;
    state = MOVING;
    this.diameter = STARTING_SIZE;
    x = random(width);
    y = random(19*height/20) + height/20;
    dx = (int)random(INITIAL_SPEED) + 1;
    dy = (int)random(INITIAL_SPEED) + 1;

    //Set Colors
    colorRed = (int)random(255);
    colorGreen = (int)random(255);
    colorBlue = (int)random(255);
    colorTransparency = 255;
  }

  //Creates Ball Constantly
  void draw() {
    if  (state != DEAD) {
      noStroke();
      fill(colorRed, colorGreen, colorBlue, colorTransparency);
      ellipse(x, y, diameter, diameter);
    }
  }

  //Moves
  void move() {
    if (state == MOVING) {
      if (x >= width || x <= 0) {
        dx *= -1;
      }
      if (y >= height || y <= height/20) {
        dy *= -1;
      }

      x += dx;
      y += dy;
    }

    //Explosion
    if (state == GROWING) {
      colorTransparency = 100;
      if (diameter < MAX_SIZE) {
        diameter += boomSpeed;
      }
      if (diameter >= MAX_SIZE) {
        state = SHRINKING;
      }
    }

    if (state == SHRINKING) {
      if (EXPLODE_DELAY > 0) {
        EXPLODE_DELAY -= 1;
        diameter += SHRINK_SPEED;
      }
      if (diameter >= 0) {
        diameter -= SHRINK_SPEED;
      } else {
        state = DEAD;
        numDead += 1;
        Score += Multipler;
      }
    }
  }

  //Start Explosion for Individual Ball
  void explode() {
    if (state == MOVING) {
      state = GROWING;
    }
  }

  //// Use This Method in the original test!
  //Test to see if Collide
  boolean isColliding(Ball b) {
    float centerDistance = dist(x, y, b.x, b.y);
    float radiiSum = (diameter / 2) + (b.diameter / 2);
    //    println(centerDistance < radiiSum);
    return centerDistance <= radiiSum && (state == GROWING || state == SHRINKING);
  }

  void triggerExplode() {
    //Explosion
    if (state == GROWING) {
      colorTransparency = 100;
      if (diameter < MAX_SIZE) {
        diameter += boomSpeed;
      }
      if (diameter >= MAX_SIZE) {
        state = SHRINKING;
      }
    }

    if (state == SHRINKING) {
      if (EXPLODE_DELAY > 0) {
        EXPLODE_DELAY -= 1;
        diameter += SHRINK_SPEED;
      }
      if (diameter >= 0) {
        diameter -= SHRINK_SPEED;
      } else {
        state = DEAD;
      }
    }
  }
}
