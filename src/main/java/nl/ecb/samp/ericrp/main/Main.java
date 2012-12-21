package nl.ecb.samp.ericrp.main;

import java.util.Date;

import org.slf4j.Logger;

import net.gtaun.shoebill.SampObjectFactory;
import net.gtaun.shoebill.SampObjectStore;
import net.gtaun.shoebill.object.Server;
import net.gtaun.shoebill.object.World;
import net.gtaun.shoebill.resource.Gamemode;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.controllers.account.AccountController;
import nl.ecb.samp.ericrp.model.Account;
import nl.ecb.samp.ericrp.persistance.MysqlAdapter;

public class Main extends Gamemode{
	private static Logger logger;
	private static AccountController lc;
	public static Logger logger()
	{
		return logger;
	}
	@Override
	protected void onDisable() throws Throwable {
		lc.uninitialize();

	}

	@Override
	protected void onEnable() throws Throwable {
		logger = getLogger();

		final SampObjectStore store = getShoebill().getSampObjectStore();
		final SampObjectFactory factory = getShoebill().getSampObjectFactory();
		final EventManager eventManager = getEventManager();
		lc = new AccountController(getShoebill(), eventManager);

		Server server = store.getServer();
		World world = store.getWorld();
		logger.info("Done.");
		Account a = MysqlAdapter.getInstance().getAccount("Ericbv","lol123");
		System.out.println("Username: "+a.getUsername());
		System.out.println("Password: "+a.getPassword());
		System.out.println("Email: "+a.getEmail());
		System.out.println("ID: "+a.getID());
		world.addPlayerClass(3,(float)1958.3783,(float)1343.1572,(float)15.3746,(float)270.1425,0,0,24,300,-1,-1);
	}

}
