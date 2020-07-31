package launcher;

import displays.StartState;
import displays.HelpState;

/* Group Members: Alan, Kajan, Eva, Sam
 * 
 * Date: 12/16/2019
 * 
 * Course Code: ICS4U1
 * 
 * Purpose: Our program allows the user to select materials to use for their art project and also allows them
 * to create a library of materials which allows them to become more organized and more aware of the impact 
 * their items have on the environment
 * 
 * Major Skills: 
 * - Graphics
 * - DatabaseState (Eva)
 * - LoginState (Alan)
 * - MaterialState(Alan)
 * - ProjectDetailsState(Sam)
 * - HelpState (Sam)
 * - ReportScreen (Kajan)
 * - PossibleAlternatives (Kajan)
 * - State (Alan)
 * 
 * Extra Features: 
 * - Messages for User
 * - Animations
 * - Login Screen
 * - Account management
 * 
 * Areas of Concern: 
 * 
 * - Some pictures are more stretched when scaled to fit the buttons
 * 
 */
public class Main {
	
	public static void main(String[] args) {
		
		new StartState();
		
	}

}
