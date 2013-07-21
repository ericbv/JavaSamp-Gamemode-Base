package nl.ecb.samp.ericrp.controllers.character;

import java.util.List;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.event.PlayerEventHandler;
import net.gtaun.shoebill.event.player.PlayerRequestSpawnEvent;
import net.gtaun.shoebill.event.player.PlayerSpawnEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.dialog.character.selection.CharacterSelectionDialog;
import nl.ecb.samp.ericrp.events.AccountRequestLoginEvent;
import nl.ecb.samp.ericrp.events.AccountRequestLogoutEvent;
import nl.ecb.samp.ericrp.events.PlayerLoginEvent;
import nl.ecb.samp.ericrp.events.handler.AccountEventHandler;
import nl.ecb.samp.ericrp.exceptions.NoCharacterSelectedException;
import nl.ecb.samp.ericrp.main.AccountStore;
import nl.ecb.samp.ericrp.main.CharacterStore;
import nl.ecb.samp.ericrp.model.Account;
import nl.ecb.samp.ericrp.persistance.MysqlAdapter;
import nl.ecb.samp.ericrp.model.Character;

public class CharacterInputController {
	private Shoebill shoebill;
	private ManagedEventManager eventManager;
	private AccountStore store;
	private CharacterController con;

	public CharacterInputController(Shoebill shoebill,
			EventManager rootEventManager) {
		this.shoebill = shoebill;
		this.eventManager = new ManagedEventManager(rootEventManager);
		this.store = AccountStore.getInstance();
		this.con = new CharacterController(shoebill, rootEventManager);
		eventManager.registerHandler(PlayerLoginEvent.class,
				accountEventHandler, HandlerPriority.NORMAL);
		eventManager.registerHandler(AccountRequestLoginEvent.class,
				accountEventHandler, HandlerPriority.HIGH);
		eventManager.registerHandler(AccountRequestLogoutEvent.class,
				accountEventHandler, HandlerPriority.NORMAL);
		eventManager.registerHandler(PlayerSpawnEvent.class,
				playerEventHandler, HandlerPriority.NORMAL);

	}

	public void uninitialize() {
		eventManager.cancelAll();
	}

	private AccountEventHandler accountEventHandler = new AccountEventHandler() {
		public void onPlayerLogin(PlayerLoginEvent event) {
			Player player = event.getPlayer();
			new CharacterSelectionDialog(player, shoebill, eventManager, con)
					.show();
		}

		public void onAccountRequestLogin(AccountRequestLoginEvent event) {
			Account a = event.getAccount();
			a.setCharacters(MysqlAdapter.getInstance().getCharacters(a.getID())); // Load
																					// Characters
		}

		public void onAccountRequestLogout(AccountRequestLogoutEvent event) {
			Account a = event.getAccount();
			List<Character> characters = a.getCharacters();
			for (Character c : characters) {
				c.save();
			}

		}

	};

	private PlayerEventHandler playerEventHandler = new PlayerEventHandler() {
		public void onPlayerSpawn(PlayerSpawnEvent event) {
			Player player = event.getPlayer();
			try {
				con.SpawnCharacter(player);
			} catch (NoCharacterSelectedException e) {
				e.printStackTrace();
			}
		}

		public void onPlayerRequestSpawn(PlayerRequestSpawnEvent event) {
			Player player = event.getPlayer();
			if (!CharacterStore.getInstance().isOnCharacter(player)) {

				new CharacterSelectionDialog(player, shoebill, eventManager,
						con).show();
				event.disallow();
			}
		}

	};
}
