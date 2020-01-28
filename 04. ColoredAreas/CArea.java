package edu.fmi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static edu.fmi.ColoredArea.*;

public class CArea {

    private JPanel pnlBase;
    private JButton btnGenerateAreas;
    private JTable tblAreas;

    private int rowData[][] = {
            {1, 1, 1, 2, 2, 2},
            {3, 3, 1, 4, 4, 5},
            {3, 3, 5, 4, 4, 5},
            {3, 3, 5, 5, 5, 5}};

    public class MyGraphics extends JComponent {

        private static final long serialVersionUID = 1L;

        MyGraphics() {
            setPreferredSize(new Dimension(350, 250));
        }

        @Override
        public void paintComponent(Graphics g) {
            Color myColor = Color.RED;
            //i0 - left margin, i1 - up margin, i2 - width, i3 - height
            int i0 = 50;
            int i1 = 30;
            int i2 = 40;
            int i3 = 40;

            for (int i = 0; i < rowData.length; i++) {
                for (int j = 0; j < rowData[0].length; j++) {
                    switch (rowData[i][j]){
                        case 1:
                            myColor = Color.RED;
                            break;
                        case 2:
                            myColor = Color.YELLOW;
                            break;
                        case 3:
                            myColor = Color.GREEN;
                            break;
                        case 4:
                            myColor = Color.BLUE;
                            break;
                        case 5:
                            myColor = Color.CYAN;
                            break;
                        default:
                            myColor = Color.ORANGE;
                    }
                    super.paintComponent(g);
                    g.setColor(myColor);
                    g.fillRect(i0, i1, i2, i3);
                    i0+=41;
                }
                i0=50;
                i1+=41;
            }
        }
    }

    public String resultRanges() {
        ColoredArea ca = new ColoredArea(rowData);

        Object[] ranges =  ranges().toArray();
        ArrayList<Integer> rangeSizes = ca.rangeSizes();

        String res = "";
        for (int i = 0; i < ranges.length; i++) {
            if(i == ranges.length-1){
                res+=String.format("Area %d: %d",ranges[i],rangeSizes.get(i));
            }else {
                res += String.format("Area %d: %d \n", ranges[i], rangeSizes.get(i));
            }
        }
        return res;
    }

    public void createGUI() {
        final JFrame frame = new JFrame("Colored areas");
        JPanel panel = new JPanel();
        panel.add(new MyGraphics());
        JTextArea textarea = new JTextArea(resultRanges());
        panel.add(textarea);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                CArea GUI = new CArea();
                GUI.createGUI();
            }
        });
    }
}