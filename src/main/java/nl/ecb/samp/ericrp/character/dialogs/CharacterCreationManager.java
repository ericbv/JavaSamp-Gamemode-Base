package nl.ecb.samp.ericrp.character.dialogs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractDialog;
import net.gtaun.shoebill.common.dialog.AbstractInputDialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.character.controllers.CharacterController;
import nl.ecb.samp.ericrp.character.model.Character;
import nl.ecb.samp.ericrp.character.model.Character.*;
import nl.ecb.samp.ericrp.character.dialogs.creation.CharacterDayDialog;
import nl.ecb.samp.ericrp.character.dialogs.creation.CharacterFirstnameDialog;
import nl.ecb.samp.ericrp.character.dialogs.creation.CharacterGenderDialog;
import nl.ecb.samp.ericrp.character.dialogs.creation.CharacterLastnameDialog;
import nl.ecb.samp.ericrp.character.dialogs.creation.CharacterMonthDialog;
import nl.ecb.samp.ericrp.character.dialogs.creation.CharacterYearDialog;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.InputTooHighException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.InputTooLowException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.NoInputException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.TooLongInputException;
import nl.ecb.samp.ericrp.character.dialogs.creation.exceptions.TooShortInputException;
import nl.ecb.samp.ericrp.character.dialogs.selection.CharacterSelectionDialog;
import nl.ecb.samp.ericrp.persistance.MysqlAdapter;

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
	private CharacterController con;

	public CharacterCreationManager(Player p, Shoebill shoebill,
			EventManager rootEventManager, CharacterController con) {
		this.p = p;
		this.shoebill = shoebill;
		this.rootEventManager = rootEventManager;
		this.con = con;
		StartCharacterCreationSequence();
	}

	private void StartCharacterCreationSequence() {
		new CharacterFirstnameDialog(p, shoebill, rootEventManager,
				"Please enter you're desired firstname.", this).show();
	}

	public void RecieveFirstname(String firstName) throws NoInputException,
			TooShortInputException, TooLongInputException {
		// TODO ADD A CHECK TO PREVENT NAME DUPLIVATION
		if (firstName.length() == 0) {
			throw new NoInputException();
		} else if (firstName.length() > 12) {
			throw new TooLongInputException();
		} else if (firstName.length() < 3) {
			throw new TooShortInputException();
		} else {
			this.firstName = firstName;
		}
		new CharacterLastnameDialog(p, shoebill, rootEventManager,
				"Please enter you're desired lastname.", this).show();
	}

	public void RecieveLastname(String lastName) throws TooShortInputException,
			NoInputException, TooLongInputException {
		// TODO ADD A CHECK TO PREVENT NAME DUPLIVATION
		if (lastName.length() == 0) {
			throw new NoInputException();
		} else if (lastName.length() > 12) {
			throw new TooLongInputException();
		} else if (lastName.length() < 3) {
			throw new TooShortInputException();
		}else if(!MysqlAdapter.getInstance().isCharacterNameAvalible(firstName + "_" + lastName)){
			new CharacterFirstnameDialog(p, shoebill, rootEventManager,
					"[ERROR]That name is already choosen, please enter another name..\nEnter a firstname:", this).show();
		} else {
			this.lastName = lastName;
		}
		new CharacterGenderDialog(p, shoebill, rootEventManager, this).show();
	}

	public void RecieveGender(Gender gender) {
		this.gender = gender;
		new CharacterDayDialog(p, shoebill, rootEventManager,
				"Please enter the day[1-30] you were born.", this).show();
	}

	public void RecieveDay(String day) throws NoInputException,
			InputTooHighException, InputTooLowException, NumberFormatException {
		Integer iDay = Integer.parseInt(day);
		if (lastName.length() == 0) {
			throw new NoInputException();
		} else if (iDay < 1) {
			throw new InputTooLowException();
		} else if (iDay > 30) {
			throw new InputTooHighException();
		}
		this.day = iDay;
		new CharacterMonthDialog(p, shoebill, rootEventManager,
				"Please enter the Month[1-12] you were born.", this).show();
	}

	public void RecieveMonth(String month) throws InputTooLowException,
			InputTooHighException, NoInputException,NumberFormatException {
		Integer iMonth = Integer.parseInt(month);
		if (lastName.length() == 0) {
			throw new NoInputException();
		} else if (iMonth < 1) {
			throw new InputTooLowException();
		} else if (iMonth > 12) {
			throw new InputTooHighException();
		}
		this.month = iMonth;
		new CharacterYearDialog(p, shoebill, rootEventManager,
				" Please enter the Year[1940-2013] you were born.", this)
				.show();
	}

	public void RecieveYear(String year) throws InputTooLowException,
			InputTooHighException, NoInputException,NumberFormatException {
		try {
			Integer iYear = Integer.parseInt(year);
			if (lastName.length() == 0) {
				throw new NoInputException();
			} else if (iYear < 1940) {
				throw new InputTooLowException();
			} else if (iYear > 2013) {
				throw new InputTooHighException();
			}
			this.year = iYear;
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			Character c;
			if (day < 10) {
				if (month < 10) {
					c = Character.create(firstName + "_" + lastName,
							BaseModelID(), df.parse("0" + month + "-0" + day
									+ "-" + this.year), gender);
				} else {
					c = Character.create(firstName + "_" + lastName,
							BaseModelID(),
							df.parse(month + "-0" + day + "-" + this.year),
							gender);
				}
			} else {
				if (month < 10) {

					c = Character
							.create(firstName + "_" + lastName,
									BaseModelID(),
									df.parse("0" + month + "-" + day + "-"
											+ this.year), gender);

				} else {
					c = Character.create(firstName + "_" + lastName,
							BaseModelID(),
							df.parse(month + "-" + day + "-" + this.year),
							gender);
				}
			}
			con.createCharacter(c, p);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int BaseModelID() {
		// TODO set properId's here. or make them dynamic from config file.
		switch (gender) {
		case MALE:
			return 1;
		case FEMALE:
			return 12;
		}
		return day;
	}

	public void goBack(AbstractDialog d) {
		if (d instanceof AbstractInputDialog) {
			if (d instanceof CharacterFirstnameDialog) {
				new CharacterSelectionDialog(p, shoebill, rootEventManager, con)
						.show();
			} else if (d instanceof CharacterLastnameDialog) {
				new CharacterFirstnameDialog(p, shoebill, rootEventManager,
						"Please enter you're desired firstname.", this).show();
			} else if (d instanceof CharacterDayDialog) {
				new CharacterGenderDialog(p, shoebill, rootEventManager, this)
						.show();
			} else if (d instanceof CharacterMonthDialog) {
				new CharacterDayDialog(p, shoebill, rootEventManager,
						"Please enter the day[1-30] you were born.", this).show();
			} else if (d instanceof CharacterYearDialog) {
				new CharacterMonthDialog(p, shoebill, rootEventManager,
						"Please enter the Month[1-12] you were born.", this).show();
			}
		} else {// GENDER SELECT
			new CharacterLastnameDialog(p, shoebill, rootEventManager,
					"Please enter you're desired lastname.", this).show();
		}
	}
}
