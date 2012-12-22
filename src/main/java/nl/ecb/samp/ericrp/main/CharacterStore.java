package nl.ecb.samp.ericrp.main;

import java.util.HashMap;
import java.util.Map;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.exceptions.NoCharacterSelectedException;
import nl.ecb.samp.ericrp.exceptions.playerAlreadyOnCharacterException;

public class CharacterStore {
	private Map<Player,Character> characterList;
	private static CharacterStore INSTANCE;
	public CharacterStore(){
		characterList = new HashMap<Player, Character>();
	}
	public static CharacterStore getInstance(){
		if(INSTANCE == null){
			INSTANCE = new CharacterStore();
		}
		return INSTANCE;
	}

	public boolean isOnCharacter(Player p){
		if(characterList.get(p) == null){
			return false;
		}
		else{ 
			return true;
		}
	}

	public Character getAccount(Player p) throws NoCharacterSelectedException{
		if(!isOnCharacter(p)){
			throw new NoCharacterSelectedException();
		}
		return characterList.get(p);
		

	}
	public void setAccount(Player p, Character character) throws playerAlreadyOnCharacterException{
		if(!!isOnCharacter(p) == false){
			throw new playerAlreadyOnCharacterException();
		}
		characterList.put(p, character);
	}
	public void removeAccount(Player p){
		characterList.remove(p);
	}
}
