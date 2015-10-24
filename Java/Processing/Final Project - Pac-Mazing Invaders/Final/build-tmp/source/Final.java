import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Final extends PApplet {

Wall[] w;
Dot[] d;
Maze[] m;
Gui ui;
int[] states;
Spaceship invader;
Bullet bullet;
ArrayList<Bullet> bullets = new ArrayList<Bullet>();
PacMan Bob;
//Always have sizex/sizey ratio be 4/3

//Sizex MUST BE ABOVE 500 and BELOW 2000 for smooth gameplay. Recommended: 1000
int sizex = 1000;
int sizey = 3*sizex/4;
PImage img;

int screen;
int INVISIBLE=0;
int REGULAR=1;
int BULLETPROOF=2;
int SPACE;
int num=0;//number in the array of walls
int Num=0;//number in the array of dots
int loadNum;
boolean load;

//Background!
PImage imag;
Health healthbar;

//instructions graphics
PImage Img;
int spacing;
int rectWidth;
int initialY;
int wordX;
int picX;
int navY;
int useY;
int avoidY;
int ghostsY;
int solveY;
int playY;

//screen switching
int screenTimer;

public void setup() {
  size(sizex, sizey);
  screen=0;

  //Health
  healthbar = new Health();

  screenTimer=240;

  ui=new Gui();
  invader = new Spaceship();
  //  bullets.add(new Bullet(invader.xcor, invader.ycor, 0, -3));
  Bob = new PacMan();
  SPACE=width/16;
  w=new Wall[246];
  d=new Dot[135];
  num=0;
  Num=0;
  loadNum=-1;
  load=false;
  states = new int[((5*((height/SPACE)/6)-2)*(width/SPACE-1))+((width/SPACE-2)*(5*((height/SPACE)/6)-1))];

  loadNum=-1;
  m=new Maze[6];
  m[0]=new Maze(0);
  m[1]=new Maze(1);
  m[2]=new Maze(2);
  m[3]=new Maze(3);
  m[4]=new Maze(4);


  //instructions graphics
  spacing=width/200*6;
  rectWidth=width/40;
  initialY=width/10-10;
  wordX=width/5;
  picX=width/10;
  navY=initialY;
  useY=navY+4*spacing;
  avoidY=useY+4*spacing;
  ghostsY=avoidY+4*spacing;
  solveY=ghostsY+4*spacing;
  playY=solveY+4*spacing;

  /////////////////////////// DOTS AND WALLS, INVISIBLE AND VISIBLE AND BORDERS!!!! DO NOT TOUCH UNLESS NECCESSARY /////////////////////////////////////

  //println(SPACE);
  //Horizontal Walls
  for (int h=1; h< 5* ( (height/SPACE)/6); h++) {

    //loops for each row
    for (int g=1; g< (width/SPACE)-1; g++) {

      //loops for each dot in the row
      w[num]=new Wall(g*SPACE, h*SPACE, g*SPACE+SPACE, h*SPACE);
      num++;
    }
  }

  //Vertical Walls
  for (int g=1; g< width/SPACE; g++) {
    //loops for each collumn

    for (int h=1; h< 5* ( (height/SPACE)/6)-1; h++) {

      //loops for each dot in each collumn
      w[num]=new Wall(g*SPACE, h*SPACE, g*SPACE, h*SPACE+SPACE);
      num++;
      //println(num);
    }
  }

  //create borders() 

  for (int i = 0; i < 14; i++) {
    w[i].state = REGULAR;
  }
  for (int i = 112; i < 126; i++) {
    w[i].state = REGULAR;
  }
  for (int i = 126; i < 134; i++) {
    w[i].state = REGULAR;
  }
  for (int i = 238; i < 246; i++) {
    w[i].state = REGULAR;
  }

  w[245].state = INVISIBLE;
  w[126].state = INVISIBLE;

  //dots
  for (float i=SPACE; i<5*height/6; i+=SPACE) {
    for (float j=SPACE; j<width - SPACE; j+=SPACE) {
      if (Num < 135) {
        d[Num]=new Dot(j, i);
        Num ++;
      }
    }
  }


  //////////////////////////////////////// END OF DOTS AND WALLS SECTION OF THE SETUP CODE, DO NOT TOUCH WITHIN THIS AREA!! /////////////////////////////////////

  imag = loadImage("Space.gif");
  imag.resize(width, height);
  image (imag, 0, 0);
}

public void drawButtons() {
  //Title of Level Select
  noStroke();
  fill(255);
  textAlign(CENTER, TOP);
  textSize(width/25);
  text("CHOOSE A LEVEL", width/2, width/200);

  //Buttons
  textAlign(CENTER, CENTER);
  textSize(width/16);
  fill(0, 0, 255, 100);
  rect(width/7, 14*width/100, width/8, width/8);
  fill(255);
  text("1", width/7+width/16, 14*width/100+width/16);
  fill(0, 255, 0, 100);
  rect(2*width/7, 14*width/100, width/8, width/8);
  fill(255);
  text("2", 2*width/7+width/16, 14*width/100+width/16);
  fill(205, 255, 0, 100);
  rect(3*width/7, 14*width/100, width/8, width/8);
  fill(255);
  text("3", 3*width/7+width/16, 14*width/100+width/16);
  fill(255, 200, 0, 100);
  rect(4*width/7, 14*width/100, width/8, width/8);
  fill(255);
  text("4", 4*width/7+width/16, 14*width/100+width/16);
  fill(255, 0, 0, 100);
  rect(5*width/7, 14*width/100, width/8, width/8);
  fill(255);
  text("5", 5*width/7+width/16, 14*width/100+width/16);

  //Or Text
  fill(255);
  textSize(width/25);
  text("OR", width/2, height/2);

  //Custom Maze Button
  fill(255, 0, 255, 100);
  rect(width/2-22*width/80, 400*width/800, 43*width/80, 36*width/800);
  fill(255);
  textSize(width/25);
  text("CREATE A CUSTOM MAZE", width/2-22*width/80+43*width/160, 400*width/800+36*width/1600);
}

public void screen0() {
  //start screen
  ui.draw();
}

public void screen1() {
  //level selector screen
  drawButtons();
}

public void screen2() {
  //game screen
  if (loadNum>-1) {
    for (int i=0; i<5; i++) {
      m[loadNum].draw();
    }
    //draws walls
    for (int i=0; i< 246; i++) {
      w[i].draw();
    }

    //draws dots
    for (int i=0; i< 135; i++) {
      d[i].draw();
      //print("Pie");
    }
  } else if (load) {
    for (int i=0; i< 246; i++) {
      w[i].draw();
      //print("Cake");
    }

    //draws dots
    for (int i=0; i< 135; i++) {
      d[i].draw();
    }
    loadMaze();
  }

  //Space Invader Shooting
  invader.draw();

  //Chance of Shooting
  if (random(100) < 2) {
    bullets.add(new Bullet(invader.xcor, invader.ycor - 5*width/32, 0, -height/100));
  }

  for (int i = 0; i < bullets.size (); i++) {
    Bullet b = bullets.get(i);
    b.draw();

    //Detect!
    boolean xDetect = ((b.xcor + b.w/8 >= Bob.xcor - Bob.PacWidth/2) && (b.xcor - b.w/8 <= Bob.xcor + Bob.PacWidth/2));
    boolean yDetect = ((b.ycor + b.h/16 >= Bob.ycor - Bob.PacHeight/2) && (b.ycor - b.h/16 <= Bob.ycor + Bob.PacHeight/2));
    if (xDetect && yDetect) {
      STATE = EXPANDING;
      Bob.explode();
    }
    if (b.ycor < 0 || b.hitWall()) {
      bullets.remove(i);
      break;
    }
  }
  Bob.draw();
  healthbar.draw();
}

public void screen3() {

  //custom maze builder screen
  for (int i=0; i< 246; i++) {
    w[i].draw();
  }

  //draws dots
  for (int i=0; i< 135; i++) {
    d[i].draw();
  }
  drawButtons3();
}

public void saveStates() {
  for (int i = 0; i < w.length; i++) {
    states[i] = w[i].state;
  }
  load=true;
}

public void loadMaze() {
  //loads the maze that was customuzed
  for (int i=0; i<246; i++) {
    w[i].state=states[i];
  }
}

public void drawButtons3() {
  textSize(32);
  noStroke();

  fill(0, 0, 255);
  rect(width/2, 5*(height/6), 200, 38);
  fill(255);
  text("DONE", width/2+100, 5*(height/6)+16);

  fill(255, 0, 0);
  rect(width/2-200, 5*(height/6), 200, 38);
  fill(255);
  text("RESET", width/2-100, 5*(height/6)+16);
}


//Instructions
public void screen4() {
  fill(255);
  noStroke();

  textSize(spacing+8);
  textAlign(CENTER, TOP);
  text("INSTRUCTIONS:", width/2, 0);

  textSize(spacing);
  textAlign(LEFT, TOP);
  text("Navigate the PacMan through the maze from START", wordX, navY);
  text("to FINISH", wordX, navY+spacing);
  Bob.xcor=width/10;
  Bob.ycor=navY+spacing;
  Bob.PacWidth=rectWidth*3;
  Bob.PacHeight=rectWidth*3;
  Bob.draw();
  //width/10-25+spacing
  Bob.xcor = SPACE;
  Bob.ycor = SPACE+SPACE/2;
  Bob.PacWidth = width/32;
  Bob.PacHeight = Bob.PacWidth;
  noStroke();
  fill(255);

  textAlign(LEFT, TOP);
  text("Use the arrow keys to move the PacMan.", width/5, useY);
  fill(0, 0, 255);
  rectMode(CENTER);
  rect(picX, useY, rectWidth, rectWidth);
  rect(picX, useY+rectWidth+2, rectWidth, rectWidth);
  rect(picX-rectWidth-2, useY+rectWidth+2, rectWidth, rectWidth);
  rect(picX+rectWidth+2, useY+rectWidth+2, rectWidth, rectWidth);
  fill(255);
  //up (top, left, right)
  triangle(picX, useY-(rectWidth/2)+1, picX-(rectWidth/2)+1, useY+rectWidth/2-1, picX+(rectWidth/2)-1, useY+rectWidth/2-1);
  //down (bottom, left, right)
  triangle(picX, useY+rectWidth+2+(rectWidth/2)-1, picX-(rectWidth/2)+1, useY+rectWidth+2-(rectWidth/2)+1, picX+(rectWidth/2)-1, useY+rectWidth+2-(rectWidth/2)+1);
  //left (left,top,bottom)
  triangle(picX-rectWidth-2-rectWidth/2+1, useY+rectWidth+2, picX-rectWidth/2-2-1, useY+rectWidth/2+2+1, picX-rectWidth/2-2-1, useY+rectWidth+rectWidth/2+2-1);
  //right (right,top,bottom)
  triangle(picX+rectWidth+2+rectWidth/2-1, useY+rectWidth+2, picX+rectWidth/2+2+2, useY+rectWidth/2+2+1, picX+rectWidth/2+2+2, useY+rectWidth+rectWidth/2+2-1);


  textAlign(LEFT, TOP);
  text("Avoid the ghost bullets to stay alive.", width/5, avoidY);
  Img = loadImage("Bullet.png");
  imageMode(CENTER);
  image (Img, picX, avoidY+2*rectWidth/3, rectWidth*2, rectWidth*3);

  textAlign(LEFT, TOP);
  text("Ghosts can go through blue walls, but red walls are", wordX, ghostsY);
  text("ghostproof.", wordX, ghostsY+spacing);
  stroke(0, 0, 255);
  strokeWeight(rectWidth/4);
  line(picX-(3*rectWidth/2), ghostsY+rectWidth/2, picX+(3*rectWidth/2), ghostsY+rectWidth/2);
  noStroke();
  fill(0, 0, 255);
  ellipse(picX-(3*rectWidth/2), ghostsY+rectWidth/2, rectWidth/2, rectWidth/2);
  ellipse(picX+(3*rectWidth/2), ghostsY+rectWidth/2, rectWidth/2, rectWidth/2);
  stroke(255, 0, 0);
  strokeWeight(rectWidth/4);
  line(picX-(3*rectWidth/2), ghostsY+spacing+rectWidth/2, picX+(3*rectWidth/2), ghostsY+spacing+rectWidth/2);
  noStroke();
  fill(0, 0, 255);
  ellipse(picX-(3*rectWidth/2), ghostsY+spacing+rectWidth/2, rectWidth/2, rectWidth/2);
  ellipse(picX+(3*rectWidth/2), ghostsY+spacing+rectWidth/2, rectWidth/2, rectWidth/2);
  fill(255);

  textAlign(CENTER, TOP);
  text("Solve the maze and stay alive to win the game. Good luck!", width/2, solveY);

  fill(0, 0, 255);
  rectMode(CENTER);
  rect(width/2, playY+2, width/4, spacing+8+6);
  rectMode(CORNER);
  textSize(spacing+8);
  textAlign(CENTER, CENTER);
  fill(255);
  text("PLAY", width/2, playY);

  textAlign(LEFT, BOTTOM);
}

public void screen5() {
  if (screenTimer>0) {
    screenTimer--;
    fill(0, 255, 0);
    textSize(150);
    textAlign(CENTER, BOTTOM);
    text("YOU WIN!", width/2, height/2);
  } else {
    setup();
    screen=0;
    screenTimer=240;
  }
}

public void screen6() {
  if (screenTimer>0) {
    screenTimer--;
    fill(255, 0, 0);
    textSize(150);
    textAlign(CENTER, BOTTOM);
    text("YOU LOSE!", width/2, height/2);
  }else{
    setup();
    screen=0;
    screenTimer=240;
  }
}

public void draw() {
  background(imag);
  if (screen==0) {
    //start screen
    screen0();
  }
  if (screen==1) {
    //level selection screen
    screen1();
  }
  if (screen==2) {
    //game screen
    screen2();
  }
  if (screen==3) {
    //custom maze builder screen
    screen3();
  }
  if (screen==4) {
    //instruction screen
    screen4();
  }
  if (screen==5) {
    //win screen
    screen5();
  }
  if (screen==6) {
    //win screen
    screen6();
  }
}

public void mouseClicked() {
  if (screen==0) {
    if (dist(mouseX, mouseY, 6*width/8, 3*width/8) <= width/16) {
      screen=1;
    }
  }
  if (screen==1) {

    //preset maze buttons
    if (mouseX>=width/7 && mouseX<=width/7+width/8 && mouseY >=14*width/100 && mouseY <=14*width/100+width/8) {
      loadNum=0;
      screen=4;
    } 
    if (mouseX>=2*width/7 && mouseX<=2*width/7+width/8 && mouseY >=14*width/100 && mouseY <=14*width/100+width/8) {
      loadNum=1;
      screen=4;
    } 
    if (mouseX>=3*width/7 && mouseX<=3*width/7+width/8 && mouseY >=14*width/100 && mouseY <=14*width/100+width/8) {
      loadNum=2;
      screen=4;
    } 
    if (mouseX>=4*width/7 && mouseX<=4*width/7+width/8 && mouseY >=14*width/100 && mouseY <=14*width/100+width/8) {
      loadNum=3;
      screen=4;
    } 
    if (mouseX>=5*width/7 && mouseX<=5*width/7+width/8 && mouseY >=14*width/100 && mouseY <=14*width/100+width/8) {
      loadNum=4;
      screen=4;
    }

    //custom maze button
    if (mouseX>=width/2-22*width/80 && mouseX<=width/2-22*width/80+43*width/80 && mouseY >=400*width/800 && mouseY <=436*width/800) {
      screen=3;
    }
  }
  //Restart Button Activation
  if (screen==2) {

    if (mouseX >= 400*width/1000 && mouseX < 600*width/1000 && mouseY >= 20*width/1000 && mouseY <= 50*width/1000) {
      setup();
      screen = 1;
    }
  }

  if (screen==3) {
    //custom screen clicks

    //selecting walls (and dots);
    for (int i=0; i< 246; i++) {
      w[i].selected();
    }

    //done button
    //saves customized maze and moves to game
    if  (mouseX>=width/2 && mouseX<=width/2+200 && mouseY >=5*(height/6) && mouseY <=5*(height/6)+38) {
      saveStates();
      //      for (int i=0; i< 246; i++) {
      //        print("w["+str(i)+"].state="+str(w[i].state)+";");
      //      }
      screen=4;
    }

    //reset button
    //removes all drawn walls (except for the preset borders)
    if (mouseX>=width/2-200 && mouseX<=width/2 && mouseY >=5*(height/6) && mouseY <=5*(height/6)+38) {
      for (int a=0; a<246; a++) {
        w[a].state=INVISIBLE;
        //create borders() 

        for (int i = 0; i < 14; i++) {
          w[i].state = REGULAR;
        }
        for (int i = 112; i < 126; i++) {
          w[i].state = REGULAR;
        }
        for (int i = 126; i < 134; i++) {
          w[i].state = REGULAR;
        }
        for (int i = 238; i < 246; i++) {
          w[i].state = REGULAR;
        }

        w[245].state = INVISIBLE;
        w[126].state = INVISIBLE;
      }
    }
  }
  if (screen==4) {
    //play button
    if (mouseX>=width/2-width/8 && mouseX<=width/2+width/8 && mouseY>=playY+2-(spacing+8+6)/2 && mouseY<=playY+2+(spacing+8+6)/2) {
      screen=2;
    }
  }
}

//Move Pac-Man
public void keyPressed() {
  if (key == CODED) {
    if (keyCode == RIGHT) {
      //movement distance 
      if (Bob.canMove(RIGHT))
        Bob.xcor += sizex/100;
      //mouth
      Bob.mouthDegreeStart = (PI/5);
      Bob.mouthDegreeEnd = (2*PI - PI/5);
    } else if (keyCode == LEFT) {
      if (Bob.canMove(LEFT))
        Bob.xcor -= sizex/100;      
      Bob.mouthDegreeStart = (PI + PI/5);
      Bob.mouthDegreeEnd = (3*PI - PI/5);
    } else if (keyCode == UP) {
      if (Bob.canMove(UP))
        Bob.ycor -= sizey/100;
      Bob.mouthDegreeStart = (2*PI - 3*PI/10);
      Bob.mouthDegreeEnd = (3*PI + 3*PI/10);
    } else if (keyCode == DOWN) {
      if (Bob.canMove(DOWN))
        Bob.ycor += sizey/100;      
      Bob.mouthDegreeStart = (PI - 3*PI/10);
      Bob.mouthDegreeEnd = (2*PI + 3*PI/10);
    } else if (keyCode == 32) { // If Space Pressed
      Bob.colorPac = random(255);
      Bob.colorPac2 = random(255);
      Bob.colorPac3 = random(255);
    }   
    //Animate
    if (Bob.mouthOpenCounter >= 10) {
      Bob.mouthOpen += 1;
      Bob.mouthOpenCounter = 0;
    }
  }

}

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

  public void draw () {
    img = loadImage("Bullet.png");
    image (img, xcor, ycor, width/50, height/30);
    ycor += yspeed;
  }

  public boolean hitWall() {
    PImage frame = get();
    int c = frame.get((int)xcor, (int)(ycor - height/30));
    if (c == color(255, 0, 0)) {
      return true;
    } else { 
      return false;
    }
  }
}

