package nl.ecb.samp.ericrp.character.dialogs.creation;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractInputDialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.character.dialogs.CharacterCreationManager;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.InputTooHighException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.InputTooLowException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.NoInputException;

public class CharacterYearDialog extends AbstractInputDialog {
	private Player p;
	private CharacterCreationManager characterCreationManager;

	public CharacterYearDialog(Player player, Shoebill shoebill,
			EventManager rootEventManager, String info,
			CharacterCreationManager characterCreationManager) {
		super(player, shoebill, rootEventManager);
		message = info;
		this.p = player;
		this.characterCreationManager = characterCreationManager;
	}

	public void show() {
		buttonOk = ("Create");
		buttonCancel = ("Back");
		super.show();
	}

	@Override
	public void onClickOk(String inputText) {
		try {
			characterCreationManager.RecieveYear(inputText);
		} catch (InputTooHighException e) {
			new CharacterYearDialog(
					p,
					shoebill,
					rootEventManager,
					"[ERROR]That number is too high to be a birth year, 2013 is the maximum.\nEnter a Proper year of birth[1940-2013]::",
					characterCreationManager).show();
			;
		} catch (InputTooLowException e) {
			new CharacterYearDialog(
					p,
					shoebill,
					rootEventManager,
					"[ERROR]That number is too low to be a birth year, 1940 is the minimum.\nEnter a Proper year of birth[1940-2013]::",
					characterCreationManager).show();
			;
		} catch (NoInputException e) {
			new CharacterYearDialog(
					p,
					shoebill,
					rootEventManager,
					"[ERROR]Enter a number...\nEnter a Proper year of birth[1940-2013]:",
					characterCreationManager).show();
		} catch (NumberFormatException e) {
			new CharacterDayDialog(
					p,
					shoebill,
					rootEventManager,
					"[ERROR]Enter a number, NO TEXT...\nEnter a Proper day of birth[1-30]:",
					characterCreationManager).show();
		}
		super.onClickOk(inputText);
	}

	@Override
	protected void onClickCancel() {
		characterCreationManager.goBack(CharacterYearDialog.this);
		super.onClickCancel();
	}

}
