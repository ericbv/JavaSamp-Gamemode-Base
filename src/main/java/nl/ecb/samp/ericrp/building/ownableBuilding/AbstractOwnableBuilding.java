package nl.ecb.samp.ericrp.building.ownableBuilding;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.building.interfaces.LockableBuilding;
import nl.ecb.samp.ericrp.building.model.AbstractBuilding;
import nl.ecb.samp.ericrp.building.ownableBuilding.exceptions.NotEnoughMoney;
import nl.ecb.samp.ericrp.building.ownableBuilding.exceptions.NotForSale;
import nl.ecb.samp.ericrp.building.ownableBuilding.exceptions.NotOwner;
import nl.ecb.samp.ericrp.character.exceptions.NoCharacterSelectedException;
import nl.ecb.samp.ericrp.main.CharacterStore;
import nl.ecb.samp.ericrp.main.Main;

public abstract class AbstractOwnableBuilding extends AbstractBuilding
		implements LockableBuilding {

	private Integer OwnerId;
	private boolean forSale;
	private Integer BaseValue;
	private Integer Price;
	private boolean locked;

	public AbstractOwnableBuilding(String name, int pickupid, Integer price) {
		super(name, pickupid);
		OwnerId = null;
		forSale = true;
		Price = price;
		BaseValue = Price;

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

	public boolean isForSale() {
		return forSale;
	}

	public void setForSale(boolean forSale) {
		this.forSale = forSale;
	}

	public Integer getPrice() {
		return Price;
	}

	public void setPrice(Integer price) {
		Price = price;
	}

	@Override
	public void lock(Player p) throws NotOwner {
		if (isOwner(p)) {
			locked = true;
		} else {
			throw new NotOwner();
		}

	}

	@Override
	public void unLock(Player p) throws NotOwner {
		if (isOwner(p)) {
			locked = false;
		} else {
			throw new NotOwner();
		}

	}

	public void buy(Player p) throws NotEnoughMoney, NotForSale {
		if (forSale) {
			if (p.getMoney() > this.Price) {
				try {
					//TODO Refactor code to make this look better
					p.giveMoney(-Price);
					if(OwnerId != null){
						//TODO MEchaNIC TO give money to a character that may not be online
					}
					this.OwnerId = CharacterStore.getInstance().getCharacter(p)
							.getCharId();
					forSale = false;
				} catch (NoCharacterSelectedException e) {
					e.printStackTrace();
				}
			} else {
				throw new NotEnoughMoney();
			}
		} else {
			throw new NotForSale();
		}
	}

	public void sell(Player p, Integer price) throws NotOwner {
		if (isOwner(p)) {
			Price = price;
			forSale = true;
		} else {
			throw new NotOwner();
		}
	}

	public void Whoresale(Player p) throws NotOwner {
		if (isOwner(p)) {
			p.giveMoney((int) (BaseValue*0.75));
			OwnerId = null;
			Price = BaseValue;
		} else {
			throw new NotOwner();
		}
	}

	private boolean isOwner(Player p) {
		try {
			return (CharacterStore.getInstance().getCharacter(p).getCharId() == OwnerId);
		} catch (NoCharacterSelectedException e) {
			e.printStackTrace();
		}
		return false;
	}

}