class Dot{
  //instance variables
  float x,y;
  
  //constructor method
  Dot(float X, float Y){
    x=X;
    y=Y;
  }
  
  public void draw(){
    noStroke();
    fill(0, 0, 255);
    ellipse(x, y, 10, 10);
  }
}
class Gui {
  PImage img;

  public void draw() {
    //Background
    img = loadImage("Space.gif");
    image (img, 0, 0, width/1, height/1);
    stroke(random (255), random (255), random (255));
    textSize(width/16);
    fill(255, 255, 255);
    textAlign(LEFT,BOTTOM);
    
    //Title
    text("PAC", 5*width/16, 27*width/80); 
    fill(0xff1EE3E1);
    text("MAZING", 5*width/16, 31*width/80);
    fill(255, 255, 255);
    text("INVADERS", 5*width/16, 7*width/16);
    fill(255, 255, 255);
    
    //Start Button
    noStroke();
    fill(255, 255, 255, 0);
    ellipse(6*width/8, 3*width/8, width/8, width/8);
    fill(0xffF8FC0A, 100);
    arc(6*width/8, 3*width/8, width/8, width/8, PI/5, 2*PI - PI/5);
    textSize(width/40);
    fill(0, 0, 0);
    fill(0xff1EE3E1);
    text("START", 57*width/80, 31*width/80);
  }
}
class Health {
  public void draw() {
    noStroke();
    textAlign(CENTER,CENTER);
    fill(255, 255, 255, 25);
    rect(570, 25, 210, 20);
    fill(0xff00DE26);
    rect(570, 25, 210, 20);
    fill(0xff1EE3E1);
    textSize(16);
    text("100%", 570+210/2, 32);
    fill(255, 255, 255, 15);
    rect(500, 25, 59, 20);
    fill(0xffDE0000);
    textSize(24);
    text("RESET", 500+(59/2), 32);
  }
}
class Maze { 
  int mazeNum;

