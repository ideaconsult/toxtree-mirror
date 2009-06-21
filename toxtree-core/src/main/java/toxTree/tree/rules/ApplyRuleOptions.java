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
import java.io.Serializable;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.openscience.cdk.interfaces.IAtomContainer;

public class ApplyRuleOptions implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3494906179654154028L;
	public static final String[] user_options = {"Yes","No","Yes to all","No to all"};
	public boolean interactive = false;
	public boolean answer = true;

	public ApplyRuleOptions(boolean interactive, boolean answer) {
		setInteractive(interactive);
		setAnswer(answer);
	}
	public boolean getInteractive() {
		return interactive;
	}
	
	public JComponent optionsPanel(String message,String hint, String title, IAtomContainer atomContainer) {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,1));
		JLabel label = new JLabel(message);
		label.setToolTipText(hint);
		p.add(label);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JLabel(title));
		ButtonGroup g = new ButtonGroup();
		
		int selected = 0;
		if (interactive)
			if (answer) selected = 0; else selected = 1;
		else
			if (answer) selected = 2; else selected = 3;
			
		for (int i=0; i < user_options.length;i++) {
			JRadioButton b = new JRadioButton(new AbstractAction(user_options[i]) {
				public void actionPerformed(ActionEvent e) {
					String a = e.getActionCommand();
					for (int j=0; j < user_options.length;j++) {
						if (a.equals(user_options[j])) {
							interactive = j<2;
							if ((j % 2) == 0) answer = true; else answer = false;
							break;
						}
					}
			}}
			);
			b.setSelected(selected == i);
			b.setActionCommand(user_options[i]);
			g.add(b);
			buttonPanel.add(b);
		}
		p.add(buttonPanel);
		p.setBorder(BorderFactory.createEtchedBorder());
		return p; 
	}
	public void setInteractive(boolean value) {
		this.interactive = value;		
	}		
	public void setAnswer(boolean value) {
		this.answer = value;		
	}		
	public boolean isAnswer() {
		return answer;
	}	
}


