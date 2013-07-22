package nl.ecb.samp.ericrp.building.model;

import java.util.List;

import net.gtaun.shoebill.object.Player;

public abstract class AbstractBuilding {
	private List<Entrance> entrances;

	public abstract boolean attemptToEnter(Player p);

	public abstract boolean attemptToExit(Player p);
	
	public abstract String getTextdraw();

	public List<Entrance> getEntrances() {
		return entrances;
	}

	public void setEntrances(List<Entrance> entrances) {
		this.entrances = entrances;
	}

	public void addEntrance(Entrance e) {
		entrances.add(e);
	}

	public void removeEntrance(Entrance e) {
		entrances.remove(e);
	}
}
