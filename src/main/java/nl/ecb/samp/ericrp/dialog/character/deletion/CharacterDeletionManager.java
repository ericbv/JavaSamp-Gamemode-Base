package nl.ecb.samp.ericrp.dialog.character.deletion;

import java.util.List;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.controllers.character.CharacterController;
import nl.ecb.samp.ericrp.dialog.character.selection.CharacterSelectionDialog;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.model.Character;

public class CharacterDeletionManager {
	private Character c;
	private Player p;
	private Shoebill shoebill;
	private CharacterController con;
	private EventManager rootEventManager;

	public CharacterDeletionManager(Player p, Shoebill shoebill,
			EventManager rootEventManager, CharacterController con) {
		try {
			if (AccountStore.getInstance().getAccount(p).getCharacters().size() == 0) {
				new CharacterSelectionDialog(p, shoebill, rootEventManager, con)
				.show();
			} else {
				this.p = p;
				this.shoebill = shoebill;
				this.con = con;
				this.rootEventManager = rootEventManager;
				new CharacterDeletionSelectionDialog(p, shoebill,
						rootEventManager, this).show();
			}
		} catch (NotLoggedInException e) {
			e.printStackTrace();
		}

	}

	public void recieveConfirm(boolean b) {
		if (b == true) {
			try {
				con.deleteCharacter(p, c, AccountStore.getInstance()
						.getAccount(p));
			} catch (NotLoggedInException e) {
				e.printStackTrace();
			}
		} else {
			new CharacterSelectionDialog(p, shoebill, rootEventManager, con)
					.show();
		}
	}

	public void recieveSelect(Character c) {
		this.c = c;
		new CharacterDeletionConfirmDialog(p, c, shoebill, rootEventManager,
				this).show();
	}

}
