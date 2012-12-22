package nl.ecb.samp.ericrp.model;

public class Character {

	private String CharacterName;
	private int ModelID;
	private String Birthdate;
	public Character(String characterName, int modelID, String birthdate) {
		super();
		CharacterName = characterName;
		ModelID = modelID;
		Birthdate = birthdate;
	}
	public static Character load(String characterName, int modelID, String birthdate){
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
	public String getBirthdate() {
		return Birthdate;
	}
	public void setBirthdate(String birthdate) {
		Birthdate = birthdate;
	}

}