  Maze(int n) {
    mazeNum=n;
  }

  public void draw() { //Hard code for maze layout
    if (mazeNum==1) {
      w[0].state=1;
      w[1].state=1;
      w[2].state=1;
      w[3].state=1;
      w[4].state=1;
      w[5].state=1;
      w[6].state=1;
      w[7].state=1;
      w[8].state=1;
      w[9].state=1;
      w[10].state=1;
      w[11].state=1;
      w[12].state=1;
      w[13].state=1;
      w[14].state=0;
      w[15].state=1;
      w[16].state=0;
      w[17].state=0;
      w[18].state=0;
      w[19].state=0;
      w[20].state=0;
      w[21].state=1;
      w[22].state=0;
      w[23].state=0;
      w[24].state=0;
      w[25].state=2;
      w[26].state=0;
      w[27].state=0;
      w[28].state=1;
      w[29].state=0;
      w[30].state=2;
      w[31].state=1;
      w[32].state=0;
      w[33].state=0;
      w[34].state=2;
      w[35].state=0;
      w[36].state=0;
      w[37].state=2;
      w[38].state=0;
      w[39].state=0;
      w[40].state=1;
      w[41].state=0;
      w[42].state=0;
      w[43].state=0;
      w[44].state=2;
      w[45].state=0;
      w[46].state=1;
      w[47].state=0;
      w[48].state=1;
      w[49].state=0;
      w[50].state=0;
      w[51].state=0;
      w[52].state=1;
      w[53].state=1;
      w[54].state=0;
      w[55].state=0;
      w[56].state=0;
      w[57].state=0;
      w[58].state=0;
      w[59].state=1;
      w[60].state=0;
      w[61].state=0;
      w[62].state=0;
      w[63].state=1;
      w[64].state=1;
      w[65].state=0;
      w[66].state=0;
      w[67].state=0;
      w[68].state=0;
      w[69].state=0;
      w[70].state=0;
      w[71].state=1;
      w[72].state=1;
      w[73].state=0;
      w[74].state=0;
      w[75].state=2;
      w[76].state=2;
      w[77].state=0;
      w[78].state=0;
      w[79].state=2;
      w[80].state=0;
      w[81].state=1;
      w[82].state=2;
      w[83].state=2;
      w[84].state=0;
      w[85].state=1;
      w[86].state=0;
      w[87].state=0;
      w[88].state=0;
      w[89].state=1;
      w[90].state=0;
      w[91].state=0;
      w[92].state=1;
      w[93].state=1;
      w[94].state=1;
      w[95].state=0;
      w[96].state=0;
      w[97].state=0;
      w[98].state=0;
      w[99].state=0;
      w[100].state=0;
      w[101].state=2;
      w[102].state=0;
      w[103].state=0;
      w[104].state=0;
      w[105].state=2;
      w[106].state=0;
      w[107].state=0;
      w[108].state=0;
      w[109].state=1;
      w[110].state=0;
      w[111].state=0;
      w[112].state=1;
      w[113].state=1;
      w[114].state=1;
      w[115].state=1;
      w[116].state=1;
      w[117].state=1;
      w[118].state=1;
      w[119].state=1;
      w[120].state=1;
      w[121].state=1;
      w[122].state=1;
      w[123].state=1;
      w[124].state=1;
      w[125].state=1;
      w[126].state=0;
      w[127].state=1;
      w[128].state=1;
      w[129].state=1;
      w[130].state=1;
      w[131].state=1;
      w[132].state=1;
      w[133].state=1;
      w[134].state=0;
      w[135].state=0;
      w[136].state=1;
      w[137].state=0;
      w[138].state=1;
      w[139].state=0;
      w[140].state=1;
      w[141].state=0;
      w[142].state=0;
      w[143].state=1;
      w[144].state=0;
      w[145].state=2;
      w[146].state=0;
      w[147].state=0;
      w[148].state=0;
      w[149].state=1;
      w[150].state=1;
      w[151].state=0;
      w[152].state=2;
      w[153].state=0;
      w[154].state=0;
      w[155].state=0;
      w[156].state=2;
      w[157].state=0;
      w[158].state=0;
      w[159].state=1;
      w[160].state=0;
      w[161].state=1;
      w[162].state=0;
      w[163].state=1;
      w[164].state=0;
      w[165].state=0;
      w[166].state=1;
      w[167].state=1;
      w[168].state=0;
      w[169].state=0;
      w[170].state=2;
      w[171].state=0;
      w[172].state=0;
      w[173].state=1;
      w[174].state=0;
      w[175].state=2;
      w[176].state=0;
      w[177].state=1;
      w[178].state=0;
      w[179].state=0;
      w[180].state=1;
      w[181].state=0;
      w[182].state=0;
      w[183].state=0;
      w[184].state=0;
      w[185].state=0;
      w[186].state=2;
      w[187].state=0;
      w[188].state=2;
      w[189].state=0;
      w[190].state=0;
      w[191].state=1;
      w[192].state=1;
      w[193].state=0;
      w[194].state=0;
      w[195].state=1;
      w[196].state=0;
      w[197].state=0;
      w[198].state=2;
      w[199].state=0;
      w[200].state=0;
      w[201].state=1;
      w[202].state=0;
      w[203].state=0;
      w[204].state=0;
      w[205].state=2;
      w[206].state=0;
      w[207].state=2;
      w[208].state=0;
      w[209].state=0;
      w[210].state=2;
      w[211].state=0;
      w[212].state=1;
      w[213].state=2;
      w[214].state=0;
      w[215].state=2;
      w[216].state=0;
      w[217].state=1;
      w[218].state=0;
      w[219].state=0;
      w[220].state=0;
      w[221].state=0;
      w[222].state=2;
      w[223].state=0;
      w[224].state=1;
      w[225].state=0;
      w[226].state=2;
      w[227].state=1;
      w[228].state=1;
      w[229].state=0;
      w[230].state=0;
      w[231].state=1;
      w[232].state=0;
      w[233].state=1;
      w[234].state=0;
      w[235].state=0;
      w[236].state=1;
      w[237].state=1;
      w[238].state=1;
      w[239].state=1;
      w[240].state=1;
      w[241].state=1;
      w[242].state=1;
      w[243].state=1;
      w[244].state=1;
      w[245].state=0;
    }
    if (mazeNum==2) {
      w[0].state=1;
      w[1].state=1;
      w[2].state=1;
      w[3].state=1;
      w[4].state=1;
      w[5].state=1;
      w[6].state=1;
      w[7].state=1;
      w[8].state=1;
      w[9].state=1;
      w[10].state=1;
      w[11].state=1;
      w[12].state=1;
      w[13].state=1;
      w[14].state=0;
      w[15].state=1;
      w[16].state=0;
      w[17].state=0;
      w[18].state=0;
      w[19].state=0;
      w[20].state=2;
      w[21].state=2;
      w[22].state=0;
      w[23].state=0;
      w[24].state=0;
      w[25].state=1;
      w[26].state=1;
      w[27].state=0;
      w[28].state=0;
      w[29].state=0;
      w[30].state=2;
      w[31].state=0;
      w[32].state=0;
      w[33].state=0;
      w[34].state=0;
      w[35].state=1;
      w[36].state=0;
      w[37].state=0;
      w[38].state=0;
      w[39].state=0;
      w[40].state=0;
      w[41].state=1;
      w[42].state=1;
      w[43].state=0;
      w[44].state=0;
      w[45].state=1;
      w[46].state=0;
      w[47].state=0;
      w[48].state=1;
      w[49].state=0;
      w[50].state=0;
      w[51].state=0;
      w[52].state=1;
      w[53].state=2;
      w[54].state=0;
      w[55].state=0;
      w[56].state=0;
      w[57].state=1;
      w[58].state=0;
      w[59].state=0;
      w[60].state=0;
      w[61].state=0;
      w[62].state=1;
      w[63].state=0;
      w[64].state=0;
      w[65].state=0;
      w[66].state=1;
      w[67].state=0;
      w[68].state=0;
      w[69].state=0;
      w[70].state=2;
      w[71].state=2;
      w[72].state=0;
      w[73].state=0;
      w[74].state=0;
      w[75].state=0;
      w[76].state=0;
      w[77].state=0;
      w[78].state=2;
      w[79].state=2;
      w[80].state=0;
      w[81].state=1;
      w[82].state=1;
      w[83].state=0;
      w[84].state=0;
      w[85].state=1;
      w[86].state=1;
      w[87].state=0;
      w[88].state=0;
      w[89].state=0;
      w[90].state=0;
      w[91].state=1;
      w[92].state=0;
      w[93].state=0;
      w[94].state=0;
      w[95].state=0;
      w[96].state=0;
      w[97].state=0;
      w[98].state=0;
      w[99].state=0;
      w[100].state=0;
      w[101].state=0;
      w[102].state=2;
      w[103].state=0;
      w[104].state=0;
      w[105].state=0;
      w[106].state=1;
      w[107].state=0;
      w[108].state=0;
      w[109].state=2;
      w[110].state=2;
      w[111].state=0;
      w[112].state=1;
      w[113].state=1;
      w[114].state=1;
      w[115].state=1;
      w[116].state=1;
      w[117].state=1;
      w[118].state=1;
      w[119].state=1;
      w[120].state=1;
      w[121].state=1;
      w[122].state=1;
      w[123].state=1;
      w[124].state=1;
      w[125].state=1;
      w[126].state=0;
      w[127].state=1;
      w[128].state=1;
      w[129].state=1;
      w[130].state=1;
      w[131].state=1;
      w[132].state=1;
      w[133].state=1;
      w[134].state=0;
      w[135].state=1;
      w[136].state=0;
      w[137].state=0;
      w[138].state=0;
      w[139].state=0;
      w[140].state=1;
      w[141].state=0;
      w[142].state=0;
      w[143].state=0;
      w[144].state=1;
      w[145].state=0;
      w[146].state=0;
      w[147].state=0;
      w[148].state=0;
      w[149].state=1;
      w[150].state=0;
      w[151].state=2;
      w[152].state=0;
      w[153].state=1;
      w[154].state=0;
      w[155].state=1;
      w[156].state=1;
      w[157].state=0;
      w[158].state=1;
      w[159].state=1;
      w[160].state=0;
      w[161].state=0;
      w[162].state=1;
      w[163].state=0;
      w[164].state=2;
      w[165].state=0;
      w[166].state=0;
      w[167].state=1;
      w[168].state=1;
      w[169].state=0;
      w[170].state=1;
      w[171].state=1;
      w[172].state=2;
      w[173].state=0;
      w[174].state=0;
      w[175].state=1;
      w[176].state=0;
      w[177].state=0;
      w[178].state=1;
      w[179].state=0;
      w[180].state=1;
      w[181].state=1;
      w[182].state=0;
      w[183].state=0;
      w[184].state=0;
      w[185].state=0;
      w[186].state=0;
      w[187].state=1;
      w[188].state=1;
      w[189].state=0;
      w[190].state=0;
      w[191].state=0;
      w[192].state=0;
      w[193].state=1;
      w[194].state=2;
      w[195].state=0;
      w[196].state=0;
      w[197].state=0;
      w[198].state=0;
      w[199].state=1;
      w[200].state=0;
      w[201].state=1;
      w[202].state=0;
      w[203].state=0;
      w[204].state=1;
      w[205].state=0;
      w[206].state=1;
      w[207].state=1;
      w[208].state=0;
      w[209].state=0;
      w[210].state=2;
      w[211].state=0;
      w[212].state=1;
      w[213].state=1;
      w[214].state=0;
      w[215].state=1;
      w[216].state=0;
      w[217].state=0;
      w[218].state=0;
      w[219].state=0;
      w[220].state=2;
      w[221].state=0;
      w[222].state=0;
      w[223].state=0;
      w[224].state=2;
      w[225].state=1;
      w[226].state=0;
      w[227].state=1;
      w[228].state=0;
      w[229].state=0;
      w[230].state=0;
      w[231].state=0;
      w[232].state=0;
      w[233].state=1;
      w[234].state=0;
      w[235].state=0;
      w[236].state=2;
      w[237].state=0;
      w[238].state=1;
      w[239].state=1;
      w[240].state=1;
      w[241].state=1;
      w[242].state=1;
      w[243].state=1;
      w[244].state=1;
      w[245].state=0;
    }
    if (mazeNum==3) {
      w[0].state=1;
      w[1].state=1;
      w[2].state=1;
      w[3].state=1;
      w[4].state=1;
      w[5].state=1;
      w[6].state=1;
      w[7].state=1;
      w[8].state=1;
      w[9].state=1;
      w[10].state=1;
      w[11].state=1;
      w[12].state=1;
      w[13].state=1;
      w[14].state=0;
      w[15].state=0;
      w[16].state=1;
      w[17].state=0;
      w[18].state=1;
      w[19].state=2;
      w[20].state=2;
      w[21].state=0;
      w[22].state=0;
      w[23].state=1;
      w[24].state=1;
      w[25].state=0;
      w[26].state=0;
      w[27].state=0;
      w[28].state=0;
      w[29].state=0;
      w[30].state=1;
      w[31].state=0;
      w[32].state=0;
      w[33].state=0;
      w[34].state=1;
      w[35].state=0;
      w[36].state=0;
      w[37].state=0;
      w[38].state=0;
      w[39].state=0;
      w[40].state=0;
      w[41].state=0;
      w[42].state=0;
      w[43].state=0;
      w[44].state=0;
      w[45].state=2;
      w[46].state=0;
      w[47].state=0;
      w[48].state=0;
      w[49].state=0;
      w[50].state=0;
      w[51].state=1;
      w[52].state=0;
      w[53].state=2;
      w[54].state=2;
      w[55].state=0;
      w[56].state=1;
      w[57].state=1;
      w[58].state=0;
      w[59].state=1;
      w[60].state=1;
      w[61].state=0;
      w[62].state=0;
      w[63].state=0;
      w[64].state=2;
      w[65].state=0;
      w[66].state=0;
      w[67].state=0;
      w[68].state=0;
      w[69].state=0;
      w[70].state=0;
      w[71].state=1;
      w[72].state=0;
      w[73].state=0;
      w[74].state=0;
      w[75].state=2;
      w[76].state=0;
      w[77].state=1;
      w[78].state=2;
      w[79].state=0;
      w[80].state=2;
      w[81].state=2;
      w[82].state=0;
      w[83].state=0;
      w[84].state=0;
      w[85].state=0;
      w[86].state=1;
      w[87].state=0;
      w[88].state=0;
      w[89].state=0;
      w[90].state=0;
      w[91].state=0;
      w[92].state=2;
      w[93].state=0;
      w[94].state=1;
      w[95].state=1;
      w[96].state=0;
      w[97].state=0;
      w[98].state=0;
      w[99].state=2;
      w[100].state=0;
      w[101].state=0;
      w[102].state=0;
      w[103].state=0;
      w[104].state=0;
      w[105].state=0;
      w[106].state=2;
      w[107].state=0;
      w[108].state=0;
      w[109].state=1;
      w[110].state=1;
      w[111].state=2;
      w[112].state=1;
      w[113].state=1;
      w[114].state=1;
      w[115].state=1;
      w[116].state=1;
      w[117].state=1;
      w[118].state=1;
      w[119].state=1;
      w[120].state=1;
      w[121].state=1;
      w[122].state=1;
      w[123].state=1;
      w[124].state=1;
      w[125].state=1;
      w[126].state=0;
      w[127].state=1;
      w[128].state=1;
      w[129].state=1;
      w[130].state=1;
      w[131].state=1;
      w[132].state=1;
      w[133].state=1;
      w[134].state=1;
      w[135].state=1;
      w[136].state=1;
      w[137].state=0;
      w[138].state=0;
      w[139].state=1;
      w[140].state=2;
      w[141].state=0;
      w[142].state=0;
      w[143].state=0;
      w[144].state=1;
      w[145].state=0;
      w[146].state=0;
      w[147].state=0;
      w[148].state=0;
      w[149].state=0;
      w[150].state=0;
      w[151].state=0;
      w[152].state=0;
      w[153].state=0;
      w[154].state=1;
      w[155].state=1;
      w[156].state=1;
      w[157].state=0;
      w[158].state=0;
      w[159].state=0;
      w[160].state=2;
      w[161].state=0;
      w[162].state=0;
      w[163].state=1;
      w[164].state=1;
      w[165].state=0;
      w[166].state=0;
      w[167].state=1;
      w[168].state=1;
      w[169].state=0;
      w[170].state=0;
      w[171].state=0;
      w[172].state=1;
      w[173].state=1;
      w[174].state=0;
      w[175].state=0;
      w[176].state=0;
      w[177].state=1;
      w[178].state=0;
      w[179].state=1;
      w[180].state=1;
      w[181].state=0;
      w[182].state=0;
      w[183].state=0;
      w[184].state=1;
      w[185].state=1;
      w[186].state=0;
      w[187].state=1;
      w[188].state=1;
      w[189].state=0;
      w[190].state=1;
      w[191].state=1;
      w[192].state=0;
      w[193].state=1;
      w[194].state=0;
      w[195].state=0;
      w[196].state=0;
      w[197].state=0;
      w[198].state=0;
      w[199].state=1;
      w[200].state=0;
      w[201].state=0;
      w[202].state=2;
      w[203].state=0;
      w[204].state=2;
      w[205].state=0;
      w[206].state=0;
      w[207].state=0;
      w[208].state=1;
      w[209].state=0;
      w[210].state=2;
      w[211].state=0;
      w[212].state=1;
      w[213].state=1;
      w[214].state=0;
      w[215].state=0;
      w[216].state=2;
      w[217].state=1;
      w[218].state=0;
      w[219].state=0;
      w[220].state=0;
      w[221].state=0;
      w[222].state=1;
      w[223].state=1;
      w[224].state=0;
      w[225].state=0;
      w[226].state=2;
      w[227].state=0;
      w[228].state=0;
      w[229].state=0;
      w[230].state=0;
      w[231].state=1;
      w[232].state=2;
      w[233].state=0;
      w[234].state=1;
      w[235].state=1;
      w[236].state=0;
      w[237].state=0;
      w[238].state=1;
      w[239].state=1;
      w[240].state=1;
      w[241].state=1;
      w[242].state=1;
      w[243].state=1;
      w[244].state=1;
      w[245].state=0;
    }
    if (mazeNum==0) {
      w[0].state=1;
      w[1].state=1;
      w[2].state=1;
      w[3].state=1;
      w[4].state=1;
      w[5].state=1;
      w[6].state=1;
      w[7].state=1;
      w[8].state=1;
      w[9].state=1;
      w[10].state=1;
      w[11].state=1;
      w[12].state=1;
      w[13].state=1;
      w[14].state=0;
      w[15].state=0;
      w[16].state=0;
      w[17].state=0;
      w[18].state=0;
      w[19].state=0;
      w[20].state=0;
      w[21].state=0;
      w[22].state=1;
      w[23].state=0;
      w[24].state=1;
      w[25].state=0;
      w[26].state=2;
      w[27].state=0;
      w[28].state=0;
      w[29].state=0;
      w[30].state=2;
      w[31].state=0;
      w[32].state=2;
      w[33].state=2;
      w[34].state=0;
      w[35].state=0;
      w[36].state=0;
      w[37].state=0;
      w[38].state=0;
      w[39].state=2;
      w[40].state=0;
      w[41].state=0;
      w[42].state=0;
      w[43].state=0;
      w[44].state=1;
      w[45].state=0;
      w[46].state=0;
      w[47].state=0;
      w[48].state=1;
      w[49].state=0;
      w[50].state=2;
      w[51].state=0;
      w[52].state=0;
      w[53].state=1;
      w[54].state=0;
      w[55].state=0;
      w[56].state=0;
      w[57].state=0;
      w[58].state=0;
      w[59].state=2;
      w[60].state=0;
      w[61].state=0;
      w[62].state=1;
      w[63].state=0;
      w[64].state=1;
      w[65].state=0;
      w[66].state=0;
      w[67].state=0;
      w[68].state=0;
      w[69].state=2;
      w[70].state=0;
      w[71].state=2;
      w[72].state=0;
      w[73].state=1;
      w[74].state=1;
      w[75].state=0;
      w[76].state=0;
      w[77].state=0;
      w[78].state=2;
      w[79].state=2;
      w[80].state=0;
      w[81].state=1;
      w[82].state=0;
      w[83].state=0;
      w[84].state=0;
      w[85].state=0;
      w[86].state=1;
      w[87].state=0;
      w[88].state=0;
      w[89].state=0;
      w[90].state=2;
      w[91].state=0;
      w[92].state=1;
      w[93].state=0;
      w[94].state=1;
      w[95].state=0;
      w[96].state=1;
      w[97].state=0;
      w[98].state=0;
      w[99].state=0;
      w[100].state=0;
      w[101].state=2;
      w[102].state=0;
      w[103].state=0;
      w[104].state=1;
      w[105].state=0;
      w[106].state=0;
      w[107].state=2;
      w[108].state=0;
      w[109].state=0;
      w[110].state=0;
      w[111].state=0;
      w[112].state=1;
      w[113].state=1;
      w[114].state=1;
      w[115].state=1;
      w[116].state=1;
      w[117].state=1;
      w[118].state=1;
      w[119].state=1;
      w[120].state=1;
      w[121].state=1;
      w[122].state=1;
      w[123].state=1;
      w[124].state=1;
      w[125].state=1;
      w[126].state=0;
      w[127].state=1;
      w[128].state=1;
      w[129].state=1;
      w[130].state=1;
      w[131].state=1;
      w[132].state=1;
      w[133].state=1;
      w[134].state=0;
      w[135].state=1;
      w[136].state=0;
      w[137].state=1;
      w[138].state=0;
      w[139].state=0;
      w[140].state=1;
      w[141].state=0;
      w[142].state=0;
      w[143].state=2;
      w[144].state=0;
      w[145].state=1;
      w[146].state=0;
      w[147].state=0;
      w[148].state=1;
      w[149].state=0;
      w[150].state=0;
      w[151].state=2;
      w[152].state=0;
      w[153].state=0;
      w[154].state=0;
      w[155].state=0;
      w[156].state=0;
      w[157].state=0;
      w[158].state=0;
      w[159].state=2;
      w[160].state=0;
      w[161].state=2;
      w[162].state=0;
      w[163].state=0;
      w[164].state=2;
      w[165].state=0;
      w[166].state=1;
      w[167].state=0;
      w[168].state=0;
      w[169].state=1;
      w[170].state=0;
      w[171].state=0;
      w[172].state=1;
      w[173].state=0;
      w[174].state=0;
      w[175].state=2;
      w[176].state=1;
      w[177].state=0;
      w[178].state=0;
      w[179].state=2;
      w[180].state=0;
      w[181].state=0;
      w[182].state=0;
      w[183].state=1;
      w[184].state=0;
      w[185].state=0;
      w[186].state=0;
      w[187].state=2;
      w[188].state=0;
      w[189].state=0;
      w[190].state=0;
      w[191].state=0;
      w[192].state=2;
      w[193].state=0;
      w[194].state=0;
      w[195].state=0;
      w[196].state=0;
      w[197].state=1;
      w[198].state=0;
      w[199].state=1;
      w[200].state=0;
      w[201].state=0;
      w[202].state=0;
      w[203].state=0;
      w[204].state=2;
      w[205].state=0;
      w[206].state=0;
      w[207].state=0;
      w[208].state=1;
      w[209].state=0;
      w[210].state=2;
      w[211].state=0;
      w[212].state=0;
      w[213].state=0;
      w[214].state=0;
      w[215].state=2;
      w[216].state=0;
      w[217].state=0;
      w[218].state=1;
      w[219].state=0;
      w[220].state=1;
      w[221].state=0;
      w[222].state=0;
      w[223].state=0;
      w[224].state=0;
      w[225].state=1;
      w[226].state=0;
      w[227].state=0;
      w[228].state=1;
      w[229].state=0;
      w[230].state=0;
      w[231].state=2;
      w[232].state=0;
      w[233].state=2;
      w[234].state=2;
      w[235].state=0;
      w[236].state=1;
      w[237].state=1;
      w[238].state=1;
      w[239].state=1;
      w[240].state=1;
      w[241].state=1;
      w[242].state=1;
      w[243].state=1;
      w[244].state=1;
      w[245].state=0;
    }
    if (mazeNum==4) {

      w[0].state=1;
      w[1].state=1;
      w[2].state=1;
      w[3].state=1;
      w[4].state=1;
      w[5].state=1;
      w[6].state=1;
      w[7].state=1;
      w[8].state=1;
      w[9].state=1;
      w[10].state=1;
      w[11].state=1;
      w[12].state=1;
      w[13].state=1;
      w[14].state=1;
      w[15].state=1;
      w[16].state=1;
      w[17].state=0;
      w[18].state=0;
      w[19].state=0;
      w[20].state=0;
      w[21].state=0;
      w[22].state=0;
      w[23].state=1;
      w[24].state=1;
      w[25].state=1;
      w[26].state=1;
      w[27].state=0;
      w[28].state=0;
      w[29].state=1;
      w[30].state=0;
      w[31].state=0;
      w[32].state=0;
      w[33].state=0;
      w[34].state=0;
      w[35].state=0;
      w[36].state=0;
      w[37].state=0;
      w[38].state=1;
      w[39].state=1;
      w[40].state=1;
      w[41].state=1;
      w[42].state=2;
      w[43].state=0;
      w[44].state=0;
      w[45].state=2;
      w[46].state=2;
      w[47].state=0;
      w[48].state=0;
      w[49].state=0;
      w[50].state=0;
      w[51].state=1;
      w[52].state=1;
      w[53].state=1;
      w[54].state=1;
      w[55].state=0;
      w[56].state=0;
      w[57].state=2;
      w[58].state=1;
      w[59].state=1;
      w[60].state=1;
      w[61].state=1;
      w[62].state=0;
      w[63].state=0;
      w[64].state=1;
      w[65].state=0;
      w[66].state=0;
      w[67].state=1;
      w[68].state=0;
      w[69].state=0;
      w[70].state=0;
      w[71].state=0;
      w[72].state=0;
      w[73].state=0;
      w[74].state=2;
      w[75].state=0;
      w[76].state=0;
      w[77].state=1;
      w[78].state=0;
      w[79].state=0;
      w[80].state=1;
      w[81].state=0;
      w[82].state=2;
      w[83].state=2;
      w[84].state=0;
      w[85].state=0;
      w[86].state=0;
      w[87].state=0;
      w[88].state=0;
      w[89].state=2;
      w[90].state=2;
      w[91].state=0;
      w[92].state=2;
      w[93].state=2;
      w[94].state=1;
      w[95].state=1;
      w[96].state=1;
      w[97].state=0;
      w[98].state=0;
      w[99].state=0;
      w[100].state=0;
      w[101].state=0;
      w[102].state=0;
      w[103].state=0;
      w[104].state=0;
      w[105].state=1;
      w[106].state=1;
      w[107].state=1;
      w[108].state=1;
      w[109].state=1;
      w[110].state=0;
      w[111].state=0;
      w[112].state=1;
      w[113].state=1;
      w[114].state=1;
      w[115].state=1;
      w[116].state=1;
      w[117].state=1;
      w[118].state=1;
      w[119].state=1;
      w[120].state=1;
      w[121].state=1;
      w[122].state=1;
      w[123].state=1;
      w[124].state=1;
      w[125].state=1;
      w[126].state=0;
      w[127].state=1;
      w[128].state=1;
      w[129].state=1;
      w[130].state=1;
      w[131].state=1;
      w[132].state=1;
      w[133].state=1;
      w[134].state=0;
      w[135].state=0;
      w[136].state=0;
      w[137].state=0;
      w[138].state=1;
      w[139].state=1;
      w[140].state=1;
      w[141].state=0;
      w[142].state=0;
      w[143].state=0;
      w[144].state=1;
      w[145].state=2;
      w[146].state=0;
      w[147].state=1;
      w[148].state=1;
      w[149].state=1;
      w[150].state=0;
      w[151].state=1;
      w[152].state=2;
      w[153].state=0;
      w[154].state=1;
      w[155].state=1;
      w[156].state=1;
      w[157].state=0;
      w[158].state=1;
      w[159].state=1;
      w[160].state=0;
      w[161].state=0;
      w[162].state=0;
      w[163].state=1;
      w[164].state=1;
      w[165].state=1;
      w[166].state=0;
      w[167].state=1;
      w[168].state=2;
      w[169].state=0;
      w[170].state=0;
      w[171].state=0;
      w[172].state=1;
      w[173].state=0;
      w[174].state=1;
      w[175].state=1;
      w[176].state=1;
      w[177].state=1;
      w[178].state=1;
      w[179].state=2;
      w[180].state=0;
      w[181].state=1;
      w[182].state=0;
      w[183].state=1;
      w[184].state=1;
      w[185].state=1;
      w[186].state=1;
      w[187].state=0;
      w[188].state=1;
      w[189].state=0;
      w[190].state=1;
      w[191].state=1;
      w[192].state=1;
      w[193].state=1;
      w[194].state=0;
      w[195].state=2;
      w[196].state=0;
      w[197].state=0;
      w[198].state=0;
      w[199].state=1;
      w[200].state=1;
      w[201].state=0;
      w[202].state=1;
      w[203].state=0;
      w[204].state=0;
      w[205].state=0;
      w[206].state=0;
      w[207].state=0;
      w[208].state=0;
      w[209].state=1;
      w[210].state=1;
      w[211].state=2;
      w[212].state=0;
      w[213].state=0;
      w[214].state=0;
      w[215].state=0;
      w[216].state=0;
      w[217].state=0;
      w[218].state=0;
      w[219].state=0;
      w[220].state=0;
      w[221].state=0;
      w[222].state=0;
      w[223].state=0;
      w[224].state=0;
      w[225].state=0;
      w[226].state=2;
      w[227].state=0;
      w[228].state=0;
      w[229].state=0;
      w[230].state=0;
      w[231].state=0;
      w[232].state=0;
      w[233].state=1;
      w[234].state=0;
      w[235].state=0;
      w[236].state=1;
      w[237].state=1;
      w[238].state=1;
      w[239].state=1;
      w[240].state=1;
      w[241].state=1;
      w[242].state=1;
      w[243].state=1;
      w[244].state=1;
      w[245].state=0;
      w[0].state=1;
      w[1].state=1;
      w[2].state=1;
      w[3].state=1;
      w[4].state=1;
      w[5].state=1;
      w[6].state=1;
      w[7].state=1;
      w[8].state=1;
      w[9].state=1;
      w[10].state=1;
      w[11].state=1;
      w[12].state=1;
      w[13].state=1;
      w[14].state=1;
      w[15].state=1;
      w[16].state=1;
      w[17].state=0;
      w[18].state=0;
      w[19].state=0;
      w[20].state=0;
      w[21].state=0;
      w[22].state=0;
      w[23].state=1;
      w[24].state=1;
      w[25].state=1;
      w[26].state=1;
      w[27].state=0;
      w[28].state=0;
      w[29].state=1;
      w[30].state=0;
      w[31].state=0;
      w[32].state=0;
      w[33].state=0;
      w[34].state=0;
      w[35].state=0;
      w[36].state=0;
      w[37].state=0;
      w[38].state=1;
      w[39].state=1;
      w[40].state=1;
      w[41].state=1;
      w[42].state=2;
      w[43].state=0;
      w[44].state=0;
      w[45].state=2;
      w[46].state=2;
      w[47].state=0;
      w[48].state=0;
      w[49].state=0;
      w[50].state=0;
      w[51].state=1;
      w[52].state=1;
      w[53].state=1;
      w[54].state=1;
      w[55].state=0;
      w[56].state=0;
      w[57].state=2;
      w[58].state=1;
      w[59].state=1;
      w[60].state=1;
      w[61].state=1;
      w[62].state=0;
      w[63].state=0;
      w[64].state=1;
      w[65].state=0;
      w[66].state=0;
      w[67].state=1;
      w[68].state=0;
      w[69].state=0;
      w[70].state=0;
      w[71].state=0;
      w[72].state=0;
      w[73].state=0;
      w[74].state=2;
      w[75].state=0;
      w[76].state=0;
      w[77].state=1;
      w[78].state=0;
      w[79].state=0;
      w[80].state=1;
      w[81].state=0;
      w[82].state=2;
      w[83].state=2;
      w[84].state=0;
      w[85].state=0;
      w[86].state=0;
      w[87].state=0;
      w[88].state=0;
      w[89].state=2;
      w[90].state=2;
      w[91].state=0;
      w[92].state=2;
      w[93].state=2;
      w[94].state=1;
      w[95].state=1;
      w[96].state=1;
      w[97].state=0;
      w[98].state=0;
      w[99].state=0;
      w[100].state=0;
      w[101].state=0;
      w[102].state=0;
      w[103].state=0;
      w[104].state=0;
      w[105].state=1;
      w[106].state=1;
      w[107].state=1;
      w[108].state=1;
      w[109].state=1;
      w[110].state=0;
      w[111].state=0;
      w[112].state=1;
      w[113].state=1;
      w[114].state=1;
      w[115].state=1;
      w[116].state=1;
      w[117].state=1;
      w[118].state=1;
      w[119].state=1;
      w[120].state=1;
      w[121].state=1;
      w[122].state=1;
      w[123].state=1;
      w[124].state=1;
      w[125].state=1;
      w[126].state=0;
      w[127].state=1;
      w[128].state=1;
      w[129].state=1;
      w[130].state=1;
      w[131].state=1;
      w[132].state=1;
      w[133].state=1;
      w[134].state=0;
      w[135].state=0;
      w[136].state=0;
      w[137].state=0;
      w[138].state=1;
      w[139].state=1;
      w[140].state=1;
      w[141].state=0;
      w[142].state=0;
      w[143].state=0;
      w[144].state=1;
      w[145].state=2;
      w[146].state=0;
      w[147].state=1;
      w[148].state=1;
      w[149].state=1;
      w[150].state=0;
      w[151].state=1;
      w[152].state=2;
      w[153].state=0;
      w[154].state=1;
      w[155].state=1;
      w[156].state=1;
      w[157].state=0;
      w[158].state=1;
      w[159].state=1;
      w[160].state=0;
      w[161].state=0;
      w[162].state=0;
      w[163].state=1;
      w[164].state=1;
      w[165].state=1;
      w[166].state=0;
      w[167].state=1;
      w[168].state=2;
      w[169].state=0;
      w[170].state=0;
      w[171].state=0;
      w[172].state=1;
      w[173].state=0;
      w[174].state=1;
      w[175].state=1;
      w[176].state=1;
      w[177].state=1;
      w[178].state=1;
      w[179].state=2;
      w[180].state=0;
      w[181].state=1;
      w[182].state=0;
      w[183].state=1;
      w[184].state=1;
      w[185].state=1;
      w[186].state=1;
      w[187].state=0;
      w[188].state=1;
      w[189].state=0;
      w[190].state=1;
      w[191].state=1;
      w[192].state=1;
      w[193].state=1;
      w[194].state=0;
      w[195].state=2;
      w[196].state=0;
      w[197].state=0;
      w[198].state=0;
      w[199].state=1;
      w[200].state=1;
      w[201].state=0;
      w[202].state=1;
      w[203].state=0;
      w[204].state=0;
      w[205].state=0;
      w[206].state=0;
      w[207].state=0;
      w[208].state=0;
      w[209].state=1;
      w[210].state=1;
      w[211].state=2;
      w[212].state=0;
      w[213].state=0;
      w[214].state=0;
      w[215].state=0;
      w[216].state=0;
      w[217].state=0;
      w[218].state=0;
      w[219].state=0;
      w[220].state=0;
      w[221].state=0;
      w[222].state=0;
      w[223].state=0;
      w[224].state=0;
      w[225].state=0;
      w[226].state=2;
      w[227].state=0;
      w[228].state=0;
      w[229].state=0;
      w[230].state=0;
      w[231].state=0;
      w[232].state=0;
      w[233].state=1;
      w[234].state=0;
      w[235].state=0;
      w[236].state=1;
      w[237].state=1;
      w[238].state=1;
      w[239].state=1;
      w[240].state=1;
      w[241].state=1;
      w[242].state=1;
      w[243].state=1;
      w[244].state=1;
      w[245].state=0;
      w[0].state=1;
      w[1].state=1;
      w[2].state=1;
      w[3].state=1;
      w[4].state=1;
      w[5].state=1;
      w[6].state=1;
      w[7].state=1;
      w[8].state=1;
      w[9].state=1;
      w[10].state=1;
      w[11].state=1;
      w[12].state=1;
      w[13].state=1;
      w[14].state=1;
      w[15].state=1;
      w[16].state=1;
      w[17].state=0;
      w[18].state=0;
      w[19].state=0;
      w[20].state=0;
      w[21].state=0;
      w[22].state=0;
      w[23].state=1;
      w[24].state=1;
      w[25].state=1;
      w[26].state=1;
      w[27].state=0;
      w[28].state=0;
      w[29].state=1;
      w[30].state=0;
      w[31].state=0;
      w[32].state=0;
      w[33].state=0;
      w[34].state=0;
      w[35].state=0;
      w[36].state=0;
      w[37].state=0;
      w[38].state=1;
      w[39].state=1;
      w[40].state=1;
      w[41].state=1;
      w[42].state=2;
      w[43].state=0;
      w[44].state=0;
      w[45].state=2;
      w[46].state=2;
      w[47].state=0;
      w[48].state=0;
      w[49].state=0;
      w[50].state=0;
      w[51].state=1;
      w[52].state=1;
      w[53].state=1;
      w[54].state=1;
      w[55].state=0;
      w[56].state=0;
      w[57].state=2;
      w[58].state=1;
      w[59].state=1;
      w[60].state=1;
      w[61].state=1;
      w[62].state=0;
      w[63].state=0;
      w[64].state=1;
      w[65].state=0;
      w[66].state=0;
      w[67].state=1;
      w[68].state=0;
      w[69].state=0;
      w[70].state=0;
      w[71].state=0;
      w[72].state=0;
      w[73].state=0;
      w[74].state=2;
      w[75].state=0;
      w[76].state=0;
      w[77].state=1;
      w[78].state=0;
      w[79].state=0;
      w[80].state=1;
      w[81].state=0;
      w[82].state=2;
      w[83].state=2;
      w[84].state=0;
      w[85].state=0;
      w[86].state=0;
      w[87].state=0;
      w[88].state=0;
      w[89].state=2;
      w[90].state=2;
      w[91].state=0;
      w[92].state=2;
      w[93].state=2;
      w[94].state=1;
      w[95].state=1;
      w[96].state=1;
      w[97].state=0;
      w[98].state=0;
      w[99].state=0;
      w[100].state=0;
      w[101].state=0;
      w[102].state=0;
      w[103].state=0;
      w[104].state=0;
      w[105].state=1;
      w[106].state=1;
      w[107].state=1;
      w[108].state=1;
      w[109].state=1;
      w[110].state=0;
      w[111].state=0;
      w[112].state=1;
      w[113].state=1;
      w[114].state=1;
      w[115].state=1;
      w[116].state=1;
      w[117].state=1;
      w[118].state=1;
      w[119].state=1;
      w[120].state=1;
      w[121].state=1;
      w[122].state=1;
      w[123].state=1;
      w[124].state=1;
      w[125].state=1;
      w[126].state=0;
      w[127].state=1;
      w[128].state=1;
      w[129].state=1;
      w[130].state=1;
      w[131].state=1;
      w[132].state=1;
      w[133].state=1;
      w[134].state=0;
      w[135].state=0;
      w[136].state=0;
      w[137].state=0;
      w[138].state=1;
      w[139].state=1;
      w[140].state=1;
      w[141].state=0;
      w[142].state=0;
      w[143].state=0;
      w[144].state=1;
      w[145].state=2;
      w[146].state=0;
      w[147].state=1;
      w[148].state=1;
      w[149].state=1;
      w[150].state=0;
      w[151].state=1;
      w[152].state=2;
      w[153].state=0;
      w[154].state=1;
      w[155].state=1;
      w[156].state=1;
      w[157].state=0;
      w[158].state=1;
      w[159].state=1;
      w[160].state=0;
      w[161].state=0;
      w[162].state=0;
      w[163].state=1;
      w[164].state=1;
      w[165].state=1;
      w[166].state=0;
      w[167].state=1;
      w[168].state=2;
      w[169].state=0;
      w[170].state=0;
      w[171].state=0;
      w[172].state=1;
      w[173].state=0;
      w[174].state=1;
      w[175].state=1;
      w[176].state=1;
      w[177].state=1;
      w[178].state=1;
      w[179].state=2;
      w[180].state=0;
      w[181].state=1;
      w[182].state=0;
      w[183].state=1;
      w[184].state=1;
      w[185].state=1;
      w[186].state=1;
      w[187].state=0;
      w[188].state=1;
      w[189].state=0;
      w[190].state=1;
      w[191].state=1;
      w[192].state=1;
      w[193].state=1;
      w[194].state=0;
      w[195].state=2;
      w[196].state=0;
      w[197].state=0;
      w[198].state=0;
      w[199].state=1;
      w[200].state=1;
      w[201].state=0;
      w[202].state=1;
      w[203].state=0;
      w[204].state=0;
      w[205].state=0;
      w[206].state=0;
      w[207].state=0;
      w[208].state=0;
      w[209].state=1;
      w[210].state=1;
      w[211].state=2;
      w[212].state=0;
      w[213].state=0;
      w[214].state=0;
      w[215].state=0;
      w[216].state=0;
      w[217].state=0;
      w[218].state=0;
      w[219].state=0;
      w[220].state=0;
      w[221].state=0;
      w[222].state=0;
      w[223].state=0;
      w[224].state=0;
      w[225].state=0;
      w[226].state=2;
      w[227].state=0;
      w[228].state=0;
      w[229].state=0;
      w[230].state=0;
      w[231].state=0;
      w[232].state=0;
      w[233].state=1;
      w[234].state=0;
      w[235].state=0;
      w[236].state=1;
      w[237].state=1;
      w[238].state=1;
      w[239].state=1;
      w[240].state=1;
      w[241].state=1;
      w[242].state=1;
      w[243].state=1;
      w[244].state=1;
      w[245].state=0;
    }
  }
}

