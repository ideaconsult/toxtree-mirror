/*
Copyright Ideaconsult Ltd. (C) 2005-2012 

Contact: jeliazkova.nina@gmail.com

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

package toxtree.plugins.ames.descriptors;

public class SubstituentPosition {
    protected int position = 0;
    protected boolean ring = true;
    public SubstituentPosition(int position, boolean ring) {
        setPosition(position);
        setRing(ring);
    }

    public synchronized int getPosition() {
        return position;
    }

    public synchronized void setPosition(int position) {
        this.position = position;
    }

    public synchronized boolean isRing() {
        return ring;
    }
    public synchronized void setRing(boolean ring) {
        this.ring = ring;
    }
    @Override
    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append("Position ");
        b.append(position);
        b.append(" In ring ");
        b.append(ring);        
        return b.toString();
    }
}
