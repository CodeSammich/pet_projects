class Gui {
  PImage img;

  void draw() {
    //Background
    img = loadImage("Space.gif");
    image (img, 0, 0, width/1, height/1);
    stroke(random (255), random (255), random (255));
    textSize(width/16);
    fill(255, 255, 255);
    textAlign(LEFT,BOTTOM);
    
    //Title
    text("PAC", 5*width/16, 27*width/80); 
    fill(#1EE3E1);
    text("MAZING", 5*width/16, 31*width/80);
    fill(255, 255, 255);
    text("INVADERS", 5*width/16, 7*width/16);
    fill(255, 255, 255);
    
    //Start Button
    noStroke();
    fill(255, 255, 255, 0);
    ellipse(6*width/8, 3*width/8, width/8, width/8);
    fill(#F8FC0A, 100);
    arc(6*width/8, 3*width/8, width/8, width/8, PI/5, 2*PI - PI/5);
    textSize(width/40);
    fill(0, 0, 0);
    fill(#1EE3E1);
    text("START", 57*width/80, 31*width/80);
  }
}
