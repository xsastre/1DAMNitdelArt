package rellotge;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xavier
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.util.Random;


public class Clock extends javax.swing.JFrame {


    public int hour;
    public int min;
    public int sec;
    ClockDial cd;
    ImageIcon icon;
    //public Image fons=new ImageIcon(getClass().getResource("./nitdelart2018.jpg")).getImage();  
    


    public Clock() {
        //setSize(510,530);
        Image icon = new ImageIcon(getClass().getResource("./faviconcide1.jpg")).getImage();
        setIconImage(icon);
        setVisible(true);
        setSize(610,530);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cd=new ClockDial(this);
        getContentPane().add(cd);
        //getContentPane().add(new JLabel(new ImageIcon(fons)));
        Date curr=new Date();
        String time=curr.toString();
        hour=Integer.parseInt(time.substring(11,13));
        min=Integer.parseInt(time.substring(14,16));
        sec=Integer.parseInt(time.substring(17,19));
        ClockEngine.setPriority(ClockEngine.getPriority()+3);
        ClockEngine.start();
    }

public static void main(String args[]) {
                new Clock().setVisible(true);
    }

Thread ClockEngine=new Thread()

{
int newsec,newmin;

public void run()
  {
  while(true)
    {
    newsec=(sec+1)%60;
    newmin=(min+(sec+1)/60)%60;
    hour=(hour+(min+(sec+1)/60)/60)%12;
    sec=newsec;
    min=newmin;

    try {

          Thread.sleep(1000);

      } catch (InterruptedException ex) {}

    cd.repaint();
    }
}
};
}


class ClockDial extends JPanel{
    //public Image logo=new ImageIcon(getClass().getResource("./logocide2x_rellotge.jpg")).getImage(); 
    //public Image logo2=new ImageIcon(getClass().getResource("./logocide_icon.jpg")).getImage();
    public Image nitdelart2018=new ImageIcon(getClass().getResource("./nitdelart2018.jpg")).getImage(); 
    public Image[] logo=new Image[121];
    String url="";
    public boolean segona_volta=false;
    Color verd_cide = new Color(0,137,45);
    Clock parent;
    Random rand = new Random();

    public ClockDial(Clock pt){
        setSize(520,530);
        parent=pt;
        for (int z=0;z<=120;z++) {
            url="./"+z+".jpg";
            logo[z]=new ImageIcon(getClass().getResource("./"+z+".jpg")).getImage();
        }
    }

   
    @Override
 public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        BasicStroke agulla_minuts = new BasicStroke(10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        BasicStroke agulla_hores = new BasicStroke(14f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        int apuntador=0;
        //System.out.println(parent.sec+String.valueOf(segona_volta));
        //g.setColor(Color.BLACK);
        //g.fillRect(0, 0, getWidth(), getHeight());
        //g.setColor(Color.WHITE);
        //g.fillOval(5, 5,480,480);
        //g.setColor(Color.RED);
        //g.fillOval(10, 10,470,470);
        if (segona_volta) 
            apuntador=parent.sec+60;
        else
            apuntador=parent.sec;        
        if (parent.sec==59)
            segona_volta=!segona_volta;
        g.drawImage(logo[apuntador], -8, -20, this);
        //g.drawImage(logo2,rand.nextInt(350),rand.nextInt(350), this);
        g.drawImage(nitdelart2018,490,0,this);
        g.setColor(Color.RED);
        g.fillOval(237,237,15,15);
        g.setFont(g.getFont().deriveFont(Font.BOLD,32));

        for(int i=1;i<=12;i++) {
            if ((i<=4) || (i>=8)) {
                g.drawString(Integer.toString(i),240-(i/12)*11+(int)(210*Math.sin(i*Math.PI/6)),253-(int)(210*Math.cos(i*Math.PI/6)));
            }
        }
        
        
       
        
                
        double minsecdeg=(double)Math.PI/30;
        double hrdeg=(double)Math.PI/6;
        int tx,ty;
        int xpoints[]=new int[3];
        int ypoints[]=new int[3];
 
       //second hand
        tx=245+(int)(210*Math.sin(parent.sec*minsecdeg));
        ty=245-(int)(210*Math.cos(parent.sec*minsecdeg));
        g.drawLine(245,245,tx,ty);

        //minute hand
        tx=245+(int)(190*Math.sin(parent.min*minsecdeg));
        ty=245-(int)(190*Math.cos(parent.min*minsecdeg));
        /*xpoints[0]=245;
        xpoints[1]=tx+2;
        xpoints[2]=tx-2;
        ypoints[0]=245;
        ypoints[1]=ty+2;
        ypoints[2]=ty-2;
        g.fillPolygon(xpoints, ypoints,3);
        */
        g2.setStroke(agulla_minuts);
        g2.drawLine(245,245,tx,ty);   //thick

        //hour hand
        tx=245+(int)(160*Math.sin(parent.hour*hrdeg+parent.min*Math.PI/360));
        ty=245-(int)(160*Math.cos(parent.hour*hrdeg+parent.min*Math.PI/360));
        /*
        xpoints[1]=tx+4;
        xpoints[2]=tx-4;
        ypoints[1]=ty-4;
        ypoints[2]=ty+4;
        g.fillPolygon(xpoints, ypoints, 3);
        */
        g2.setStroke(agulla_hores);
        g2.drawLine(245,245,tx,ty);
    }

}