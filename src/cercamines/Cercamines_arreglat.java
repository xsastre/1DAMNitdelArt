/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cercamines;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Insets;
 
public class Cercamines_arreglat extends JFrame {
  private JPanel panell = new JPanel();
  private JButton boto_inici = new JButton();
  private int ample=10;
  private int alt=8;
  public  JButton[][] botons=new JButton [ample][alt];
  public  String [][] matriu_aux_text_botons =new String [ample][alt];
  public int numerobombes;
  boolean perdutonovapartida=false;
 

 
public static void main (String [] args){
   Cercamines_arreglat TabBuscaMin = new Cercamines_arreglat();
}
public Cercamines_arreglat()  {
    try    {
      inicialitzacio_boto_inici();
    }
    catch(Exception e)    {
      e.printStackTrace();
    }
  }
 
    private void inicialitzacio_boto_inici() throws Exception  {
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(483, 380));
        this.setTitle("Cerca mines");
        panell.setBounds(new Rectangle(0, 40, 483, 380));
        panell.setBackground(new Color(162, 175, 227));
        panell.setLayout(null);
        boto_inici.setText("INICI");
        boto_inici.setBounds(new Rectangle(0, 0, 125, 40));
        boto_inici.setFont(new Font("Tahoma", 0, 12));
        boto_inici.setHorizontalTextPosition(SwingConstants.CENTER);
        boto_inici.setAlignmentY((float)0.0);
        boto_inici.setMargin(new Insets(2, 14, 2, 12));
        boto_inici.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    jButton1_actionPerformed(e);
                }
            });
        this.getContentPane().add(boto_inici, null);
        this.getContentPane().add(panell, null);

        this.setVisible(true);  
    }
  
    private void jButton1_actionPerformed(ActionEvent e)  {
        if (perdutonovapartida) {
            recarregarTaulell();
            perdutonovapartida=false;
        } else {
            carregartaulell();
        }
        colocarBomba();
        comprova();
        amaga_botons_marge();
        this.setTitle("Cerca mines");
        boto_inici.setText("INICI");
    }
  
    public void recarregarTaulell(){
        for (int i=0;i<ample;i++){
            for (int z=0;z<alt;z++){
                matriu_aux_text_botons[i][z]="";
                botons[i][z].setText("");
                botons[i][z].setEnabled(true);
            }
        }
    }

  
    public void carregartaulell(){  
        for (int i=0;i<ample;i++){
            for (int z=0;z<alt;z++){
                matriu_aux_text_botons[i][z]="";
                botons[i][z]= new JButton();
                panell.add(botons[i][z]);
                botons[i][z].setBounds(i*25,z*25,25,25);
                botons[i][z].setMargin(new Insets(0, 0, 0, 0));
                botons[i][z].setFont(new Font("Tahoma", 0,10));
                botons[i][z].setText("");
                botons[i][z].setEnabled(true);

                //—– Agrego un ActionListener a cada boton del Array de botons
                //—– Ahora puede escuchar eventos.

                botons[i][z].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent ar) {
                        for (int i=0;i<ample;i++){
                            for (int z=0;z<alt;z++){
                                if (ar.getSource()==botons[i][z]){
                                    monstratextboto(i,z);
                                    comprovafideljoc();
                                }
                            }
                        }
                    }
                }
                ); 
            }
        }
    }
    
  //—- Col·loca el numero de bombes.
    public void colocarBomba(){
        double i=0;
        double z=0;
        int compte_num_bombes=0;
        do  {
            i=Math.random()*getAmple();
            z=Math.random()*getAlt();
            i=(int)i;
            z=(int)z;
            if  (z!=0 && i!=0 && z!=alt-1 && i!=ample-1){
                matriu_aux_text_botons[(int)i][(int) z ]="B";
                compte_num_bombes++;
            }
        }
        while (compte_num_bombes<=ample);
        numerobombes=compte_num_bombes;
    }
    
    public void setAmple (int sAmple){
        ample=sAmple;
    }
    public int getAmple (){
        return ample;
    }
    public void setAlto (int sAlt){
        alt=sAlt;
    }
    public int getAlt (){
        return alt;
    }
 
