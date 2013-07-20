package nl.ecb.samp.ericrp.dialog.character.creation;

import java.util.List;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.dialog.AbstractListDialog;
import nl.ecb.samp.ericrp.dialog.AbstractListDialog.DialogListItem;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.exceptions.playerAlreadyOnCharacterException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.model.Character;
import nl.ecb.samp.ericrp.model.Character.Gender;

public class CharacterGenderDialog extends AbstractListDialog {
	private Player p;
	private CharacterCreationManager characterCreationManager;

	protected CharacterGenderDialog(Player player, Shoebill shoebill,
			EventManager eventManager, CharacterCreationManager characterCreationManager) {
		super(player, shoebill, eventManager);
		this.p = player;
		this.characterCreationManager=characterCreationManager;
		setCaption("Choose Gender:");
	}
	public void show(){
		dialogListItems.add(
				new DialogListItem("Male")
				{
					@Override
					public void onItemSelect() {
						characterCreationManager.RecieveGender(Gender.MALE);
					}

				});
		dialogListItems.add(
				new DialogListItem("Female")
				{
					@Override
					public void onItemSelect() {
						characterCreationManager.RecieveGender(Gender.FEMALE);	
					}

				});
		super.show();
	}

}
