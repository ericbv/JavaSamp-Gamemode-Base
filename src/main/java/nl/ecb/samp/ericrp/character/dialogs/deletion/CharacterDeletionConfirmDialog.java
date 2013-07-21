package nl.ecb.samp.ericrp.character.dialogs.deletion;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.constant.DialogStyle;
import net.gtaun.shoebill.event.DialogEventHandler;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.ManagedEventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import nl.ecb.samp.ericrp.character.dialogs.CharacterDeletionManager;
import nl.ecb.samp.ericrp.character.model.Character;
import nl.ecb.samp.ericrp.dialog.AbstractDialog;

public class CharacterDeletionConfirmDialog extends AbstractDialog {
	private Player p;
	private Character c;
	private CharacterDeletionManager characterDeletionManager;
	private ManagedEventManager eventManager;
	
	public CharacterDeletionConfirmDialog(Player p, Character c, Shoebill shoebill,
			EventManager rootEventManager,
			CharacterDeletionManager characterDeletionManager) {
		super(DialogStyle.MSGBOX, p, shoebill, rootEventManager);
		this.c=c;
		this.p=p;
		this.characterDeletionManager =characterDeletionManager;
		new ManagedEventManager(rootEventManager).registerHandler(DialogResponseEvent.class,
				super.getDialog(), dialogEventHandler, HandlerPriority.NORMAL);
		
	}
	@Override
	public void show() {
		super.show("Are you sure that you wan't to delete the character:\nname: "+c.getCharacterName()+"\nGender"+c.getHisOrHerGender());
		setButtonOk("Yes");
		setButtonCancel("No");
	}
	private DialogEventHandler dialogEventHandler = new DialogEventHandler() {
		public void onDialogResponse(DialogResponseEvent event) {
			if (event.getDialogResponse() == 1) {
				characterDeletionManager.recieveConfirm(true);
			} else {
				characterDeletionManager.recieveConfirm(false);
			}

		}
	};
}
