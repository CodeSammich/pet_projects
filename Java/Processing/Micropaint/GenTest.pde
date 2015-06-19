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
 
 float grade() { //Returns total grade of drawing
   grade = 0;
  // System.out.println(newRed.size());
   for(int i = 0; i < newRed.size(); i++) 
      grade += comparePixel(i);
   grade = 100* ((float)grade / (float)(newRed.size())); //Gives a grade of out 100
   return grade;
 }
  
  //Should return 1 or 0, to get a total number of points
 int comparePixel(int pixelNumber) { //Pixel number of original and new Colors arrays, no colors should be missing
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
 
 String evaluate() {
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
