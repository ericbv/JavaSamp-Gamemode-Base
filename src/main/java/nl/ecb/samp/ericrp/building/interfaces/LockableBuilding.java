package nl.ecb.samp.ericrp.building.interfaces;

import net.gtaun.shoebill.object.Player;

public interface LockableBuilding {
	public boolean isLocked();

	public void lock(Player p);
	
	public void unLock(Player p);
}
