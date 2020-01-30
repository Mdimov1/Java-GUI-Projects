package edu.fmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TreeForm {
    private JPanel pnlMain;
    private JTextField txtLevel;
    private JTextField txtCoeficient;
    private JLabel lblLevel;
    private JLabel lblCoefficient;
    private JPanel pnlCanvas;
    private JRadioButton rbtnSimple;
    private JRadioButton rbtnLeafs;
    private JButton btnReset;
    private JButton btnDraw;
    private JPanel pnlInputData;

    private int w;
    private int h;
    private Graphics g;
    private int choice = 1;
    private int level = 1;
    private int coef = 1;

    public TreeForm() {
        btnReset.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlCanvas.repaint();

                txtLevel.setText("");
                txtCoeficient.setText("");
            }
        });
        rbtnSimple.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 1;
                pnlCanvas.repaint();
            }
        });
        rbtnLeafs.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 2;
                pnlCanvas.repaint();

                Graphics2D g2d = (Graphics2D) g;
                //drawTriangleFractal(g2d, 50, 50,50,60,60,1);
            }
        });
        btnDraw.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                readData();
                
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(Color.GREEN);

                //Style
                Stroke lineStyle = new BasicStroke(3);
                g2d.setStroke(lineStyle);

                //Start values of the parameters
                double x0 = w / 2;
                double y0 = h;
                double angle = 90;

                //Choice
                switch (choice) {
                    case 1:
                        drawTree_1(g2d, coef, x0, y0, angle, level);
                        break;
                    case 2:
                        drawFractal(g2d);
                        break;
                }
            }
        });
    }

    //http://rosettacode.org/wiki/Fractal_tree#Java
    private void drawTree_1(Graphics2D g2d, int koef, double x1, double y1, double angle, int level) {

        if (level == 0) {
            return;
        }

        Color c;
        Stroke lineStyle;

        if (level == 1) {
            c = Color.GREEN;
            lineStyle = new BasicStroke(3);
        } else {
            c = new Color(128, 0, 0);
            lineStyle = new BasicStroke(level);
        }
        g2d.setColor(c);
        g2d.setStroke(lineStyle);

        double x2 = x1 + Math.cos(Math.toRadians(angle)) * level * koef;
        double y2 = y1 - Math.sin(Math.toRadians(angle)) * level * koef;
        g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        drawTree_1(g2d, koef, x2, y2, angle - 20, level - 1);
        drawTree_1(g2d, koef, x2, y2, angle + 30, level - 1);
    }

    
    //My Fractal
    
    private void drawSidesOfFractal(Graphics2D g2d, int length, double x1, double y1, double x2, double y2, int angle, int level) {

        if (level == 0){
            return;
        }

        //Coordinates of the first point
        double p1X = (x1+x2)/2;
        double p1Y = (y1+y2)/2;

        //Coordinates of the second point
        double p2X = p1X + length * Math.sin(Math.toRadians(60+angle));
        double p2Y = p1Y + length * Math.cos(Math.toRadians(60+angle));

        //Coordinates of the third point
        double p3X = p1X + length * Math.sin(Math.toRadians(120+angle));
        double p3Y = p1Y + length * Math.cos(Math.toRadians(120+angle));

        g2d.drawLine((int) p1X, (int) p1Y, (int) p2X, (int) p2Y);
        g2d.drawLine((int) p2X, (int) p2Y, (int) p3X, (int) p3Y);
        g2d.drawLine((int) p3X, (int) p3Y, (int) p1X, (int) p1Y);

        drawSidesOfFractal(g2d, length/2, p2X, p2Y, p3X, p3Y, angle, level-1);
    }

    private void drawFractal(Graphics2D g2d){
        double width = w;
        double height = h;

        double centerX = width/2;
        double centerY = height/4;

        //Size of the sides of the triangle
        double length = coef;

        //Center triangle positioning
        //Pythagorean theorem
        double s = Math.sqrt(Math.pow(length, 2) - Math.pow(length/2, 2));

        //Center triangle
        //First side
        double x1 = centerX;
        double y1 = centerY + (centerY - s*2/3);

        //Second side
        double x2 = centerX + length/2;
        double y2 = centerY + (centerY + s*1/3);

        //Third side
        double x3 = centerX-length/2;
        double y3 = centerY + (centerY + s*1/3);

        g.setColor(Color.GREEN);
        //Right side
        g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

        //left side
        g2d.drawLine((int) x3, (int) y3, (int) x1, (int) y1);

        //Down side
        g2d.drawLine((int) x2, (int) y2, (int) x3, (int) y3);

        drawSidesOfFractal(g2d, coef/2, x1, y1, x2, y2, 30, level);
        drawSidesOfFractal(g2d, coef/2, x3, y3, x1, y1, -210, level);
        drawSidesOfFractal(g2d, coef/2, x2, y2, x3, y3, -90, level);
    }

    private void readData(){
        try {
            String strLevel = txtLevel.getText();
            level = Integer.parseInt(strLevel);
            String strKoef = txtCoeficient.getText();
            coef = Integer.parseInt(strKoef);

            g = pnlCanvas.getGraphics();
            w = pnlCanvas.getWidth();
            h = pnlCanvas.getHeight();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null,
                    "Enter a natural number!",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);
            txtLevel.setText("");
            txtCoeficient.setText("");
            txtLevel.grabFocus();
        }
    }

    public static void main(String[] args) {
        JFrame treeFrame = new JFrame("Fractals");
        treeFrame.setContentPane(new TreeForm().pnlMain);
        treeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        treeFrame.pack();
        treeFrame.setSize(600, 400);
        treeFrame.setVisible(true);
    }
}
