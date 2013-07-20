package nl.ecb.samp.ericrp.main;


import org.slf4j.Logger;

import net.gtaun.shoebill.SampObjectFactory;
import net.gtaun.shoebill.SampObjectStore;
import net.gtaun.shoebill.object.Server;
import net.gtaun.shoebill.object.World;
import net.gtaun.shoebill.resource.Gamemode;
import net.gtaun.util.event.EventManager;

import nl.ecb.samp.ericrp.controllers.account.AccountInputController;
import nl.ecb.samp.ericrp.controllers.character.CharacterInputController;


public class Main extends Gamemode{
	private static Logger logger;
	private static AccountInputController IN_CO_Acc;
	private static CharacterInputController IN_CO_Char;
	public static Logger logger()
	{
		return logger;
	}
	@Override
	protected void onDisable() throws Throwable {
		IN_CO_Acc.uninitialize();
		IN_CO_Char.uninitialize();

	}

	@Override
	protected void onEnable() throws Throwable {
		logger = getLogger();

		final SampObjectStore store = getShoebill().getSampObjectStore();
		final SampObjectFactory factory = getShoebill().getSampObjectFactory();
		final EventManager eventManager = getEventManager();
		IN_CO_Acc = new AccountInputController(getShoebill(), eventManager);
		IN_CO_Char = new CharacterInputController(getShoebill(), eventManager);
		Server server = store.getServer();
		World world = store.getWorld();
		world.addPlayerClass(3,(float)1958.3783,(float)1343.1572,(float)15.3746,(float)270.1425,0,0,24,300,-1,-1);
		logger.info("Done.");
	}

}
