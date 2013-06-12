package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import local.IThirdPartyServer;

import client.Client;

import ws.Outlet;

public class OutletPanel extends JPanel{
	
	Outlet outlet;
	
	private JLabel id = new JLabel();
	private JLabel room = new JLabel();
	private JLabel name = new JLabel();

	private JButton on = new JButton("on");
	private JButton off = new JButton("off");
	private JButton timer = new JButton("timer");
	private JButton presence = new JButton("presence");
	private Font UI_light = getFont("UI.ttf");
	
	private ImageIcon lampe_on;
	private ImageIcon lampe_off;
	private ImageIcon timerIco;
	private ImageIcon presenceIco;
	IThirdPartyServer serv;
	
	private Client c;
	
	public OutletPanel(Outlet out, Client c, IThirdPartyServer ser) {
		this.outlet = out;
		this.c = c;
		this.serv = ser;
	}
	
	@Override
	public void paintComponents(Graphics arg0) {
		super.paintComponents(arg0);
		
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

		this.add(id, BorderLayout.WEST);
		//pannelOutlet.add(room, BorderLayout.SOUTH);
		
		this.add(name, BorderLayout.NORTH);

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
		
		this.add(droit, BorderLayout.EAST);
		
		this.add(buttons, BorderLayout.CENTER);
		on.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JButton but = (JButton) arg0.getSource();
				String pan = but.getActionCommand();
				System.out.println("on press" + pan);
				int id_outlet = Integer.parseInt(pan);
				serv.switchOn(id_outlet);
				
				on.repaint();
				off.repaint();
			}
		});

		off.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton but = (JButton) e.getSource();
				String pan = but.getActionCommand();
				System.out.println("off press" + pan);
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
				System.out.println("presence press" + pan);
				int id_outlet = Integer.parseInt(pan);
				serv.simulatePresence(id_outlet, 5);
			}
		});
		
		timer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JButton but = (JButton) arg0.getSource();
				String pan = but.getActionCommand();
				System.out.println("timer press" + pan);
				int id_outlet = Integer.parseInt(pan);
				
		        /* Create and display the form */
				timerSet t = null;
				
		        java.awt.EventQueue.invokeLater(new Runnable() {
		            public void run() {
		                new timerSet().setVisible(true);
		            }
		        });
		        
		        System.out.println(t.getTime());
			}
		});
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
