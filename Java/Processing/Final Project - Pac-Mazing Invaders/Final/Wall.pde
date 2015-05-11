class Wall {
  //instance variables
  float x1, y1;
  float x2, y2;
  int state;
  color c;
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

  void setColor() {
    if (state==INVISIBLE) {
      noStroke();//sets color transparent
    } else if (state==REGULAR) {
      stroke(color(0, 0, 255, 255));//sets color blue
    } else if (state==BULLETPROOF) {
      stroke(color(255, 0, 0, 255));//sets color red
    }
  }

  boolean select() {
    if (x1==x2) {
      return (mouseX>=x1-8 && mouseX<=x1+8 && mouseY>=y1+10 && mouseY<=y2-10);
    } else if (y1==y2) {
      return (mouseX>=x1+10 && mouseX<=x2-10 && mouseY>=y1-8 && mouseY<=y1+8);
    } else {
      return false;
    }
  }

  void selected() {
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


  void draw() {

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

  boolean block(int k) {
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

