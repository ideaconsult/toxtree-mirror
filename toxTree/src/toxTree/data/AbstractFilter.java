/*
Copyright Ideaconsult (C) 2005-2007 
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
package toxTree.data;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.openscience.cdk.interfaces.IChemObject;

public abstract class AbstractFilter implements IObjectFilter {
	protected Hashtable parameters;
	public AbstractFilter() {
		super();
		parameters = new Hashtable();
	}

    public void preprocess(IChemObject object) {
        
    }
	public void setProperty(String key, Object value) {
		parameters.put(key,value);

	}

	public Object getProperty(String key) {
		return parameters.get(key);
	}

	public Object removeProperty(String key) {
		return parameters.remove(key);
	}
	public void setProperties(Map properties) {
		parameters.clear();
		parameters.putAll(properties);
		
	}
	public Map getProperties() {
		return parameters;
	}

	public JComponent getEditor() {
		return new AbstractFilterEditor(toString(),this);
	}
	
    public int hashCode() {
    	return parameters.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof AbstractFilter) {
    		return parameters.equals(((AbstractFilter)obj).parameters);
    	} else return false;
    }
    public int compareTo(Object obj) {
    	if (obj instanceof AbstractFilter) {
    		return parameters.toString().compareTo(((AbstractFilter)obj).parameters.toString());
    	} else return -1;
    }
}



class AbstractFilterEditor extends JPanel implements  ActionListener,  FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5047904516589635601L;
	protected String title;
	protected Object object;
	protected boolean editable;
	
	
	public AbstractFilterEditor(LayoutManager layout ,String title,IObjectFilter object) {
		super(layout);
		this.object = object;
		this.title = title;
	    setName(object.toString());
	}
	
	public AbstractFilterEditor(String title,IObjectFilter object) {
		this(new BorderLayout(),title,object);
	}
	
	public AbstractFilterEditor() {
		this("",null);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}



	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
}