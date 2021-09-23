package Vistas;

import Vistas.ComandoPizarra;
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


/**
 * Shared whiteboard, each new instance joins the same group. Each instance chooses a random color,
 * mouse moves are broadcast to all group members, which then apply them to their canvas<p>
 * @author Bela Ban, Oct 17 2001
 */
public class Pizarra extends ReceiverAdapter implements ActionListener, ChannelListener {
    protected String               cluster_name="draw";
    private JChannel               channel=null;
    private int                    member_size=1;
    private JFrame                 mainFrame=null;
    private JPanel                 sub_panel=null;
    private DrawPanel              panel=null;
    private JButton                clear_button, leave_button;
    private final Random           random=new Random(System.currentTimeMillis());
    private final Font             default_font=new Font("Helvetica",Font.PLAIN,12);
    private final Color            draw_color=selectColor();
    private static final Color     background_color=Color.white;
    boolean                        no_channel=false;
    boolean                        jmx;
    private boolean                use_state=false;
    private long                   state_timeout=5000;
    private boolean                use_unicasts=false;
    protected boolean              send_own_state_on_merge=true;
    private final                  List<Address> members=new ArrayList<>();


    public Pizarra(String props, boolean no_channel, boolean jmx, boolean use_state, long state_timeout,
                boolean use_unicasts, String name, boolean send_own_state_on_merge, AddressGenerator gen) throws Exception {
        this.no_channel=no_channel;
        this.jmx=jmx;
        this.use_state=use_state;
        this.state_timeout=state_timeout;
        this.use_unicasts=use_unicasts;
        if(no_channel)
            return;

        channel=new JChannel(props);
        if(gen != null)
            channel.addAddressGenerator(gen);
        if(name != null)
            channel.setName(name);
        channel.setReceiver(this);
        channel.addChannelListener(this);
        this.send_own_state_on_merge=send_own_state_on_merge;
    }

    public Pizarra(JChannel channel) throws Exception {
        this.channel=channel;
        channel.setReceiver(this);
        channel.addChannelListener(this);
    }


    public Pizarra(JChannel channel, boolean use_state, long state_timeout) throws Exception {
        this.channel=channel;
        channel.setReceiver(this);
        channel.addChannelListener(this);
        this.use_state=use_state;
        this.state_timeout=state_timeout;
    }

    public Pizarra() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public String getClusterName() {
        return cluster_name;
    }

    public void setClusterName(String clustername) {
        if(clustername != null)
            this.cluster_name=clustername;
    }


     public static void main(String[] args) {
   // public void start() throws Exception {
      //  String[] args = null;
        Pizarra             draw=null;
        String           props=null;
        boolean          no_channel=false;
        boolean          jmx=true;
        boolean          use_state=false;
        String           group_name=null;
        long             state_timeout=5000;
        boolean          use_unicasts=false;
        String           name=null;
        boolean          send_own_state_on_merge=true;
        AddressGenerator generator=null;

       

        try {
            draw=new Pizarra(props, no_channel, jmx, use_state, state_timeout, use_unicasts, name,
                          send_own_state_on_merge, generator);
            if(group_name != null)
                draw.setClusterName(group_name);
            draw.go();
        }
        catch(Throwable e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    } 
    
     public  void start() {
   // public void start() throws Exception {
      //  String[] args = null;
        Pizarra             draw=null;
        String           props=null;
        boolean          no_channel=false;
        boolean          jmx=true;
        boolean          use_state=false;
        String           group_name=null;
        long             state_timeout=5000;
        boolean          use_unicasts=false;
        String           name=null;
        boolean          send_own_state_on_merge=true;
        AddressGenerator generator=null;


        try {
            draw=new Pizarra(props, no_channel, jmx, use_state, state_timeout, use_unicasts, name,
                          send_own_state_on_merge, generator);
            if(group_name != null)
                draw.setClusterName(group_name);
            draw.go();
        }
        catch(Throwable e) {
            e.printStackTrace(System.err);
            System.exit(0);
        }
    }
    

    private Color selectColor() {
        int red=Math.abs(random.nextInt()) % 255;
        int green=Math.abs(random.nextInt()) % 255;
        int blue=Math.abs(random.nextInt()) % 255;
        return new Color(red, green, blue);
    }


    private void sendToAll(byte[] buf) throws Exception {
        for(Address mbr: members)
            channel.send(new Message(mbr, buf));
    }


    public void go() throws Exception {
        if(!no_channel && !use_state)
            channel.connect(cluster_name);
       
        
        mainFrame=new JFrame();
                mainFrame.setBackground(new Color(20,20,40));

        mainFrame.setLayout(new BorderLayout(5, 5));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setPreferredSize(new Dimension(690, 480));
        mainFrame.setSize(690, 480);
        mainFrame.setResizable(false); 
        mainFrame.setLocation(485, 5);
        
        
        panel=new DrawPanel(use_state);
        panel.setBackground(background_color);
        sub_panel=new JPanel();
        mainFrame.getContentPane().add("Center", panel);
        clear_button=new JButton("Borrar");
        clear_button.setBackground(new Color(20,20,40));
        clear_button.setIcon(new ImageIcon(new ImageIcon("assets/images/system/borrador.png").getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT)));
        clear_button.setFont(default_font);
        clear_button.addActionListener(this);
        leave_button=new JButton("Cerrar");
        leave_button.setBackground(new Color(20,20,40));
        leave_button.setIcon(new ImageIcon(new ImageIcon("assets/images/system/salir.png").getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT)));
        leave_button.setFont(default_font);
        leave_button.addActionListener(this);
        sub_panel.add("South", clear_button);
        sub_panel.add("South", leave_button);
        mainFrame.getContentPane().add("South", sub_panel);
        clear_button.setForeground(Color.blue);
        leave_button.setForeground(Color.blue);
        mainFrame.pack();
        mainFrame.setBounds(new Rectangle(690, 480));

