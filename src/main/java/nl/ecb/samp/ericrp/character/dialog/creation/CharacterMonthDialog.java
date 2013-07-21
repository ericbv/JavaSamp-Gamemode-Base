package nl.ecb.samp.ericrp.character.dialog.creation;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.event.DialogEventHandler;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.dialog.AbstractInputDialog;
import nl.ecb.samp.ericrp.character.dialog.CharacterCreationManager;
import nl.ecb.samp.ericrp.character.dialog.creation.exceptions.InputTooHighException;
import nl.ecb.samp.ericrp.character.dialog.creation.exceptions.InputTooLowException;
import nl.ecb.samp.ericrp.character.dialog.creation.exceptions.NoInputException;

public class CharacterMonthDialog extends AbstractInputDialog {
	private Player p;
	private CharacterCreationManager characterCreationManager;
	private ManagedEventManager eventManager;

	public CharacterMonthDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info,
			CharacterCreationManager characterCreationManager) {
		super(player, shoebill, rootEventManager, info);
		this.p = player;
		this.characterCreationManager = characterCreationManager;
		this.eventManager = new ManagedEventManager(rootEventManager);
		eventManager.registerHandler(DialogResponseEvent.class,
				super.getDialog(), dialogEventHandler, HandlerPriority.NORMAL);
	}

	public void show() {
		this.setButtonOk("Next");
		this.setButtonCancel("Back");
		super.show();
	}

	private DialogEventHandler dialogEventHandler = new DialogEventHandler() {
		public void onDialogResponse(DialogResponseEvent event) {
			if (event.getDialogResponse() == 1) {
				Player p = event.getPlayer();
				try {
					characterCreationManager.RecieveMonth(event.getInputText());
				} catch (InputTooHighException e) {
					new CharacterMonthDialog(
							p,
							shoebill,
							rootEventManager,
							"[ERROR]That number is too high to be a month, 12 is the maximum.\nEnter a Proper month of birth[1-12]:",
							characterCreationManager).show();
					;
				} catch (InputTooLowException e) {
					new CharacterMonthDialog(
							p,
							shoebill,
							rootEventManager,
							"[ERROR]That number is too low to be a month, 1 is the minimum.\nEnter a Proper month of birth[1-12]:",
							characterCreationManager).show();
					;
				} catch (NoInputException e) {
					new CharacterMonthDialog(
							p,
							shoebill,
							rootEventManager,
							"[ERROR]Enter a number...\nEnter a Proper month of birth[1-12]:",
							characterCreationManager).show();
				}catch (NumberFormatException e){
					new CharacterDayDialog(
							p,
							shoebill,
							rootEventManager,
							"[ERROR]Enter a number, NO TEXT...\nEnter a Proper day of birth[1-30]:",
							characterCreationManager).show();
				}
				event.setProcessed();
			} else {
				characterCreationManager.goBack(CharacterMonthDialog.this);
			}

		}
	};
}
