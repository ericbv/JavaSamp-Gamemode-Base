package nl.ecb.samp.ericrp.character.dialogs.creation;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractInputDialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.character.dialogs.CharacterCreationManager;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.NoInputException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.TooLongInputException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.TooShortInputException;

public class CharacterFirstnameDialog extends AbstractInputDialog {
	private Player p;
	private CharacterCreationManager characterCreationManager;

	public CharacterFirstnameDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info,
			CharacterCreationManager characterCreationManager) {
		super(player, shoebill, rootEventManager);
		message = info;
		this.p = player;
		this.characterCreationManager = characterCreationManager;
	}

	public void show() {
		buttonOk = ("Next");
		buttonCancel = ("Back");
		super.show();
	}

	@Override
	public void onClickOk(String inputText) {
		try {
			characterCreationManager.RecieveFirstname(inputText);
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
		}
		super.onClickOk(inputText);
	}
	@Override
	protected void onClickCancel() {
		characterCreationManager.goBack(CharacterFirstnameDialog.this);
		super.onClickCancel();
	}

}
