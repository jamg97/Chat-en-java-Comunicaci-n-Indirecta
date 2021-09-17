/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Elier64
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
public class Draw extends javax.swing.JFrame {
    int x,y =0;
    public  Draw(String titulo) {
    super(titulo);
      setBackground(Color.white);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    Button b = new Button("BORRAR");
    add("North",b);

    final Canvas c = new Canvas() {
            public Dimension getPreferredSize() {
                return new Dimension(320,240);
            }
            public void paint(Graphics g) {
                g.setColor(Color.red);
                g.drawRect(5,5,getWidth()-10,getHeight()-10);
            }
        };

      c.setBackground(Color.yellow);
    add("Center",c);

    pack();
        setVisible(true);

    b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics g = c.getGraphics();
                g.clearRect(0,0,c.getWidth(),c.getHeight());
                c.repaint();
            }
        });

    c.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                
                int [] cor= new int [2];
                Component c = (Component) e.getSource();
                Graphics g = c.getGraphics();
                g.clipRect(10,10,
                           (int)c.getWidth()-20,
                           (int)c.getHeight()-20);
                g.setColor(Color.red);
                g.fillOval(e.getX(),e.getY(),8,8);
                x=e.getX();
                y=e.getY();
                
                cor=getcoordenadas();
            }
        });
    }
    
    public int [] getcoordenadas(){
        int [] cor= new int [2];
        cor[0]=x;
        cor[1]=y;
        
        return cor;
    }
    public Dimension getPreferredSize() {
    return new Dimension(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width,java.awt.Toolkit.getDefaultToolkit().getScreenSize().height-40);
    }
    
    
    public static void main(String args) {
    Draw p1 = new Draw(args);
    }

    
}
