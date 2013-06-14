package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import local.IThirdPartyServer;
import ws.Outlet;
import client.Client;

public class DisplayOutlet extends JFrame {

	private JLabel id = new JLabel();
	private JLabel room = new JLabel();
	private JLabel name = new JLabel();

	private JButton on = new JButton("on");
	private JButton off = new JButton("off");
	private JButton timer = new JButton("timer");
	private JButton presence = new JButton("presence");
	private Font UI_light = getFont("UI.ttf");
	JPanel statusPanel = new JPanel();
	
	private ImageIcon lampe_on;
	private ImageIcon lampe_off;
	private ImageIcon timerIco;
	private ImageIcon presenceIco;

	JPanel contener = new JPanel();
	JPanel outletPan = new JPanel();
	JPanel gauche = new JPanel();
	
	Client c;
	IThirdPartyServer serv;
	DisplayOutlet d;
	String time = "";

	public void setTime(String time) {
		this.time = time;
	}

	public DisplayOutlet(Client c, final IThirdPartyServer serv) {
		super("Liste");
		d = this;
		
		lampe_on = new ImageIcon("img/lampe_on.png");
		lampe_off = new ImageIcon("img/lampe_off.png");
		timerIco = new ImageIcon("img/time.png");
		presenceIco = new ImageIcon("img/presence.png");
		
		this.c = c;
		this.serv = serv;
		
		this.serv = serv;
		this.setSize(500, 750);
		getContentPane().setLayout(new BorderLayout());
		
		contener = new  JPanel();
		contener.setLayout(new BoxLayout(contener, BoxLayout.Y_AXIS));
		
		JScrollPane vertical = new JScrollPane(outletPan);
		vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contener.add(vertical);

		maj_outlets();
		
		createStatusBar("Client created...");
		
		this.add(contener);
		this.setLocationRelativeTo(null);
		this.setLocation(900, 100);
		this.setVisible(true);
	}

	public void createStatusBar(String msg){
		statusPanel.removeAll();
		
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(this.getWidth(), 20));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		JLabel statusLabel = new JLabel(msg);
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		
		statusPanel.repaint();
		statusPanel.revalidate();
	}
	
	public void maj_outlets() {	
		contener.removeAll();
		
		int nb = 0;
		for (Outlet outlet : c.getOutlet()) {

			JPanel pannelOutlet = new JPanel(new BorderLayout());
			JPanel buttons = new JPanel();

			id = new JLabel("" + outlet.getId());
			id.setFont(UI_light.deriveFont(40f).deriveFont(Font.BOLD));

			room = new JLabel(outlet.getName());
			room.setFont(new Font("sansserif", Font.PLAIN, 30));

			name = new JLabel(outlet.getRoom() + " - " + outlet.getName());
			name.setFont(UI_light.deriveFont(25f).deriveFont(Font.BOLD));

			on = new JButton("on");
			on.setActionCommand("" + outlet.getId());
			on.setFont(new Font("sansserif", Font.BOLD, 20));

			presence = new JButton("Presence");
			presence.setActionCommand("" + outlet.getId());
			presence.setFont(new Font("sansserif", Font.BOLD, 12));
			
			off = new JButton("off");
			off.setFont(new Font("sansserif", Font.BOLD, 20));
			off.setActionCommand("" + outlet.getId());

			off.setBackground(Color.WHITE);
			on.setBackground(Color.WHITE);

			timer = new JButton("Timer");
			timer.setActionCommand("" + outlet.getId());
			timer.setFont(new Font("sansserif", Font.BOLD, 12));
			
			if (outlet.isState())
				on.setBackground(Color.gray);
			else
				off.setBackground(Color.gray);

			pannelOutlet.add(id, BorderLayout.WEST);
			pannelOutlet.add(name, BorderLayout.NORTH);

			buttons.add(on);
			buttons.add(off);
			
			JPanel droit = new JPanel();
			
			if(outlet.isState())
				droit.add(new JLabel(lampe_on));
			else
				droit.add(new JLabel(lampe_off));
			
			boolean affichage = true;
			
			if(c.asTimer(outlet.getId())) {
				droit.add(new JLabel(timerIco));
				affichage = false;
			}
				
			if(c.asPresence(outlet.getId())) {
				droit.add(new JLabel(presenceIco));
				affichage = false;
			}
			
			if(affichage) {
				buttons.add(presence);
				buttons.add(timer);
			}
			
			pannelOutlet.add(droit, BorderLayout.EAST);
			pannelOutlet.add(buttons, BorderLayout.CENTER);
			
			on.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JButton but = (JButton) arg0.getSource();
					String pan = but.getActionCommand();
					
					int id_outlet = Integer.parseInt(pan);
					serv.switchOn(id_outlet);
					
					contener.validate();
				}
			});

			off.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JButton but = (JButton) e.getSource();
					String pan = but.getActionCommand();

					int id_outlet = Integer.parseInt(pan);
					boolean swichooffgo = true;
					
					/**
					 * Stop presence simulator
					 */
					if(c.asPresence(id_outlet)){
						swichooffgo = false;
						serv.stopPresenceSimulator(id_outlet);
					}
					
					/**
					 * Stop timer
					 */
					if(c.asTimer(id_outlet)){
						swichooffgo = false;
						serv.stopTimer(id_outlet);
					}
					
					if(swichooffgo)
						serv.switchOff(id_outlet);
				}
			});

			presence.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JButton but = (JButton) arg0.getSource();
					String pan = but.getActionCommand();

					int id_outlet = Integer.parseInt(pan);
					
					DialoguePresence pre = new DialoguePresence(d, true);
					pre.setVisible(true);
					
					if(pre.isValide())
						serv.simulatePresence(id_outlet, pre.getFrequency());
				}
			});
			
			timer.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JButton but = (JButton) arg0.getSource();
					String pan = but.getActionCommand();

					int id_outlet = Integer.parseInt(pan);
					
			        /* Create and display the form */
					DialogueTimer t = new DialogueTimer(d, true);
					t.setVisible(true);
					
					if(t.isValid())
						serv.setTimer(id_outlet, t.getTime(), t.getMode());
				}
			});
			
			/**
			 * Set the background color
			 */
			if(nb%2==0){ 
				pannelOutlet.setBackground(new Color(208,208,208));
				gauche.setBackground(new Color(208,208,208));
				buttons.setBackground(new Color(208,208,208));
				droit.setBackground(new Color(208,208,208));
			}
			
			contener.add(pannelOutlet);
			nb++;
		}
		
		contener.repaint();
		contener.validate();
	}
	
	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
		maj_outlets();
	}
	
	@Override
	public void repaint() {
		maj_outlets();
	}
	
	/**
	 * Get a font by name
	 * @param path
	 * @return
	 */
	public Font getFont(String path){
		File fontFile = new File(path);
		Font font = null;
		
		if (fontFile.exists())
		{
		    if (fontFile.getName().toLowerCase().endsWith(".ttf"))
		    {
		        try {
					font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
				} catch (FontFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
		
		return font;
	}
}
