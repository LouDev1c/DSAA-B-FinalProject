package Project;

import edu.princeton.cs.algs4.StdOut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.ArrayList;

import static Project.Main.*;
import static Project.ARAStar.findLocation;

public class MainJFrame extends JFrame implements ActionListener {

    ARAStar araStar;

    int m = 0;
    int l = 0;
    JTextField showTime;
    private char[][] chessBoard;
    private int numOfRow;
    private int numOfCol;
    private int width;
    private int height;


    public MainJFrame(ARAStar aStar, int width, int height, char[][] chessBoard, int row, int col) {

        this.araStar = aStar;
        this.width = width;
        this.height = height;

        this.numOfRow = row;
        this.numOfCol = col;
        this.chessBoard = chessBoard;

        this.paintView();
        this.initJFrame();

    }


    public void initJFrame() {
        this.setTitle("Explore the labyrinth");

        this.setSize(width, height);

        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setLayout(null);

        this.setVisible(true);
    }

    public void paintView() {
        int shu = 100;
        int heng = 75;
        this.setLayout(null);
        this.getContentPane().removeAll();

        Font fnt = new Font("Times New Roman", Font.BOLD + Font.ITALIC, 23);
        JButton JB_next = new JButton("Go");
        JB_next.setBorderPainted(false);
        JB_next.setBorder(BorderFactory.createRaisedBevelBorder());
        JB_next.setBackground(new Color(136, 72, 37));
        JB_next.addActionListener(this);
        JB_next.setLocation(250, 20);
        JB_next.setSize(100, 50);
        JB_next.setFont(fnt);
        JB_next.setForeground(new Color(224, 164, 102));
        this.getContentPane().add(JB_next);

        Font fnt1 = new Font("Times New Roman", Font.BOLD + Font.ITALIC, 20);
        int a;
        if (!value_of_e1_A.isEmpty()) {
            if (l == 0) {
                a = Main.a;
                l++;
            } else {
                a = value_of_e1_A.dequeue();
            }

            this.showTime = new JFormattedTextField("Time:" + a);
        } else {
            this.showTime = new JFormattedTextField("Final Path");
        }

        showTime.setBorder(BorderFactory.createRaisedBevelBorder());
        showTime.setBackground(new Color(136, 72, 37));
        showTime.setLocation(500, 20);
        showTime.setSize(100, 50);
        showTime.setFont(fnt1);
        showTime.setForeground(new Color(246, 159, 13));
        this.getContentPane().add(showTime);

        int w = (width - 150) / numOfCol;
        int h = (height - 150) / numOfRow;
        for (int i = 0; i < numOfRow; i++) {
            for (int j = 0; j < numOfCol; j++) {
                JLabel numLabel = new JLabel("", JLabel.CENTER);
                if (chessBoard[i][j] == 'A') {
                    numLabel.setBackground(new Color(0, 35, 255, 255));
                } else if (chessBoard[i][j] == 'N') {
                    numLabel.setBackground(new Color(255, 0, 0, 219));
                } else if (chessBoard[i][j] == 'W') {
                    numLabel.setBackground(new Color(3, 0, 0, 255));
                } else {
                    numLabel.setBackground(new Color(190, 234, 209));
                }
                numLabel.setOpaque(true);
                numLabel.setBounds(heng +  w* j, shu + h * i, w, h);
                this.getContentPane().add(numLabel);
            }
        }
        this.getContentPane().repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if (m < araStar.pathAll.length) {

                if (m != 0) {
                    for (int k = 0; k < araStar.pathAll[m - 1].size(); k++) {
                        int[] location = findLocation(chessBoard, araStar.pathAll[m - 1].get(k));
                        chessBoard[location[0]][location[1]] = 'R';
                    }
                }
                ArrayList<Integer> temp = new ArrayList<>();
                if ((!value_of_e1.isEmpty()) && (!place_of_wall1.isEmpty())) {
                    while (place_of_wall1.peek() >= value_of_e1.peek()) {
                        temp.add(place_of_wall1.dequeue());
                        temp.add(place_of_wall1.dequeue());
                        temp.add(place_of_wall1.dequeue());
                        if (place_of_wall1.isEmpty()) {
                            break;
                        }
                    }

                    value_of_e1.dequeue();

                    for (int k = 0; k < temp.size() / 3; k++) {
                        chessBoard[temp.get(3 * k + 1)][temp.get(3 * k + 2)] = 'A';
                    }
                    StdOut.println(araStar.pathAll[m].size());
                    for (int k = 0; k < araStar.pathAll[m].size(); k++) {
                        int[] location = findLocation(chessBoard, araStar.pathAll[m].get(k));
                        chessBoard[location[0]][location[1]] = 'N';
                        StdOut.print(location[0] + " " + location[1] + " ");
                    }
                    StdOut.println();
                    paintView();
                    m++;
                } else if (place_of_wall1.isEmpty() && (!value_of_e1.isEmpty())) {
                    value_of_e1.dequeue();
                    StdOut.println(araStar.pathAll[m].size());
                    for (int k = 0; k < araStar.pathAll[m].size(); k++) {
                        int[] location = findLocation(chessBoard, araStar.pathAll[m].get(k));
                        chessBoard[location[0]][location[1]] = 'N';
                        StdOut.print(location[0] + " " + location[1] + " ");
                    }
                    StdOut.println();
                    paintView();
                    m++;
                }

            } else if (m == araStar.pathAll.length) {
                for (int k = 0; k < araStar.pathAll[m - 1].size(); k++) {
                    int[] location = findLocation(chessBoard, araStar.pathAll[m - 1].get(k));
                    chessBoard[location[0]][location[1]] = 'R';
                }
                ArrayList<Integer> temp = new ArrayList<>();
                while (!place_of_wall1.isEmpty()) {
                    temp.add(place_of_wall1.dequeue());
                    temp.add(place_of_wall1.dequeue());
                    temp.add(place_of_wall1.dequeue());
                }
                for (int k = 0; k < temp.size() / 3; k++) {
                    chessBoard[temp.get(3 * k + 1)][temp.get(3 * k + 2)] = 'A';
                }

                StdOut.println(araStar.path1.size() + 1);
                araStar.path1.addFirst(0);
                for (int k = 0; k < araStar.path1.size(); k++) {
                    int[] location = findLocation(chessBoard, araStar.path1.get(k));
                    chessBoard[location[0]][location[1]] = 'N';
                    StdOut.print(location[0] + " " + location[1] + " ");
                }
                StdOut.println();
                paintView();
                m++;
            } else {
                System.exit(0);
            }

    }


}
