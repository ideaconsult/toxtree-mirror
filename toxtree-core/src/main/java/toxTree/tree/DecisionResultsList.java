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
package toxTree.tree;

import java.util.Observable;
import java.util.Observer;

import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionResult;

public class DecisionResultsList extends DecisionMethodsList implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2269460535752336829L;

	public DecisionResultsList() {
		super();

	}
	@Override
	public void addDecisionMethod(IDecisionMethod method) {
		try {
			IDecisionResult result = method.createDecisionResult();
			result.setDecisionMethod(method);
			if (result instanceof Observable) ((Observable) result).addObserver(this);
			list.add(result);
			setChanged();
			notifyObservers();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	@Override
	public IDecisionMethod getMethod(int index) {
		IDecisionResult result = (IDecisionResult)get(index);
		return result.getDecisionMethod();
	}
	public IDecisionResult getResult(int index) {
		return (IDecisionResult)get(index);

	}
	public void update(Observable arg0, Object arg1) {
		setChanged();
		notifyObservers();
		
	}
    @Override
    public Object remove(int arg0) {
        Object o = super.remove(arg0);
        if ((o!=null) && (o instanceof Observable))
            ((Observable)o).deleteObserver(this);
        setChanged();
        notifyObservers();
        return o;
    }
    @Override
    public boolean remove(Object arg0) {
        if (super.remove(arg0)) {
            if ((arg0!=null) && (arg0 instanceof Observable))
                ((Observable)arg0).deleteObserver(this);
            return true;
        } else return false;
    }
}
