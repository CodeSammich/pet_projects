import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import javax.swing.*; 
import java.awt.*; 
import java.util.*; 
import java.io.*; 
import java.io.IOException; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Micropaint extends PApplet {

 



final static String CIRCLE = "circle.jpg";
final static String DOGE = "doge.jpg";
final static String DEGAS = "Degas.jpg";

int stage = 0;
String pic = "";
PImage img;
float red = 0;
float green = 0;
float blue = 0;
float brushsize = 5;

//Palette
int palettewidth = 150;

//Regular Brush 
boolean brush = true;
//float widthOfEllipseBrush = 10; float heightOfEllipseBrush = 10;

//Brush size slider
float startSliderSizeX = 10; float startSliderSizeY = 50;
float sliderWidth = 10; 
float sliderSizeLength = 127; float buttonSliderSizeX = (1/2)*sliderSizeLength + startSliderSizeX;
  
  public void mouseClicked() {
    if (stage == 0) {
      if (mouseX >= 20 && mouseX <= 420) {
        if (mouseY > 90 && mouseY < 240) {
          stage = 1;
          pic = CIRCLE;
        }
        else if (mouseY > 260 && mouseY < 410) {
          stage = 2;
          pic = DOGE;
        }
        else if (mouseY > 430 && mouseY < 580) {
          stage = 3;
          pic = DEGAS;
        }
      }
      println("Stage: " + stage);
    }
      
      img = loadImage(pic); 
      frame.setSize(2*img.width + palettewidth, img.height);
      background(255);
      image(img, img.width + palettewidth, 0, img.width, img.height);
      resetSidebar();
      //frameRate(9999);
      //loadPixels(); //into pixels[], use upgdatepixels after
      if (mouseX > palettewidth && mouseX < (palettewidth + img.width)) {
      if (brush) {
       fill(red, green, blue);
       ellipse(mouseX, mouseY, brushsize, brushsize);
      }
      }    
      if (stage != 0) {
     if ((mouseX > 40 && mouseX < 90) && (mouseY > 320 && mouseY <350)) {
       grades();
     }
    }
  }
    
  public void mouseDragged() {
    if ( 
    (mouseX < buttonSliderSizeX + 2 && mouseX > buttonSliderSizeX - 2
    && ( mouseX > startSliderSizeX && mouseX < startSliderSizeX + sliderSizeLength )
    && mouseY > startSliderSizeY + 5 && mouseY < startSliderSizeY - 5)
    ) {
      fill(0);
      ellipse(mouseX, mouseY, 10, 10);
    }
    if (mouseX > palettewidth && mouseX < (palettewidth + img.width)) {
      //frameRate(9999);
      if (brush) {
       fill(red, green, blue);
       ellipse(mouseX, mouseY, brushsize, brushsize);
      }
    }
  
     if (mouseX > startSliderSizeX && mouseX < startSliderSizeX + sliderSizeLength) {
       resetSidebar();
       if (mouseY > startSliderSizeY - 1 && mouseY < startSliderSizeY + sliderWidth + 1) 
         red = 2*(mouseX - startSliderSizeX);
       if (mouseY > startSliderSizeY + 40 - 1 && mouseY < startSliderSizeY + 40 + sliderWidth + 1)
         green = 2*(mouseX - startSliderSizeX);
       if (mouseY > startSliderSizeY + 80 - 1 && mouseY < startSliderSizeY + 80 + sliderWidth + 1)
         blue = 2* (mouseX - startSliderSizeX); 
       if (mouseY > 220 - sliderWidth && mouseY < 220)
         brushsize = (mouseX - startSliderSizeX)/10 + 1; 
       if (mouseY > 260 - sliderWidth && mouseY < 260) {
         brushsize = (mouseX - startSliderSizeX)/10 + 1;
         red = 255; green = 255; blue = 255;
       }
     } 
}
  
  public void grades() {//setup    
    loadPixels();
   GenTest g = new GenTest();
    //Status of Drawing; NEXT LEVEL BUTTON
    fill(200);
    rect(200, 100, 500, 300);
    PFont fgrade = createFont("Arial", 20, true);
    textFont(fgrade);
    fill(255);
    text("YOUR GRADE: " + g.grade(), 230, 200);
    
    PFont f0 = createFont("Arial", 20, true);
    textFont(f0);
    fill(255);
    text(g.evaluate(), 230, 300);
    println(g.grade());
    println(g.evaluate());
    strokeWeight(1);
    stroke(0);
    line(500, 100, 500, 400);
    
    PFont fscoretitle = createFont("Arial", 20, true);
    textFont(fscoretitle);
    fill(255);
    text("SCOREBOARD\n\tTop Three:", 525, 150);
    
    PFont fscores = createFont("Arial", 16, true);
    textFont(fscores);
    fill(255);
    text("1.\n\n2.\n\n3. " , 525, 225);
  }
  
  public void setup() {  
   size(400 + 40, 3*150 + 50 + 100);
  frame.setResizable(true);
  if (stage == 0) {
  background(255, 10, 20);
  PFont ftext = createFont("Arial", 40, true);
    textFont(ftext);
    fill(0);
    text("CHOOSE A LEVEL", 50, 50);
  for (int i = 0; i < 3; i++) {
    fill(255);
    rect(20, 90 + i*170, 400, 150);
    PFont flevel = createFont("Arial", 30, true);
    textFont(ftext);
    fill(0);
    text("Level " + (i + 1), 150, 90 + i*170 + 75);
    frameRate(9999);
  }
  }
    Username user = new Username();
    user.readFile();
    user.addToScoreBoard();
    user.writeFile(15.0f);
    //user.readFile();
    //user.addToScoreBoard();
  }

  public boolean isEraser() { 
    return (red == 255 && blue == 255 && green == 255);
  }

  public void resetSidebar() { 
    //setup sidebar
    noStroke();
    fill(70, 199, 199);
    rect(0, 0, palettewidth, height);
    
    //set up text boxes titles
    PFont f0 = createFont("Arial", 16, true);
    textFont(f0);
    fill(255);
    text("MicroPaint Palette", 10, 20);
    PFont f1 = createFont("Arial", 10, true);
    textFont(f1);
    fill(255);
    text("Red: " + red, 10, 40);
    PFont f2 = createFont("Arial", 10, true);
    textFont(f2);
    fill(255);
    text("Green: "+ green, 10, 80);
    PFont f3 = createFont("Arial", 10, true);
    textFont(f3);
    fill(255);
    text("Blue: " + blue, 10, 120);
    
    PFont f4 = createFont("Arial", 11, true);
    textFont(f4);
    fill(255);
    text("To turn off eraser, please \nread adjust the color slides.", 10, 280);
  
    //create color slides
    for (int i = 0; i < 3; i++) {
      if ( i == 0)
        fill(199, 70, 70); 
      else if (i == 1)
        fill(70, 199, 70);
      else 
        fill(70, 70, 199);
      strokeWeight(30);
      rect(startSliderSizeX, startSliderSizeY + i*40, startSliderSizeX + sliderSizeLength, sliderWidth);
      //ellipse(buttonSliderSizeX, startSliderSizeY + i*40, 10, 10);
    }
    //Collective color
    PFont f5 = createFont("Arial", 12, true);
    textFont(f5);
    fill(255);
    text("Color and Size:", 10, 160);
    if ( isEraser() ) {
      fill(0,0,0);
      ellipse(20, 175, 20, 20);
      rect(22, 165, palettewidth - 35, 20 );
    }
    else {
      fill(255, 255, 255);
      ellipse(20, 175, 20, 20);
      rect(22, 165, palettewidth - 35, 20 );
    }
    //NOTE: circle AT (X, Y) = (10, 170)
    //Width of brush
    PFont f6 = createFont("Arial", 12, true);
    textFont(f6);
    fill(255);
    if (! isEraser() ) {
      text("Brush Width: " + brushsize, 10, 200);
    }
    else {
      text("Brush Width: N/A", 10, 200);
    }
    fill(255, 255, 255);
    triangle(10, 220, 10 + sliderSizeLength, 220 - sliderWidth, 10 + sliderSizeLength, 220); 
    //Eraser
    PFont f7 = createFont("Arial", 12, true);
    textFont(f7);
    fill(255);
    if (red == 255 && blue == 255 && green == 255) {
      text("Eraser Size: " + brushsize, 10, 240);
    }
    else {
      text("Eraser Size: N/A", 10, 240);
    }
    fill(255, 255, 255);
    triangle(10, 260, 10 + sliderSizeLength, 260 - sliderWidth, 10 + sliderSizeLength, 260); 
    //ellipse(20, 280, 10, 10);
    
    fill(255, 10, 10);
    rect(40, 320, 50, 30);
    PFont fsubmit = createFont("Arial", 12, true);
    textFont(fsubmit);
    fill(255);
    text("Submit", 50, 340);
  }

  public void draw() {
    //collective color box: displays color!
    fill(red, green, blue);
    fill(red, green, blue);
    ellipse(20, 175, brushsize, brushsize);

  }


class GenTest {
 ArrayList<Float> originalRed;
 ArrayList<Float> originalGreen;
 ArrayList<Float> originalBlue;
 ArrayList<Float> newRed;
 ArrayList<Float> newGreen;
 ArrayList<Float> newBlue; 
 
 int rightStartSize = palettewidth + img.width; 
 
 int allowedDifference;
 float grade;
 
  GenTest() {
    originalRed = new ArrayList<Float>();
    originalGreen = new ArrayList<Float>();
    originalBlue = new ArrayList<Float>();
    newRed = new ArrayList<Float>();
    newGreen = new ArrayList<Float>();
    newBlue = new ArrayList<Float>();
 
   //Gets color values of screen, original and new image should be the same size****
   //Left Side
   for(int i = 0; i < height; i++)
     for(int j = palettewidth; j < rightStartSize; j++) {
       originalRed.add(red(pixels[i*width + j]));
       originalGreen.add(green(pixels[i*width + j]));
       originalBlue.add(blue(pixels[i*width + j]));
     }
     //Right Side
    for(int i = 0; i < height; i++)
     for(int j = rightStartSize; j < width; j++) {
       newRed.add(red(pixels[i*width + j]));
       newGreen.add(green(pixels[i*width + j]));
       newBlue.add(blue(pixels[i*width + j]));
     }

    grade = 0;     
    allowedDifference = 100; //Amount of leeway for coloring, SUBJECT TO CHANGE!!
 }
 
 public float grade() { //Returns total grade of drawing
   grade = 0;
  // System.out.println(newRed.size());
   for(int i = 0; i < newRed.size(); i++) 
      grade += comparePixel(i);
   grade = 100* ((float)grade / (float)(newRed.size())); //Gives a grade of out 100
   return grade;
 }
  
  //Should return 1 or 0, to get a total number of points
 public int comparePixel(int pixelNumber) { //Pixel number of original and new Colors arrays, no colors should be missing
  float totalDiff = 0;
  float redDiff = Math.abs(originalRed.get(pixelNumber) - newRed.get(pixelNumber));
  float greenDiff = Math.abs(originalGreen.get(pixelNumber) - newGreen.get(pixelNumber));
  float blueDiff =  Math.abs(originalBlue.get(pixelNumber) - newBlue.get(pixelNumber));
  totalDiff = redDiff + greenDiff + blueDiff;
  
  if(totalDiff < allowedDifference) {
    //System.out.print(1);
    return 1;
  }
  else {
   // System.out.print(0);
    return 0;
  }
 } 
 
 public String evaluate() {
   if(grade < 65)
     return "YOU ARE A FAILURE";
   else if(grade <= 75)
     return "YOU ARE PARTIAL FAILURE";
   else if(grade <= 88)
     return "STARTING NOT TO FAIL ";
   else if(grade < 93)
     return "SEMI-PASS ";
   else
     return "GOOD. ASIAN-PASS ";
 }
}



class Username {
  
  //Instance Variables
double[] scoreboard;
String[] lines;

//read file
  public void readFile() {//takes data from scores.txt and turns into string[] lines
    lines = loadStrings("scores.txt");
    println("readFile(): ");
    for (int i = 0; i < lines.length; i++) 
      println(lines[i]);
  }

  public void addToScoreBoard() {//tranfers data from String[] lines to double[] scoreboard
    scoreboard = new double[lines.length];
    println("addToScoreBoard(): ");
     for (int i = 0; i < lines.length; i++) {
      println("lines: " + lines[i]);
      println("scoreboard: " + scoreboard[i]);
      println(lines[i]);
      if(lines[i].length() > 0)
       scoreboard[i] = Float.parseFloat(lines[i]);
     }
  }
  
 public void mergeSort(double[] A) {// sorts array
        if (A.length > 1) {
            int q = A.length/2;

            double[] leftArray = Arrays.copyOfRange(A, 0, q);
            double[] rightArray = Arrays.copyOfRange(A,q,A.length);

            mergeSort(leftArray);
            mergeSort(rightArray);

            merge(A,leftArray,rightArray);
        }
    }

   public void merge(double[] a, double[] l, double[] r) {
        int totElem = l.length + r.length;
        //int[] a = new int[totElem];
        int i,li,ri;
        i = li = ri = 0;
        while ( i < totElem) {
            if ((li < l.length) && (ri<r.length)) {
                if (l[li] < r[ri]) {
                    a[i] = l[li];
                    i++;
                    li++;
                }
                else {
                    a[i] = r[ri];
                    i++;
                    ri++;
                }
            }
            else {
                if (li >= l.length) {
                    while (ri < r.length) {
                        a[i] = r[ri];
                        i++;
                        ri++;
                    }
                }
                if (ri >= r.length) {
                    while (li < l.length) {
                        a[i] = l[li];
                        li++;
                        i++;
                    }
                }
            }
        }
        //return a;

    }
    
  public void writeFile(double newscore) {//uses temp array to transfer new sorted data to lines
  println("writeFile(newscore): ");
    double[] newarray = new double[scoreboard.length + 1];
    for (int i = 0; i < scoreboard.length; i++)
      newarray[i] = scoreboard[i];
    newarray[scoreboard.length] = newscore;
    mergeSort(newarray);
    lines = new String[newarray.length];
    for (int i = 0; i < lines.length; i++) 
      lines[i] = "" + newarray[i];
    saveStrings("scores.txt", lines);
    for (int i = 0; i < lines.length; i++) 
    println("Lines " + i + ": " + lines[i]);
  }
    /*try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(...somefilename..., true));
      writer.write(...somedata...);
      writer.flush();
      writer.close();
     } catch (IOException ioe) {
    println("error: " + ioe);
     }
    */
  }


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Micropaint" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
