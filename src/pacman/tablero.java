/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author Hugo,Alberto y Salva
 */


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.applet.AudioClip;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class tablero extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallfont = new Font("Helvetica", Font.BOLD, 14);
    AudioClip so_inici,so_pacman_menja,so_pacman_menja_cherry,so_pacman_menja_fantasma;
    private Image ii;
    private final Color dotcolor = new Color(192, 192, 0);
    int	bigdotcolor=192;
    int	dbigdotcolor=-2;
    private Color mazecolor;

    private boolean ingame = false;
    private boolean gameover = false;
    private boolean dying = false;
    boolean scared=false;

    public static final int TAMANYBLOC = 24;
    public static final int NUMEROFILES = 13 ; 
    public static final int NUMEROCOLUMNES = 33;
    private final int scrsize = NUMEROFILES * TAMANYBLOC;
    private final int pacanimdelay = 2;
    private final int ghostanimcount=2;
    private final int pacmananimcount = 4;
    private final int maxghosts = 12;
    private final int pacmanspeed = 6;

    private int pacanimcount = pacanimdelay;
    private int pacanimdir = 1;
    private int	ghostanimpos=0;
    private int pacmananimpos = 0;
    private boolean pacmanpassaporta=false;
    private boolean[] fantasmapassaporta;
    private int nrofghosts = 6;
    private int pacsleft, score;
    private int deathcounter;
    private int[] dx, dy;
    private int[] ghostx, ghosty, ghostdx, ghostdy, ghostspeed;

    private Image ghost1,ghost2,ghostscared1,ghostscared2,ghost;
    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacmanx, pacmany, pacmandx, pacmandy;
    private int reqdx, reqdy, viewdx, viewdy;
    int	scaredcount, scaredtime;
    final int maxscaredtime=120;
    final int minscaredtime=20;
    Thread thread = new Thread(new So_Pacman_Mort());

    // MAPA 13x33 COOP CIDE 
    private final short leveldata[] = {
        19,26,26,26,26,18,18,26,26,26,26,18,18,26,26,26,26,18,18,26,26,26,22,11,10,10, 2, 6,19,26,18,26,22,
        21, 3,10,10,14,17,20, 3,10,10, 6,17,20, 2,10,10, 2,17,20, 3,10, 6,17,26,26,22, 9,12,21, 5,21, 7,21,
        21, 5,19,26,26,24,20, 5, 3, 6, 5,17,20, 4, 3, 6, 1,17,20, 5,15, 5,21, 3, 6,17,18,26,28, 5,21, 5,21,
        21, 5,21,11,10,14,21, 5, 1, 4, 5,17,20, 5, 1, 4, 5,17,20, 1,10,12,21, 1, 5,17,20,11,10, 4,21, 5,21,
        21, 5,25,26,26,18,20, 5, 9,12, 5,17,20, 5, 9,12, 5,17,36, 5,19,18,20, 1, 5,17,24,26,22, 5,21, 5,21,
        21, 9,10,10,14,17,20, 9,10,10,12,17,20, 9,10,10,12,17,20,13,17,20,11, 8,12,21,11,14,21,13,21,13,21,
        16,26,26,26,26,32,24,26,26,26,18,24,24,26,26,18,18,24,24,26,24,16,18,26,26,24,18,18,16,26,24,26,16,
        21, 3, 2, 2, 6,21, 3, 2, 2, 6,21, 3,10,10,14,17,20,11, 2, 2,14,17,20, 3,10, 6,25,32,20, 3,10,14,21,
        21, 9, 8, 8,12,21, 9, 8, 8, 4,21, 5,19,18,18,16,16,22, 1, 4,19,16,20, 5, 7, 1, 6,17,20, 5,11,10,20,
        17,26,26,26,26,24,26,26,22, 5,21, 5,17,32,16, 0, 0, 4, 1, 4,17,32,20, 5, 5, 1, 4,17,20, 1,10,14,21,
        21,11,10,10,10,10,10,14,21,13,21, 5,25,24,24, 0, 0,12, 1, 4,25,16,20, 5,13, 1,12,17,20, 5,11,10,20,
        17,26,26,26,26,26,26,26,24,26,20, 9,10,10,14,17,20,11, 8, 8,14,17,20, 9,10,12,19,16,20, 9,10,14,21,
        29,11,10,10,10,10,10,10,10,14,25,26,26,26,26,24,24,26,26,26,26,24,24,26,26,26,24,24,24,26,26,26,28,  };

    // Mapa 13x33 SOM CIDE
    private final short leveldata2[] = {
        19,26,26,26,22,13,19,26,26,18,26,26,26,22, 3,10, 2,10, 6,19,26,26,26,18,26,26,22,13,19,26,42,26,22,
        21, 3, 2, 6,25,18,28, 3, 6,21, 3,10, 6,21, 5,23,13,23, 5,21, 3,10, 6,21, 3, 6,25,18,28, 3, 2, 6,21,
        21, 9, 8, 8,14,21,11, 8,12,21,13,23,13,21,13,17,10,20,13,21,13,23,13,21, 9, 8,14,21,11, 8, 8,12,21,
        17,26,26,26,18,24,26,26,18,24,26,16,26,24,18,28, 7,25,18,24,26,24,18,24,18,26,26,16,18,26,26,26,20,
        21, 3,10,14,21, 3,10, 6,21, 3, 6,29, 3, 6,21, 3, 8, 6,21, 3,10,14,21, 7,21, 3, 6,25,20, 3,10,14,21,
        21, 5,43,26,20, 5, 5, 5,21, 1, 8, 2, 8, 4,21,13,23,13,21, 5,19,26,20, 5,21, 1, 8, 6,21, 5,27,26,20,
        21, 9,10, 6,21, 5, 5, 5,21, 5,23,13,23, 5,17,26,16,26,20, 5,21,15,21, 5,21, 5,15, 5,21, 1,10,14,21,
        17,26,30, 5,21, 5,13, 5,21, 5,17,26,20, 5,21, 7,29, 7,21, 5,25,26,20, 5,21, 1, 2,12,21, 5,27,26,20,
        21,11,10,12,21, 9,10,12,21,13,21,15,21,13,21, 9, 2,12,21, 9,10,14,21,13,21, 9,12,19,20, 9,10,14,21,
        17,26,18,26,24,18,26,26,16,26,16,26,24,18,24,22,13,19,24,18,26,26,16,26,16,26,26,16,24,26,18,26,20,
        21, 7,21,11, 6,29, 3,14,21, 7,21,11, 6,21, 7,25,10,28, 7,21, 3,14,21, 7,21,11, 6,21, 3,14,21, 7,21,
        45, 5,25,22, 9,10,12,19,28, 5,25,22, 5,21, 9,10,10,10,12,21, 5,19,28, 5,25,22, 9,10,12,19,28, 5,45,
        11, 8,14,25,26,26,26,28,11, 8,14,29,13,25,26,26,26,26,26,28,13,29,11, 8,14,25,26,26,26,28,11, 8,14  
    };
    
    //Mapa 13x33 NIT DE L'ART
    private final short leveldata3[] = {
        19,26,18,26,18,18,26,18,26,26,26,26,26,18,26,26,26,26,26,22, 3,10,10,10,10,10, 6,19,26,26,18,26,22,
        21, 7,21, 7,25,20, 7,21,11,10, 2,10,14,21,11, 2, 0, 2,14,21, 5,19,26,42,26,22, 5,21, 3, 6,21, 7,21,
        21,13,21, 1, 6,45, 5,17,26,22, 5,19,26,24,22, 9, 8,12,19,20,13,21,11,10,14,21,13,21, 9,12,21,13,21,
        17,26,20, 1, 8, 2, 4,21, 7,21, 5,21, 3, 6,25,18,26,18,24,16,26,24,26,18,26,24,26,24,18,26,24,26,20,
        21, 7,21, 5,23, 9, 4,21, 5,21, 5,21, 1, 0, 6,21, 7,21, 7,21, 3,10, 6,21, 3,10,10, 6,21,11, 2,14,21,
        21,13,21,13,17,22,13,21,13,21,13,21, 9, 8,12,21, 5,21,13,21, 5,15, 5,21, 5,11,14,13,17,22, 5,19,28,
        17,26,24,18,24,24,26,16,18,24,26,24,18,26,26,20, 5,17,18,20, 1,10, 4,21, 1,10, 6,27,16,20, 5,21, 7,
        21, 3, 6,21, 3,10, 6,25,20, 3,10,14,21, 3, 6,21, 5,25,16,20, 5,23, 5,21, 5,39, 9, 6,17,20, 5,21, 5,
        21, 9,12,21, 5, 7, 9, 6,21, 5,27,26,20, 1, 4,21, 9,14,17,20,13,21,13,21,13,17,22,13,17,20, 5,21, 5,
        25,18,26,20, 5, 1,14, 5,21, 1,10,14,21, 9,12,17,18,18,24,24,18,24,26,16,26,24,24,26,16,24,26,20,13,
         7,21,15,21, 5,13, 3,12,21, 5,43,26,16,26,26,16, 8,20,11,14,21,11, 6,21,11,10,10,14,21, 3, 6,25,22,
         5,25,26,20, 9,10,12,19,20, 9,10,14,21, 3, 6,21, 0,17,26,26,24,22, 5,25,26,18,26,26,20, 9, 8,14,21,
         9,10,14,25,26,26,26,24,24,26,26,26,28, 9,12,25,26,28,11,10,14,29, 9,10,14,29,11,14,25,26,26,26,28,
    };

    //Velocidades del juego
    private final int validspeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxspeed = 6;

    private int currentspeed = 2;
    private short[] screendata;
    private Timer timer;


    public tablero() {

        loadImages();
        initVariables();

        addKeyListener(new TAdapter());

        setFocusable(true);

        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }
    //Inicia variables
    private void initVariables() {
        so_inici = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/pacman-song.wav"));
        so_pacman_menja = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/pacman-waka-waka.wav"));
        so_pacman_menja_fantasma = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/pacman-eating-ghost.wav"));
        so_pacman_menja_cherry = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/pacman-eating-cherry.wav"));
        screendata = new short[NUMEROFILES * NUMEROCOLUMNES];
        mazecolor = new Color(255,255,255);
        d = new Dimension(400, 400);
        ghostx = new int[maxghosts];
        ghostdx = new int[maxghosts];
        ghosty = new int[maxghosts];
        ghostdy = new int[maxghosts];
        ghostspeed = new int[maxghosts];
        fantasmapassaporta = new boolean[maxghosts];
        for (int i=0;i<maxghosts;i++)
            fantasmapassaporta[i]=false;
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void doAnim() { // Simula que obri i tanca la boca el pacman

        pacanimcount--;

        if (pacanimcount <= 0) {
            pacanimcount = pacanimdelay;
            pacmananimpos = pacmananimpos + pacanimdir;
            ghostanimpos++;
            if (ghostanimpos>=ghostanimcount)
              ghostanimpos=0;

            if (pacmananimpos == (pacmananimcount - 1) || pacmananimpos == 0) {
                pacanimdir = -pacanimdir;
            }
        }
    }
    //Dibuja el mapa
    private void playGame(Graphics2D g2d) {

            if (dying) {
                 if (!thread.isAlive()) {
                    thread = new Thread(new So_Pacman_Mort());
                    thread.start();
            }

            death(g2d);

        } else {

            CheckScared();
            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }
    //Metodo que muestra el mensaje al empezar el juego
    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        scared=false;
        g2d.fillRect(50, NUMEROFILES*TAMANYBLOC / 2 - 30, NUMEROCOLUMNES*TAMANYBLOC - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, NUMEROFILES*TAMANYBLOC / 2 - 30, NUMEROCOLUMNES*TAMANYBLOC - 100, 50);

        String s = "Pulsa espacio para empezar.";
        Font small = new Font("Helvetica", Font.BOLD, 15);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (NUMEROCOLUMNES*TAMANYBLOC - metr.stringWidth(s)) / 2,
                NUMEROFILES*TAMANYBLOC / 2);
    }
    //Metodo que muestra el mensaje cuando pierdes
    private void GameOver(Graphics2D g2d){
    g2d.setColor(new Color(0, 32, 48));
        scared=false;
        g2d.fillRect(50, NUMEROFILES*TAMANYBLOC / 2 - 30, NUMEROCOLUMNES*TAMANYBLOC - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, NUMEROFILES*TAMANYBLOC / 2 - 30, NUMEROCOLUMNES*TAMANYBLOC - 100, 50);

        String s = "¡GAME OVER!";
        String x = "Pulsa espacio para empezar otra partida";
        Font small = new Font("Helvetica", Font.BOLD, 15);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (NUMEROCOLUMNES*TAMANYBLOC - metr.stringWidth(s)) / 2,
                NUMEROFILES*TAMANYBLOC / 2 -10);
        g2d.drawString(x, (NUMEROCOLUMNES*TAMANYBLOC - metr.stringWidth(x)) / 2,
                NUMEROFILES*TAMANYBLOC / 2 + 10);
    }
    //Dibuja el Score        
    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallfont);
        g.setColor(new Color(96, 128, 255));
        s = "Punts: " + score;
        g.drawString(s, scrsize / 2 + 550, scrsize + 16);

        for (i = 0; i < pacsleft; i++) {
            g.drawImage(pacman3left, i * 28 + 8, scrsize + 1, this);
        }
    }
    
  //Metodo que marca el color de los bloques que varia si los fantasmas estan asustados
  public void CheckScared()
  {
    scaredcount--;
    if (scaredcount<=0)
      scared=false;
    if (scared && scaredcount>=30)
      mazecolor=new Color(192,32,255);//Tablero Fantasmas asustados
    else
      mazecolor=new Color(32,192,32); //ColorTrablero Default
    if (scared)
    {
      //screendata[7*NUMEROFILES+6]=11;
      //screendata[7*NUMEROFILES+8]=14;
    }
    else
    {
      //screendata[7*NUMEROFILES+6]=10;
      //screendata[7*NUMEROFILES+8]=10;
    }
  }

    private void checkMaze() {

        short i = 0;
        boolean finished = true;

        while (i < NUMEROFILES * NUMEROCOLUMNES && finished) {

            if ((screendata[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }
        //Si se completa el nivel suma puntos, aumeta la velocidad de los fantasmas e inicia otro nivel
        if (finished) {

            score += 50;

            /*if (nrofghosts < maxghosts) {
                nrofghosts++;
            }*/
            
            if (currentspeed < maxspeed) {
                currentspeed++;
            }
            scaredtime=scaredtime-20;
            if (scaredtime<minscaredtime)
                scaredtime=minscaredtime;
            initLevel();
        }
    }

    private void death(Graphics2D g2d) {//murio
        int k;
        deathcounter--;
        k=(deathcounter&15)/4;
        switch(k)
        {
            case 0:
                g2d.drawImage(pacman2down, pacmanx + 1, pacmany + 1, this);
                break;
            case 1:
                g2d.drawImage(pacman4right,pacmanx+1,pacmany+1,this);
                break;
            case 2:
                g2d.drawImage(pacman4down,pacmanx+1,pacmany+1,this);
                break;
            default:
                g2d.drawImage(pacman4left,pacmanx+1,pacmany+1,this);
        }
        if (deathcounter==0){
        pacsleft--;

        if (pacsleft == 0) {
            gameover = true;
            ingame = false;
           
        }
        continueLevel();
            
        }
    }

    private void moveGhosts(Graphics2D g2d) {//mover fantasmas

        short i;
        int pos;
        int count;
        boolean[] porta_dreta=new boolean[maxghosts];
        boolean[] porta_esquerra=new boolean[maxghosts];
        for (int z=0;z<maxghosts;z++) {
            porta_dreta[z]=false;
            porta_esquerra[z]=false;
        }
            

        for (i = 0; i < nrofghosts; i++) {
            if (ghostx[i] % TAMANYBLOC == 0 && ghosty[i] % TAMANYBLOC == 0) {
                pos = ghostx[i] / TAMANYBLOC + NUMEROCOLUMNES * (int) (ghosty[i] / TAMANYBLOC);

                count = 0;

                if (((screendata[pos] == 0) || (screendata[pos]==16)) && ((ghostx[i]/TAMANYBLOC)==NUMEROCOLUMNES-1) &&
                        !(fantasmapassaporta[i])) {
                    porta_dreta[i]=true;
                }
                if (((screendata[pos]==00) || (screendata[pos]==16)) && ((ghostx[i]/TAMANYBLOC)==0) &&
                        !(fantasmapassaporta[i])) {
                    porta_esquerra[i]=true;
                }

                
                
                if ((screendata[pos] & 1) == 0 && ghostdx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screendata[pos] & 2) == 0 && ghostdy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screendata[pos] & 4) == 0 && ghostdx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screendata[pos] & 8) == 0 && ghostdy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screendata[pos] & 15) == 15) {
                        ghostdx[i] = 0;
                        ghostdy[i] = 0;
                    } else {
                        ghostdx[i] = -ghostdx[i];
                        ghostdy[i] = -ghostdy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghostdx[i] = dx[count];
                    ghostdy[i] = dy[count];
                }

            }
            
            if (porta_dreta[i]) {
                ghostx[i]=0;
                fantasmapassaporta[i]=true;
            } else
                if (porta_esquerra[i]) {
                    ghostx[i]=(NUMEROCOLUMNES-1)*TAMANYBLOC;
                    fantasmapassaporta[i]=true;
                }
                else {
                    ghostx[i] = ghostx[i] + (ghostdx[i] * ghostspeed[i]);
                    fantasmapassaporta[i]=false;
                }
            ghosty[i] = ghosty[i] + (ghostdy[i] * ghostspeed[i]);
            drawGhost(g2d, ghostx[i] + 1, ghosty[i] + 1);
            if (pacmanx>(ghostx[i]-12) && pacmanx<(ghostx[i]+12) &&
                pacmany>(ghosty[i]-12) && pacmany<(ghosty[i]+12) && ingame)
            {
              if (scared)
              {
                so_pacman_menja_fantasma.play();
                score+=10;
               
                ghosty[i] = 2 * TAMANYBLOC;
                ghostx[i] = 16 * TAMANYBLOC;
              }
              else
              {
                dying=true;
                deathcounter=64;
              }
            }        }
    }

