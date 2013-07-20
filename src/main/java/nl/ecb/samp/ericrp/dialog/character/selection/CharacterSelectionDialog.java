package nl.ecb.samp.ericrp.dialog.character.selection;

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

public class CharacterSelectionDialog extends AbstractListDialog {
private Player player;
private CharacterController con;


	public CharacterSelectionDialog(Player player, Shoebill shoebill,
			EventManager eventManager,CharacterController con) {
		super(player, shoebill, eventManager);
		this.player = player;
		this.con = con;
		setCaption("Character Mangement");
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
								try {
									con.loadChar(player, c);
								} catch (playerAlreadyOnCharacterException e) {
									e.printStackTrace();
								}
								
							}

						});
			}
		} catch (NotLoggedInException e) {
			e.printStackTrace();
		}
		dialogListItems.add(
				new DialogListItem("Create Character")
				{
					@Override
					public void onItemSelect() {
						new CharacterCreationManager(player, shoebill, rootEventManager, con);
					}

				});
		dialogListItems.add(
				new DialogListItem("Delete Character")
				{
					@Override
					public void onItemSelect() {
						// TODO Auto-generated method stub
						
					}

				});
		super.show();
	}

}
