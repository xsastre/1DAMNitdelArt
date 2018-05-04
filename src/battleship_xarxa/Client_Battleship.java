/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship_xarxa;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author administrator
 */
public class Client_Battleship {
    public static void main(String[] args) {
        try {
            BattleshipClient partida=new BattleshipClient();
        } catch (IOException ex) {
            Logger.getLogger(Client_Battleship.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
