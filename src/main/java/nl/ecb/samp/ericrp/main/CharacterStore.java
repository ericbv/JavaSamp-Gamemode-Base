package nl.ecb.samp.ericrp.main;

import java.util.HashMap;
import java.util.Map;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.character.exceptions.NoCharacterSelectedException;
import nl.ecb.samp.ericrp.character.exceptions.playerAlreadyOnCharacterException;
import nl.ecb.samp.ericrp.character.model.Character;

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

	public Character getCharacter(Player p) throws NoCharacterSelectedException{
		if(!isOnCharacter(p)){
			throw new NoCharacterSelectedException();
		}
		return characterList.get(p);
		

	}
	public void setCharacter(Player p, Character character) throws playerAlreadyOnCharacterException{
		if(isOnCharacter(p)){
			throw new playerAlreadyOnCharacterException();
		}
		characterList.put(p, character);
	}
	public void removeCharacter(Player p){
		characterList.remove(p);
	}
}
