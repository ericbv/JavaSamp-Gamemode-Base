package nl.ecb.samp.ericrp.dialog.character.deletion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.controllers.character.CharacterController;
import nl.ecb.samp.ericrp.dialog.AbstractListDialog;
import nl.ecb.samp.ericrp.dialog.AbstractListDialog.DialogListItem;
import nl.ecb.samp.ericrp.dialog.character.creation.CharacterCreationManager;
import nl.ecb.samp.ericrp.dialog.user.ChangePassword;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.exceptions.playerAlreadyOnCharacterException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.model.Character;

public class CharacterDeletionSelectionDialog extends AbstractListDialog {
private Player player;
private CharacterDeletionManager characterDeletionManager;


	public CharacterDeletionSelectionDialog(Player player, Shoebill shoebill,
			EventManager eventManager,CharacterDeletionManager characterDeletionManager) {
		super(player, shoebill, eventManager);
		this.player = player;
		this.characterDeletionManager = characterDeletionManager;
		setCaption("Choose a Character to delete");
	}
	public void show(){
		try {
			List<Character> Characters = AccountStore.getInstance().getAccount(player).getCharacters();
			for(final Character c :Characters){
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				dialogListItems.add(
						new DialogListItem("- "+c.getCharacterName()+" "+c.getHisOrHerGender()+" "+df.format(c.getBirthdate()))
						{
							@Override
							public void onItemSelect() {
									characterDeletionManager.recieveSelect(c);	
							}

						});
			}
		} catch (NotLoggedInException e) {
			e.printStackTrace();
		}
		super.show();
	}

}
