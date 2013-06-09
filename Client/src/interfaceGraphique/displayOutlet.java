package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import local.IThirdPartyServer;
import ws.Outlet;

public class displayOutlet extends JFrame {

	private IThirdPartyServer server;

	private JLabel id = new JLabel();
	private JLabel room = new JLabel();
	private JLabel name = new JLabel();

	private JButton on = new JButton("on");
	private JButton off = new JButton("off");
	private JButton timer = new JButton("timer");
	private JButton presence = new JButton("presence");
	private JCheckBox check = new JCheckBox();

	JPanel contener = new JPanel();
	JPanel outletPan = new JPanel();
	JPanel gauche = new JPanel();
	
	private ActionListenerInscription alc = new ActionListenerInscription();

	public displayOutlet(Outlet outlet, IThirdPartyServer serv) {
		super("Outlets");

		server = serv;
		this.setSize(500, 700);
		getContentPane().setLayout(new FlowLayout());
		
		contener.setLayout(new BorderLayout());

		id.setFont(new Font("Helvetica", Font.PLAIN, 20));
		room.setFont(new Font("Helvetica", Font.ITALIC, 20));
		name.setFont(new Font("Helvetica", Font.PLAIN, 20));

		id.setText("" + outlet.getId());
		room.setText(outlet.getRoom());
		name.setText(outlet.getName());

		gauche.add(id);
		gauche.add(room);
		gauche.add(name);
		
		contener.add(gauche, BorderLayout.WEST);
		
		on.addActionListener(alc);
		outletPan.add(on);
		
		off.addActionListener(alc);
		outletPan.add(off);

		timer.addActionListener(alc);
		outletPan.add(timer);

		presence.addActionListener(alc);
		outletPan.add(presence);

		contener.add(outletPan, BorderLayout.CENTER);

		this.add(contener);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void init() {
		Outlet o1 = new Outlet();
		o1.setId(1);
		o1.setName("Lampe");
		o1.setRoom("Salle à manger");
		o1.setState(false);

		Outlet o2 = new Outlet();
		o2.setId(1);
		o2.setName("TV");
		o2.setRoom("Salle à manger");
		o2.setState(false);

		Object elements[][] = { { o1.getName(), o1.getRoom() },
				{ o2.getName(), o2.getRoom() }, { o2.getName(), o2.getRoom() } };

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JList jlist = new JList(elements);
		ListCellRenderer renderer = new ComplexCellRenderer();
		jlist.setCellRenderer(renderer);
		JScrollPane scrollPane = new JScrollPane(jlist);
		this.add(scrollPane, BorderLayout.CENTER);

		this.setSize(300, 200);
		this.setVisible(true);
	}

	private class ActionListenerInscription implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();

			if (on.equals(obj)) {
				server.switchOn(12);
			}
			
			if (off.equals(obj)) {
				server.switchOff(12);
			}

			if (presence.equals(obj)) {
				server.simulatePresence(45, 4);
			}
			
			if (timer.equals(obj)) {
				server.stopPresenceSimulator(45);
			}

			// //Si on a cliqué sur le bouton inscription
			// if(inscription.equals(obj)){
			// //Créer le ClientTweet
			//
			// //Ajouter le client à la BD
			// if(mdp1Field.getText() == mdp2Field.getText()){
			// nomField.getText();
			// prenomField.getText();
			// loginField.getText();
			// }
			//
			// //Ouvrir son compte
			// dispose();
			// }
		}
	}
}
