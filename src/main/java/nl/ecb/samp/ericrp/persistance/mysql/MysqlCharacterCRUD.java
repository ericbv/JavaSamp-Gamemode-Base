package nl.ecb.samp.ericrp.persistance.mysql;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import nl.ecb.samp.ericrp.exceptions.CharacterAlreadyCreatedException;
import nl.ecb.samp.ericrp.main.Main;
import nl.ecb.samp.ericrp.model.Character;
import nl.ecb.samp.ericrp.model.Character.Gender;

import nl.ecb.samp.ericrp.model.Account;

public class MysqlCharacterCRUD {
	DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

	public int createCharacter(Connection connection, Account a, Character c)
			throws CharacterAlreadyCreatedException {
		int iD = -1;
		try {
			Statement statement = connection.createStatement();
			statement
					.execute(" INSERT INTO characters (CharacterName,gender, ModelID, Birthdate) VALUES ('"
							+ c.getCharacterName()
							+ "', '"
							+ c.getHisOrHerGender()
							+ "', '"
							+ c.getModelID()
							+ "', '" + df.format(c.getBirthdate()) + " ')");
			System.out.println(connection.getAutoCommit());
			ResultSet resultSet = statement
					.executeQuery("SELECT charId FROM characters WHERE CharacterName='"
							+ c.getCharacterName() + "'");
			if (resultSet.next()) {
				iD = resultSet.getInt(1);
				if (iD != -1) {
					statement
							.execute(" INSERT INTO accchar (acountID,charId) VALUES ('"
									+ a.getID() + "', '" + iD + " ')");
				}
			}

		} catch (SQLException ex) {
			if (ex.getErrorCode() == 1062) {
				throw new CharacterAlreadyCreatedException();
			} else {
				proccesMysqlError(ex);
			}
		}
		return iD;
	}

	public void saveCharacter(Connection connection, Character c) {
		try {
			Statement statement = connection.createStatement();
			statement.execute(" UPDATE characters SET" + " CharacterName='"
					+ c.getCharacterName() + "', gender='"
					+ c.getHisOrHerGender() + "', ModelID='" + c.getModelID()
					+ "', Birthdate='" + df.format(c.getBirthdate())
					+ "' WHERE charId=" + c.getCharId() + "");
		} catch (SQLException ex) {
			proccesMysqlError(ex);
		}
	}

	public List<Character> getCharacters(Connection connection, int userID) {
		List<Character> charList = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM characters WHERE charid=(SELECT ac.charId FROM accchar ac WHERE ac.acountID = '"
							+ userID + "')");
			charList = new ArrayList<Character>();
			while (resultSet.next()) {

				int iD = resultSet.getInt(1);
				String CharacterName = resultSet.getString(2);
				Gender gender = null;
				if (resultSet.getString(3).equalsIgnoreCase("man")) {
					gender = Gender.MALE;
				} else {
					gender = Gender.FEMALE;
				}
				int modelID = resultSet.getInt(4);
				Date birthdate;
				birthdate = df.parse(resultSet.getString("Birthdate"));
				charList.add(Character.load(iD, CharacterName, modelID,
						birthdate, gender));
			}

		} catch (SQLException ex) {
			proccesMysqlError(ex);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return charList;

	}

	public void deleteCharacter(Connection connection, Character c) {
		try {
			Statement statement = connection.createStatement();
			statement.execute(" DELETE FROM characters WHERE charId='"
					+ c.getCharId() + "'");
		} catch (SQLException e) {
			proccesMysqlError(e);
		}
	}

	public boolean isCharacterNameAvalible(Connection connection, String name) {
		try {
			Statement statement;
			statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM characters WHERE CharacterName='"
							+ name + "'");
			if (resultSet.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			proccesMysqlError(e);
		}
		return false;
	}

	private void proccesMysqlError(SQLException ex) {
		Main.logger().error("SQLException: " + ex.getMessage());
		Main.logger().error("SQLState: " + ex.getSQLState());
		Main.logger().error("VendorError: " + ex.getErrorCode());
	}
}
