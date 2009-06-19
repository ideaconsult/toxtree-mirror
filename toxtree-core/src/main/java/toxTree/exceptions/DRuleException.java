/* DRuleException.java
 * Author: Nina Jeliazkova
 * Date: Mar 20, 2008 
 * Revision: 0.1 
 * 
 * Copyright (C) 2005-2008  Nina Jeliazkova
 * 
 * Contact: nina@acad.bg
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 */

package toxTree.exceptions;

import toxTree.core.IDecisionCategory;

public class DRuleException extends DecisionMethodException {
    protected IDecisionCategory category2assign;
    protected boolean answer;
    /**
     * 
     */
    private static final long serialVersionUID = -7009633103421299967L;

    public DRuleException(IDecisionCategory c, boolean answer) {
        super();
        setCategory2assign(c);
        setAnswer(answer);
        
    }

    public DRuleException(String message,IDecisionCategory c, boolean answer) {
        super(message);
        setCategory2assign(c);
        setAnswer(answer);
    }

    public DRuleException(Throwable cause,IDecisionCategory c, boolean answer) {
        super(cause);
        setCategory2assign(c);
        setAnswer(answer);
    }

    public DRuleException(String message, Throwable cause,IDecisionCategory c, boolean answer) {
        super(message, cause);
        setCategory2assign(c);
    }

    public synchronized IDecisionCategory getCategory2assign() {
        return category2assign;
    }

    public synchronized void setCategory2assign(IDecisionCategory category2assign) {
        this.category2assign = category2assign;
    }

    public synchronized boolean isAnswer() {
        return answer;
    }

    public synchronized void setAnswer(boolean answer) {
        this.answer = answer;
    }

}
