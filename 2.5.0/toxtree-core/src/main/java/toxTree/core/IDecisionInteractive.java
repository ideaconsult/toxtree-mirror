/*
Copyright Ideaconsult Ltd. (C) 2005-2007  
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


package toxTree.core;

import java.beans.PropertyChangeListener;


/**
 * An interface for rules, requiring user interaction (e.g. property input) 
 * @author nina
 *
 * @param <T>
 */
public interface IDecisionInteractive{
	public enum UserOptions {
		YES  {
			@Override
			public boolean getAnswer() {
				return true;
			}
			@Override
			public boolean isInteractive() {
				return true;
			}	
			@Override
			public String toString() {
				return "Yes";
			}
		},
		NO  {
			@Override
			public boolean getAnswer() {
				return false;
			}
			@Override
			public boolean isInteractive() {
				return true;
			}				
			@Override
			public String toString() {
				return "No";
			}
		},
		YEStoALL {
			@Override
			public boolean getAnswer() {
				return true;
			}
			@Override
			public boolean isInteractive() {
				return false;
			}
			@Override
			public String toString() {
				return "Yes to All";
			}
		},
		NOtoAll {
			@Override
			public boolean getAnswer() {
				return false;
			}
			@Override
			public boolean isInteractive() {
				return false;
			}
			public String toString() {
				return "No to All";
			}			
		};
		public abstract boolean isInteractive();
		public abstract boolean getAnswer();
		public UserOptions setAnswer(boolean answer) {
			return answer?
					(isInteractive()?UserOptions.YES:UserOptions.YEStoALL)   //YES
					:
					(isInteractive()?UserOptions.NO:UserOptions.NOtoAll)    	//NO
					;
		}			
		public UserOptions setInteractive(boolean interactive) {
			return interactive?
					(getAnswer()?UserOptions.YES:UserOptions.NO)   //YES
					:
					(getAnswer()?UserOptions.YEStoALL:UserOptions.NOtoAll)    	//NO
					;
		}			
	}
	
    public void setInteractive(boolean value);
    public boolean getInteractive();
    void setListener(PropertyChangeListener listener);
    void removeListener();
    PropertyChangeListener getListener();
    UserOptions getOptions();
    void setOptions(UserOptions options);
}


