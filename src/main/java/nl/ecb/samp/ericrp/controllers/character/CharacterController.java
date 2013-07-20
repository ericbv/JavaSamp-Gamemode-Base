package nl.ecb.samp.ericrp.controllers.character;

import java.util.List;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.exception.AlreadyExistException;
import net.gtaun.shoebill.exception.IllegalLengthException;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import nl.ecb.samp.ericrp.dialog.character.selection.CharacterSelectionDialog;
import nl.ecb.samp.ericrp.exceptions.NoCharacterSelectedException;
import nl.ecb.samp.ericrp.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.exceptions.playerAlreadyOnCharacterException;

import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.CharacterStore;
import nl.ecb.samp.ericrp.model.Account;
import nl.ecb.samp.ericrp.model.Character;

public class CharacterController {
	private CharacterStore store;
	private Shoebill shoebill;
	private EventManager rootEventManager;
	private ManagedEventManager eventManager;

	/**
	 * This is the constructor for the account controller.
	 * 
	 * @param rootEventManager
	 * @param shoebill
	 */
	public CharacterController(Shoebill shoebill, EventManager rootEventManager) {
		this.store = CharacterStore.getInstance();
		this.shoebill = shoebill;
		this.rootEventManager = rootEventManager;
		this.eventManager = new ManagedEventManager(rootEventManager);
	}

	public void loadChar(Player p, Character c)
			throws playerAlreadyOnCharacterException {
		try {
			store.setCharacter(p, c);
			p.setName(store.getCharacter(p).getCharacterName());
		} catch (IllegalArgumentException | IllegalLengthException
				| AlreadyExistException | NoCharacterSelectedException e) {
			e.printStackTrace();
		}
	}

	public void unLoadChar(Player p) {
		store.removeCharacter(p);
		new CharacterSelectionDialog(p, shoebill, eventManager, this).show();
	}

	public void createCharacter(Character c, Player p) {
		Account a;
		try {
			a = AccountStore.getInstance().getAccount(p);
			List<Character> characters = a.getCharacters();
			characters.add(c);
			a.setCharacters(characters);
		} catch (NotLoggedInException e) {
			e.printStackTrace();
		}
		new CharacterSelectionDialog(p, shoebill, eventManager, this).show();
	}

	public void deleteCharacter() {

	}

	public void SpawnCharacter(Player player) throws NoCharacterSelectedException {
		player.setSkin(store.getCharacter(player).getModelID());
		
	}
}
