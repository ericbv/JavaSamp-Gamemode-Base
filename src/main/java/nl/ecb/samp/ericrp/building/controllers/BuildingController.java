package nl.ecb.samp.ericrp.building.controllers;

import java.awt.List;
import java.util.ArrayList;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
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
	 */
	public BuildingController(Shoebill shoebill, EventManager rootEventManager) {
		this.store = BuildingStore.getInstance();
		this.shoebill = shoebill;
		this.eventManager = new ManagedEventManager(rootEventManager);
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
