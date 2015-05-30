
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.GridLayout;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;

import oracle.jdeveloper.layout.OverlayLayout2;
import oracle.jdeveloper.layout.PaneConstraints;
import oracle.jdeveloper.layout.PaneLayout;
import oracle.jdeveloper.layout.VerticalFlowLayout;
import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

public class GamePan extends JPanel {

    private JPanel SidBand = new JPanel();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private JPanel jPanel5 = new JPanel();
    private JButton jButton1 = new JButton();
    private JLabel jLabel1 = new JLabel();
    private JCheckBox jCheckBox1 = new JCheckBox();
    private JCheckBox jCheckBox2 = new JCheckBox();
    private JProgressBar jProgressBar1 = new JProgressBar();
    public Canvas canvas = new Canvas();
    private JPanel minimap = new Minimap();



    public GamePan(int Height,int Width ) {
        super();
        this.setLayout(null);
        SidBand.setBounds(0,0,(int)(Width*0.2),(int)(Height*0.8));
        minimap.setBounds(0,(int)(Height*0.8),(int)(Width*0.2),(int)(Height*0.2));
        canvas.setBounds ((int)(Width*0.2),0,(int)(Width*0.8),Height);
        SidBand.setBackground(Color.gray);

        SidBand.setLayout(verticalFlowLayout1);
        jButton1.setText("jButton1");
        int nbU =0;
        if (Game.getHuman()!=null) nbU =Game.getHuman().units.size();

        jLabel1.setText("nombre d'unite : "+nbU);
        jCheckBox1.setText("jCheckBox1");
        jCheckBox2.setText("jCheckBox2");
        jPanel3.setBackground(null);
        jPanel3.add(jLabel1, null);
        jPanel3.add(jButton1, null);
        SidBand.add(jPanel3, null);
        jPanel4.add(jCheckBox2, null);
        SidBand.add(jPanel4, null);
        jPanel5.add(jProgressBar1, null);
        SidBand.add(jPanel5, null);

        this.add(SidBand);
        this.add(canvas);
        this.add(minimap);
    }

}