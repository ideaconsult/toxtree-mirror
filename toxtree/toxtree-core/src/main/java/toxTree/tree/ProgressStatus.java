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

import java.io.Serializable;

import toxTree.core.IProgressStatus;

public class ProgressStatus implements IProgressStatus, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5579521841811010110L;

    // 0 not estimated, 1 estimating; 2 estimated ; 3 : unknown ; 4 : error
    public enum STATUS {
	NOTESTIMATED, INPROGRESS, COMPLETED, UNKNOWN, ERROR
    }

    public static final transient String _eMethodNotAssigned = "Decision method not assigned!";
    public static final transient String _eResultNotEstimated = "Decision method has not been applied yet!";
    public static final transient String _eResultIsEstimating = "Decision method is being applied now ...!";
    public static final transient String _eUnknownState = "Unknown state !\t";
    public static final transient String _eError = "ERROR: \t";
    public static final transient String _pClassID = "ClassID";
    public static final transient String _pDecisionMethod = "DecisionMethod";
    public static final transient String _pRuleResult = "RuleResult";
    protected STATUS estimated;
    protected String errMessage = "";
    protected int percent = 0;

    public synchronized int getPercentEstimated() {
	return percent;
    }

    public synchronized void setPercentEstimated(int percent) {
	this.percent = percent;
    }

    public ProgressStatus() {
	super();
	estimated = STATUS.NOTESTIMATED;
    }

    public void clear() {
	estimated = STATUS.NOTESTIMATED;
    }

    public boolean isEstimated() {
	return (estimated == STATUS.COMPLETED);
    }

    public boolean isEstimating() {
	return estimated == STATUS.INPROGRESS;
    }

    public boolean isError() {
	return estimated == STATUS.ERROR;
    }

    public void setEstimated() {
	estimated = STATUS.COMPLETED;
    }

    public void setEstimated(boolean value) {
	if (value)
	    estimated = STATUS.COMPLETED;
	else
	    estimated = STATUS.NOTESTIMATED;
    }

    public void setEstimating() {
	estimated = STATUS.INPROGRESS;
    }

    public void setError(String message) {
	estimated = STATUS.ERROR;
	this.errMessage = message;

    }

    public String getErrMessage() {
	return errMessage;
    }

    @Override
    public String toString() {
	switch (estimated) {
	case NOTESTIMATED:
	    return _eResultNotEstimated;
	case INPROGRESS:
	    return _eResultIsEstimating;
	case COMPLETED:
	    return "Completed ";
	case ERROR:
	    return "Error\t" + getErrMessage();
	default:
	    return _eUnknownState;
	}
    }
}
