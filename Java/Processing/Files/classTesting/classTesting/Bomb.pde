class Bomb {
  float x, y, dx, dy; //Location, Speed
  float wide, high; //
  float ringDiameter = 200; //Ring Diameter

  //Creates Target Ring/Cursor
  Bomb() {
    x = width/2;
    y = height/2;
  }

  //Recreates Cursor Constantly
  void draw() {
    noStroke(); 
    fill(#F7F123);
    ellipse(x, y, 50, 50); //Inner Circle
    
    //Target Lines
    stroke(#F7F123);
    line(x, y - ringDiameter/2, x, y + ringDiameter/2);
    line(x - ringDiameter/2, y, x + ringDiameter/2, y);
    
    stroke(#F7F123);
    fill(#F7F123, 50); //Transparency
    ellipse(x, y, ringDiameter, ringDiameter); //Outer Ring
  }

  //Ring follows mouse
  void move() {
    x = mouseX;
    y = mouseY;
  }

  //Detects if Ball in Target
  boolean isOverlapping(Ball b) {
    float centerDistance = dist(x, y, b.x, b.y);
    float radiiSum = (ringDiameter / 2) + (b.diameter / 2);
    return centerDistance < radiiSum;
  }
}

