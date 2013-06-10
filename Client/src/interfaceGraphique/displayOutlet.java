package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import local.IThirdPartyServer;
import ws.Outlet;
import client.Client;

public class displayOutlet extends JFrame {

	private IThirdPartyServer server;

	private JLabel id = new JLabel();
	private JLabel room = new JLabel();
	private JLabel name = new JLabel();

	private JButton on = new JButton("on");
	private JButton off = new JButton("off");
	private JButton timer = new JButton("timer");
	private JButton presence = new JButton("presence");

	JPanel contener = new JPanel();
	JPanel outletPan = new JPanel();
	JPanel gauche = new JPanel();
	
	Client c;
	IThirdPartyServer serv;
	

	public displayOutlet(Client c, final IThirdPartyServer serv) {
		super("Liste");

		this.c = c;
		this.serv = serv;
		
		server = serv;
		this.setSize(500, 750);
		getContentPane().setLayout(new BorderLayout());

		maj_outlets();
		
		this.add(contener);
		// this.setLocationRelativeTo(null);
		this.setLocation(900, 100);
		this.setVisible(true);
	}

	public void maj_outlets() {
		contener = new  JPanel();
		
		contener.setLayout(new BoxLayout(contener, BoxLayout.Y_AXIS));
		
		JScrollPane scroller = new JScrollPane(contener);
		this.getContentPane().add(scroller);
		
		for (Outlet outlet : c.getOutlet()) {

			JPanel pannelOutlet = new JPanel(new BorderLayout());
			JPanel buttons = new JPanel();

			id = new JLabel("" + outlet.getId());
			id.setFont(new Font("sansserif", Font.ROMAN_BASELINE, 30));

			room = new JLabel(outlet.getName());
			room.setFont(new Font("sansserif", Font.PLAIN, 30));

			name = new JLabel(outlet.getRoom() + " - " + outlet.getName());
			name.setFont(new Font("sansserif", Font.ITALIC, 30));

			on = new JButton("on");
			on.setActionCommand("" + outlet.getId());
			on.setFont(new Font("sansserif", Font.BOLD, 20));

			presence = new JButton("Presence");
			presence.setActionCommand("" + outlet.getId());
			presence.setFont(new Font("sansserif", Font.BOLD, 20));
			
			off = new JButton("off");
			off.setFont(new Font("sansserif", Font.BOLD, 20));
			off.setActionCommand("" + outlet.getId());

			off.setBackground(Color.WHITE);
			on.setBackground(Color.WHITE);

			if (outlet.isState())
				on.setBackground(Color.gray);
			else
				off.setBackground(Color.gray);

			pannelOutlet.add(id, BorderLayout.WEST);
			//pannelOutlet.add(room, BorderLayout.SOUTH);
			pannelOutlet.add(name, BorderLayout.NORTH);

			buttons.add(on);
			buttons.add(off);
			buttons.add(presence);

			pannelOutlet.add(buttons, BorderLayout.CENTER);
			on.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JButton but = (JButton) arg0.getSource();
					String pan = but.getActionCommand();
					System.out.println("on press" + pan);
					int id_outlet = Integer.parseInt(pan);
					serv.switchOn(id_outlet);
				}
			});

			off.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JButton but = (JButton) e.getSource();
					String pan = but.getActionCommand();
					System.out.println("off press" + pan);
					int id_outlet = Integer.parseInt(pan);
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
					serv.simulatePresence(id_outlet, 3600);
				}
			});
			
			contener.add(pannelOutlet);
		}
	}
	
	@Override
	public void repaint() {
		maj_outlets();
	}
}
