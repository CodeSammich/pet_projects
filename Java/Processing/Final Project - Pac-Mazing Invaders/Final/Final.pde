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

void setup() {
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

void drawButtons() {
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

void screen0() {
  //start screen
  ui.draw();
}

void screen1() {
  //level selector screen
  drawButtons();
}

void screen2() {
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

void screen3() {

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

void saveStates() {
  for (int i = 0; i < w.length; i++) {
    states[i] = w[i].state;
  }
  load=true;
}

void loadMaze() {
  //loads the maze that was customuzed
  for (int i=0; i<246; i++) {
    w[i].state=states[i];
  }
}

void drawButtons3() {
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
void screen4() {
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

void screen5() {
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

void screen6() {
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

void draw() {
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

void mouseClicked() {
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
void keyPressed() {
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

