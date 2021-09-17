/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vistas.Registrar;
import static java.awt.image.ImageObserver.HEIGHT;
import javax.swing.JOptionPane;

/**
 *
 * @author jeremy
 */
public class Administrador extends Persona {
    
//declaracion de variables
    private String contraseña;
    private String condicion;
    
    
    //construtor

    public Administrador(String contraseña, String condicion, String nombre, String apellido, String correo) {
        super(nombre, apellido, correo);
        this.contraseña = contraseña;
        this.condicion = condicion;
    }   
    
    public Administrador(){
    
        super();
        contraseña = "";
        condicion = "";
       
        
    }
    
    //setter

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

  
    
    //Getter

    public String getContraseña() {
        return contraseña;
    }

    public String getCondicion() {
        return condicion;
    }

    
    
}


