package nl.ecb.samp.ericrp.building.model;

import net.gtaun.shoebill.data.Location;
import net.gtaun.shoebill.object.Player;

public class CarEntrance extends Entrance {

	public CarEntrance(Location locEnter, Location locExit,
			AbstractBuilding building) {
		super(locEnter, locExit, building);
	}
	
	@Override
	public void enter(Player p){
		super.enter(p);
		if(getBuilding().attemptToEnter(p)){
			p.setLocation(getLocEnter());
			return;
		}
		
	}
	@Override
	public void exit(Player p){
		super.exit(p);
		if(getBuilding().attemptToExit(p)){
			p.setLocation(getLocExit());
			return;
		}
		
	}
}
