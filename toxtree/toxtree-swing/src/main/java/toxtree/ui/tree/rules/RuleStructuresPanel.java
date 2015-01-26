/*
Copyright (C) 2005-2006  

Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
All we ask is that proper credit is given for our work, which includes
- but is not limited to - adding the above copyright notice to the beginning
of your source code files, and to any copyright notice that you may distribute
with programs based on this work.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package toxtree.ui.tree.rules;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import toxTree.core.IDecisionRule;
import toxTree.core.IDecisionRuleEditor;
import toxTree.io.MolFileFilter;
import toxTree.tree.rules.RuleStructuresList;
import toxtree.data.ToxTreeActions;

public class RuleStructuresPanel extends RuleEditor implements IDecisionRuleEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5000057842144986302L;
	protected JTextField fileLabel;

	public RuleStructuresPanel(RuleStructuresList object) {
		super(object);
		setRule(object);
	}

	public RuleStructuresPanel() {
		this(null);
	}
	@Override
	protected void addWidgets(Object object) {
		super.addWidgets(object);
		JPanel p = new JPanel();
		
		p.setLayout(new BoxLayout(p,BoxLayout.PAGE_AXIS));
		p.add(explanationPane);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBorder(BorderFactory.createTitledBorder("Searching for exact structures from file:"));
		JButton b = new JButton(new AbstractAction("File") {
			/**
		     * 
		     */
		    private static final long serialVersionUID = 7552999440534127570L;

			public void actionPerformed(ActionEvent arg0) {
		        selectFile();

			}
		});
		fileLabel = new JTextField();
		fileLabel.setEditable(false);
		if (rule != null) {
			File file =((RuleStructuresList)rule).getFile();
			if (file != null) {
				fileLabel.setText(file.getName());
				fileLabel.setToolTipText(file.getAbsolutePath());
			}
		}
		p1.add(b,BorderLayout.SOUTH);
		p1.add(fileLabel,BorderLayout.CENTER);
		
		p.add(p1);
		add(p,BorderLayout.CENTER);
	}
	@Override
	public void setRule(IDecisionRule rule) {
		super.setRule(rule);
		File file =((RuleStructuresList)rule).getFile();
		if (file != null) {
			fileLabel.setText("File: " +file.getName());
			fileLabel.setToolTipText(file.getAbsolutePath());
		}
		
	}
	protected void selectFile() {
		File file = ToxTreeActions.selectFile(this, 
        		MolFileFilter.supported_extensions,MolFileFilter.supported_exts_description
        		, true);
		if (file != null) try {
			((RuleStructuresList)rule).setFile(file);
			fileLabel.setText("File: " +file.getName());
			fileLabel.setToolTipText(file.getAbsolutePath());

		}	catch (Exception x) {
			
		}
	}
	

}


