package interfaceGraphique;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

class ComplexCellRenderer implements ListCellRenderer {
  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

  public Component getListCellRendererComponent(JList list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {
	  
    Font theFont = null;
    Color theForeground = null;
    Icon theIcon = null;
    String theText = null;
    
    String room = null;
    String name = null;
    JTextField roomT = new JTextField();
    JButton but = new JButton("salut");
    but.setText("test");

    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
        isSelected, cellHasFocus);

    if (value instanceof Object[]) {
    	Object values[] = (Object[]) value;
//      theFont = (Font) values[0];
//      theForeground = (Color) values[1];
//      theIcon = (Icon) values[2];
//      theText = (String) values[3];
    	
    	room = (String) values[0];
    	name = (String) values[1];
    	roomT.setText(room);
    } else {
      theFont = list.getFont();
      theForeground = list.getForeground();
      theText = "";
    }
//    if (!isSelected) {
//      renderer.setForeground(theForeground);
//    }
//    if (theIcon != null) {
//      renderer.setIcon(theIcon);
//    }
//    
//    renderer.setText(theText);
//    renderer.setFont(theFont);
    renderer.add(roomT);
    renderer.add(but);
    return renderer;
  }
}