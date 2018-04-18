package cercamines;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Cercamines extends JFrame {
  private JPanel jPanel1 = new JPanel();
  private JButton jButton1 = new JButton();
    private javax.swing.JFrame jFrame1;
    private javax.swing.JMenuItem Dificultad_Dificil;
    private javax.swing.JMenuItem Dificultad_Facil;
    private javax.swing.JMenuItem Dificultad_Media;
    private javax.swing.JMenu Menu_Dificultad;
    private javax.swing.JMenuBar jMenuBar1;
    public int ample=14;
    public int alt=14;
    public  JButton[][] Botons;
    public  String [][] elArray;
 
  //—- Depenent de l'ample també s'assignarà el número de bombes
 
public static void main (String [] args){
   Cercamines BuscaMinFacil = new Cercamines();
}
public Cercamines()  {
    try    {
      jbInit(10,10);
    }
    catch(Exception e)    {
      e.printStackTrace();
    }
  }
public Cercamines(int alt,int ample) {
    try {
        jbInit(alt,ample);
    } catch(Exception e) {
        e.printStackTrace();
    }   
}
 
  private void jbInit(int nou_alt,int nou_ample) throws Exception  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(365, 420));
    this.setTitle("Cerca Mines");
    this.ample=nou_ample;
    this.alt=nou_alt;
    Botons=new JButton [ample][alt];
    elArray =new String [ample][alt];
    jPanel1.setBounds(new Rectangle(0, 40, 365, 420));
    jPanel1.setBackground(new Color(162, 175, 227));
    jPanel1.setLayout(null);
    jButton1.setText("COMENÇAR");
    jButton1.setBounds(new Rectangle(0, 0, 100, 40));
    jButton1.setFont(new Font("Tahoma", 0, 12));
    jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
    jButton1.setAlignmentY((float)0.0);
    jButton1.setMargin(new Insets(2, 14, 2, 12));
    
    jMenuBar1 = new javax.swing.JMenuBar();
    Menu_Dificultad = new javax.swing.JMenu();
    Dificultad_Facil = new javax.swing.JMenuItem();
    Dificultad_Media = new javax.swing.JMenuItem();
    Dificultad_Dificil = new javax.swing.JMenuItem();
    jFrame1 = new javax.swing.JFrame();
    
            javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Menu_Dificultad.setText("Dificultad");

        Dificultad_Facil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cercamines/125.png"))); // NOI18N
        Dificultad_Facil.setText("Fácil");
        Dificultad_Facil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dificultad_FacilActionPerformed(evt);
            }
        });
        Menu_Dificultad.add(Dificultad_Facil);

        Dificultad_Media.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cercamines/131.png"))); // NOI18N
        Dificultad_Media.setText("Medio");
        Dificultad_Media.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dificultad_MediaActionPerformed(evt);
            }
        });
        Menu_Dificultad.add(Dificultad_Media);

        Dificultad_Dificil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cercamines/129.png"))); // NOI18N
        Dificultad_Dificil.setText("Dificil");
        Dificultad_Dificil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dificultad_DificilActionPerformed(evt);
            }
        });
        Menu_Dificultad.add(Dificultad_Dificil);

        jMenuBar1.add(Menu_Dificultad);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );

        pack();

    
    jButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton1_actionPerformed(e);
        }
      });
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jPanel1, null);
    carregarTablero();
    colocarBomba(getAmple());
    comprova();
 
    this.setVisible(true);  
 
  //—- Oculta els botons del contorn que no participaran en el joc
 
    amagaBotons();
  }
  
  
  private void Dificultad_FacilActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
       Cercamines BuscaMinFacil = new Cercamines(10,10);
    }                                                

   private void Dificultad_MediaActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
        Cercamines BuscaMinMedia = new Cercamines(14,14);
    }
   
   private void Dificultad_DificilActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
        Cercamines BuscaMinDificil = new Cercamines(18,18);
    } 
    
  private void jButton1_actionPerformed(ActionEvent e)  {
   for (int i=0;i<ample;i++){
       for (int z=0;z<alt;z++){
        elArray[i][z]=" ";
        Botons[i][z].setEnabled(true);
        Botons[i][z].setText(" ");
        }
   }
   colocarBomba(getAmple());
   comprova();
   this.setTitle("Cerca Mines");
   jButton1.setText("COMENÇAR");
  }
  //—– Inicialitza el tablero a 0
 
  public void carregarTablero(){
     for (int i=0;i<ample;i++){
      for (int z=0;z<alt;z++){
      elArray[i][z]=" "; // Text que apareixerà a cada botó (casella). Inicialment serà espai en blanc
         Botons[i][z]= new JButton();
             jPanel1.add(Botons[i][z]);
             Botons[i][z].setBounds(i*25,z*25,25,25);
             Botons[i][z].setMargin(new Insets(0, 0, 0, 0));
             Botons[i][z].setFont(new Font("Tahoma", 0,10));
            
                        
 
  //—– Afagesc un ActionListener a cada botó del Array de Botons
  //—– Ara ja pot escoltar esdeveniments
 
             Botons[i][z].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ar) {
                 for (int i=0;i<ample;i++){
                       for (int z=0;z<alt;z++){
                            if (ar.getSource()==Botons[i][z]){
                                 mostraTextBoto(i,z);                 
                            }                
                       }        
                 }            
                }
             });             
 
             }               
     }       
  }
  //—- Coloca el numero de bombes depenent de l'ample.
 
  public void colocarBomba(int numeroBombes){
   double i=0;
   double z=0;
   int condicion=0;
  do  {
     i=Math.random()*getAmple();
        z=Math.random()*getAlt();
           i=(int)i;
           z=(int)z;
           if  (z!=0 && i!=0 && z!=alt-1 && i!=ample-1){
             // Botons[(int) i][(int) z].setText("B");
              elArray[(int)i][(int) z ]="B";
              condicion++;
           }
       }
  while (condicion<=ample);
  }
  public void setAmple (int sAmple){
    ample=sAmple;
  }
  public int getAmple (){
    return ample;
  }
  public void setAlt (int sAlt){
    alt=sAlt;
  }
  public int getAlt (){
    return alt;
  }
 
 //—- Assigna un número a cada botó depenent de les B que tengui al voltant

  public void comprova(){
    for (int i=0;i<ample;i++){
        for (int z=0;z<alt;z++){
            int numeroComprova=0;   //el número que vaig a assignar al botó
            if (elArray[i][z]!=("B")){
                if  (z!=0 && i!=0 && z!=alt-1 && i!=ample-1){
                    System.out.println(i+ " "+ z +" "+ ample +" " +alt);

                    if (elArray[i][z-1]=="B"){
                        numeroComprova++;
                    }
                    if (elArray[i-1][z-1]=="B"){
                        numeroComprova++;
                    }
                    if (elArray[i+1][z-1]=="B"){
                        numeroComprova++;
                    }
                    if (elArray[i][z+1]=="B"){
                        numeroComprova++;
                    }
                    if (elArray[i+1][z+1]=="B"){
                        numeroComprova++;
                    }
                    if (elArray[i-1][z+1]=="B"){
                        numeroComprova++;
                    }
                    if (elArray[i+1][z]=="B"){
                        numeroComprova++;
                    }
                    if (elArray[i-1][z]=="B"){
                        numeroComprova++;
                    }
                    if (numeroComprova!=0){
                          elArray[i][z]=(String.valueOf(numeroComprova));
                    }
                }         
            }                
        }     
    }
  }
  //—- Oculta els botons dels laterals
  public void amagaBotons(){
   for (int i=0;i<ample;i++){
       for (int z=0;z<alt;z++){
   if  (z==0 || i==0 || z==alt-1 || i==ample-1){
      Botons[i][z].setVisible(false);
   }       }    }   
 
}
 
  //—- Mostra el text en tots els botons quan hem trobat una bomba
 
    public void textBotons(){
      for (int i=0;i<ample;i++){
          for (int z=0;z<alt;z++){
            Botons[i][z].setText(elArray[i][z]);
            Botons[i][z].setEnabled(false);      
      //codi per mostrar el text damunt els botons.
           }
      }   

    }
  //—- Mostra el Numero damunt el botó.
  //—- Canvia les propietats del botó mostrat.
 
  public void mostraTextBoto(int i,int z){
    Botons[i][z].setText(elArray[i][z]);
    Botons[i][z].setEnabled(false);
    
    if (elArray[i][z]==" "){
        Botons[i][z].setEnabled(false);
        metodeCasellesProperes(i,z);
    }
    else {
        Botons[i][z].setEnabled(false);
    }
    if (Botons[i][z].getText()=="B"){
         textBotons();
         }
 
 }
  //—-  Posa el número en els botons propers.
 