//Life States
int STATE;
int MOVING = 0;
int EXPANDING = 1;
int SHRINKING = 2;
int DEAD = 3;
int STARTING_SIZE = 50;
int MAX_SIZE = 400;
int colorTransparency;
int BOOM_SPEED = 20;
int SHRINK_SPEED = 40;
int EXPLOSION_DELAY = 30; //Frames per second, computer processes 60 fps; so, 30 = .5 second delay
int Brightness = 150;

class PacMan {
  //Location/Pac-Shape
  int xcor, ycor;
  float colorPac, colorPac2, colorPac3;
  float mouthDegreeStart, mouthDegreeEnd;
  float PacWidth;
  float PacHeight;

  //Animation Frames/Delay;
  int mouthOpen;
  int mouthOpenCounter = 0;

  //Health Level to be put on Health Bar
  int health; 

  //Dead?
  boolean Dead;

  //Directions Allowed for Pac
  boolean right = true;
  boolean left = true;
  boolean down = true;
  boolean up = true;

  PacMan() {
    //Initialization of Mouth Angles
    mouthDegreeStart = (PI/5);
    mouthDegreeEnd = (2*PI - PI/5);

    //Location of Initial Pac-Man
    //    xcor = sizex/2; 
    //    ycor = sizey/2 - sizey/100;
    xcor = SPACE;
    ycor = SPACE + SPACE/2;

    PacWidth = width/32;
    PacHeight = PacWidth;

    //Color of Pac-Man
    colorPac = random(105) + Brightness;
    colorPac2 = random(105) + Brightness;
    colorPac3 = random(105) + Brightness;
    colorTransparency = 255;
    mouthOpen = 0;

    //Life Proficiency
    Dead = false;
    health = 1000;

    STATE = MOVING;
  }

