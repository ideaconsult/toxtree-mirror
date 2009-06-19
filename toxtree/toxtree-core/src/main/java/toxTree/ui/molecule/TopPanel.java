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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.LayoutManager;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import toxTree.data.DataContainer;
import toxTree.data.DecisionMethodData;

/**
 * The top panel of  {@link toxTree.apps.ToxTreeApp}
 * @author Nina Jeliazkova
 * <b>Modified</b> 2005-10-2
 * 
 */
public class TopPanel extends JPanel implements Observer {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -8235667744901926748L;
	protected String[] id = {"SMILESENRTY","FILE"};
	protected SmilesEntryPanel smilesPanel;
	protected JPanel filePanel;
	protected JLabel fileLabel;
	
	public TopPanel() {
		super(new CardLayout());
		setBorder(BorderFactory.createEmptyBorder(1,5,1,5));
		addWidgets();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public TopPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		addWidgets();
	}

	/**
	 * @param layout
	 */
	public TopPanel(LayoutManager layout) {
		super(new CardLayout());
		addWidgets();
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public TopPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(new CardLayout(), isDoubleBuffered);
		addWidgets();
	}

	
	/**
	 * @return Returns the toxData.
	 */
	public synchronized DataContainer getDataContainer() {
		return smilesPanel.getDataContainer();
	}
	/**
	 * @param toxData The toxData to set.
	 */
	public synchronized void setDataContainer(DataContainer data) {
		smilesPanel.setDataContainer(data);
		data.addObserver(this);
	}
	protected void addWidgets() {
		smilesPanel = new SmilesEntryPanel();
		add(smilesPanel,id[0]);
		filePanel = new JPanel(new BorderLayout());
		fileLabel = new JLabel();
		filePanel.add(fileLabel,BorderLayout.CENTER);
		add(filePanel,id[1]);
	}
	
	public void update(Observable o, Object arg) {
		if (o instanceof DecisionMethodData) {
			DecisionMethodData data = (DecisionMethodData) o;
	 	    CardLayout cl = (CardLayout)getLayout();
			if (data.loadedFromFile()) {
				fileLabel.setText("<html><b>File: </b>"+data.getFileName()+"</html>");
				cl.show(this, id[1]);		
			} else {
				cl.show(this, id[0]);
			}
			
		}
	}
}
