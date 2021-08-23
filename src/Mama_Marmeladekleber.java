/*
 * This Programm is written for "Daniela Krampf"
 */

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

class DJTextArea extends JTextArea implements Printable {

    @Override
    public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {
        if (pi >= 1) {
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(pf.getImageableX(), pf.getImageableY());
        paint(g2d);

        return Printable.PAGE_EXISTS;
    }
}

/**
 * @author Kilian
 * @for Mama
 */
public class Mama_Marmeladekleber extends JFrame implements ActionListener {

    // JTextField name = null;
    static String gesamt = null;
    JTextField jahr = null;
    JTextField sorte = null;
    JCheckBox garten = null;
    JRadioButton marm = null;

    public Mama_Marmeladekleber(String title) {
        super(title);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Textfeld für die Sorte
        sorte = new JTextField();
        sorte.setBounds(320, 30, 270, 30);
        sorte.setText("Sorte");
        add(sorte);

        //Textfeld für das Herstellungsjahr
        jahr = new JTextField();
        jahr.setBounds(320, 90, 270, 30);
        jahr.setText("20XX");
        add(jahr);

        //Textfeld für das Herstellungsjahr (Rausgenommen)
        //name = new JTextField();
        // name.setBounds(320, 150, 270, 30);
        // name.setText("--Name--");
        // add(name);
        //Info für Sorte
        JLabel info1 = new JLabel("Sorte");
        info1.setBounds(50, 30, 270, 30);
        add(info1);

        //Info für Jahr
        JLabel info2 = new JLabel("Jahr:");
        info2.setBounds(50, 90, 270, 30);
        add(info2);

        //Info für Name
        JLabel info3 = new JLabel("Programmiert von Kilian                                            6 Aufkleber pro Seite");
        info3.setBounds(50, 360, 540, 30);
        add(info3);

        //Knopf zum Drucken
        JButton drucken = new JButton("Drucken");
        drucken.setBounds(50, 270, 540, 60);
        drucken.addActionListener(this);
        add(drucken);

        //Optionen zum Wechseln zw. Marmelade und Eingekochtem
        marm = new JRadioButton("Marmelade");
        marm.setBounds(50, 150, 270, 30);
        JRadioButton ande = new JRadioButton("Eingemachtes u.ä.");
        ande.setBounds(320, 150, 270, 30);
        marm.setSelected(true);
        ButtonGroup etikettart = new ButtonGroup();
        etikettart.add(marm);
        etikettart.add(ande);
        add(marm);
        add(ande);

        //Option für "Eigener Garten"
        garten = new JCheckBox("Aus dem Eigenen Garten");
        garten.setBounds(50, 210, 540, 30);
        add(garten);
    }

    public static void main(String[] args) {
        Mama_Marmeladekleber fenster = new Mama_Marmeladekleber("Aufkleber");
        fenster.setBounds(100, 100, 640, 450);
        fenster.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String Sorte = sorte.getText();
        String Jahr = jahr.getText();
        String Etikett = null;
        //String Name = name.getText();
        if (garten.isSelected()) {
            if (marm.isSelected()) {
                Etikett = "\n" + "Sorte: " + Sorte + "\n" + "Herstellungsjahr: " + Jahr + "\n" + "Aus dem eigenen Garten" + "\n" + "______________________________________\n";
            } else {
//                Etikett = "\n" + Sorte + "\n" + "Herstellungsjahr: " + Jahr + "\n" + "Aus dem eigenen Garten" + "\n" + "______________________________________\n";
                Etikett = "\n" + Sorte + "\n" + "Herstellungsjahr: " + Jahr + "\n" + "Anwendung: 1EL Speisestärke hinzugeben und aufkochen" + "\n" + "______________________________________\n";
            }
        } else {
            if (marm.isSelected()) {
                Etikett = "\n" + "Sorte: " + Sorte + "\n" + "Herstellungsjahr: " + Jahr + "\n" + "Homemade" + "\n" + "______________________________________\n";
            } else {
                Etikett = "\n" + Sorte + "\n" + "Herstellungsjahr: " + Jahr + "\n" + "Homemade" + "\n" + "______________________________________\n";
            }
        }
        gesamt = "_________________________\n" + Etikett + Etikett + Etikett + Etikett + Etikett + Etikett;
        //   System.out.println(gesamt);
        Zwischen D = new Zwischen();
        D.setVisible(true);
        D.pack();
        D.drucken();
        D.dispose();
    }
}

class Zwischen extends Frame {

    DJTextArea druck = null;

    public Zwischen() {
        druck = new DJTextArea();
        druck.setVisible(true);
        add(druck);
        druck.setFont(new Font("Segoe Script", Font.PLAIN, 14));
        druck.setText(Mama_Marmeladekleber.gesamt);
        setLayout(new FlowLayout());
    }

    public void drucken() {
        PrinterJob auftrag = PrinterJob.getPrinterJob();
        auftrag.setPrintable(druck);
        PageFormat format = auftrag.pageDialog(auftrag.defaultPage());
        if (auftrag.printDialog()) {
            try {
                auftrag.print();
            } catch (Exception e) {
                System.out.println("Fehler beim Drucken");
            }
        }
    }
}
