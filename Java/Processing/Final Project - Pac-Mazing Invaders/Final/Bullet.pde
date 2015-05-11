class Bullet {
  float xcor, ycor, xspeed, yspeed;
  PImage img;
  int w = width/50;
  int h = height/30;

  Bullet (float x, float y, int xSpeed, int ySpeed) {
    xcor   = x;
    ycor   = y;
    xspeed = xSpeed;
    yspeed = ySpeed;
  }

  void draw () {
    img = loadImage("Bullet.png");
    image (img, xcor, ycor, width/50, height/30);
    ycor += yspeed;
  }

  boolean hitWall() {
    PImage frame = get();
    color c = frame.get((int)xcor, (int)(ycor - height/30));
    if (c == color(255, 0, 0)) {
      return true;
    } else { 
      return false;
    }
  }
}

