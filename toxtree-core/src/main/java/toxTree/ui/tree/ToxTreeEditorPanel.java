/*
Copyright Ideaconsult Ltd. (C) 2005-2007 

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

package toxTree.ui.tree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import toxTree.core.IToxTreeEditor;

/**
 * Absatract class, implementing {@link IToxTreeEditor}.
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 17, 2006
 */
public abstract class ToxTreeEditorPanel extends JPanel implements IToxTreeEditor, FocusListener,
		Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -763213118243686164L;
    protected JTextField idField ;
    protected JTextField nameField ;
    protected JEditorPane explanation;
    protected JScrollPane explanationPane; 
    protected Color bgColor = new Color(250,250,250);
    protected boolean editable = true;


	public ToxTreeEditorPanel(Object object) {
		super(new BorderLayout());
		addWidgets(object);
	}

	public ToxTreeEditorPanel() {
		this(null);
	}

	protected void addWidgets(Object object) {
    	JPanel pp = new JPanel();
    	pp.setLayout(new BorderLayout());
    	pp.setBackground(bgColor);
    	
        idField = new JTextField();
        idField.addFocusListener(this);
        idField.setPreferredSize(new Dimension(64,24));
        idField.setEditable(editable);
        idField.setBorder(BorderFactory.createTitledBorder("ID"));
        idField.setBackground(bgColor);
        pp.add(idField,BorderLayout.WEST);
        
        nameField = new JTextField();
        nameField.addFocusListener(this);
        nameField.setEditable(editable);
        nameField.setBorder(BorderFactory.createTitledBorder("Title"));
        nameField.setBackground(bgColor);
        pp.add(nameField,BorderLayout.CENTER);
        
        explanation = new JEditorPane("text/html","");
        explanation.addFocusListener(this);
        explanation.setBackground(bgColor);
        explanation.setEditable(editable);
        explanation.setAutoscrolls(true);
        //explanation.setLineWrap(true);
        //explanation.setWrapStyleWord(true);
        explanationPane = new JScrollPane(explanation,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        explanation.setPreferredSize(new Dimension(200,200));
        explanationPane.setBorder(BorderFactory.createTitledBorder("Explanation"));        
        explanationPane.setBackground(bgColor);

        add(pp,BorderLayout.NORTH);
	}
	public abstract void setID(String id);
	public abstract void setTitle(String title);
	public abstract void setExplanation(String explanation);

	public abstract String getID();
	public abstract String getTitle();
	public abstract String getExplanation();
	
	public void focusGained(FocusEvent arg0) {
	}

	public void focusLost(FocusEvent e) {
        if (e.getSource() ==nameField) {
            setTitle(nameField.getText());
        } else if (e.getSource() ==idField) {
            setID(idField.getText());
        } else if (e.getSource() ==explanation) 
            setExplanation(explanation.getText());      
	}
	
	public void update(Observable arg0, Object arg1) {
        idField.setText(getID());        
        nameField.setText(getTitle());
        explanation.setText(getExplanation());
	}

	public Component getVisualCompoment() {
		return this;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		idField.setEditable(editable);
		nameField.setEditable(editable);
		explanation.setEditable(editable);

	}

}


