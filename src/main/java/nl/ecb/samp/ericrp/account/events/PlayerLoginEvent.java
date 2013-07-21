package nl.ecb.samp.ericrp.account.events;

import net.gtaun.shoebill.event.player.PlayerEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.Interruptable;


public class PlayerLoginEvent extends PlayerEvent{

		public PlayerLoginEvent(Player player) {
			super(player);
		}


}
