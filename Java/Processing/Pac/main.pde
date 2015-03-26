PacMan Bob;
int sizex = 2000;
int sizey = 2000;

void setup() {
  size(sizex, sizey);
  background(0);
  Bob = new PacMan();
}

void draw() {
  background(0);
  Bob.draw();
}

//Move Pac-Man
void keyPressed() {
  if (key == CODED) {
    if (keyCode == RIGHT) {
      //movement distance
      Bob.px += sizex/100;
      //mouth
      Bob.mouthDegreeStart = (PI/5);
      Bob.mouthDegreeEnd = (2*PI - PI/5);
    } else if (keyCode == LEFT) {
      Bob.px -= sizex/100;      
      Bob.mouthDegreeStart = (PI + PI/5);
      Bob.mouthDegreeEnd = (3*PI - PI/5);
    } else if (keyCode == UP) {
      Bob.py -= sizey/100;
      Bob.mouthDegreeStart = (2*PI - 3*PI/10);
      Bob.mouthDegreeEnd = (3*PI + 3*PI/10);
    } else if (keyCode == DOWN) {
      Bob.py += sizey/100;      
      Bob.mouthDegreeStart = (PI - 3*PI/10);
      Bob.mouthDegreeEnd = (2*PI + 3*PI/10);
    }
  }
  if (Bob.mouthOpenCounter >= 10) {
    Bob.mouthOpen += 1;
    Bob.mouthOpenCounter = 0;
  }
}

void mousePressed() {
  Bob.colorPac = random(255);
  Bob.colorPac2 = random(255);
  Bob.colorPac3 = random(255);
  STATE = EXPANDING;
}
