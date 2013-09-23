package nl.ecb.samp.ericrp.main;

import java.util.ArrayList;

import nl.ecb.samp.ericrp.building.model.Doorway;

public class DoorwayList {
	private ArrayList<Doorway> doorways;
	private static DoorwayList INSTANCE;

	public DoorwayList(){
		doorways = new ArrayList<Doorway>();
	}
	
	public ArrayList<Doorway> getEntrances() {
		return doorways;
	}
	public void setEntrances(ArrayList<Doorway> entrances) {
		this.doorways = entrances;
	}
	
	public void addEntrance(Doorway e){
		doorways.add(e);
	}
	public void removeEntrance(Doorway e){
		doorways.remove(e);
	}

	public static DoorwayList getInstance() {
		if(INSTANCE == null){
			INSTANCE = new DoorwayList();
		}
		return INSTANCE;
	}
}
