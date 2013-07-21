package nl.ecb.samp.ericrp.building.model;

import java.util.List;

import net.gtaun.shoebill.object.Player;

public abstract class AbstractBuilding {
	private List<Entrance> entrances;
	
	public abstract boolean attemptToEnter(Player p);
	public abstract boolean attemptToExit(Player p);
}
