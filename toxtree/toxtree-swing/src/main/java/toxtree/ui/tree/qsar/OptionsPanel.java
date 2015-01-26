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

package toxtree.ui.tree.qsar;

import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.GroupLayout.ParallelGroup;
import org.jdesktop.layout.GroupLayout.SequentialGroup;

public abstract class OptionsPanel<T> extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -7284064363444159260L;
    protected T object;
    protected JLabel[] labelsDescriptor;
    protected JFormattedTextField[] textDescriptor;
    protected JLabel[] labels;
    protected JFormattedTextField text[];

    public OptionsPanel() {
	this(null);
    }

    public OptionsPanel(T object) {
	super();
	setObject(object);
	Dimension d = addWidgets(object);
	placeWidgets();
	setPreferredSize(d);
	setMinimumSize(d);
    }

    public abstract int getHeaderRows();

    public abstract JLabel createHeaderLabel(int index);

    public abstract JFormattedTextField createHeaderTextField(int index);

    public abstract int getDataRows();

    public abstract JLabel createDataLabel(int index);

    public abstract JFormattedTextField createDataTextField(int index);

    public Dimension addHeaderWidgets(T object) {
	int w = 256;
	int h = 32;
	int n = getHeaderRows();
	labelsDescriptor = new JLabel[n];
	textDescriptor = new JFormattedTextField[n];
	Dimension d = new Dimension(w, 32);
	for (int i = 0; i < n; i++) {
	    labelsDescriptor[i] = createHeaderLabel(i);
	    textDescriptor[i] = createHeaderTextField(i);
	    textDescriptor[i].setMinimumSize(d);
	    textDescriptor[i].setPreferredSize(d);
	    h += d.height;
	}
	return new Dimension(w, h);
    }

    public Dimension addDataWidgets(T object) {
	int w = 256;
	int h = 32;
	int n = getDataRows();
	labels = new JLabel[n];
	text = new JFormattedTextField[n];
	Dimension d = new Dimension(w, 32);
	for (int i = 0; i < n; i++) {
	    labels[i] = createDataLabel(i);
	    text[i] = createDataTextField(i);
	    text[i].setMinimumSize(d);
	    text[i].setPreferredSize(d);
	    h += d.height;
	}
	return new Dimension(w, h);
    }

    public Dimension addWidgets(T object) {
	Dimension h = addHeaderWidgets(object);
	Dimension d = addDataWidgets(object);
	return new Dimension(h.width + d.width, h.height + d.height);
    }

    protected void placeWidgets() {
	if (object == null)
	    return;
	org.jdesktop.layout.GroupLayout glayout = new org.jdesktop.layout.GroupLayout(this);
	setLayout(glayout);
	glayout.setAutocreateGaps(true);
	glayout.setAutocreateContainerGaps(true);

	JLabel params = new JLabel("<html><u>Parameters:</u></html>");

	SequentialGroup sg = glayout.createSequentialGroup();
	ParallelGroup pg = glayout.createParallelGroup(GroupLayout.LEADING);
	for (int i = 0; i < labelsDescriptor.length; i++)
	    pg.add(labelsDescriptor[i]);
	pg.add(params);

	for (int i = 0; i < labels.length; i++)
	    pg.add(labels[i]);
	sg.add(pg);

	pg = glayout.createParallelGroup(GroupLayout.LEADING);
	for (int i = 0; i < textDescriptor.length; i++)
	    pg.add(textDescriptor[i]);
	for (int i = 0; i < text.length; i++)
	    pg.add(text[i]);
	sg.add(pg);
	glayout.setHorizontalGroup(sg);

	SequentialGroup sg1 = glayout.createSequentialGroup();
	for (int i = 0; i < labelsDescriptor.length; i++) {
	    pg = glayout.createParallelGroup(GroupLayout.LEADING);
	    pg.add(labelsDescriptor[i]);
	    pg.add(textDescriptor[i]);
	    sg1.add(pg);
	}

	pg = glayout.createParallelGroup(GroupLayout.LEADING);
	pg.add(params);
	sg1.add(pg);
	for (int i = 0; i < labels.length; i++) {
	    pg = glayout.createParallelGroup(GroupLayout.LEADING);
	    pg.add(labels[i]);
	    pg.add(text[i]);
	    sg1.add(pg);
	}
	glayout.setVerticalGroup(sg1);
	/*
	 * setPreferredSize(new
	 * Dimension(Integer.MAX_VALUE,(labels.length+4)*32));
	 * setMinimumSize(new Dimension(100,(labels.length+4)*32));
	 * setMaximumSize(getPreferredSize());
	 */

    }

    public T getObject() {
	return object;
    }

    public void setObject(T object) {
	this.object = object;
	removeAll();
	Dimension d = addWidgets(object);
	placeWidgets();
	setPreferredSize(d);
	setMinimumSize(d);
	revalidate();
    }
}