//—- Asigna un número a cada botó en funció de les B que tengui per devora

    public void comprova(){
        for (int i=0;i<ample;i++){
            for (int z=0;z<alt;z++){
                int numero_bombes_al_voltant=0;   //el numero que voy a asignar al boton
                if (matriu_aux_text_botons[i][z]!=("B")){
                    if  (z!=0 && i!=0 && z!=alt-1 && i!=ample-1){
                       System.out.println(i+ " "+ z +" "+ ample +" " +alt);
                       if (matriu_aux_text_botons[i][z-1]=="B"){
                              numero_bombes_al_voltant++;
                             }
                        if (matriu_aux_text_botons[i-1][z-1]=="B"){
                         numero_bombes_al_voltant++;
                        }
                        if (matriu_aux_text_botons[i+1][z-1]=="B"){
                         numero_bombes_al_voltant++;
                        }
                        if (matriu_aux_text_botons[i][z+1]=="B"){
                         numero_bombes_al_voltant++;
                        }
                        if (matriu_aux_text_botons[i+1][z+1]=="B"){
                         numero_bombes_al_voltant++;
                        }
                        if (matriu_aux_text_botons[i-1][z+1]=="B"){
                         numero_bombes_al_voltant++;
                        }
                        if (matriu_aux_text_botons[i+1][z]=="B"){
                         numero_bombes_al_voltant++;
                        }
                        if (matriu_aux_text_botons[i-1][z]=="B"){
                         numero_bombes_al_voltant++;
                        }
                        if (numero_bombes_al_voltant!=0){
                        matriu_aux_text_botons[i][z]=(String.valueOf(numero_bombes_al_voltant));
                        }
                    }
                }
            }
        }
    }
    
    //—- Oculta els botons dels laterals.
    public void amaga_botons_marge(){
        for (int i=0;i<ample;i++){
            for (int z=0;z<alt;z++){
                if  (z==0 || i==0 || z==alt-1 || i==ample-1){
                    botons[i][z].setVisible(false);
                }
            }
        }   
    }
 
    //—- Mostra el text de tots els botons quan hem trobat mina. 
    public void textBotons(){
        for (int i=0;i<ample;i++){
            for (int z=0;z<alt;z++){
              botons[i][z].setText(matriu_aux_text_botons[i][z]);
              botons[i][z].setEnabled(false);

            }
        }   

    }
    
    //—- Mostra el numero damunt del botó
    //—- Canvia les propietats del botó mostrat
    public void monstratextboto(int i,int z){
        botons[i][z].setText(matriu_aux_text_botons[i][z]);
        botons[i][z].setEnabled(false);

        if (matriu_aux_text_botons[i][z]==""){
            botons[i][z].setEnabled(false);
            metodeCasellesProperes(i,z);
        }
        else {
            botons[i][z].setEnabled(false);
        }
        if (botons[i][z].getText()=="B"){
             textBotons();
             perdutonovapartida=true;
        }
    }
  //—-  Pone el numero en los botones cercanos.
 
    private void metodeCasellesProperes(int i, int z) {
        if ( z!=0 && i!=0 && z!=alt-1 && i!=ample-1){
            botons[i-1][z].setEnabled(false);
            botons[i-1][z-1].setEnabled(false);
            botons[i-1][z+1].setEnabled(false);
            botons[i][z-1].setEnabled(false);
            botons[i][z+1].setEnabled(false);
            botons[i+1][z].setEnabled(false);
            botons[i+1][z+1].setEnabled(false);
            botons[i+1][z-1].setEnabled(false);

            botons[i-1][z].setText(matriu_aux_text_botons[i-1][z]);
            botons[i-1][z-1].setText(matriu_aux_text_botons[i-1][z-1]);
            botons[i-1][z+1].setText(matriu_aux_text_botons[i-1][z+1]);
            botons[i][z-1].setText(matriu_aux_text_botons[i][z-1]);
            botons[i][z+1].setText(matriu_aux_text_botons[i][z+1]);
            botons[i+1][z].setText(matriu_aux_text_botons[i+1][z]);
            botons[i+1][z+1].setText(matriu_aux_text_botons[i+1][z+1]);
            botons[i+1][z-1].setText(matriu_aux_text_botons[i+1][z-1]);
        }
    }
 

    public void comprovafideljoc (){
        int contadorFinal=0;

        for (int i=1;i<ample-1;i++){
           for (int z=1;z<alt-1;z++){
               if (botons[i][z].getText().equals(" ") || 
                    botons[i][z].getText().equals("1") ||
                    botons[i][z].getText().equals("2") ||
                    botons[i][z].getText().equals("3") ||
                    botons[i][z].getText().equals("4") ||
                    botons[i][z].getText().equals("5")) {
                    contadorFinal++;
                    if (contadorFinal==((ample-2)*(alt-2)-numerobombes)){
                        this.setTitle("Has guanyat !!!");
                        boto_inici.setText("COMENÇA DE NOU");
                        perdutonovapartida=true;
                    }
                }
           }
        }
    }
 
}