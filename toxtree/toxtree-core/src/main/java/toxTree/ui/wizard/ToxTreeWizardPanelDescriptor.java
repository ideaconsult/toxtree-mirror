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
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nexes.wizard.WizardPanelDescriptor;

public class ToxTreeWizardPanelDescriptor extends WizardPanelDescriptor {
    protected Object id;
    protected Object backId;
    protected Object nextId;
    
    public ToxTreeWizardPanelDescriptor(Object id, Object nextId, Object backId, JComponent panel, ImageIcon icon) {
        super(id,new LogoPanel(panel,icon));
        this.id = id;
        this.nextId = nextId;
        this.backId = backId;
           
    }
    
    @Override
	public Object getNextPanelDescriptor() {
        return nextId;
    }
    
    @Override
	public Object getBackPanelDescriptor() {
        return backId;
    }

    public synchronized Object getBackId() {
        return backId;
    }

    public synchronized void setBackId(Object backId) {
        this.backId = backId;
    }

    public synchronized Object getNextId() {
        return nextId;
    }

    public synchronized void setNextId(Object nextId) {
        this.nextId = nextId;
    }

    public synchronized Object getId() {
        return id;
    }

    public synchronized void setId(Object id) {
        this.id = id;
    }  

}

class LogoPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -5992881643568220514L;
    public LogoPanel(JComponent component, ImageIcon icon) {
        super();
        
        JLabel iconLabel = new JLabel();
        setLayout(new java.awt.BorderLayout());
        iconLabel.setOpaque(true);
        iconLabel.setBackground(new Color(35,207,213));
        //iconLabel.setBackground(new Color(192,192,192));
        if (icon == null) icon = getImageIcon();
        if (icon != null)
            iconLabel.setIcon(icon);
        
        //iconLabel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        
        add(iconLabel, BorderLayout.WEST);
        add(component, BorderLayout.CENTER);
        setBorder(null);

    }
    private ImageIcon getImageIcon() {
        return null; //.createImageIcon("ambit/ui/images/wizard.png");//new ImageIcon((URL)getResource("clouds.jpg"));
    }
}
