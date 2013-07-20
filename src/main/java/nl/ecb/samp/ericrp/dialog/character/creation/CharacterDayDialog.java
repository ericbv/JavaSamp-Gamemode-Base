package nl.ecb.samp.ericrp.dialog.character.creation;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.event.DialogEventHandler;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.dialog.AbstractInputDialog;
import nl.ecb.samp.ericrp.dialog.character.creation.exceptions.InputTooHighException;
import nl.ecb.samp.ericrp.dialog.character.creation.exceptions.InputTooLowException;
import nl.ecb.samp.ericrp.dialog.character.creation.exceptions.NoInputException;

public class CharacterDayDialog extends AbstractInputDialog {
	private Player p;
	private CharacterCreationManager characterCreationManager;
	private ManagedEventManager eventManager;

	public CharacterDayDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info, CharacterCreationManager characterCreationManager) {
		super(player, shoebill, rootEventManager, info);
		this.p = player;
		this.characterCreationManager=characterCreationManager;
		this.eventManager = new ManagedEventManager(rootEventManager);
		eventManager.registerHandler(DialogResponseEvent.class, super.getDialog(), dialogEventHandler, HandlerPriority.NORMAL);
	}
	private DialogEventHandler dialogEventHandler = new DialogEventHandler()
	{
		public void onDialogResponse(DialogResponseEvent event)
		{	
			if(event.getDialogResponse() == 1){
				Player p = event.getPlayer();
				try {
					characterCreationManager.RecieveDay(event.getInputText());
				}catch (InputTooHighException e) {
					new CharacterDayDialog(p, shoebill, rootEventManager,
							"[ERROR]That number is too high to be a day, 30 is the maximum.\nEnter a Proper day of birth[1-30]:", characterCreationManager).show();;
				} catch (InputTooLowException e) {
					new CharacterDayDialog(p, shoebill, rootEventManager,
							"[ERROR]That number is too low to be a day, 1 is the minimum.\nEnter a Proper day of birth[1-30]:", characterCreationManager).show();;
				}catch (NoInputException e) {
					new CharacterDayDialog(p, shoebill, rootEventManager,
							"[ERROR]Enter a number...\nEnter a Proper day of birth[1-30]:", characterCreationManager).show();
					}
				event.setProcessed();
			}else{
				//TODO BACK
			}

		}
	};
}