private void metodeCasellesProperes(int i, int z) {
    if ( z!=0 && i!=0 && z!=alt-1 && i!=ample-1){
    Botons[i-1][z].setEnabled(false);
    Botons[i-1][z-1].setEnabled(false);
    Botons[i-1][z+1].setEnabled(false);
    Botons[i][z-1].setEnabled(false);
    Botons[i][z+1].setEnabled(false);
    Botons[i+1][z].setEnabled(false);
    Botons[i+1][z+1].setEnabled(false);
    Botons[i+1][z-1].setEnabled(false);
 
    Botons[i-1][z].setText(elArray[i-1][z]);
    Botons[i-1][z-1].setText(elArray[i-1][z-1]);
    Botons[i-1][z+1].setText(elArray[i-1][z+1]);
    Botons[i][z-1].setText(elArray[i][z-1]);
    Botons[i][z+1].setText(elArray[i][z+1]);
    Botons[i+1][z].setText(elArray[i+1][z]);
    Botons[i+1][z+1].setText(elArray[i+1][z+1]);
    Botons[i+1][z-1].setText(elArray[i+1][z-1]);
    }
}
 
  //—- Aquest és el mètode que motrarà la fi del joc.
  //—- 17 columnes per 10 linias visibles= 170 Botons[][]
  //—- 170 - 19 Bombes = 171 Botons amb text

  public void finalGame (){
   int contadorFinal=0;
    for (int i=1;i<ample-1;i++){
       for (int z=1;z<alt-1;z++){
       if (Botons[i][z].getText()==" " || Botons[i][z].getText()=="1" ||
        Botons[i][z].getText()=="2" || Botons[i][z].getText()=="3" ||
        Botons[i][z].getText()=="5" || Botons[i][z].getText()=="4" ){
            contadorFinal++;
                 if (contadorFinal==171){
                  this.setTitle("Has Guanyat… oooooo…..");
                  jButton1.setText("NOU…");
                 }        }   }  }     }
 
}