  public void draw() {
    if (STATE != DEAD) {
      if (STATE == EXPANDING || STATE == SHRINKING) {
        explode();
      }

      winCheck();

      //Make Entire Pac-Man
      fill(colorPac, colorPac2, colorPac3, colorTransparency);
      noStroke();

      //Animate Mouth Open/Close
      if (mouthOpen == 0) {
        arc(xcor, ycor, PacWidth, PacHeight, mouthDegreeStart, mouthDegreeEnd);
        mouthOpenCounter += 1;
      } else if (mouthOpen == 1) {
        arc(xcor, ycor, PacWidth, PacHeight, mouthDegreeStart - PI/8, mouthDegreeEnd + PI/8);
        mouthOpenCounter += 1;
        //    } else if (mouthOpen == 2) {
        //      ellipse(xcor, ycor, PacWidth, PacHeight);
        //      mouthOpenCounter += 1;
      } else if (mouthOpen == 2) {
        arc(xcor, ycor, PacWidth, PacHeight, mouthDegreeStart - PI/8, mouthDegreeEnd + PI/8);
        mouthOpenCounter += 1;
        mouthOpen = 0;
      }
    } else if (STATE == DEAD) {
      die();
    }
  }

  public void die() {
    //Set Color to Black
    colorPac = 0;
    colorPac2 = 0;
    colorPac3 = 0;

    //Stop Animating
    mouthOpen = 10;

    screen=6;
  }

