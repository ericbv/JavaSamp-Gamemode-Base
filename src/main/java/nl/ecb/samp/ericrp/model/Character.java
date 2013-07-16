package nl.ecb.samp.ericrp.model;

import java.util.Date;

public class Character {

	private String CharacterName;
	private int ModelID;
	private Date Birthdate;
	public Character(String characterName, int modelID, Date birthdate) {
		super();
		CharacterName = characterName;
		ModelID = modelID;
		Birthdate = birthdate;
	}
	public static Character load(String characterName, int modelID, Date birthdate){
		return new Character(characterName, modelID, birthdate);
	}
	public String getCharacterName() {
		return CharacterName;
	}
	public void setCharacterName(String characterName) {
		CharacterName = characterName;
	}
	public int getModelID() {
		return ModelID;
	}
	public void setModelID(int modelID) {
		ModelID = modelID;
	}
	public Date getBirthdate() {
		return Birthdate;
	}
	public void setBirthdate(Date birthdate) {
		Birthdate = birthdate;
	}

}
