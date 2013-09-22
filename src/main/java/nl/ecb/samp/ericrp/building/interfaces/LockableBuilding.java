package nl.ecb.samp.ericrp.building.interfaces;

import net.gtaun.shoebill.object.Player;

public interface LockableBuilding {
	public boolean isLocked();

	public void setLocked(Player p, boolean locked);
}