  public void explode() {
    //Explode
    if (STATE == EXPANDING) {
      colorTransparency = 100;
      if (PacWidth < MAX_SIZE) {
        PacWidth += BOOM_SPEED;
        PacHeight += BOOM_SPEED;
      }
      if (PacWidth >= MAX_SIZE || PacHeight >= MAX_SIZE) {
        STATE = SHRINKING;
      }
    }

    if (STATE == SHRINKING) {
      if (EXPLOSION_DELAY > 0) {
        EXPLOSION_DELAY -= 1;
        PacWidth += SHRINK_SPEED; //Cancels Out to make Freeze Effect
        PacHeight += SHRINK_SPEED;
        //Makes Eye
        fill(random(255), random(255), random(255));
        ellipse(xcor - width/40, ycor - height/20, PacWidth/5, PacHeight/5);
      }
      if (PacWidth >= 0) {
        PacWidth -= SHRINK_SPEED;
        PacHeight -= SHRINK_SPEED;
      } else {
        STATE = DEAD;
      }
    }
  }
//  boolean canMove(int k) {
//    for (Wall wall : w) {
//      if (wall.block(k)) {
//        print(false);
//        return false;
//      }
//    } 
//    print(true);
//    return true;
//  }

  public void winCheck() {
    if (xcor>=width-SPACE && ycor>=8*SPACE && ycor<=9*SPACE) {
      screen=5;
    }
  }

