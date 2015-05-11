class Health {
  void draw() {
    noStroke();
    textAlign(CENTER,CENTER);
    fill(255, 255, 255, 25);
    rect(570, 25, 210, 20);
    fill(#00DE26);
    rect(570, 25, 210, 20);
    fill(#1EE3E1);
    textSize(16);
    text("100%", 570+210/2, 32);
    fill(255, 255, 255, 15);
    rect(500, 25, 59, 20);
    fill(#DE0000);
    textSize(24);
    text("RESET", 500+(59/2), 32);
  }
}
