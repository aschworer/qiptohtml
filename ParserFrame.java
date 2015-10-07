import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ParserFrame extends JFrame {
    private static Dimension FRAME_DIMENSION = new Dimension(500, 280);
    private JButton btnOpen;
    private JButton btnSave;
    private JButton btnParse;
    private JTextField sourseTextField;
    private JTextField outputTextField;
    private JComboBox jcRes;
    private JComboBox jcSen;
    private File [] sourseFiles;
    private String receiverColor;
    private String senderColor;

    public ParserFrame() {
        super("QIP History to HTML Converter");
        this.setSize(FRAME_DIMENSION);
        initUI();
        initListeners();
    }

    private void initUI() {
        Container contentPane = getContentPane();
        contentPane.setLayout((new GridLayout(2, 2, 40, 10)));
        sourseTextField = new JTextField(20);
        outputTextField = new JTextField(20);
        sourseTextField.setEditable(false);
        outputTextField.setEditable(false);
        btnOpen = new JButton("Browse");
        btnSave = new JButton("Browse");
        btnParse = new JButton("Parse");
        jcRes = new JComboBox();
        jcRes.addItem("Blue");
        jcRes.addItem("Red");
        jcRes.addItem("Green");
        jcRes.addItem("Yellow");
        jcRes.addItem("Silver");
        jcRes.addItem("Gold");
        jcRes.addItem("Pink");
        jcRes.addItem("Purple");
        jcRes.addItem("Grey");
        jcRes.addItem("Orange");

        jcSen = new JComboBox();
        jcSen.addItem("Red");
        jcSen.addItem("Blue");
        jcSen.addItem("Green");
        jcSen.addItem("Yellow");
        jcSen.addItem("Silver");
        jcSen.addItem("Gold");
        jcSen.addItem("Pink");
        jcSen.addItem("Purple");
        jcSen.addItem("Grey");
        jcSen.addItem("Orange");

        JPanel pnl1 = new JPanel(new BorderLayout());
        JPanel pnl2 = new JPanel(new BorderLayout());
        JPanel pnlNorthLeft = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel pnlSouthLeft = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel pnlNorthRight = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel pnlSouthRight = new JPanel(new GridBagLayout());
        pnlNorthLeft.add(new JLabel("Choose Sourse File:", 0));
        pnlNorthLeft.add(btnOpen);
        pnlNorthLeft.add(sourseTextField);
        pnlSouthLeft.add(new JLabel("Choose Output Directory:", 0));
        pnlSouthLeft.add(btnSave);
        pnlSouthLeft.add(outputTextField);
        pnlNorthRight.add(new JLabel("Choose Colors:", 0));

        pnl1.add(new JLabel("Receiver Color:", 0), BorderLayout.WEST);
        pnl1.add(jcRes, BorderLayout.EAST);
        pnl2.add(new JLabel("Sender Color:", 0), BorderLayout.WEST);
        pnl2.add(jcSen, BorderLayout.EAST);

        pnlNorthRight.add(pnl1);
        pnlNorthRight.add(pnl2);

        pnlSouthRight.add(btnParse);

        contentPane.add(pnlNorthLeft);
        contentPane.add(pnlNorthRight);
        contentPane.add(pnlSouthLeft);
        contentPane.add(pnlSouthRight);

    }

    public Insets getInsets() {
        return new Insets(40, 20, 20, 20);
    }

    private void initListeners() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fch = new JFileChooser();
                //fch.setCurrentDirectory(new File("C:\\Documents and Settings\\Home\\Рабочий стол\\QIP\\Users\\234400423\\History"));
                fch.setMultiSelectionEnabled(true);
                switch (fch.showDialog(null, "Open")) {
                    case JFileChooser.APPROVE_OPTION:
                        sourseFiles = fch.getSelectedFiles();
                        String strFiles = "";
                        for (File sourseFile : sourseFiles) {
                            strFiles = strFiles + sourseFile.toString() + "; ";
                        }
                        sourseTextField.setText(strFiles);
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        break;
                    case JFileChooser.ERROR_OPTION:
                        JOptionPane.showMessageDialog(null, "Incorrect name of file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        );
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fch = new JFileChooser();
                fch.setFileSelectionMode(1);
                //fch.setCurrentDirectory(new File("C:\\Documents and Settings\\Home\\Рабочий стол"));
                switch (fch.showDialog(null, "Open")) {
                    case JFileChooser.APPROVE_OPTION:
                        File file = fch.getSelectedFile();
                        outputTextField.setText(file.toString());
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        break;
                    case JFileChooser.ERROR_OPTION:
                        JOptionPane.showMessageDialog(null, "Incorrect name of file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        );
        btnParse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receiverColor = (String) jcRes.getSelectedItem();
                senderColor = (String) jcSen.getSelectedItem();
                try {
                    if ("".equals(sourseTextField.getText()) || "".equals(outputTextField.getText())) {
                        JOptionPane.showMessageDialog(null, "Set File", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String inp;
                        for (File sourseFile : sourseFiles) {
                            if (!sourseFile.toString().endsWith(".txt")) {
                                JOptionPane.showMessageDialog(null, "Probably one or more files are not QIP History Files", "Error", JOptionPane.WARNING_MESSAGE);

                            }
                            inp = sourseFile.toString();
                            String out = outputTextField.getText() + "\\" + inp.substring(inp.lastIndexOf("\\") + 1, inp.length() - 4) + ".html";
                            OutputStringHTMLCode htmlCode = new OutputStringHTMLCode(inp, receiverColor, senderColor);
                            try {
                                htmlCode.writeInFile(out);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "IOException", "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                } catch (NullPointerException npe) {
                    JOptionPane.showMessageDialog(null, "Set File", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        );
    }
}