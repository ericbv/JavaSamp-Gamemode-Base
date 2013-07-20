package nl.ecb.samp.ericrp.model;

import java.util.Date;

public class Character {


	private Gender gender;
	private String CharacterName;
	private int ID;
	private int ModelID;
	private Date Birthdate;
	
	public enum Gender {
		MALE, FEMALE
	};
	
	public Character(String characterName, int modelID, Date birthdate,Gender gender) {
		super();
		CharacterName = characterName;
		ModelID = modelID;
		Birthdate = birthdate;
		this.gender = gender;
	}

	public static Character load(String characterName, int modelID,
			Date birthdate,Gender gender) {
		return new Character(characterName, modelID, birthdate, gender);
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

	public static String getGenderName(Gender g) {
		switch (g) {
		case MALE:
			return "man";
		case FEMALE:
			return "woman";
		default:
			return null;
		}
	}

	public String getHisOrHerGender() {
		return getGenderName(this.gender);
	}

	public void setGender(Gender g) {
		this.gender = g;
	}
}
