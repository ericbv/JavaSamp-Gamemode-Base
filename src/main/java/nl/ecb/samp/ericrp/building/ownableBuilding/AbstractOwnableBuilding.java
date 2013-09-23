package nl.ecb.samp.ericrp.building.ownableBuilding;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.building.interfaces.LockableBuilding;
import nl.ecb.samp.ericrp.building.model.AbstractBuilding;
import nl.ecb.samp.ericrp.character.exceptions.NoCharacterSelectedException;
import nl.ecb.samp.ericrp.main.CharacterStore;
import nl.ecb.samp.ericrp.main.Main;

public abstract class AbstractOwnableBuilding extends AbstractBuilding implements
		LockableBuilding {
	
	private int OwnerId;
	private boolean locked;
	
	public AbstractOwnableBuilding(String name, int pickupid) {
		super(name, pickupid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isLocked() {
		return locked;
	}

	public int getOwnerId() {
		return OwnerId;
	}

	public void setOwnerId(int ownerId) {
		OwnerId = ownerId;
	}
	
	@Override
	public void lock(Player p) {
		try {
			if(CharacterStore.getInstance().getCharacter(p).getCharId() == OwnerId){
				locked = true;
				
			}else{
				
			}
		} catch (NoCharacterSelectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void unLock(Player p) {
		// TODO Auto-generated method stub
		
	}
	

}
