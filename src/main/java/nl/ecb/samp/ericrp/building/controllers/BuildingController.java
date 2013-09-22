package nl.ecb.samp.ericrp.building.controllers;

import java.util.ArrayList;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.data.Location;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import nl.ecb.samp.ericrp.building.Gouverment.PoliceDepartment;
import nl.ecb.samp.ericrp.building.model.AbstractBuilding;
import nl.ecb.samp.ericrp.building.model.Doorway;
import nl.ecb.samp.ericrp.main.BuildingStore;
import nl.ecb.samp.ericrp.main.DoorwayList;

public class BuildingController {
	private BuildingStore store;
	private DoorwayList list;
	private Shoebill shoebill;
	private ManagedEventManager eventManager;

	/**
	 * This is the constructor for the account controller.
	 * 
	 * @param rootEventManager
	 * @param shoebill
	 * @param list 
	 */
	public BuildingController(Shoebill shoebill, EventManager rootEventManager, DoorwayList list) {
		this.store = BuildingStore.getInstance();
		this.shoebill = shoebill;
		this.eventManager = new ManagedEventManager(rootEventManager);
		this.list =  list;
		
		//TODO REMOVE THIS LATER
		PoliceDepartment pd = new PoliceDepartment("Main PD LV");
		Doorway PDdoor = new Doorway(new Location((float)2023.2477,(float)1347.8400,(float)10.8130),new Location((float)246.40,(float)110.84,(float)1003.22,10,12),pd);
		pd.addEntrance(PDdoor);
		load(pd);
	}

	public void load(AbstractBuilding building) {
		store.setBuilding(building.getID(), building);
		ArrayList<Doorway> e = new ArrayList<Doorway>();;
		e.addAll(building.getEntrances());
		for (Doorway ent : e) {
			list.addEntrance(ent);
		}
	}
}
