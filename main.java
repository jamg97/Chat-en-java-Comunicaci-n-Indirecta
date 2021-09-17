
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Administrador;
import Vistas.login;

import javax.swing.JOptionPane;

/**
 *
 * @author jeremy
 */
public class main{
    
    //Administrador admin;
    public static void main(String[] args) {
        
       Administrador admin = new Administrador();
       login abrir = new login(admin);
       abrir.setVisible(true);
        
    }
}
