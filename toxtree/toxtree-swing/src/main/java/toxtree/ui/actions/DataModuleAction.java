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
package toxtree.ui.actions;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.SwingWorker;

import toxtree.data.DataModule;
import toxtree.data.ToxTreeActions;

/**
 * An abstract action to manipulate data in {@link toxtree.data.ToxTreeModule}
 * @author Nina Jeliazkova
 *
 */
public abstract class DataModuleAction extends AbstractAction {
	protected DataModule module;
    protected Component frame=null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 932720779234702367L;

	public DataModuleAction(DataModule module) {
		super();
		this.module = module;
	}

	public DataModuleAction(DataModule module, String name) {
		super(name);
		this.module = module;
	}

	public DataModuleAction(DataModule module, String name, Icon icon) {
		super(name, icon);
		this.module = module;
	}

    public synchronized Component getFrame() {
        return frame;
    }

    public synchronized void setFrame(Component frame) {
        this.frame = frame;
    }
    public abstract void runAction() throws Exception;
    public void actionPerformed(ActionEvent arg0) {
    	SwingWorker<DataModule, Object> worker = new SwingWorker<DataModule, Object>() {
    			 
    	       @Override
    	       public DataModule doInBackground() {
                   EventQueue.invokeLater(new Runnable() {
                       public void run() {
            	    	   module.getActions().allActionsEnable(false);
                       }
                   });
	               	try {
	               		runAction();
	               	} catch (Exception x) {
	               		x.printStackTrace();
	               		ToxTreeActions.showMsg("Error", x.getMessage());
	               	}
               	//module.getActions().allActionsEnable(true);
	               	return module;
    	       }

    	       @Override
    	       protected void done() {
    	           try { 
    	        	   DataModule result = get();
    	        	   result.getActions().allActionsEnable(true);
    	           } catch (Exception x) {
    	        	   x.printStackTrace();
    	        	   module.getActions().allActionsEnable(true);
    	           }
    	       }
    	   };
    	 
    	   worker.execute();
    	/*
        final toxtree.ui.GUIWorker worker = new toxtree.ui.GUIWorker() {
            @Override
			public Object construct() {
            	module.getActions().allActionsEnable(false);
            	try {
            		run();
            	} catch (Exception x) {
            		x.printStackTrace();
            		ToxTreeActions.showMsg("Error", x.getMessage());
            		module.getActions().allActionsEnable(true);
            	}
            	//module.getActions().allActionsEnable(true);
            	return module;
            }
            //Runs on the event-dispatching thread.
            @Override
			public void finished() {
            	module.getActions().allActionsEnable(true);
            }
        };
        worker.start(); 
        */
    }

}
