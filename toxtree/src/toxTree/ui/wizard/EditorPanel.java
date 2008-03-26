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

package toxTree.ui.wizard;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

import toxTree.core.IDecisionRuleEditor;
import toxTree.core.IToxTreeEditor;

public class EditorPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4466287344655179932L;
	protected Component component = null;

	public EditorPanel() {
        super(new BorderLayout());
    }

	public EditorPanel(IDecisionRuleEditor editor) {
        super(new BorderLayout());
        setEditor(editor);
    }	
    public void setEditor(IToxTreeEditor editor) {
        if (editor == null) setVisualComponent(null);
        else setVisualComponent(editor.getVisualCompoment());
    }
    public void setVisualComponent(Component component) {
        if (this.component!=null) remove(this.component);
        this.component = component; 
        add(this.component,BorderLayout.CENTER);        
    }
    public Component getVisualComponent() {
        return component;
    }
}