  public boolean canMove(int k) {
    //    for (Wall wall : w) {
    //      if (wall.block(k)) {
    //        print(false);
    //        return false;
    //      }
    //    } 
    //    print(true);
    PImage frame = get();
    for (int i = 0; i < 6; i++) {      
      if (k == UP) {
        int c = frame.get(xcor, (int)(ycor - PacWidth - i));
        if (c == color(255, 0, 0) || c == color(0, 0, 255))
          return false;
      } else if (k == DOWN) {  
        int c = frame.get(xcor, (int)(ycor + PacWidth - i));
        if (c == color(255, 0, 0) || c == color(0, 0, 255))
          return false;
      } else if (k == RIGHT) {
        int c = frame.get((int)(xcor + PacWidth + i), ycor);
        if (c == color(255, 0, 0) || c == color(0, 0, 255))
          return false;
      } else if (k == LEFT) {
        int c = frame.get((int)(xcor - PacWidth - i), ycor);
        if (c == color(255, 0, 0) || c == color(0, 0, 255))
          return false;
      }
    }
    return true;
  }
}

class Spaceship {
  float xcor, ycor, xspeed, yspeed;
  PImage img;

  Spaceship () {
    xcor   = 45*width/80;
    ycor   = 65*width/80;
    xspeed = width/160;
    yspeed = 0;
  }
  public void draw () {
    img = loadImage("Invader.png");
    image (img, xcor, ycor - width/8, width/5, width/5);
    xcor += xspeed;
    if (xcor < width/16 || xcor > 15*width/16) {
      xspeed *= -1;
    }
  }
}
class Wall {
  //instance variables
  float x1, y1;
  float x2, y2;
  int state;
  int c;
  boolean selected;

