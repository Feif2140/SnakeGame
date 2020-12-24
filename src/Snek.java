import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static java.awt.Font.BOLD;

public class Snek extends JFrame implements KeyListener, ActionListener {
    private JLabel scoreDisplay;
    private int score, xDir, yDir, xPos, yPos, length, dir;
    private boolean gameOver;
    private ArrayList<Point> pointList = new ArrayList<>();
    private Point food;
    private Timer t;

    public Snek() {

        scoreDisplay = new JLabel("Score: " + score);
        scoreDisplay.setBorder(new LineBorder(Color.red, 2));

        JFrame f = new JFrame();
        f.setBounds(0, 0, 800, 800);

        t = new Timer(120, this);
        t.start();
        pointList.add(new Point(xPos, yPos));
        gameOver = false;
        score = 0;
        xDir = 0;
        yDir = 0;
        xPos = 400;
        yPos = 400;
        length = 1;
        dir = 0;
        food();

        setFocusable(true);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0, 0, 800, 800);

        g.setColor(Color.yellow);
        g.fillRect(food.getX(), food.getY(), 25, 25);

        g.setColor(Color.red);
        for (int i = 0; i < pointList.size(); i++)
            g.fillRect(pointList.get(i).getX(), pointList.get(i).getY(), 25, 25);

        g.setColor(Color.red);
        g.drawString("Score: " + score, 700, 100);

        if (gameOver) {
            g.setColor(Color.white);
            g.drawString("press r to replay", 450,350);
            g.setFont(new Font("default", BOLD, 40));
            g.drawString("GAMEOVER!!! SCORE: " + score, 150, 400);
        }

    }

    public void food() {
        int foodX, foodY;
        boolean works = true;
        foodX = (int) (Math.random() * 775);
        foodY = (int) (Math.random() * 750 + 25);
        foodX = foodX + 25 - foodX % 25;
        foodY = foodY + 25 - foodY % 25;
        for (int i = 0; i < pointList.size(); i++) {
            if (foodX == pointList.get(i).getX() && foodY == pointList.get(i).getY()) {
                works = false;
                break;
            }
        }
        if (works)
            food = new Point(foodX, foodY);
        else
            food();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver && e.getKeyCode() == KeyEvent.VK_R){
            t.start();
            gameOver = false;
            score = 0;
            xDir = 0;
            yDir = 0;
            xPos = 400;
            yPos = 400;
            length = 1;
            dir = 0;
            pointList.clear();
            pointList.add(new Point(xPos, yPos));
            food();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && dir != 1) {
            xDir = 0;
            yDir = -25;
            dir = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && dir != 1) {
            xDir = 0;
            yDir = 25;
            dir = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && dir != -1) {
            xDir = -25;
            yDir = 0;
            dir = -1;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && dir != -1) {
            xDir = 25;
            yDir = 0;
            dir = -1;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (!gameOver) {
            if (pointList.size() > 1) {
                System.out.println(pointList.size());
                for (int i = 0; i < pointList.size() - 1; i++) {
                    for (int j = i + 1; j < pointList.size(); j++) {
                        int x1 = pointList.get(i).getX();
                        int y1 = pointList.get(i).getY();
                        int x2 = pointList.get(j).getX();
                        int y2 = pointList.get(j).getY();
                        if (x1 == x2 && y1 == y2) {
                            gameOver = true;
                        }
                    }
                }
            }

            if (xPos + xDir < 0 || xPos + xDir >= 800 || yPos + yDir < 25 || yPos + yDir >= 800)
                gameOver = true;
        }
        if (!gameOver) {

            xPos += xDir;
            yPos += yDir;
            pointList.add(new Point(xPos, yPos));

            if (food.getX() == xPos && food.getY() == yPos) {
                length++;
                score+=5;
                food();
            }

            if (pointList.size() > length)
                pointList.remove(0);

        }

        if (gameOver) {
            xDir = 0;
            yDir = 0;
            System.out.println("(" + xPos + "," + yPos + ")");
            t.stop();
        }

        repaint();
    }
}

