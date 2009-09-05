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

package toxTree.tree.rules;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.openscience.cdk.interfaces.IAtomContainer;

import toxTree.core.IDecisionInteractive.UserOptions;
import toxTree.ui.PropertyEditor;

public class ApplyRuleOptions implements Serializable,PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3494906179654154028L;


	public ApplyRuleOptions() {
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() instanceof UserInputRule) {
			final UserInputRule rule = (UserInputRule) evt.getSource();
			
			String message = evt.getPropertyName();
			String hint = evt.getOldValue().toString();

			Object o = evt.getNewValue();
			if (o instanceof IAtomContainer) {
				IAtomContainer mol = (IAtomContainer)o;
				PropertyEditor p = new PropertyEditor(mol,optionsPanel(rule,message,hint));
				if (JOptionPane.showConfirmDialog(null,p,String.format("Rule %s.%s",rule.getID(),rule.getTitle()),
			                JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			        

			    }			
			}
		}
		
	}
	public JComponent optionsPanel(final UserInputRule rule,String message, String hint) {

			UserOptions options = rule.getOptions();
			JPanel p = new JPanel();
			p.setLayout(new GridLayout(2,1));
			JLabel label = new JLabel(message);
			label.setToolTipText(hint);
			p.add(label);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.add(new JLabel(rule.getID()));
			ButtonGroup g = new ButtonGroup();
			
			int selected = options.ordinal();
			
			for (UserOptions value : UserOptions.values()) {
				JRadioButton b = new JRadioButton(new AbstractAction(value.toString()) {
					public void actionPerformed(ActionEvent e) {
						String a = e.getActionCommand();
						for (UserOptions o : UserOptions.values()) 
							if (o.toString().equals(a)) {
								rule.setOptions(o);
								break;
							}
				}}
				);
				b.setSelected(selected == value.ordinal());
				b.setActionCommand(value.toString());
				g.add(b);
				buttonPanel.add(b);
			}
			p.add(buttonPanel);
			p.setBorder(BorderFactory.createEtchedBorder());
			return p;
		
	}

}


