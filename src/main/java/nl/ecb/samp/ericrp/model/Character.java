package nl.ecb.samp.ericrp.model;

import java.util.Date;

import nl.ecb.samp.ericrp.persistance.MysqlAdapter;

public class Character implements SaveAble{

	private int charId;
	private String CharacterName;
	private Gender gender;
	private int ModelID;
	private Date Birthdate;
	
	public enum Gender {
		MALE, FEMALE
	};
	
	public Character(int charId, String characterName, int modelID, Date birthdate,Gender gender) {
		super();
		CharacterName = characterName;
		ModelID = modelID;
		Birthdate = birthdate;
		this.gender = gender;
		this.charId = charId;
	}

	public static Character load(int charId,String characterName, int modelID,
			Date birthdate,Gender gender) {
		return new Character(charId,characterName, modelID, birthdate, gender);
	}
	
	public static Character create(String characterName, int modelID,
			Date birthdate,Gender gender) {
		return new Character(-1,characterName, modelID, birthdate, gender);
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

	public int getCharId() {
		return charId;
	}

	public void setCharId(int charId) {
		this.charId = charId;
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

	@Override
	public void save() {
		MysqlAdapter.getInstance().saveCharacter(this);
		
	}
}
