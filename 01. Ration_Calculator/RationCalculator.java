package edu.fmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RationCalculator {
    private JPanel pnlBase;
    private JTextField txtRationNumber;
    private JButton btnNormalization;
    private JButton btnPlus;
    private JButton btnDivision;
    private JButton btnMinus;
    private JLabel lblNum1;
    private JButton btnMultiply;
    private JButton btnReciprocal;
    private JPanel pnlResult;
    private JTextPane txtRes;
    private JButton btnClear;
    private JButton btnResult;
    private JLabel lblResult;

    private Ration lastRation;
    private Ration newRation;
    private final String reg = "[-*[ @#&!,.]*/*//**+*=*]";

    public RationCalculator() {
        btnNormalization.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {

                    if(txtRationNumber.getText().equals("") && !txtRes.getText().equals("")){
                        readLastRation();
                        txtRes.setText(lastRation.ordinaryType().toString());
                    }else{
                        readNewRation();
                        txtRes.setText(newRation.ordinaryType().toString());
                    }
                    txtRationNumber.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (!txtRes.getText().equals("")) {
                        readLastRation();
                        readNewRation();

                        txtRes.setText(lastRation + " + " + newRation + " = " + Ration.sum(lastRation, newRation).toString()+" +");
                        txtRationNumber.setText("");
                    } else {
                        readNewRation();
                        txtRes.setText(newRation.toString() + " +");
                        txtRationNumber.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                try {
                    if (!txtRes.getText().equals("")) {
                        readLastRation();
                        readNewRation();

                        txtRes.setText(lastRation + " - " + newRation + " = " + Ration.diff(lastRation, newRation).toString()+" -");
                        txtRationNumber.setText("");
                    } else {
                        readNewRation();
                        txtRes.setText(newRation.toString() + " -");
                        txtRationNumber.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnMultiply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (!txtRes.getText().equals("")) {
                        readLastRation();
                        readNewRation();

                        txtRes.setText(lastRation + " x " + newRation + " = " + Ration.multiply(lastRation, newRation).toString()+" x");
                        txtRationNumber.setText("");
                    } else {
                        readNewRation();
                        txtRes.setText(newRation.toString() + " x");
                        txtRationNumber.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnDivision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (!txtRes.getText().equals("")) {
                        readLastRation();
                        readNewRation();

                        txtRes.setText(lastRation + " / " + newRation + " = " + Ration.division(lastRation, newRation).toString()+" /");
                        txtRationNumber.setText("");
                    } else {
                        readNewRation();
                        txtRes.setText(newRation.toString() + " /");
                        txtRationNumber.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnReciprocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    readNewRation();

                    txtRes.setText(Ration.recprocally(newRation).toString());
                    txtRationNumber.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                txtRationNumber.setText("");
                txtRes.setText("");
            }
        });

       btnResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] items = txtRes.getText().split("");

                String operation = items[items.length-1];
                try {
                    readNewRation();
                    readLastRation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                switch (operation){

                    case "+":
                        try {
                            Ration sum = Ration.sum(lastRation, newRation);
                            if(isInt(sum)){
                                txtRes.setText(lastRation+" + "+newRation+" = "+sum.printInt());
                            }else{
                                txtRes.setText(lastRation+" + "+newRation+" = "+sum.toString());
                            }
                            txtRationNumber.setText("");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        txtRationNumber.setText("");
                        break;
                    case "-":
                        try {
                            Ration diff = Ration.diff(lastRation, newRation);
                            if(isInt(diff)){
                                txtRes.setText(lastRation+" - "+newRation+" = "+diff.printInt());
                            }else{
                                txtRes.setText(lastRation+" - "+newRation+" = "+diff.toString());
                            }
                            txtRationNumber.setText("");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        txtRationNumber.setText("");
                        break;
                    case "x":
                        try {
                            Ration multiply = Ration.multiply(lastRation, newRation);
                            if(isInt(multiply)){
                                txtRes.setText(lastRation+" x "+newRation+" = "+multiply.printInt());
                            }else{
                                txtRes.setText(lastRation+" x "+newRation+" = "+multiply.toString());
                            }
                            txtRationNumber.setText("");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        txtRationNumber.setText("");
                        break;
                    case "/":
                        try {
                            Ration div = Ration.division(lastRation, newRation);
                            if(isInt(div)){
                                txtRes.setText(lastRation+" / "+newRation+" = "+div.printInt());
                            }else{
                                txtRes.setText(lastRation+" / "+newRation+" = "+div.toString());
                            }
                            txtRationNumber.setText("");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        txtRationNumber.setText("");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void readLastRation() throws Exception {
        //read last Ration and set
        String[] resItems = txtRes.getText().split(reg);
        int resLength = txtRes.getText().split(reg).length;

        int rationNumerator;
        int rationDenominator;

        if(resLength > 3){
            rationNumerator = Integer.parseInt(resItems[8]);
            rationDenominator = Integer.parseInt(resItems[9]);
        }else{
            rationNumerator = Integer.parseInt(resItems[0]);
            rationDenominator = Integer.parseInt(resItems[1]);
        }

        lastRation = new Ration(rationNumerator, rationDenominator);
    }

    private void readNewRation() throws Exception {
        //read new Ration and set
        String[] rationItems = txtRationNumber.getText().split(reg);
        int rationLength = txtRationNumber.getText().split(reg).length;

        int rationNumerator = Integer.parseInt(rationItems[0]);
        int rationDenominator = Integer.parseInt(rationItems[1]);

        newRation = new Ration(rationNumerator, rationDenominator);
    }

    private boolean isInt(Ration r){
        if(r.getNumerator() == r.getDenominator()){
            return true;
        }else if(r.getDenominator() == 1){
            return true;
        }else if(r.getNumerator() == 0){
            //else numerator = 0
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ration Calculator");
        frame.setContentPane(new RationCalculator().pnlBase);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 250));
        frame.pack();
        frame.setVisible(true);
    }
}