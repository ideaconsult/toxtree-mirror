/*
Copyright (C) 2005-2007  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public License
as published by the Free Software Foundation; either version 2.1
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
*/

package toxTree.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class PreferencesPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5074505644698297727L;
	public PreferencesPanel() {
		addWidgets();
	}
	protected void addWidgets() {
			Dimension d = new Dimension(Integer.MAX_VALUE,24);
			setLayout(new SpringLayout());
			int rows = 0;
   			for (int i=0; i < Preferences.default_values.length; i++) {
   				if ((Boolean)Preferences.default_values[i][5]) continue;//hidden
   				rows++;
   				JLabel l = new JLabel(Preferences.default_values[i][1].toString());
   				l.setToolTipText(Preferences.default_values[i][4].toString());
   		        l.setAlignmentX(CENTER_ALIGNMENT);
   		        l.setPreferredSize(new Dimension(200,24));
   				add(l);
   				JComponent c = null;
   				if (Preferences.default_values[i][3] == Boolean.class) {
   					Action a = new CheckBoxAction(i);
   					c = new JCheckBox(a);
   					((JCheckBox)c).setSelected(Boolean.parseBoolean(
   							Preferences.getProperty(Preferences.default_values[i][0].toString())));
   				} else if (Preferences.default_values[i][3] == String.class) {	
   					c = new JFormattedTextField(Preferences.getProperty(Preferences.default_values[i][0].toString()));
   					
   					c.addPropertyChangeListener("value", new TextPropertyChangeListener(i));

   					c.setPreferredSize(d);
   					c.setMaximumSize(d);
   				}	
   				//c.setPreferredSize(d);
   				add(c);
   			}
   			toxTree.ui.SpringUtilities.makeCompactGrid(this, rows, 2, // rows, cols
   					2, 2, // initX, initY
   					1, 1); // xPad, yPad   			
	}
}


class TextPropertyChangeListener implements PropertyChangeListener {
	protected int index = -1;
	public TextPropertyChangeListener(int index) {
		this.index = index;
	}
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() instanceof JFormattedTextField) { 
			Preferences.setProperty(Preferences.default_values[index][0].toString(),
					((JFormattedTextField)evt.getSource()).getValue().toString()
					);
		}	
		
	}
}

class CheckBoxAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int index = -1;
	public CheckBoxAction(int index) {
		this.index = index;
	}
		public void actionPerformed(ActionEvent e) {
				JCheckBox cb = (JCheckBox)e.getSource();
	            Preferences.setProperty(Preferences.default_values[index][0].toString(),
	            		new Boolean(cb.isSelected()).toString());
			}

}