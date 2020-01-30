package edu.fmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class EightQueens {
    private JPanel pnlBase;

    private static int[][] solution;

    public static class MyGraphics extends JComponent {

        private static final long serialVersionUID = 1L;

        MyGraphics() {
            setPreferredSize(new Dimension(550, 400));
        }

        @Override
        public void paintComponent(Graphics g) {
            Color myColor = Color.RED;

            int i0 = 50;
            int i1 = 30;
            int i2 = 40;
            int i3 = 40;

            for (int i = 0; i < solution.length; i++) {
                for (int j = 0; j < solution[0].length; j++) {

                    switch (solution[i][j]) {
                        case 1:
                            myColor = Color.RED;
                            break;
                        default:
                            myColor = Color.ORANGE;
                            break;
                    }
                    g.setColor(myColor);
                    g.fillRect(i0, i1, i2, i3);

                    i0 += 41;
                }
                i0 = 50;
                i1 += 41;
            }
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("Мax must be greater than min!");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static void waitt(int ms){
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        TheEightQueens q = new TheEightQueens();
        q.findSolutions();
        ArrayList allSolutions = q.getAllSolutions();
        
		//Selected solution
        solution = (int[][]) allSolutions.get(1);

        final JFrame frame = new JFrame("The eight queens - Solutions");
        //Create jpanel

        MyGraphics panel = new MyGraphics();

        int numOfSolutions = allSolutions.size();
        String txt = String.format("In red color are present the queens. \nCount of all solutions of 'The eight queens' is %d.", numOfSolutions);

        //Jtext area for information
        JTextArea textarea = new JTextArea(txt);
        panel.add(textarea);
        frame.add(panel);
        
		//Jbutton to generate a solution
        JButton generate_solution =new JButton("Generate solution");
        generate_solution.setBounds(420,100,200,30);
        panel.add(generate_solution);
        generate_solution.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int randomNum = getRandomNumberInRange(0,91);
                solution = (int[][]) allSolutions.get(randomNum);
                panel.repaint();
            }
        });

        JButton generate_all_solutions =new JButton("Generate all solutions");
        generate_all_solutions.setBounds(420,200,200,30);
        panel.add(generate_all_solutions);

        generate_all_solutions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 try {
                    int counter = 0;
                    while (counter < numOfSolutions) {

                        solution = (int[][]) allSolutions.get(counter);
                        //pause for 1 sec
                        Thread.sleep(1000);
                        panel.paintComponent(panel.getGraphics());
                        counter++;
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            });


        frame.pack();
        frame.setSize(new Dimension(650,450));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
