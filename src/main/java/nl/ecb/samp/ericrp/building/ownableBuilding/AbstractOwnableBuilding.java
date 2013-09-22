package nl.ecb.samp.ericrp.building.ownableBuilding;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.building.interfaces.LockableBuilding;
import nl.ecb.samp.ericrp.building.model.AbstractBuilding;

public class AbstractOwnableBuilding extends AbstractBuilding implements
		LockableBuilding {

	public AbstractOwnableBuilding(String name, int pickupid) {
		super(name, pickupid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void lock(Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unLock(Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean attemptToEnter(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean attemptToExit(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getGameText() {
		// TODO Auto-generated method stub
		return null;
	}

}
