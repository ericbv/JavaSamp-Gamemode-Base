package nl.ecb.samp.ericrp.dialog;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.constant.DialogStyle;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;


public abstract class AbstractInputDialog extends AbstractDialog {
private String info;
	public AbstractInputDialog(Player player,
			Shoebill shoebill, EventManager rootEventManager, String info) {
		super(DialogStyle.INPUT, player, shoebill, rootEventManager);
		this.info =info;
	}

	@Override
	public void show() {
		super.show(info);
	}
}
