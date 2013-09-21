package nl.ecb.samp.ericrp.building.model;

import java.util.List;

import net.gtaun.shoebill.object.Player;

public abstract class AbstractBuilding {
	private List<Doorway> entrances;
	private int ID;
	private int pickupid;

	private String name;

	public AbstractBuilding(String Name, int pickupid) {
		this.name = name;
		this.pickupid = pickupid;
	}

	public abstract boolean attemptToEnter(Player p);

	public abstract boolean attemptToExit(Player p);

	public abstract String getGameText();

	public int getPickupid() {
		return pickupid;
	}

	public void setPickupid(int pickupid) {
		this.pickupid = pickupid;
	}

	public List<Doorway> getEntrances() {
		return entrances;
	}

	public void setEntrances(List<Doorway> entrances) {
		this.entrances = entrances;
	}

	public void addEntrance(Doorway e) {
		entrances.add(e);
		// TODO MAKE PROPERLY
	}

	public void removeEntrance(Doorway e) {
		entrances.remove(e);
		// TODO MAKE PROPERLY
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
