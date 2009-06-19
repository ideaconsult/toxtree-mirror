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

import java.util.Map;

import javax.swing.JComponent;

import org.openscience.cdk.interfaces.IChemObject;

public interface IObjectFilter extends Comparable {
    public void preprocess(IChemObject object);
    public boolean accept(IChemObject object);
    public void setProperty(String key, Object value);
    public Object getProperty(String key);
    public Object removeProperty(String key);
    public void setProperties(Map properties);
    public Map getProperties();
    public JComponent getEditor();
}
