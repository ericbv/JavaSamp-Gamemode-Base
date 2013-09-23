package nl.ecb.samp.ericrp.building.interfaces;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.building.ownableBuilding.exceptions.NotOwner;

public interface LockableBuilding {
	public boolean isLocked();

	public void lock(Player p) throws NotOwner;
	
	public void unLock(Player p) throws NotOwner;
}
