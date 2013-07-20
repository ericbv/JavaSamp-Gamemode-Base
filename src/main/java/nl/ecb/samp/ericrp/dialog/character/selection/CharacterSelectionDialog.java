package nl.ecb.samp.ericrp.dialog.character.selection;

import java.util.List;


import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.controllers.character.CharacterController;
import nl.ecb.samp.ericrp.dialog.AbstractListDialog;
import nl.ecb.samp.ericrp.dialog.AbstractListDialog.DialogListItem;
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
		
	}
	public void show(){
		try {
			List<Character> Characters = AccountStore.getInstance().getAccount(player).getCharacters();
			for(final Character c :Characters){
				dialogListItems.add(
						new DialogListItem("- "+c.getCharacterName()+" "+c.getBirthdate().toGMTString())
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
