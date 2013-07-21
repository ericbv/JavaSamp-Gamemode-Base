package nl.ecb.samp.ericrp.character.dialogs.creation;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.event.DialogEventHandler;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.dialog.AbstractInputDialog;
import nl.ecb.samp.ericrp.character.dialogs.CharacterCreationManager;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.NoInputException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.TooLongInputException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.TooShortInputException;

public class CharacterLastnameDialog extends AbstractInputDialog {
	private Player p;
	private CharacterCreationManager characterCreationManager;
	private ManagedEventManager eventManager;

	public CharacterLastnameDialog(Player player, Shoebill shoebill,
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
					characterCreationManager.RecieveLastname(event
							.getInputText());
				} catch (NoInputException e) {
					new CharacterLastnameDialog(
							p,
							shoebill,
							rootEventManager,
							"[ERROR]Please put in a lastname.\nEnter a Proper lastname:",
							characterCreationManager).show();
				} catch (TooShortInputException e) {
					new CharacterLastnameDialog(
							p,
							shoebill,
							rootEventManager,
							"[ERROR]That lastname is to short min 4 characters.\nEnter a Proper lastname:",
							characterCreationManager).show();
				} catch (TooLongInputException e) {
					new CharacterLastnameDialog(
							p,
							shoebill,
							rootEventManager,
							"[ERROR]That lastname is to long max 12 characters.\nEnter a Proper lastname:",
							characterCreationManager).show();
					;
				}
				event.setProcessed();
			} else {
				characterCreationManager.goBack(CharacterLastnameDialog.this);
			}

		}
	};
}
