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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public abstract class RadioBoxPanel extends JPanel {
    List<JRadioButton> buttons;
    List<?> options;
    /**
     * 
     */
    private static final long serialVersionUID = 2266091648110037618L;
    public RadioBoxPanel() {
        this("",null,-1);
    }
    public RadioBoxPanel(String caption, List<?> list, int selectedIndex) {
        super();
        buttons = new ArrayList<JRadioButton>();
         setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        setOptions(list,selectedIndex);
        setBorder(BorderFactory.createTitledBorder(caption));
        setMinimumSize(new Dimension(200,100));
    }
    
    public void createButtons(List<?> list, List<JRadioButton> buttons,int selectedIndex) {
        buttons.clear();
        Iterator<?> i = list.iterator();
        ButtonGroup group = new ButtonGroup();
        int c = 0;
        if (selectedIndex < 0) selectedIndex = 0;

        while (i.hasNext()) {
            JRadioButton b = new JRadioButton(new RadioButtonAction(i.next()) {
                @Override
                public void select(ActionEvent e, Object object) {
                    selectObject(e, object);
                }
            });
            
            b.setSelected(c==selectedIndex);
            group.add(b);
            buttons.add(b);
            c++;
        }
         
    }
    public int getSelectedIndex() {
        for (int i=0; i < buttons.size();i++)
            if (buttons.get(i).isSelected()) return i;
        return -1;
    }
    public abstract void selectObject(ActionEvent e, Object object);

    public synchronized List<?> getOptions() {
        return options;
    }

    public synchronized void setOptions(List<?> list,int selectedIndex) {
        Iterator<JRadioButton> i = buttons.iterator();
        while (i.hasNext()) {
            remove(i.next());
        }

        buttons.clear();
        createButtons(list,buttons,selectedIndex);
        for (int index=0; index < buttons.size();index++) {
            add(buttons.get(index));
            buttons.get(index).setSelected(selectedIndex==index);
        }

        this.options = list;
        
    }
}

class RadioButtonAction extends AbstractAction {
    protected Object object;
    public RadioButtonAction(Object object) {
        super(object.toString());
        this.object = object;
    }
    
    public void actionPerformed(ActionEvent e) {
        select(e, object);
        
    }
    public void select(ActionEvent e, Object object) {
        
    }
}
