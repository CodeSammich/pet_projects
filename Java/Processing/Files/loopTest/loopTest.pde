void setup() {
  size(800, 600);
}

void draw() {
  background(0);
  target(500, 400, 100, 100);
  target(400, 100, 100, 230);
  target(200, 300, 320, 150);
  target(400, 500, 150, 100);
}

void target(float x, float y, int diameter, int ringSize){
  boolean blue = true;
  for (int d = diameter; d >= 20; d -= 40) {
    if (blue) {
      fill(0, 0, 255); //blue
    } else {
      fill(255); //white
    }
    blue = !blue;
    ellipse(x, y, d, d); //Make Rings
  }}
