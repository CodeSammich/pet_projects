//RUN
int sizex = 1500;
int sizey = 1500;

void setup() {
  size(sizex, sizey);
  background(0);
  fill(0);

  //Location of Initial Pac-Man
  x = sizey/10; 
  y = sizey/10;

  //Initialization of Mouth Angles
  mouthDegreeStart = (PI - 3*PI/10);
  mouthDegreeEnd = (2*PI + 3*PI/10);
}

void draw() {
  background(0);
  rowOfPacs(7, 3, 6);
}

// PAC-MAN/SpaceInvader

int x; //x-cor of Pac-Man
int y; //y-cor of Pac-Man
float mouthDegreeStart; //Top Lip
float mouthDegreeEnd; //Bottom Lip
float PacWidth = 100; //Constant Pac-Size, perhaps bigger map;
float PacHeight = 100; 


//Make Pac-Man
void ball(int x, int y) {
  //Wrap Pac-Man at Portals
  if ((x > width - 10) && ((dy >= height/2 - height/20) && (dy <= height/2))) {
    x = 10;
  }
  if ((x < 10) && ((dy >= height/2 - height/20) && (dy <= height/2))) {
    x = width - 10;
  }
  //Make Entire Pac-Man
  fill(#F4F519);
  stroke(0);
  arc(x, y, PacWidth, PacHeight, mouthDegreeStart, mouthDegreeEnd);
}

//Move Rows
int dx = 0;
int dy = 0;

void rowOfPacs(float speed, int numOfRows, int numOfPacs) {
  boolean right = true;
  if (right
  dx += speed;
  //Makes Multiple Rows
  for (y = sizey/10; y <= (numOfRows)*sizey/10; y += sizey/10) {
    //Makes One Row of Pac-Men
    for (x = sizex/10; x <= (numOfPacs)*sizex/10; x += sizex/10) {
      ball(x + dx, y + dy);
    }
  }
}

