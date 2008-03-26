/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
Contact: nina@acad.bg

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

*/
package toxTree.apps.toxForest;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import toxTree.data.DataContainer;
import toxTree.ui.molecule.CompoundPanel;
import toxTree.ui.molecule.NavigationPanel;

/**
 * Top panel of {@link ToxForestApp} (structures browser).
 * @author Nina Jeliazkova nina@acad.bg
 * <b>Modified</b> Dec 16, 2006
 */
public class ToxForestCompoundPanel extends CompoundPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4086442883999695442L;

	public ToxForestCompoundPanel(DataContainer model, Color bgColor, Color fColor) {
		super(model, bgColor, fColor);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void addWidgets(Color bgColor, Color fColor) {
		setBackground(bgColor);
        
		propertiesPanel = propertiesPanel(bgColor,fColor);
		JPanel structurePanel = structurePanel(bgColor,fColor);
		navPanel = new NavigationPanel(dataContainer,bgColor,fColor);
		
        JSplitPane splitPanel = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                propertiesPanel, structurePanel);
        splitPanel.setBackground(bgColor);
        splitPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        //splitPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),"Title"));
        splitPanel.setOneTouchExpandable(false);
        splitPanel.setDividerLocation(200);
        add(splitPanel, BorderLayout.CENTER);
        
        add(navPanel, BorderLayout.SOUTH);

        display();

	}
	

}
