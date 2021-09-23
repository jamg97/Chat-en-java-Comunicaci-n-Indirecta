/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import org.jgroups.*;
import org.jgroups.jmx.JmxConfigurator;
import org.jgroups.stack.AddressGenerator;
import org.jgroups.util.OneTimeAddressGenerator;
import org.jgroups.util.Util;

import javax.management.MBeanServer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Elier64
 */
public class Chat extends ReceiverAdapter implements  ActionListener{
    
     
    JChannel channel;
    String user_name=null;
    final List<String> state=new LinkedList<String>();
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton benviar;
    
    private javax.swing.JButton bpizarra;
    private javax.swing.JButton bprivado;
    private javax.swing.JTextArea chat;
    //private javax.swing.JTextArea conectados;
    private javax.swing.JList conectados;
    private DefaultListModel modelo;//declaramos el Modelo
    private JScrollPane scrollLista;
    private javax.swing.JTextField text;
    private javax.swing.JScrollPane scrollpane1;
    private javax.swing.JScrollPane scrollpane2;
    private JFrame                 ventana=null;
    JPanel menu;
    private int                    member_size=1;
    
    private List<Address> members=new ArrayList<>();
    // End of variables declaration 
    
   
    public void viewAccepted(View new_view) {
        
           members=new_view.getMembers();
           member_size=new_view.size()-1;
           
           modelo.addElement(members.get(member_size));
                        conectados.setModel(modelo);
    }

   
    
    public void receive(Message msg) {
        String line=msg.getSrc() + ": " + msg.getObject();
        chat.append("\n"+line);
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
        chat.append(list.size() + " Mensajes sin leer");
        
        for(String str: list) {
            chat.append("\n"+str);
        }
    }


    private void start() throws Exception {
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
       
        //eventLoop();
        //channel.close();
    }

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        
            try {
                System.out.print("> "); System.out.flush();
                String line= text.getText();
                if(line.startsWith("quit") || line.startsWith("exit")) {
                   
                }
                line=user_name +": " + line;
                Message msg=new Message(null, null, line);
                channel.send(msg);
                text.setText("");
            }
            catch(Exception e) {
            }
        
    }

    
    
    //----------------------------------------------------------
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    
    
    
    public static void main(String[] args) throws Exception {
        
        Chat ch = new Chat("");
        
    }
//_____________________________________________________________________________________________________________________
  
     public Chat(String args) throws Exception {
         // ....................................................................
        user_name=args;
       // Iniciales de Ventana
        ventana = new JFrame("Chat");
        ventana.setLayout(new BorderLayout(5, 5));
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setPreferredSize(new Dimension(690, 480));
        ventana.setSize(690, 480);
        ventana.setResizable(false); 
        ventana.setLocation(485, 5);
        ventana.setUndecorated(true);
        ventana.setLayout(null);
        ventana.setVisible(true);
       // ....................................................................
       
       menu();
       mostrarMenu();
       start();
    }
   
    public void mostrarMenu(){
     menu.setVisible(true);
     ventana.repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  
 private void menu(){
        menu = new JPanel( );    
        menu.setLayout(null);
        menu.setBounds(0, 0, 690, 480);
        menu.setBackground(new Color(20,20,40));
        
        //..........................................................................
        
        /*JLabel fondo = new JLabel(new ImageIcon(new ImageIcon("assets/images/system/fondo.png").getImage().getScaledInstance(690, 480, java.awt.Image.SCALE_DEFAULT)));
            //fondo.setBounds(x,y,Ancho,alto);
            fondo.setBounds(0, 0, 690, 480);
            fondo.setBorder(null);
            ventana.add(fondo);
            menu.repaint();*/
        
        //..........................................................................
        JButton pizarra = new JButton(new ImageIcon(new ImageIcon("assets/images/system/pizarra.png").getImage().getScaledInstance(100, 50, java.awt.Image.SCALE_DEFAULT)));
        pizarra.setToolTipText("Pizarra");
        styleButton(menu, pizarra, 540, 390,100,50, "assets/images/system/pizarra2.jpg");
        pizarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    Pizarra p = new Pizarra();
                    
                } catch (Exception ex) {
                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        JButton enviar = new JButton(new ImageIcon(new ImageIcon("assets/images/system/enviar.png").getImage().getScaledInstance(100, 29, java.awt.Image.SCALE_DEFAULT)));
        enviar.setToolTipText("enviar");
        styleButton(menu, enviar, 320, 390,100,29, "assets/images/system/enviar2.png");
        enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    eventLoop();
                } catch (Exception ex) {
                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
     
       /* Campo de texto */
       text=new JTextField();
       text.setBounds(10,390,300,29);
       menu.add(text);
       menu.repaint();
     
       chat=new JTextArea();
        chat.setLineWrap(true); //Para que salte de línea al llegar al final del ancho del jTextArea
        scrollpane1=new JScrollPane(chat);
        scrollpane1.setBounds(10,80,500,300);
        menu.add(scrollpane1);
        ventana.repaint();
        
     //instanciamos la lista
        conectados = new JList();
        conectados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );

        //instanciamos el modelo
        modelo = new DefaultListModel();

          
        //instanciamos el Scroll que tendra la lista
        scrollLista = new JScrollPane();
        scrollLista.setBounds(520,80,150,300);
        scrollLista.setViewportView(conectados);
        menu.add(scrollLista);
        
        JLabel label = new JLabel("status", SwingConstants.SOUTH_EAST);
        
     ventana.add(menu);
     ventana.repaint();
     
     
    }
  
 private void contenidoActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }   
 public void adress (){
          
                        
                        
                        
                        conectados.repaint();
                     
          
          
 }

// ...................................................................
  private void styleButton(JPanel nido, JButton boton,int x, int y,int a,int l,String img){
            boton.setBounds(x,y,a,l);
            boton.setPressedIcon(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(100, 50, java.awt.Image.SCALE_DEFAULT)));
            boton.setContentAreaFilled(false);
            boton.setBorder(null);
            nido.add(boton);
            
    }  
   // ...................................................................

   
   
    
}
