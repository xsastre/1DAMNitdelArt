/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author xavier
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
    private boolean dying = false;
    boolean scared=false;

    public static final int TAMANYBLOC = 24;
    public static final int NUMEROFILES = 18 ; 
    public static final int NUMEROCOLUMNES = 22;
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


    // LEVELDATA PER 18 FILES 22 COLUMNES
    private final short leveldata[] = {
        19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18,18, 18, 18, 22,
        17, 48, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 48, 20,
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
        17, 16, 16, 16, 16, 16, 24, 24, 24, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 20,  3,  2,  6, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 20,  1,  0,  4, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 20,  9,  8, 12, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        16, 16, 16, 16, 16, 16, 18, 18, 18, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
        17, 48, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 48, 20,
        25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28    };

// LEVELDATA PER 11 FILES I 22 COLUMNES 
/*    private final short leveldata[] = {
        19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18,18, 18, 18, 22,
        17, 48, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 48, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 24, 24, 24, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 20,  3,  2,  6, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        16, 16, 16, 16, 16, 20,  1,  0,  4, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
        17, 16, 16, 16, 16, 20,  9,  8, 12, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 18, 18, 18, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 48, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 48, 20,
        25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28    };
*/   

    
    private final int validspeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxspeed = 6;

    private int currentspeed = 4;
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

    private void initVariables() {
        so_inici = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/pacman-song.wav"));
        so_pacman_menja = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/pacman-waka-waka.wav"));
        so_pacman_menja_fantasma = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/pacman-eating-ghost.wav"));
        so_pacman_menja_cherry = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/pacman-eating-cherry.wav"));
        
        
        screendata = new short[NUMEROFILES * NUMEROCOLUMNES];
        mazecolor = new Color(5, 100, 5);
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

    private void playGame(Graphics2D g2d) {
        
        if (dying) {
            /*if (!this.PacManMort.isAlive())
                this.PacManMort.start();
            */
            //this.so_pacman_menja.stop();
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

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        scared=false;
        g2d.fillRect(50, NUMEROFILES*TAMANYBLOC / 2 - 30, NUMEROCOLUMNES*TAMANYBLOC - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, NUMEROFILES*TAMANYBLOC / 2 - 30, NUMEROCOLUMNES*TAMANYBLOC - 100, 50);

        String s = "Pitja s per començar";
        Font small = new Font("Helvetica", Font.BOLD, 15);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (NUMEROCOLUMNES*TAMANYBLOC - metr.stringWidth(s)) / 2,
                NUMEROFILES*TAMANYBLOC / 2);
    }

    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallfont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, scrsize / 2 + 96, scrsize + 16);

        for (i = 0; i < pacsleft; i++) {
            g.drawImage(pacman3left, i * 28 + 8, scrsize + 1, this);
        }
    }
  public void CheckScared()
  {
    scaredcount--;
    if (scaredcount<=0)
      scared=false;
    if (scared && scaredcount>=30)
      mazecolor=new Color(192,32,255);
    else
      mazecolor=new Color(32,192,255);
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

        if (finished) {

            score += 50;

            if (nrofghosts < maxghosts) {
                nrofghosts++;
            }

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
                ghostx[i]=7*TAMANYBLOC;
                ghosty[i]=7*TAMANYBLOC;
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
            
            if (((ch == 16) || (ch==0)) && ((pacmanx/TAMANYBLOC)==NUMEROCOLUMNES-1) && (!pacmanpassaporta)) {
                porta_dreta=true;
            }
            if (((ch==16) || (ch==0)) && ((pacmanx/TAMANYBLOC)==0) && (!pacmanpassaporta)) {
                porta_esquerra=true;
            }

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
              score+=5;
              so_pacman_menja_cherry.play();
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

    private void initGame() {

        pacsleft = 3;
        scaredtime=maxscaredtime;
        scaredtime=maxscaredtime;        
        score = 0;
        initLevel();
        nrofghosts = 6; //Aquí especificam el número de fantasmes inicials
        currentspeed = 3;  
    }

    private void initLevel() {

        int i;
        for (i = 0; i < NUMEROFILES * NUMEROCOLUMNES; i++) {
            screendata[i] = leveldata[i];
        }
        if (ingame) {
            this.so_inici.play();
            try {
                sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(tablero.class.getName()).log(Level.SEVERE, null, ex);
            }
            //so_pacman_menja.loop();
        }

        continueLevel();
    }

    private void continueLevel() {

        short i;
        int dx = 1;
        int random;

        for (i = 0; i < nrofghosts; i++) {

            ghosty[i] = 7 * TAMANYBLOC;
            ghostx[i] = 7 * TAMANYBLOC;
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
        pacmany = (NUMEROFILES-2) * TAMANYBLOC;
        pacmandx = 0;
        pacmandy = 0;
        reqdx = 0;
        reqdy = 0;
        viewdx = -1;
        viewdy = 0;
        dying = false;
        //if (ingame) 
            // so_pacman_menja.loop();
    }

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
        } else {
            showIntroScreen(g2d);
        }
        
        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

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
                if (key == 's' || key == 'S') {
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

