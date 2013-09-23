package nl.ecb.samp.ericrp.building.Gouverment;

import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.building.exceptions.LockedDoorException;
import nl.ecb.samp.ericrp.building.interfaces.LockableBuilding;
import nl.ecb.samp.ericrp.building.model.AbstractBuilding;

public class PoliceDepartment extends AbstractBuilding implements
		LockableBuilding {
	private boolean LockedDown;

	public PoliceDepartment(String name) {
		super(name, 1318);
		LockedDown = false;
	}

	@Override
	public void attemptToEnter(Player p) throws LockedDoorException {
		if (this.isLocked()) {
			throw new LockedDoorException();
		}
	}

	@Override
	public void attemptToExit(Player p) throws LockedDoorException {
		if (this.isLocked()) {
			throw new LockedDoorException();
		}
	}

	@Override
	public String getGameText() {
		return "~g~" + this.getName() + "~n~~w~type /enter to enter";
	}

	@Override
	public boolean isLocked() {
		return LockedDown;
	}

	@Override
	public void lock(Player p) {
		LockedDown = true;
	}

	@Override
	public void unLock(Player p) {
		LockedDown = false;
		p.sendMessage(Color.GREEN, "Door has been unlocked");
		
	}

	@Override
	public String getExitMessage() {
		return "Welcome to the "+getName();
	}

	@Override
	public String getEnterMessage() {
		return "Thanks for visiting "+getName();
	}

}