  //constructor method
  Wall(float X1, float Y1, float X2, float Y2) {
    state=INVISIBLE;
    c=color(0, 0, 0, 0);
    x1=X1;
    y1=Y1;
    x2=X2;
    y2=Y2;
    selected=false;
  }


  //other methods

  public void setColor() {
    if (state==INVISIBLE) {
      noStroke();//sets color transparent
    } else if (state==REGULAR) {
      stroke(color(0, 0, 255, 255));//sets color blue
    } else if (state==BULLETPROOF) {
      stroke(color(255, 0, 0, 255));//sets color red
    }
  }

  public boolean select() {
    if (x1==x2) {
      return (mouseX>=x1-8 && mouseX<=x1+8 && mouseY>=y1+10 && mouseY<=y2-10);
    } else if (y1==y2) {
      return (mouseX>=x1+10 && mouseX<=x2-10 && mouseY>=y1-8 && mouseY<=y1+8);
    } else {
      return false;
    }
  }

  public void selected() {
    if (select()) {
      state=(state+1)%3;
    }
  }

  //  void borders(){
  //    if ( ((y1==SPACE && y2==SPACE) || (y1==5*height/6-SPACE && y2==5*height/6-SPACE)) || (x1==SPACE && x2==SPACE) || (x1==width-SPACE && x2==width-SPACE)){
  //      state=REGULAR;
  //    }
  //    if ( (x1==SPACE && x2== SPACE && y1== SPACE && y2== 2*SPACE)||(x1==width-SPACE && x2==width-SPACE && y1== 5*(height/6)-2*SPACE && y2== 5*(height/6)-SPACE) ){
  //      state=INVISIBLE;
  //    }
  //  }


  public void draw() {

    //initializes states
    //border walls should be regular
    //borders();

    setColor();
    strokeWeight(5);
    line(x1, y1, x2, y2);
  }

//  boolean pacNext(Pac Tod) {
//    if (Tod.xcor + Tod.PacWidth + 5 == 0)
//      return true;
//    return false;
//  }

  public boolean block(int k) {
    int x = Bob.xcor;
    int y = Bob.ycor;
    if (state == INVISIBLE) 
      return false;
//    if (dist(x, y, x1 + x2 / 2.0, y1 + y2 / 2.0) > SPACE * 2) {
//      println("not close enough");
//      return false;   
//    }
    //horizontal
    if (y1 == y2) {
      boolean withinX = min(x1,x2) <= x && max(x1,x2) >= x;
      //print(withinX);
      if (k == UP) {
        //print(y - y1 <= Bob.PacWidth /2 && y - y1 < 0 && withinX);
        return y - y1 <= Bob.PacWidth /2 && y - y1 < 0 && withinX;
      }
      if (k == DOWN) {
        //print(y1 - y <= Bob.PacWidth / 2 && y1 - y < 0 && withinX);
        return y1 - y <= Bob.PacWidth / 2 && y1 - y < 0 && withinX;
      }
    }
    //vertical
    else if (x1 == x2) {
      boolean withinY = min(y1,y2) <= y && max(y1,y2) >= y;
      //print(withinY);
      if (k == LEFT) {
        //print(x - x1 <= Bob.PacWidth / 2 && x - x1 < 0 && withinY);
        return x - x1 <= Bob.PacWidth / 2 && x - x1 < 0 && withinY;
      }
      if (k == RIGHT) {
        //print(x1 - x <= Bob.PacWidth / 2 && x1 - x < 0 && withinY);
        return x1 - x <= Bob.PacWidth / 2 && x1 - x < 0 && withinY;
      }
    }
    return false;
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Final" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
