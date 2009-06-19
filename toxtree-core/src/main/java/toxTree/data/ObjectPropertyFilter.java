/*
Copyright (C) 2005-2006  

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

package toxTree.data;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.openscience.cdk.interfaces.IChemObject;

import toxTree.exceptions.FilterException;

public class ObjectPropertyFilter extends AbstractFilter {
	public static String p_tag = "tag";
	public static String p_value="value";
	public ObjectPropertyFilter() throws FilterException {
		super();
	}
	public ObjectPropertyFilter(Object key, Object value) throws FilterException {
		this();
        if ((key ==null) || (key.equals(""))) 
            throw new FilterException("The condition \""+key+"\"=\""+value+"\" can not be created!");
		setProperty(p_tag, key);
		setProperty(p_value, value);
	}
    
	@Override
	public void setProperties(Map properties) {
		
		Iterator i = properties.keySet().iterator();
		while (i.hasNext()) {
			Object key = i.next();
			setProperty(key.toString(), properties.get(key));
		}
	}
	
	@Override
	public void setProperty(String key, Object value) {
		if (p_tag.equals(key) || p_value.equals(key))
			super.setProperty(key, value);
		//else ignore
	}
	public boolean accept(IChemObject object) {
		Object key = parameters.get(p_tag);
		return acceptProperty(object.getProperty(key));
	}


	public boolean acceptProperty(Object value) {
		try {
			if (value == null) return false;
            Object values = parameters.get(p_value);
            if (values instanceof Collection)
                return ((Collection)values).contains(value);
            else return values.toString().equals(value.toString());
		} catch (Exception x) {
			x.printStackTrace();
			return false;
		}
		
		//return this.value.equals(value);
	}	

	@Override
	public String toString() {
		return parameters.toString();
	}
	public Object getTag() {
		return getProperty(p_tag);
	}
	public void setTag(Object property) {
		setProperty(p_tag,property);
		
	}
	public Object getValue() {
		return getProperty(p_value);
	}
	public void setValue(Object property) {
		setProperty(p_value,property);
		
	}
	@Override
	public boolean equals(Object arg0) {
		ObjectPropertyFilter pf = (ObjectPropertyFilter) arg0;
		return getTag().equals(pf.getTag())  && getValue().equals(pf.getValue());
	}
	@Override
	public JComponent getEditor() {
		return new ObjectPropertyFilterEditor("",this);
	}
	

}

class ObjectPropertyFilterEditor extends AbstractFilterEditor implements ActionListener {
	/**
     * 
     */
    private static final long serialVersionUID = 4964600959825654563L;
    protected JLabel keyLabel;
	protected JComboBox keyField;
	protected JLabel valueLabel;
	protected JComboBox valueField;
	public ObjectPropertyFilterEditor(String title,  ObjectPropertyFilter filter) {
		this(new FlowLayout(),title,filter);
	}
	public ObjectPropertyFilterEditor(LayoutManager layout,String title,  ObjectPropertyFilter filter) {
		super(layout,title,filter);
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		Object key = filter.getProperty(ObjectPropertyFilter.p_tag);
		if (key == null) filter.setProperty(ObjectPropertyFilter.p_tag,""); key = "";
		keyField = new JComboBox(new String[] {key.toString()});
        keyField.setSelectedIndex(0);
		keyField.addActionListener(this);
		keyField.addFocusListener(this);
		keyField.setPreferredSize(new Dimension(100,24));
        keyField.setMaximumSize(new Dimension(Integer.MAX_VALUE,24));
		keyLabel = new JLabel("Parameter");
		add(keyLabel);
		add(keyField);
		Object value = filter.getProperty(ObjectPropertyFilter.p_value);
		if (value ==null) value = "";
		valueField = new JComboBox(new String[] {value.toString()});
        valueField.setPreferredSize(new Dimension(100,24));
        valueField.setMaximumSize(new Dimension(Integer.MAX_VALUE,24));        
        valueField.setSelectedIndex(0);
		valueField.addFocusListener(this);
		valueField.addActionListener(this);
		//valueField.setPreferredSize(new Dimension(100,24));
		valueLabel = new JLabel("Value");
		add(valueLabel);
		add(valueField);
        setBorder(BorderFactory.createTitledBorder("Define group of compounds"));
	}

	@Override
	public void setEditable(boolean editable) {
		super.setEditable(editable);
		keyField.setEditable(editable);
		valueField.setEditable(editable);
	}
	protected void update(Object source) throws Exception {
		if (source == keyField) {
			((ObjectPropertyFilter) object).setProperty(ObjectPropertyFilter.p_tag, keyField.getSelectedItem().toString());
		} else 
		if (source == valueField) {
			((ObjectPropertyFilter) object).setProperty(ObjectPropertyFilter.p_value, valueField.getSelectedItem().toString());
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
        try {
		update(e.getSource());
        } catch (Exception x) {
            JOptionPane.showMessageDialog(this,x.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
		
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		super.focusLost(e);
        try {
            update(e.getSource());
            } catch (Exception x) {
                JOptionPane.showMessageDialog(this,x.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
	}
    public void addTags(Object[] tags) {
        for (int i=0; i < tags.length;i++)
            keyField.addItem(tags[i]);
    }
}
