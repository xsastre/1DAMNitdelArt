/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.applet.AudioClip;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xavier
 */
public class So_Pacman_Mort implements Runnable {
    AudioClip so_pacman_mort;
    public void run(){
       so_pacman_mort = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/pacman-dies.wav"));
       so_pacman_mort.play();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(So_Pacman_Mort.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  }