        if(!no_channel && use_state) {
            channel.connect(cluster_name, null, state_timeout);
        }
        mainFrame.setVisible(true);
        setTitle();
    }




    void setTitle(String title) {
        String tmp="";
        if(no_channel) {
            mainFrame.setTitle(" Demostracion pizarra ");
            return;
        }
        if(title != null) {
            mainFrame.setTitle(title);
        }
        else {
            if(channel.getAddress() != null)
                tmp+=channel.getAddress();
            tmp+=" (" + member_size + ")";
            mainFrame.setTitle(tmp);
        }
    }

    void setTitle() {
        setTitle(null);
    }



    public void receive(Message msg) {
        byte[] buf=msg.getRawBuffer();
        if(buf == null) {
            System.err.println("[" + channel.getAddress() + "] Error al recibir del buffer " + msg.getSrc() +
                    ", headers: " + msg.printHeaders());
            return;
        }

        try {
            ComandoPizarra comm=(ComandoPizarra)Util.streamableFromByteBuffer(ComandoPizarra.class, buf, msg.getOffset(), msg.getLength());
            switch(comm.mode) {
                case ComandoPizarra.DRAW:
                    if(panel != null)
                        panel.drawPoint(comm);
                    break;
                case ComandoPizarra.CLEAR:
                    clearPanel();
                    break;
                default:
                    System.err.println("***** Ha recibido un comando de pizarra invalido " + comm.mode);
                    break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAccepted(View v) {
        member_size=v.size();
        if(mainFrame != null)
            setTitle();
        members.clear();
        members.addAll(v.getMembers());

        if(v instanceof MergeView) {
            System.out.println("** " + v);


            if(use_state && !members.isEmpty()) {
                Address coord=members.get(0);
                Address local_addr=channel.getAddress();
                if(local_addr != null && !local_addr.equals(coord)) {
                    try {

                        // make a copy of our state first
                        Map<Point,Color> copy=null;
                        if(send_own_state_on_merge) {
                            synchronized(panel.state) {
                                copy=new LinkedHashMap<>(panel.state);
                            }
                        }
                        System.out.println("fetching state from " + coord);
                        channel.getState(coord, 5000);
                        if(copy != null)
                            sendOwnState(copy); // multicast my own state so everybody else has it too
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
            System.out.println("** Vista=" + v);
    }


    public void getState(OutputStream ostream) throws Exception {
        panel.writeState(ostream);
    }

    public void setState(InputStream istream) throws Exception {
        panel.readState(istream);
    }


    public void clearPanel() {
        if(panel != null)
            panel.clear();
    }

    public void sendClearPanelMsg() {
        ComandoPizarra comm=new ComandoPizarra(ComandoPizarra.CLEAR);
        try {
            byte[] buf=Util.streamableToByteBuffer(comm);
            if(use_unicasts)
                sendToAll(buf);
            else
                channel.send(new Message(null, null, buf));
        }
        catch(Exception ex) {
            System.err.println(ex);
        }
    }


    public void actionPerformed(ActionEvent e) {
        String     command=e.getActionCommand();
        switch(command) {
            case "Borrar":
                if(no_channel) {
                    clearPanel();
                    return;
                }
                sendClearPanelMsg();
                break;
            case "Cerrar":
                stop();
                break;
            default:
                System.out.println("Accion desconocida");
                break;
        }
    }


    public void stop() {
        if(!no_channel) {
            try {
                channel.close();
            }
            catch(Exception ex) {
                System.err.println(ex);
            }
        }
        mainFrame.setVisible(false);
        mainFrame.dispose();
    }

    protected void sendOwnState(final Map<Point,Color> copy) {
        if(copy == null)
            return;
        for(Point point: copy.keySet()) {
            // we don't need the color: it is our draw_color anyway
            ComandoPizarra comm=new ComandoPizarra(ComandoPizarra.DRAW, point.x, point.y, draw_color.getRGB());
            try {
                byte[] buf=Util.streamableToByteBuffer(comm);
                if(use_unicasts)
                    sendToAll(buf);
                else
                    channel.send(new Message(null, buf));
            }
            catch(Exception ex) {
                System.err.println(ex);
            }
        }
    }


    public void channelConnected(Channel channel) {
        if(jmx) {
            Util.registerChannel((JChannel)channel, "jgroups");
        }
    }

    public void channelDisconnected(Channel channel) {
        if(jmx) {
            MBeanServer server=Util.getMBeanServer();
            if(server != null) {
                try {
                    JmxConfigurator.unregisterChannel((JChannel)channel,server,cluster_name);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void channelClosed(Channel channel) {

    }



    protected class DrawPanel extends JPanel implements MouseMotionListener {
        protected final Dimension         preferred_size=new Dimension(235, 170);
        protected Image                   img; // for drawing pixels
        protected Dimension               d, imgsize;
        protected Graphics                gr;
        protected final Map<Point,Color>  state;


        public DrawPanel(boolean use_state) {
            if(use_state)
                state=new LinkedHashMap<>();
            else
                state=null;
            createOffscreenImage(false);
            addMouseMotionListener(this);
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    if(getWidth() <= 0 || getHeight() <= 0) return;
                    createOffscreenImage(false);
                }
            });
        }


        public void writeState(OutputStream outstream) throws IOException {
            if(state == null)
                return;
            synchronized(state) {
                DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(outstream));
                // DataOutputStream dos=new DataOutputStream(outstream);
                dos.writeInt(state.size());
                for(Map.Entry<Point,Color> entry: state.entrySet()) {
                    Point point=entry.getKey();
                    Color col=entry.getValue();
                    dos.writeInt(point.x);
                    dos.writeInt(point.y);
                    dos.writeInt(col.getRGB());
                }
                dos.flush();
                System.out.println("wrote " + state.size() + " elements");
            }
        }


        public void readState(InputStream instream) throws IOException {
            DataInputStream in=new DataInputStream(new BufferedInputStream(instream));
            Map<Point,Color> new_state=new LinkedHashMap<>();
            int num=in.readInt();
            for(int i=0; i < num; i++) {
                Point point=new Point(in.readInt(), in.readInt());
                Color col=new Color(in.readInt());
                new_state.put(point, col);
            }

            synchronized(state) {
                state.clear();
                state.putAll(new_state);
                System.out.println("read " + state.size() + " elements");
                createOffscreenImage(true);
            }
        }


        void createOffscreenImage(boolean discard_image) {
            d=getSize();
            if(discard_image) {
                img=null;
                imgsize=null;
            }
            if(img == null || imgsize == null || imgsize.width != d.width || imgsize.height != d.height) {
                img=createImage(d.width, d.height);
                if(img != null) {
                    gr=img.getGraphics();
                    if(gr != null && state != null) {
                        drawState();
                    }
                }
                imgsize=d;
            }
            repaint();
        }


       

        public void mouseMoved(MouseEvent e) {}

        public void mouseDragged(MouseEvent e) {
            int                 x=e.getX(), y=e.getY();
            ComandoPizarra         comm=new ComandoPizarra(ComandoPizarra.DRAW, x, y, draw_color.getRGB());

            if(no_channel) {
                drawPoint(comm);
                return;
            }

            try {
                byte[] buf=Util.streamableToByteBuffer(comm);
                if(use_unicasts)
                    sendToAll(buf);
                else
                    channel.send(new Message(null, null, buf));
            }
            catch(Exception ex) {
                System.err.println(ex);
            }
        }

       
        public void drawPoint(ComandoPizarra c) {
            if(c == null || gr == null) return;
            Color col=new Color(c.rgb);
            gr.setColor(col);
            gr.fillOval(c.x, c.y, 10, 10);
            repaint();
            if(state != null) {
                synchronized(state) {
                    state.put(new Point(c.x, c.y), col);
                }
            }
        }



        public void clear() {
            if(gr == null) return;
            gr.clearRect(0, 0, getSize().width, getSize().height);
            repaint();
            if(state != null) {
                synchronized(state) {
                    state.clear();
                }
            }
        }


        /** Draw the entire panel from the state */
        public void drawState() {
            // clear();
            Map.Entry entry;
            Point pt;
            Color col;
            synchronized(state) {
                for(Iterator it=state.entrySet().iterator(); it.hasNext();) {
                    entry=(Map.Entry)it.next();
                    pt=(Point)entry.getKey();
                    col=(Color)entry.getValue();
                    gr.setColor(col);
                    gr.fillOval(pt.x, pt.y, 10, 10);

                }
            }
            repaint();
        }


        public Dimension getPreferredSize() {
            return preferred_size;
        }


        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(img != null) {
                g.drawImage(img, 0, 0, null);
            }
        }

    }

}

