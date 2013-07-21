package nl.ecb.samp.ericrp.building.model;

import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.main.CharacterStore;

public class Entrance {
	private AbstractBuilding building;
	private Location locEnter;
	private Location locExit;

	public Entrance(Location locEnter, Location locExit,
			AbstractBuilding building) {
		this.locEnter = locEnter;
		this.locEnter = locEnter;
		this.building = building;
	}

	public AbstractBuilding getBuilding() {
		return building;
	}

	public void setBuilding(AbstractBuilding building) {
		this.building = building;
	}

	public Location getLocEnter() {
		return locEnter;
	}

	public void setLocEnter(Location locEnter) {
		this.locEnter = locEnter;
	}

	public Location getLocExit() {
		return locExit;
	}

	public void setLocExit(Location locExit) {
		this.locExit = locExit;
	}

	public void enter(Player p) {
		if (!p.isInAnyVehicle()) {
			if (building.attemptToEnter(p)) {
				p.setLocation(locEnter);
				return;
			}
		}
	}
}
