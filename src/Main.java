import javax.swing.*;

public class Main {

    public static void main(String[] args){
        JFrame f = new Snek();
        f.setBounds(0,0,809,809);
        f.setTitle("Snake Game");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


