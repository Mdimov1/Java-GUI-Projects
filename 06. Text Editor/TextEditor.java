package edu.fmi;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class TextEditor {
    private JPanel pnlBase;
    private JFrame frame;
    private JTextArea t;
    private JFileChooser fc;
    private JScrollPane sp;

    private int countWord = 0;
    private int sentenceCount = 0;
    private int characterCount = 0;
    private int characterNoSpaceCount = 0;
    private int paragraphCount = 1;
    private int whitespaceCount = 0;

    TextEditor() {
        frame = new JFrame("Text Editor");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
        }

        t = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(t,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JMenuBar menuBar = new JMenuBar();

        JMenu m1 = new JMenu("File");

        JMenuItem mNew = new JMenuItem("New");
        JMenuItem mOpen = new JMenuItem("Open");
        JMenuItem mSave = new JMenuItem("Save");
        JMenuItem mSaveAs = new JMenuItem("Save As");
        JMenuItem mPrint = new JMenuItem("Print");
        JMenuItem mClose = new JMenuItem("Close");
        JMenuItem mExit = new JMenuItem("Exit");

        mNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    createFile();
                    loadFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                t.setText("");
            }
        });

        mOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                openFile();
            }
        });

        mSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    saveAction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                saveAsAction();
            }
        });

        mPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    t.print();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }
            }
        });

        mClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                close();
            }
        });

        mExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    exit();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }
            }
        });

        m1.add(mNew);
        m1.add(mOpen);
        m1.add(mSave);
        m1.add(mSaveAs);
        m1.add(mPrint);
        m1.add(mClose);
        m1.add(mExit);

        //Menu Edit
        JMenu m2 = new JMenu("Edit");

        //Menu items
        JMenuItem mFind = new JMenuItem("Find");
        JMenuItem mReplace = new JMenuItem("Replace");
        JMenuItem mReplaceAll = new JMenuItem("Replace all");
        JMenuItem mCut = new JMenuItem("Cut");
        JMenuItem mCopy = new JMenuItem("Copy");
        JMenuItem mPaste = new JMenuItem("Paste");

        mFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                find();
            }
        });

        mReplace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                replace();
            }
        });

        mReplaceAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                replaceAll();
            }
        });

        mCut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                t.cut();
            }
        });

        mCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                t.copy();
            }
        });

        mPaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                t.paste();
            }
        });

        m2.add(mFind);
        m2.add(mReplace);
        m2.add(mReplaceAll);
        m2.add(mCut);
        m2.add(mCopy);
        m2.add(mPaste);

        //Menu Analyze
        JMenu m3 = new JMenu("Analyze");

        JMenuItem mWords = new JMenuItem("Words");
        JMenuItem mSentence = new JMenuItem("Sentences");
        JMenuItem mCharacters = new JMenuItem("Characters");
        JMenuItem mCharactersNoSpace = new JMenuItem("Characters (no spaces)");
        JMenuItem mParagraphs = new JMenuItem("Paragraphs");
        JMenuItem mSpaces = new JMenuItem("Spaces");


        mWords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Analyze();
                String message = String.format("Num of the words is %d.", countWord);
                JOptionPane.showMessageDialog(frame,message);

            }
        });

        mSentence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Analyze();
                String message = String.format("Num of sentences is %d.", sentenceCount);
                JOptionPane.showMessageDialog(frame, message);
            }
        });

        mCharacters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Analyze();
                String message = String.format("Num of characters is %d.", characterCount);
                JOptionPane.showMessageDialog(frame, message);
            }
        });

        mCharactersNoSpace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Analyze();
                String message = String.format("Num of characters without spaces is %d.", characterNoSpaceCount);
                JOptionPane.showMessageDialog(frame, message);
            }
        });

        mParagraphs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Analyze();
                String message = String.format("Num of paragraphs is %d.", paragraphCount);
                JOptionPane.showMessageDialog(frame,message);
            }
        });

        mSpaces.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Analyze();
                String message = String.format("Num of spaces is %d.", whitespaceCount);
                JOptionPane.showMessageDialog(frame,message);
            }
        });

        m3.add(mWords);
        m3.add(mSentence);
        m3.add(mCharacters);
        m3.add(mCharactersNoSpace);
        m3.add(mParagraphs);
        m3.add(mSpaces);

        menuBar.add(m1);
        menuBar.add(m2);
        menuBar.add(m3);

        frame.setJMenuBar(menuBar);
        frame.add(scrollPane);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.show();
    }

    private void createFile() throws IOException {
        try {
            String data = "";
            FileOutputStream out = new FileOutputStream("D:\\My files\\СУ\\Избрани въпроси - Java\\Text Editor\\untitled.txt");
            out.write(data.getBytes());
            out.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Creating a file is failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadFile() {
        try {
            String fileName = "src//untitled.txt";
            File file = new File(fileName);
            frame.setTitle("Untitled");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Opening failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Work correctly but save txts in the project folder
    private void saveAction() throws IOException {
        //JFrame fr = new JFrame();
        JFileChooser fc = new JFileChooser();
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String frameTitle = frame.getTitle().split("[.]+")[0];


        if (!frameTitle.equals("Text Editor")) {
            //String usingSystemProperty = System.getProperty("user.dir");
            //String s = Paths.get("").toAbsolutePath().toString();
            //String d = fc.getCurrentDirectory().getAbsolutePath();
            try {
                /*String name = fr.getTitle() + ".txt";
                Path path = Paths.get(name);
                Charset charset = StandardCharsets.UTF_8;
                String content = new String(readAllBytes(path), charset);
                Files.write(path, content.getBytes(charset));*/
                //Path path = Paths.get(name);

                String name = frameTitle + ".txt";
                File file = new File(name);
                FileWriter wr = new FileWriter(file);
                BufferedWriter w = new BufferedWriter(wr);
                w.write(t.getText());
                w.flush();
                w.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Save operation failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveAsAction();
            return;
        }
    }

    private void saveAsAction() {
        JFileChooser fc = new JFileChooser();

        fc.setApproveButtonText("Save");
        fc.setDialogTitle("Save");
        fc.setDialogType(JFileChooser.SAVE_DIALOG);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text", "txt");
        fc.setFileFilter(filter);

        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            FileWriter outFile;
            try {
                String fileName = fc.getSelectedFile().getAbsolutePath();
                if (!fileName.contains(".")) {
                    fileName += ".txt";
                }
                outFile = new FileWriter(fileName);
                outFile.write(t.getText());
                outFile.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Saving failed!", "Error!", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    private void openFile() {
        fc = new JFileChooser();
        //Filter for input file format
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text", "txt");
        fc.setFileFilter(filter);
        //Showing the save dialog
        int r = fc.showOpenDialog(null);

        // If the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            // Set the label to the path of the selected directory
            File fi = new File(fc.getSelectedFile().getAbsolutePath());
            frame.setTitle(fi.getName().split("[.]+")[0]);
            try {
                String s1 = "", sl = "";
                FileReader fr = new FileReader(fi);
                BufferedReader br = new BufferedReader(fr);
                sl = br.readLine();

                while ((s1 = br.readLine()) != null) {
                    sl = sl + "\n" + s1;
                }

                t.setText(sl);

            } catch (Exception evt) {
                JOptionPane.showMessageDialog(frame, evt.getMessage());
            }
        } else
            JOptionPane.showMessageDialog(frame, "The user cancelled the operation.");
    }

    private void close() {
        try {
            final JOptionPane optionPane = new JOptionPane(
                    "Do you want to save the changes before close?",
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.YES_NO_OPTION);

            final JDialog dialog = new JDialog(frame,
                    "Click a button",
                    true);

            dialog.setContentPane(optionPane);
            dialog.setDefaultCloseOperation(
                    DO_NOTHING_ON_CLOSE);

            optionPane.addPropertyChangeListener(
                    new PropertyChangeListener() {
                        public void propertyChange(PropertyChangeEvent e) {
                            String prop = e.getPropertyName();

                            if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                                dialog.setVisible(false);
                            }
                        }
                    });
            dialog.pack();
            dialog.setVisible(true);

            int value = ((Integer) optionPane.getValue()).intValue();
            if (value == JOptionPane.YES_OPTION) {
                saveAction();
            } else if (value == JOptionPane.NO_OPTION) {
                createFile();
                loadFile();
            }

        } catch (Exception evt) {
            JOptionPane.showMessageDialog(frame, evt.getMessage());
        }
    }

    private void replace() {
        JFrame fr = new JFrame();

        JTextField txtFind = new JTextField();
        txtFind.setMaximumSize(new Dimension(200, 25));

        JTextField txtRep = new JTextField();
        txtRep.setMaximumSize(new Dimension(200, 25));

        String msgString1 = "Enter text";
        String msgString2 = "Enter replacement";

        JButton btnReplace = new JButton();
        btnReplace.setText("Replace");
        btnReplace.setPreferredSize(new Dimension(80, 25));

        JButton btnCancel = new JButton();
        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new Dimension(80, 25));

        Object[] array = {msgString1, txtFind, msgString2, txtRep};
        JButton[] options = {btnReplace, btnCancel};

        //Create the JOptionPane.
        JOptionPane optionPane = new JOptionPane(array,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        fr.setContentPane(optionPane);
        //fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setLocationRelativeTo(null);

        btnReplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAction();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                fc = new JFileChooser();

                String name = frame.getTitle() + ".txt";
                Path path = Paths.get(name);
                Charset charset = StandardCharsets.UTF_8;

                try {
                    String content = new String(readAllBytes(path), charset);
                    content = content.replaceFirst(txtFind.getText(), txtRep.getText());
                    Files.write(path, content.getBytes(charset));
                    fr.setVisible(false);

                    content = new String(readAllBytes(path), charset);
                    t.setText(content);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.setVisible(false);
            }
        });

        fr.setTitle("Find and replace");
        fr.setPreferredSize(new Dimension(320, 200));
        fr.pack();
        fr.setVisible(true);
    }

    private void replaceAll() {
        JFrame fr = new JFrame();

        JTextField txtFind = new JTextField();
        txtFind.setMaximumSize(new Dimension(200, 25));

        JTextField txtRep = new JTextField();
        txtRep.setMaximumSize(new Dimension(200, 25));

        String msgString1 = "Enter text";
        String msgString2 = "Enter replacement";

        JButton btnReplace = new JButton();
        btnReplace.setText("Replace All");
        btnReplace.setPreferredSize(new Dimension(100, 25));

        JButton btnCancel = new JButton();
        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new Dimension(100, 25));

        Object[] array = {msgString1, txtFind, msgString2, txtRep};
        JButton[] options = {btnReplace, btnCancel};

        //Create the JOptionPane.
        JOptionPane optionPane = new JOptionPane(array,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        fr.setContentPane(optionPane);
        fr.setLocationRelativeTo(null);

        btnReplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAction();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                fc = new JFileChooser();

                String name = frame.getTitle() + ".txt";
                Path path = Paths.get(name);
                Charset charset = StandardCharsets.UTF_8;

                try {
                    String content = new String(readAllBytes(path), charset);
                    content = content.replaceAll(txtFind.getText(), txtRep.getText());
                    Files.write(path, content.getBytes(charset));
                    fr.setVisible(false);

                    content = new String(readAllBytes(path), charset);
                    t.setText(content);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.setVisible(false);
            }
        });

        fr.setTitle("Find and replace");
        fr.setPreferredSize(new Dimension(320, 200));
        fr.pack();
        fr.setVisible(true);
    }

    //Make found text to UpperCase
    private void find() {
        JFrame fr = new JFrame();
        JTextField textField = new JTextField();

        String msgString1 = "Find text";

        JButton btnFind = new JButton();
        btnFind.setText("Find");
        btnFind.setPreferredSize(new Dimension(80, 25));

        JButton btnCancel = new JButton();
        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new Dimension(80, 25));

        Object[] array = {msgString1, textField};
        JButton[] options = {btnFind, btnCancel};

        //Create the JOptionPane.
        JOptionPane optionPane = new JOptionPane(array,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);

        fr.setContentPane(optionPane);
        fr.setLocationRelativeTo(null);

        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveAction();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                fc = new JFileChooser();

                String name = frame.getTitle() + ".txt";
                Path path = Paths.get(name);
                Charset charset = StandardCharsets.UTF_8;

                try {
                    String s = textField.getText();
                    String newS = s.toUpperCase();

                    String content = new String(readAllBytes(path), charset);
                    content = content.replaceAll(s, newS);
                    Files.write(path, content.getBytes(charset));
                    fr.setVisible(false);

                    content = new String(readAllBytes(path), charset);
                    t.setText(content);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.setVisible(false);
            }
        });

        fr.setTitle("Find");
        fr.setPreferredSize(new Dimension(320, 200));
        fr.pack();
        fr.setVisible(true);
    }

    private void exit() {
        final JOptionPane optionPane = new JOptionPane(
                "Do you want to save the last changes before exit?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);

        final JDialog dialog = new JDialog(frame,
                "Click a button",
                true);

        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(
                DO_NOTHING_ON_CLOSE);

        optionPane.addPropertyChangeListener(
                new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
                        String prop = e.getPropertyName();

                        if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                            dialog.setVisible(false);
                        }
                    }
                });
        dialog.pack();
        dialog.setVisible(true);

        int value = ((Integer) optionPane.getValue()).intValue();
        if (value == JOptionPane.YES_OPTION) {
            saveAsAction();
        } else if (value == JOptionPane.NO_OPTION) {
            frame.setVisible(false);
        }

    }

    private void Analyze(){
        try {
            saveAction();

            String name = frame.getTitle() + ".txt";
            File file = new File(name);
            String parent = file.getParent();
            FileInputStream fileStream = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(fileStream);
            BufferedReader reader = new BufferedReader(input);

            String line;

            countWord = 0;
            sentenceCount = 0;
            characterCount = 0;
            characterNoSpaceCount = 0;
            paragraphCount = 1;
            whitespaceCount = 0;

            while((line = reader.readLine()) != null)
            {
                if(line.equals(""))
                {
                    paragraphCount++;
                }
                if(!(line.equals("")))
                {
                    characterCount += line.length();

                    String data = line.replaceAll(" ","");
                    String[] t = data.split("");
                    characterNoSpaceCount += t.length;

                    String[] wordList = line.split("\\s+");

                    countWord += wordList.length;
                    whitespaceCount += countWord -1;

                    String[] sentenceList = line.split("[!?.:]+");

                    sentenceCount += sentenceList.length;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TextEditor t = new TextEditor();
    }
}
