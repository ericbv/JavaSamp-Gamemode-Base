package nl.ecb.samp.ericrp.building.Gouverment;

import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.building.model.AbstractBuilding;

public class PoliceDepartment extends AbstractBuilding {
	private boolean LockedDown;
	
	public PoliceDepartment(String name) {
		super(name,1318);
		LockedDown = false;
	}

	@Override
	public boolean attemptToEnter(Player p) {
		if(this.isLockedDown()){
			p.sendMessage(Color.RED, "[ERROR]This Building has been locked down.");
			return false;
		}
		p.sendMessage(Color.WHITE, "Welcome to "+this.getName()+" "+p.getName()+".");
		return true;
	}

	@Override
	public boolean attemptToExit(Player p) {
		if(this.isLockedDown()){
			p.sendMessage(Color.RED, "[ERROR]This Building has been locked down.");
			return false;
		}
		return true;
	}

	@Override
	public String getGameText() {
		return "~g~"+this.getClass().getName()+"~n~~w~type /enter to enter";
	}

	public boolean isLockedDown() {
		return LockedDown;
	}

	public void setLockedDown(boolean lockedDown) {
		LockedDown = lockedDown;
	}

}
