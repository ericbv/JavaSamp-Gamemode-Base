package nl.ecb.samp.ericrp.character.controllers;

import java.util.List;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.exception.AlreadyExistException;
import net.gtaun.shoebill.exception.IllegalLengthException;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import nl.ecb.samp.ericrp.account.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.account.model.Account;
import nl.ecb.samp.ericrp.character.exceptions.CharacterAlreadyCreatedException;
import nl.ecb.samp.ericrp.character.exceptions.NoCharacterSelectedException;
import nl.ecb.samp.ericrp.character.exceptions.playerAlreadyOnCharacterException;
import nl.ecb.samp.ericrp.character.model.Character;
import nl.ecb.samp.ericrp.character.dialog.selection.CharacterSelectionDialog;

import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.CharacterStore;
import nl.ecb.samp.ericrp.persistance.MysqlAdapter;

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
			p.spawn();
		} catch (IllegalArgumentException | IllegalLengthException
				| AlreadyExistException | NoCharacterSelectedException e) {
			e.printStackTrace();
		}
	}

	public void unLoadChar(Player p) {
		Character c;
		try {
			c = store.getCharacter(p);
			MysqlAdapter.getInstance().saveCharacter(c);
		} catch (NoCharacterSelectedException e) {
			e.printStackTrace();
		}
		store.removeCharacter(p);
		new CharacterSelectionDialog(p, shoebill, eventManager, this).show();
	}

	public void createCharacter(Character c, Player p) {
		Account a;
		try {
			a = AccountStore.getInstance().getAccount(p);
			try {
				c.setCharId(MysqlAdapter.getInstance().createCharacter(a, c));
			} catch (CharacterAlreadyCreatedException e) {
				e.printStackTrace();
			}
			List<Character> characters = a.getCharacters();
			characters.add(c);
			a.setCharacters(characters);
		} catch (NotLoggedInException e) {
			e.printStackTrace();
		}
		new CharacterSelectionDialog(p, shoebill, eventManager, this).show();
	}

	public void deleteCharacter(Player p,Character c, Account a) {
		List<Character> characters = a.getCharacters();
		characters.remove(c);
		MysqlAdapter.getInstance().deleteCharacter(c);
		new CharacterSelectionDialog(p, shoebill, eventManager, this).show();
	}

	public void SpawnCharacter(Player player) throws NoCharacterSelectedException {
		Character c = store.getCharacter(player);
		//shoebill.getResourceManager().getGamemode().getLogger().info("Modelid:"+c.getModelID());
		player.setSkin(c.getModelID());
		
	}
}
