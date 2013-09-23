package nl.ecb.samp.ericrp.building.model;

import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.building.exceptions.LockedDoorException;

public class VehicleDoorway extends Doorway {

	public VehicleDoorway(Location locEnter, Location locExit,
			AbstractBuilding building) {
		super(locEnter, locExit, building);
	}

	@Override
	public void enter(Player p) throws LockedDoorException {
		super.enter(p);
		if (p.isInAnyVehicle()) {
			try {
				getBuilding().attemptToEnter(p);
				p.getVehicle().setLocation(getLocEnter());
			} catch (LockedDoorException e) {
				throw new LockedDoorException();
			}

		} else {
			super.enter(p);
		}

	}

	@Override
	public void exit(Player p) throws LockedDoorException {
		if (p.isInAnyVehicle()) {
			try {
				getBuilding().attemptToExit(p);
				p.getVehicle().setLocation(getLocEnter());
			} catch (LockedDoorException e) {
				throw new LockedDoorException();
			}

		} else {
			super.exit(p);
		}

	}
}
