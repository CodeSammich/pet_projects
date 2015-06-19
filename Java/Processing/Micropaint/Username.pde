import java.io.*;
import java.io.IOException;

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
  
 void mergeSort(double[] A) {// sorts array
        if (A.length > 1) {
            int q = A.length/2;

            double[] leftArray = Arrays.copyOfRange(A, 0, q);
            double[] rightArray = Arrays.copyOfRange(A,q,A.length);

            mergeSort(leftArray);
            mergeSort(rightArray);

            merge(A,leftArray,rightArray);
        }
    }

   void merge(double[] a, double[] l, double[] r) {
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


