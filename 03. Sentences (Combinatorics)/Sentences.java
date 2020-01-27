package edu.fmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static edu.fmi.Combinatorics.*;

public class Sentences {
    private JPanel pnlBa;
    private JTextPane txtEnteredWords;
    private JButton btnGenerate;
    private JTextPane txtResult;
    private JLabel lblTextForExecise;
    private JLabel lblResultInfo;
    private JPanel pnlBase;
    private JLabel lblNumOfWords;
    private JTextField txtNumOfWords;


    public Sentences() {
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] words = txtEnteredWords.getText().split("\\W+");
                int n = words.length;
                int r = Integer.parseInt(txtNumOfWords.getText());
               CombinationWithoutRepetition(words, n, r);

               String res = "";

                for (int i = 0; i < getC().size(); i++) {
                    res+=String.valueOf(getC().toArray()[i]);
                    res+=".\n";


                }
                if(!txtResult.getText().isEmpty()){
                    txtResult.setText(res);
                }else{
                    txtResult.setText("");
                    txtResult.setText(res);
                }
            }
        });
    }

    public static void main(String[]args){
            JFrame frame=new JFrame("Register Form");
            frame.setContentPane(new Sentences().pnlBase);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(600, 500));
            frame.pack();
            frame.setVisible(true);
        }
}



