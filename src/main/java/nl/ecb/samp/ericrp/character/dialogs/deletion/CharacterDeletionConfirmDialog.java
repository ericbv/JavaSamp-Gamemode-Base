package nl.ecb.samp.ericrp.character.dialogs.deletion;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractMsgboxDialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import nl.ecb.samp.ericrp.character.dialogs.CharacterDeletionManager;
import nl.ecb.samp.ericrp.character.model.Character;

public class CharacterDeletionConfirmDialog extends AbstractMsgboxDialog {
	private Player p;
	private Character c;
	private CharacterDeletionManager characterDeletionManager;

	public CharacterDeletionConfirmDialog(Player p, Character c,
			Shoebill shoebill, EventManager rootEventManager,
			CharacterDeletionManager characterDeletionManager) {
		super(p, shoebill, rootEventManager);
		this.c = c;
		this.p = p;
		this.characterDeletionManager = characterDeletionManager;

	}

	@Override
	public void show() {
		super.show("Are you sure that you wan't to delete the character:\nname: "
				+ c.getCharacterName() + "\nGender" + c.getHisOrHerGender());
		buttonOk = ("Yes");
		buttonCancel = ("No");
	}
	
	@Override
	protected void onClickOk() {
		characterDeletionManager.recieveConfirm(true);
		super.onClickOk();
	}

	@Override
	protected void onClickCancel() {
		characterDeletionManager.recieveConfirm(false);
		super.onClickCancel();
	}
}
