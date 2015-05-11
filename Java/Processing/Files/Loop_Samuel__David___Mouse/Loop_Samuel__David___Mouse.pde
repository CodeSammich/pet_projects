

void setup(){
  size(1612, 1235);
  PImage img;
  img = loadImage("treasure-map1.jpg");
  background(img);
}  

void names(String name1, String name2){
  if ((mouseX < (width/2)) && (mouseY < (height/2))){
    fill(255, 255, 0);
    textSize(50);
    text(name1, random(width/2), random(height/2)); //Generates first name randomly in the second quadrant
    text(name2, random(width/2), random(height/2)); //Generates second name randomly in the second quadrant
    fill(255, 149, 0);
    textSize(100);
    text(name1, 3*width/16, height/4 - 100); //Generates Solid Name for David in Center of First box
    text(name2, 3*width/16, height/4 + 100); //Generates Solid Name for Sam in Center of First Box
  }
}

void commonInts(){
  if ((mouseX > (width/2)) && (mouseY < (height/2))) {
    fill(0);
    textSize(50);
    textSize(100);
    text("Common Interests", (4*width)/8, (2*height)/16); //Title Text
    
    fill(16, 0, 99);
    text("Social Networking", (5*width/8), (3*height / 16)); //Load textboxes
    text("Pizza", (5*width/8), (4*height/16));
    text("Big Bang Theory", (5*width/8), (5*height/16)); 
        
    PImage goldImg;
    goldImg = loadImage("gold.jpg"); //Gold Image
    image(goldImg, (5*width)/8, (6*height)/16); //Load gold image into Common Interests Box
 }
}

void uncommonInts(){
  if ((mouseX < (width/2)) && (mouseY > (height/2))) {
    fill(0);
    textSize(50);
    text("Uncommon Interests", (3*width)/16, (9*height)/16); //Title Text
    
    fill(16, 0, 99);
    text("Samuel - Chess", (3*width/16), (11*height / 16)); //Load textboxes
    text("David - Handball", (3*width/16), (12*height/16));
        
    PImage peterPan;
    peterPan = loadImage("peterPan.jpg");
    image(peterPan, (3*width/16), (13*height/16)); //Peter Pan Image 
  }
}

void randomMessage(){
  if ((mouseX > (width/2)) && (mouseY > (height/2))) {
    fill(0);
    textSize(100);
    text("Happy Treasure Hunting!", width/2, height/2, width/2, height/2);
  }
}

void draw(){
  PImage img;
  img = loadImage("treasure-map1.jpg");
  background(img);
  names("David", "Samuel"); //Generates both Sam and David's names in first box
  commonInts();  
  uncommonInts();
  randomMessage();
}
