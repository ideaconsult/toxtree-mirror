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
package toxTree.ui.molecule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import toxTree.data.DataContainer;

public class NavigationPanel extends JPanel implements Observer {
	protected DataContainer dataContainer;
	protected JLabel navStat = null;
	protected Color bgColor = Color.BLACK;
	protected Color fColor = Color.WHITE;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7523783333429225550L;

	public NavigationPanel(DataContainer datacontainer, Color bgColor, Color fColor) {
		super();
		setDataContainer(datacontainer);
		addWidgets(bgColor,fColor);
	}

	public NavigationPanel(DataContainer datacontainer,boolean arg0,Color bgColor, Color fColor) {
		super(arg0);
		setDataContainer(datacontainer);
		addWidgets(bgColor,fColor);
	}

	public NavigationPanel(DataContainer datacontainer,LayoutManager arg0,Color bgColor, Color fColor) {
		super(arg0);
		setDataContainer(datacontainer);
		addWidgets(bgColor,fColor);
	}

	public NavigationPanel(DataContainer datacontainer,LayoutManager arg0, boolean arg1,Color bgColor, Color fColor) {
		super(arg0, arg1);
		setDataContainer(datacontainer);
		addWidgets(bgColor,fColor);
	}

	protected void addWidgets(Color bgColor, Color fColor) {
		//navPanel.setBorder(BorderFactory.createTitledBorder("Title"));
		this.bgColor = bgColor;
		this.fColor = fColor;
		setLayout(new FlowLayout());
		setBackground(bgColor);
		setForeground(fColor);
		setPreferredSize(new Dimension(120,32));
		setMinimumSize(new Dimension(64,32));
		
		JLabel nav[] = new JLabel[4];
        nav[0] = createLabel("<html><u><b> First</b></u></html>",
                "Click here to go to the first molecule of the set"
                );
        nav[0].addMouseListener(new MouseAdapter() {
	   		@Override
			public void mouseClicked(MouseEvent e) {
	   			dataContainer.first();
	   		}
	    });	 
        nav[1] = createLabel("<html><u><b> Prev</b></u></html>",
                "Click here to go to the previous molecule of the set"
                );
        nav[1].addMouseListener(new MouseAdapter() {
	   		@Override
			public void mouseClicked(MouseEvent e) {
	   			dataContainer.prev();
	   		}
	    });			
        nav[2] = createLabel("<html><u><b> Next</b></u></html>",
                "Click here to go to the next molecule of the set"
                );
        nav[2].addMouseListener(new MouseAdapter() {
	   		@Override
			public void mouseClicked(MouseEvent e) {
	   			dataContainer.next();
	   		}
	    });
        nav[3] = createLabel("<html><u><b> Last</b></u></html>",
                "Click here to go to the last molecule of the set"
                );
        nav[3].addMouseListener(new MouseAdapter() {
	   		@Override
			public void mouseClicked(MouseEvent e) {
	   			dataContainer.last();
	   		}
	    });	        
	    navStat = createLabel("<html><font color=blue><b>1/1</b></font></html>","Number of molecules in the set loaded.");
	    
        for (int i = 0; i < 2; i++) add(nav[i]);
        add(navStat);
        for (int i = 2; i < 4; i++) add(nav[i]);
	}	
	public void update(Observable arg0, Object arg1) {
        navStat.setToolTipText(dataContainer.toString());
		navStat.setText("<html><font color=gray><b>" +
				(dataContainer.getCurrentNo()+1) + " / " +  
				dataContainer.getMoleculesCount() +
				"</b></font></html>");
	}

	public DataContainer getDataContainer() {
		return dataContainer;
	}

	public void setDataContainer(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		dataContainer.addObserver(this);
	}

	
	public  JLabel createLabel(String title, String tooltip) {
        JLabel label = new JLabel(title);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setOpaque(true);
        label.setBackground(bgColor);
        label.setForeground(fColor);     
        label.setToolTipText(tooltip);
        return label;
	}	
}
