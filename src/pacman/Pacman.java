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


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates por andres2288 javayotros.blogspot.com
 * and open the template in the editor.
 */


import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

public class Pacman extends JFrame {

    public Pacman() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new tablero());
        setTitle("P A C M A N - C I D E - 1er DAM");
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setSize(380,420);
        setSize(tablero.NUMEROCOLUMNES*tablero.TAMANYBLOC+16,(tablero.NUMEROFILES+2)*tablero.TAMANYBLOC+16);
        setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon(getClass().getResource("../images/logocide_icon.jpg"));
        setIconImage(img.getImage());
        setVisible(true);        
    }

    public static void main(String[] args) {

          EventQueue.invokeLater(() -> {
            Pacman ex = new Pacman();
            ex.setVisible(true);
        });

    }
}
