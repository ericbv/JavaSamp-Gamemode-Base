package nl.ecb.samp.ericrp.dialog;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.constant.DialogStyle;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;

public class AbstractPasswordDialog extends AbstractDialog {

	private String info;
	public AbstractPasswordDialog(Player player,
			Shoebill shoebill, EventManager rootEventManager, String info) {
		super(DialogStyle.PASSWORD, player, shoebill, rootEventManager);
		this.info =info;
	}

	@Override
	public void show() {
		super.show(info);
	}

}
