package nl.ecb.samp.ericrp.main;

import java.util.HashMap;
import java.util.Map;

import nl.ecb.samp.ericrp.building.model.AbstractBuilding;

public class BuildingStore {
	private Map<Integer,AbstractBuilding> buildingList;
	private static BuildingStore INSTANCE;
	public BuildingStore(){
		buildingList = new HashMap<Integer, AbstractBuilding>();
	}
	public static BuildingStore getInstance(){
		if(INSTANCE == null){
			INSTANCE = new BuildingStore();
		}
		return INSTANCE;
	}

	public AbstractBuilding getBuilding(Integer id) {
		
		return buildingList.get(id);
		

	}
	public void setBuilding(Integer id, AbstractBuilding building){
		
		buildingList.put(id, building);
	}
	public void removeBuilding(Integer id){
		buildingList.remove(id);
	}
}