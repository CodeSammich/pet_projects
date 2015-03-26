//RUN
int sizex = 2000;
int sizey = 2000;

void setup() {
  size(sizex + 10, sizey + 10);
  background(0);
  fill(0);

  //Location of Initial Pac-Man
  px = sizex/2; 
  py = sizey/2 - sizey/100;

  //Initialization of Mouth Angles
  mouthDegreeStart = (PI/5);
  mouthDegreeEnd = (2*PI - PI/5);
}

void draw() {
  background(0);
  walls();
  ball(px, py);
} 


// PAC-MAN

int px; //x-cor of Pac-Man
int py; //y-cor of Pac-Man
float mouthDegreeStart; //Top Lip
float mouthDegreeEnd; //Bottom Lip
float PacWidth = 100;
float PacHeight = 100;
float colorPac = 255; //color of Pac
float colorPac2;
float colorPac3 = 255;

//// Limit Wrapping to Portals*****
//Make Pac-Man
void ball(int x, int y) {
  //Wrap Pac-Man at Portals
  if ((px > width - 10) && ((py >= height/2 - height/20) && (py <= height/2))) {
    px = 10;
  }
  if ((px < 10) && ((py >= height/2 - height/20) && (py <= height/2))) {
    px = width - 10;
  }
  //Make Entire Pac-Man
  fill(colorPac, colorPac2, colorPac3);
  stroke(0);
  arc(x, y, PacWidth, PacHeight, mouthDegreeStart, mouthDegreeEnd);
}


//Move Pac-Man
void keyPressed() {
  if (key == CODED) {

    if (keyCode == RIGHT) {
      //      for(int n = 1, n < 
      //movement distance
      px += sizex/100;
      //mouth
      mouthDegreeStart = (PI/5);
      mouthDegreeEnd = (2*PI - PI/5);
    } else if (keyCode == LEFT) {
      px -= sizex/100;      
      mouthDegreeStart = (PI + PI/5);
      mouthDegreeEnd = (3*PI - PI/5);
    } else if (keyCode == UP) {
      py -= sizey/100;
      mouthDegreeStart = (2*PI - 3*PI/10);
      mouthDegreeEnd = (3*PI + 3*PI/10);
    } else if (keyCode == DOWN) {
      py += sizey/100;      
      mouthDegreeStart = (PI - 3*PI/10);
      mouthDegreeEnd = (2*PI + 3*PI/10);
    }
  }
}

void mousePressed() {
   colorPac = random(255);
   colorPac2 = random(255);
   colorPac3 = random(255);
}





    //WALLS
    float wx, wy; //Coordinates of Walls
    void walls() {
      //Make Inside Walls
      for (wy = sizey/100; wy < sizey; wy += PacHeight + sizey/50) {
        for (wx = sizex/100; wx < sizex; wx+=PacWidth + sizex/50) {
          stroke(0, 0, 255);
          fill(1);
          ellipse(wx, wy, sizex/100, sizey/100);
        }
      }
      //Outside Borders
      rect(0, 0, width, 10); //Top Wall
      rect(0, height - 10, width, 10); //Bottom Wall
      rect(width - 10, 0, 10, height); //Right Wall
      rect(0, 0, 10, height); //Left Wall

      fill(255); //Make Hole White
      rect(width - 10, height/2 - height/15, 10, height/10); //Right Portal
      rect(0, height/2 - height/15, 10, height/10); //Left Portal
    }

    //COINS
    int cx, cy; //Coordinates of Coins
    int numCoins; //Number of Coins Currently Existing
    int maxNumCoins; //Maximum Amount of Coins Allowed

    //void generateCoins(){
    //  //Vertical Coins
    //  for 