/*    private void drawGhost(Graphics2D g2d, int x, int y) {

        g2d.drawImage(ghost, x, y, this);
    }
*/
    public void drawGhost(Graphics2D g2d, int x, int y)
    {
      if (ghostanimpos==0 && !scared)
      {
        g2d.drawImage(ghost1,x,y,this);
      }
      else if (ghostanimpos==1 && !scared)
      {
        g2d.drawImage(ghost2,x,y,this);
      }
      else if (ghostanimpos==0 && scared)
      {
        g2d.drawImage(ghostscared1,x,y,this);
      }
      else if (ghostanimpos==1 && scared)
      {
        g2d.drawImage(ghostscared2,x,y,this);
      }
    }
    //Metodo que controla el movimiento del pacman
    private void movePacman() {

        int pos;
        short ch;
        boolean porta_dreta=false;
        boolean porta_esquerra=false;

        if (reqdx == -pacmandx && reqdy == -pacmandy) {
            pacmandx = reqdx;
           pacmandy = reqdy;
            viewdx = pacmandx;
            viewdy = pacmandy;
        }

        if (pacmanx % TAMANYBLOC == 0 && pacmany % TAMANYBLOC == 0) {
            pos = pacmanx / TAMANYBLOC + NUMEROCOLUMNES * (int) (pacmany / TAMANYBLOC);
            ch = screendata[pos];
            //Puerta derecha
            if (( (ch==0) ||(ch==16)) && ((pacmanx/TAMANYBLOC)==NUMEROCOLUMNES-1) && (!pacmanpassaporta)) {
                porta_dreta=true;
            }
            //Puerta izquierda
            if (( (ch==0) ||(ch==16)) && ((pacmanx/TAMANYBLOC)==0) && (!pacmanpassaporta)) {
                porta_esquerra=true;
            }
            //Cada vez que el pacman pasa por un punto suma en el score y suena el sonido
            if ((ch & 16) != 0) {
                screendata[pos] = (short) (ch & 15);
                so_pacman_menja.play();
                score++;
            }
            if ((ch&32)!=0)
            {
              scared=true;
              scaredcount=scaredtime;
              screendata[pos]=(short)(ch&15);
              so_pacman_menja_cherry.play();
              score+=5;
            }

            if (reqdx != 0 || reqdy != 0) {
                if (!((reqdx == -1 && reqdy == 0 && (ch & 1) != 0)
                        || (reqdx == 1 && reqdy == 0 && (ch & 4) != 0)
                        || (reqdx == 0 && reqdy == -1 && (ch & 2) != 0)
                        || (reqdx == 0 && reqdy == 1 && (ch & 8) != 0))) {
                    pacmandx = reqdx;
                    pacmandy = reqdy;
                    viewdx = pacmandx;
                    viewdy = pacmandy;
                }
            }

            // Check for standstill
            if ((pacmandx == -1 && pacmandy == 0 && (ch & 1) != 0)
                    || (pacmandx == 1 && pacmandy == 0 && (ch & 4) != 0)
                    || (pacmandx == 0 && pacmandy == -1 && (ch & 2) != 0)
                    || (pacmandx == 0 && pacmandy == 1 && (ch & 8) != 0)) {
                pacmandx = 0;
                pacmandy = 0;
            }
        }
        if (porta_dreta) {
            pacmanx=0;
            pacmanpassaporta=true;
        }
        else 
            if (porta_esquerra) {
                pacmanx=(NUMEROCOLUMNES-1)*TAMANYBLOC;
                pacmanpassaporta=true;
            }
            else {
                pacmanx = pacmanx + pacmanspeed * pacmandx;
                pacmanpassaporta=false;
            }
        
        pacmany = pacmany + pacmanspeed * pacmandy;
    }
    //dibujar pacman segun su posicion
    private void drawPacman(Graphics2D g2d) {

        if (viewdx == -1) {
            drawPacnanLeft(g2d);
        } else if (viewdx == 1) {
            drawPacmanRight(g2d);
        } else if (viewdy == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }
    //Pacman mirando arriba
    private void drawPacmanUp(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2up, pacmanx + 1, pacmany + 1, this);
        
                break;
            case 2:
                g2d.drawImage(pacman3up, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4up, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }
    //Pacman mirando abajo 
    private void drawPacmanDown(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2down, pacmanx + 1, pacmany + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3down, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4down, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }
    //Pacman mirando izquierda
    private void drawPacnanLeft(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2left, pacmanx + 1, pacmany + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3left, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4left, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }
    //Pacman mirando derecha
    private void drawPacmanRight(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2right, pacmanx + 1, pacmany + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3right, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4right, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }
    //Pintar el mapa
    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < NUMEROFILES*TAMANYBLOC; y += TAMANYBLOC) {
            for (x = 0; x < NUMEROCOLUMNES*TAMANYBLOC; x += TAMANYBLOC) {

                g2d.setColor(mazecolor);
                g2d.setStroke(new BasicStroke(2));

                if ((screendata[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + TAMANYBLOC - 1);
                }

                if ((screendata[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + TAMANYBLOC - 1, y);
                }

                if ((screendata[i] & 4) != 0) {
                    g2d.drawLine(x + TAMANYBLOC - 1, y, x + TAMANYBLOC - 1,
                            y + TAMANYBLOC - 1);
                }

                if ((screendata[i] & 8) != 0) {
                    g2d.drawLine(x, y + TAMANYBLOC - 1, x + TAMANYBLOC - 1,
                            y + TAMANYBLOC - 1);
                }

                if ((screendata[i] & 16) != 0) {
                    g2d.setColor(dotcolor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }
                
                if ((screendata[i]&32)!=0)
                {
                  g2d.setColor(new Color(224,224-bigdotcolor,bigdotcolor));
                  g2d.fillRect(x+8,y+8,8,8);
                }

                i++;
            }
        }
    }
    //Inicia el juego
    private void initGame() {

        pacsleft = 3; //Numero de vidas 
        scaredtime=maxscaredtime;
        scaredtime=maxscaredtime;        
        score = 0;
        initLevel();
        nrofghosts = 5; //Aquí especificam el número de fantasmes inicials
        currentspeed = 3;       
    }
    
    //Metodo que genera un numero random y dependiendo de este se genera un mapa
    private void GenerarMapa(){
       Random r = new Random();
        int valorDado = r.nextInt(3);
        int i;
        if (valorDado ==0){
            for (i = 0; i < NUMEROFILES * NUMEROCOLUMNES; i++) {
            screendata[i] = leveldata[i];
            }
        } else if (valorDado ==1){
            for (i = 0; i < NUMEROFILES * NUMEROCOLUMNES; i++) {
            screendata[i] = leveldata2[i];
            } 
        } else if (valorDado ==2){
            for (i = 0; i < NUMEROFILES * NUMEROCOLUMNES; i++) {
            screendata[i] = leveldata3[i];
            } 
        }
    }
    //Inicia nivel
    private void initLevel() {
        GenerarMapa();
         if (ingame) {
            this.so_inici.play(); //Sonido de inicio
            try {
                sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(tablero.class.getName()).log(Level.SEVERE, null, ex);
            }
            //so_pacman_menja.loop();
        }
        continueLevel();
    }
    //Metodo que otorga una velocidad random a los fantasmas y marca las zonas de spawn de pacman y fantasmas
    private void continueLevel() { 
        short i;
        int dx = 1;
        int random;

        for (i = 0; i < nrofghosts; i++) {

            ghosty[i] = 2 * TAMANYBLOC;
            ghostx[i] = 16 * TAMANYBLOC;
            ghostdy[i] = 0;
            ghostdx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentspeed + 1));

            if (random > currentspeed) {
                random = currentspeed;
            }

            ghostspeed[i] = validspeeds[random];
        }
        //screendata[7*NUMEROFILES+6]=10;
        //screendata[7*NUMEROFILES+8]=10;
        pacmanx = NUMEROCOLUMNES/2 * TAMANYBLOC;
        pacmany = (NUMEROFILES-3) * TAMANYBLOC;
        pacmandx = 0;
        pacmandy = 0;
        reqdx = 0;
        reqdy = 0;
        viewdx = -1;
        viewdy = 0;
        dying = false;
    }
    //Cargar imagenes 
    private void loadImages() {

        ghost1=new ImageIcon(getClass().getResource("../images/Ghost1.gif")).getImage();
        ghost2=new ImageIcon(getClass().getResource("../images/Ghost2.gif")).getImage();
        ghostscared1=new ImageIcon(getClass().getResource("../images/GhostScared1.gif")).getImage();
        ghostscared2=new ImageIcon(getClass().getResource("../images/GhostScared2.gif")).getImage();
    
        ghost = new ImageIcon(getClass().getResource("../images/ghost.gif")).getImage();
        pacman1 = new ImageIcon(getClass().getResource("../images/pacman.gif")).getImage();
        pacman2up = new ImageIcon(getClass().getResource("../images/up1.gif")).getImage();
        pacman3up = new ImageIcon(getClass().getResource("../images/up2.gif")).getImage();
        pacman4up = new ImageIcon(getClass().getResource("../images/up3.gif")).getImage();
        pacman2down = new ImageIcon(getClass().getResource("../images/down1.gif")).getImage();
        pacman3down = new ImageIcon(getClass().getResource("../images/down2.gif")).getImage();
        pacman4down = new ImageIcon(getClass().getResource("../images/down3.gif")).getImage();
        pacman2left = new ImageIcon(getClass().getResource("../images/left1.gif")).getImage();
        pacman3left = new ImageIcon(getClass().getResource("../images/left2.gif")).getImage();
        pacman4left = new ImageIcon(getClass().getResource("../images/left3.gif")).getImage();
        pacman2right = new ImageIcon(getClass().getResource("../images/right1.gif")).getImage();
        pacman3right = new ImageIcon(getClass().getResource("../images/right2.gif")).getImage();
        pacman4right = new ImageIcon(getClass().getResource("../images/right3.gif")).getImage();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);
        doAnim();

        if (ingame) {
            playGame(g2d);
        } else if (gameover){
            GameOver(g2d);
        }
        else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
    //Control de movimiento
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (ingame) {
                if (key == KeyEvent.VK_LEFT) {
                    reqdx = -1;
                    reqdy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    reqdx = 1;
                    reqdy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    reqdx = 0;
                    reqdy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    reqdx = 0;
                    reqdy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    ingame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
                if (key == ' ') {

                    ingame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                reqdx = 0;
                reqdy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}


