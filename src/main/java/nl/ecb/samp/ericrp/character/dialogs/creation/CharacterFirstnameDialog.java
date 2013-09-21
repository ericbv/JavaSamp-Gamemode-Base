package nl.ecb.samp.ericrp.character.dialogs.creation;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractInputDialog;
import net.gtaun.shoebill.event.DialogEventHandler;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.character.dialogs.CharacterCreationManager;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.NoInputException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.TooLongInputException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.TooShortInputException;

public class CharacterFirstnameDialog extends AbstractInputDialog {
	private Player p;
	private CharacterCreationManager characterCreationManager;
	private ManagedEventManager eventManager;

	public CharacterFirstnameDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info,
			CharacterCreationManager characterCreationManager) {
		super(player, shoebill, rootEventManager);
		message = info;
		this.p = player;
		this.characterCreationManager = characterCreationManager;
		this.eventManager = new ManagedEventManager(rootEventManager);
		eventManager.registerHandler(DialogResponseEvent.class, this,
				dialogEventHandler, HandlerPriority.NORMAL);
	}

	public void show() {
		buttonOk = ("Next");
		buttonCancel = ("Back");
		super.show();
	}

	private DialogEventHandler dialogEventHandler = new DialogEventHandler() {
		public void onDialogResponse(DialogResponseEvent event) {
			if (event.getDialogResponse() == 1) {
				Player p = event.getPlayer();
				try {
					characterCreationManager.RecieveFirstname(event
							.getInputText());
				} catch (NoInputException e) {
					new CharacterFirstnameDialog(
							p,
							shoebill,
							rootEventManager,
							"[ERROR]Please put in a firstname.\nEnter a Proper firstname:",
							characterCreationManager).show();
				} catch (TooShortInputException e) {
					new CharacterFirstnameDialog(
							p,
							shoebill,
							rootEventManager,
							"[ERROR]That firstname is to short min 4 characters.\nEnter a Proper firstname:",
							characterCreationManager).show();
				} catch (TooLongInputException e) {
					new CharacterFirstnameDialog(
							p,
							shoebill,
							rootEventManager,
							"[ERROR]That firstname is to long max 12 characters.\nEnter a Proper firstname:",
							characterCreationManager).show();
					;
				}
				event.setProcessed();
			} else {
				characterCreationManager.goBack(CharacterFirstnameDialog.this);
			}

		}
	};

}
