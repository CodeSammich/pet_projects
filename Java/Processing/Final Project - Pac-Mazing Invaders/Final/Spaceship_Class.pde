class Spaceship {
  float xcor, ycor, xspeed, yspeed;
  PImage img;

  Spaceship () {
    xcor   = 45*width/80;
    ycor   = 65*width/80;
    xspeed = width/160;
    yspeed = 0;
  }
  void draw () {
    img = loadImage("Invader.png");
    image (img, xcor, ycor - width/8, width/5, width/5);
    xcor += xspeed;
    if (xcor < width/16 || xcor > 15*width/16) {
      xspeed *= -1;
    }
  }
}
