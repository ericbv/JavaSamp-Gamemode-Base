package nl.ecb.samp.ericrp.character.dialogs.deletion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.event.DialogEventHandler;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.account.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.character.dialogs.CharacterDeletionManager;
import nl.ecb.samp.ericrp.character.model.Character;
import nl.ecb.samp.ericrp.dialog.AbstractListDialog;
import nl.ecb.samp.ericrp.main.AccountStore;

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
	private DialogEventHandler dialogEventHandler = new DialogEventHandler() {
		public void onDialogResponse(DialogResponseEvent event) {
			if (event.getDialogResponse() == 1) {
			} else {
				characterDeletionManager.recieveConfirm(false);
			}

		}
	};

}
