class Dot{
  //instance variables
  float x,y;
  
  //constructor method
  Dot(float X, float Y){
    x=X;
    y=Y;
  }
  
  void draw(){
    noStroke();
    fill(0, 0, 255);
    ellipse(x, y, 10, 10);
  }
}
