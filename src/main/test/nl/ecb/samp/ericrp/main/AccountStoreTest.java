package nl.ecb.samp.ericrp.main;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import net.gtaun.shoebill.object.Player;
import nl.ecb.samp.ericrp.account.exceptions.NotLoggedInException;
import nl.ecb.samp.ericrp.account.exceptions.playeridAlreadyLoggedInException;
import nl.ecb.samp.ericrp.account.model.Account;

import org.junit.Test;

public class AccountStoreTest {
	Player p;

	@Test
	public void testGetInstance() {
		AccountStore accs = AccountStore.getInstance();
		assertEquals(accs,AccountStore.getInstance());
		assertEquals(true,accs instanceof AccountStore);
	}

	@Test
	public void testIsLoggedIn() throws playeridAlreadyLoggedInException {
		AccountStore accs = AccountStore.getInstance();
		Account a = Account.load("ericbv", "lol123", "e.bonestroo@gmail.com", 3,null);

		p = mock(Player.class);
		assertEquals(false,accs.isLoggedIn(p));
		accs.setAccount(p, a);
		assertEquals(true,accs.isLoggedIn(p));
		
	}
	
	@Test
	public void testSetGetAccount() throws playeridAlreadyLoggedInException, NotLoggedInException {
		AccountStore accs = AccountStore.getInstance();
		Account a = Account.load("ericbv", "lol123", "e.bonestroo@gmail.com", 3,null);
		p = mock(Player.class);
		accs.setAccount(p, a);
		assertEquals(a,accs.getAccount(p));
	}
	@Test(expected=playeridAlreadyLoggedInException.class)
	public void testSetGetAccountAlreadyLoggedIn() throws playeridAlreadyLoggedInException, NotLoggedInException {
		AccountStore accs = AccountStore.getInstance();
		Account a = Account.load("ericbv", "lol123", "e.bonestroo@gmail.com", 3,null);
		Account a2 = Account.load("joh999", "lol123", "j.bonestroo@gmail.com", 3,null);
		p = mock(Player.class);
		accs.setAccount(p, a);
		accs.setAccount(p, a2);
		assertEquals(a,accs.getAccount(p));
	}
	@Test(expected=NotLoggedInException.class)
	public void testSetGetAccountNotLoggedIn() throws playeridAlreadyLoggedInException, NotLoggedInException {
		AccountStore accs = AccountStore.getInstance();
		Account a = Account.load("ericbv", "lol123", "e.bonestroo@gmail.com", 3,null);
		p = mock(Player.class);
		Player p2 = mock(Player.class);
		accs.setAccount(p, a);
		assertEquals(a,accs.getAccount(p2));
	}
	@Test(expected=NotLoggedInException.class)
	public void testRemoveAccount() throws playeridAlreadyLoggedInException, NotLoggedInException{
		AccountStore accs = AccountStore.getInstance();
		Account a = Account.load("ericbv", "lol123", "e.bonestroo@gmail.com", 3,null);
		p = mock(Player.class);
		accs.setAccount(p, a);
		assertEquals(a,accs.getAccount(p));
		accs.removeAccount(p);
		assertEquals(null,accs.getAccount(p));
	}

}
