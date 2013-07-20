package nl.ecb.samp.ericrp.dialog.character.creation;

import java.util.Date;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.model.Character.*;
import nl.ecb.samp.ericrp.model.Character;

public class CharacterCreationManager {
	private Player p;
	private String firstName;
	private String lastName;
	private int day;
	private int month;
	private int year;
	private Gender gender;
	private Shoebill shoebill;
	private EventManager rootEventManager;

	public CharacterCreationManager(Player p) {
		this.p = p;
		StartCharacterCreationSequence();
	}

	private void StartCharacterCreationSequence() {
		new CharacterFirstnameDialog(p, shoebill, rootEventManager,
				"Please enter you're desired firstname.").show();
	}

	public void RecieveFirstname(String firstName) {
		// TODO save check throw

		new CharacterLastnameDialog(p, shoebill, rootEventManager, "Please enter you're desired lastname.").show();
	}

	public void RecieveLastname(String lastName) {
		// TODO save check throw

		new CharacterGenderDialog(p, shoebill, rootEventManager).show();
	}

	public void RecieveGender(Gender gender) {
		// TODO save check throw
		new CharacterDayDialog(p, shoebill, rootEventManager, firstName).show();
	}

	public void RecieveDay(int day) {
		// TODO save check throw
		new CharacterMonthDialog(p, shoebill, rootEventManager, firstName).show();
	}

	public void RecieveMonth(int month) {
		// TODO save check throw
		new CharacterYearDialog(p, shoebill, rootEventManager, firstName).show();
	}

	public void RecieveYear(int year) {
		// TODO save check throw

		Character.load(firstName + "_" + lastName, BaseModelID(),
				new Date(day, month, year), gender);
	}

	private int BaseModelID() {
		//TODO set properId's here. or make them dynamic from config file.
		switch (gender) {
		case MALE:
			return 51;
		case FEMALE:
			return 52;
		}
		return day;
	}
}
