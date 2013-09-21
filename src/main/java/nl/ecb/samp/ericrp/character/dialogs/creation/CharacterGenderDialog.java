package nl.ecb.samp.ericrp.character.dialogs.creation;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractListDialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.character.dialogs.CharacterCreationManager;
import nl.ecb.samp.ericrp.character.model.Character.Gender;

public class CharacterGenderDialog extends AbstractListDialog {
	private Player p;
	private CharacterCreationManager characterCreationManager;

	public CharacterGenderDialog(Player player, Shoebill shoebill,
			EventManager eventManager,
			CharacterCreationManager characterCreationManager) {
		super(player, shoebill, eventManager);
		this.p = player;
		this.characterCreationManager = characterCreationManager;
		caption = ("Choose Gender:");
	}

	public void show() {
		dialogListItems.add(new DialogListItem("Male") {
			@Override
			public void onItemSelect() {
				characterCreationManager.RecieveGender(Gender.MALE);
			}

		});
		dialogListItems.add(new DialogListItem("Female") {
			@Override
			public void onItemSelect() {
				characterCreationManager.RecieveGender(Gender.FEMALE);
			}

		});
		buttonCancel = ("Back");
		super.show();
	}

}
