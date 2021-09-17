/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Draw;
import Vistas.login;
import Vistas.menuchat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Simplechat extends ReceiverAdapter {
    
 
    Administrador admin = new Administrador();
    Persona p =new Persona();
    
    JChannel channel;
    String nombre_usuario;
    String line;
    final List<String> state=new LinkedList<String>();
    
    
    /*
     public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
     
    public String getNombre_usuario() {
        return nombre_usuario;
    }
    */
    public void viewAccepted(View new_view) {
        System.out.println("** Vista: " + new_view);
    }

    public void receive(Message msg) {
        String line=msg.getSrc() + ": " + msg.getObject();

        System.out.println(line);
        synchronized(state) {
            state.add(line);
        }
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(state) {
            Util.objectToStream(state, new DataOutputStream(output));
        }
    }

    @SuppressWarnings("unchecked")
    public void setState(InputStream input) throws Exception {
        List<String> list=(List<String>)Util.objectFromStream(new DataInputStream(input));
        synchronized(state) {
            state.clear();
            state.addAll(list);
        }
        System.out.println("Estado recibido (" + list.size() + " Historial del chat):");
        for(String str: list) {
            System.out.println(str);
        }
    }


    public void start() throws Exception {
      
       
        nombre_usuario = JOptionPane.showInputDialog("Introduce tu nombre"); 
        //nombre_usuario = admin.getNombre();
       
        menuchat mn= new menuchat();
        mn.setVisible(true);
        
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
        eventLoop();
        channel.close();
    }

    
    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                line=in.readLine().toLowerCase();
                
                if(line.startsWith("quit") || line.startsWith("exit")) {
                    break;
                }
                if(line.startsWith("draw")) {
                    Draw pizarra = new Draw("Profesor");
                    int [] cor= new int [2];
                    while(true){
                    cor=pizarra.getcoordenadas();
                    String c = Arrays.toString(cor);
                    c="[" + nombre_usuario + "] " + c;
                    Message msg=new Message(null, null, c);
                    channel.send(msg);
                    }
                }
                line="[" + nombre_usuario + "] " + line;
                Message msg=new Message(null, null, line);
                channel.send(msg);
            }
            catch(Exception e) {
            }
        }
    }

    
    
    
    public static void main(String[] args) throws Exception {
        
        
        new Simplechat().start();
        
        
    }

    public void pizarra() throws Exception{
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
         Draw pizarra = new Draw("Profesor");
                    int [] cor= new int [2];
                    while(true){
                    cor=pizarra.getcoordenadas();
                    String c = Arrays.toString(cor);
                    c="[" + nombre_usuario + "] " + c;
                    Message msg=new Message(null, null, c);
                    channel.send(msg);
                    }
    
        
    }
    
   
}