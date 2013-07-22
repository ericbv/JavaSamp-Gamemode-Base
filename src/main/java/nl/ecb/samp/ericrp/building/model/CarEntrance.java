package nl.ecb.samp.ericrp.building.model;

import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.object.Player;

public class CarEntrance extends Entrance {

	public CarEntrance(int pickupid, Location locEnter, Location locExit,
			AbstractBuilding building) {
		super(pickupid, locEnter, locExit, building);
	}

	@Override
	public void enter(Player p) {
		super.enter(p);
		if (p.isInAnyVehicle()) {
			if (getBuilding().attemptToEnter(p)) {
				p.getVehicle().setLocation(getLocEnter());
				return;
			}
		} else {
			super.enter(p);
		}

	}

	@Override
	public void exit(Player p) {
		if (p.isInAnyVehicle()) {
			if (getBuilding().attemptToExit(p)) {
				p.getVehicle().setLocation(getLocEnter());
				return;
			}
		} else {
			super.exit(p);
		}

	}
}
