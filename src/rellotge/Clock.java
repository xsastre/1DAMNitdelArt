package rellotge;



/**
 *
 * @author Illán Hanssens y Daniel Espinosa
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.util.Random;


public class Clock extends javax.swing.JFrame {


    public int hour;
    public int min;
    public int sec;
    ClockDial cd;
    ImageIcon icon;
    public static int ancho; 
    public static int alto;
    public static int anchologo;
    public static int altologo;

     public Clock() {
        anchologo=612;
        altologo=636;
        ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        Image icon = new ImageIcon(getClass().getResource("faviconcide1.jpg")).getImage();
        setIconImage(icon);
        setSize(ancho, alto);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cd=new ClockDial(this);
        getContentPane().add(cd);
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
    public Image nitdelart2018=new ImageIcon(getClass().getResource("nitdelart2018.jpg")).getImage(); 
    public Image[] logo=new Image[121];
    String url="";
    public boolean segona_volta=false;
    Color verd_cide = new Color(0,137,45);
    Clock parent;
    Random rand = new Random();
    
    public ClockDial(Clock pt){
        parent=pt;
        for (int z=0;z<=120;z++) {
            url=z+".jpg";
            logo[z]=new ImageIcon(getClass().getResource(url)).getImage();
            logo[z]=logo[z].getScaledInstance(Clock.anchologo, Clock.altologo, 0);
        }
    } 
    @Override
 public void paintComponent(Graphics g) {
        int anchologo=((Clock.ancho/2)-(Clock.anchologo/2));
        int altologo=((Clock.alto/2)-(Clock.altologo/2));
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        BasicStroke agulla_minuts = new BasicStroke(10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        BasicStroke agulla_hores = new BasicStroke(14f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        int apuntador=0;

        if (segona_volta) 
            apuntador=parent.sec+60;
        else
            apuntador=parent.sec;        
        if (parent.sec==59)
            segona_volta=!segona_volta;
        g.drawImage(logo[apuntador],anchologo,altologo, this);
        g.drawImage(nitdelart2018,1250,250,this);
        g.setColor(Color.RED);
        g.setFont(g.getFont().deriveFont(Font.BOLD,32));

        for(int i=1;i<=12;i++) {
                g.drawString(Integer.toString(i),720-(i/12)*11+(int)(450*Math.sin(i*Math.PI/6)),450-(int)(420*Math.cos(i*Math.PI/6)));
        }
        double minsecdeg=(double)Math.PI/30;
        double hrdeg=(double)Math.PI/6;
        int tx,ty;
        int xpoints[]=new int[3];
        int ypoints[]=new int[3];
        int ancho=(Clock.ancho/2);
        int alto=(Clock.alto/2);
       //second hand
        tx=ancho+(int)(420*Math.sin(parent.sec*minsecdeg));
        ty=alto-(int)(420*Math.cos(parent.sec*minsecdeg));
        g.drawLine(ancho,alto,tx,ty);
        //minute hand
        tx=ancho+(int)(300*Math.sin(parent.min*minsecdeg));
        ty=alto-(int)(300*Math.cos(parent.min*minsecdeg));
        g2.setStroke(agulla_minuts);
        g2.drawLine(ancho,alto,tx,ty);
        //hour hand
        tx=ancho+(int)(150*Math.sin(parent.hour*hrdeg+parent.min*Math.PI/360));
        ty=alto-(int)(150*Math.cos(parent.hour*hrdeg+parent.min*Math.PI/360));
        g2.setStroke(agulla_hores);
        g2.drawLine(ancho,alto,tx,ty);
 }
}