package nl.ecb.samp.ericrp.dialog;

import net.gtaun.shoebill.SampObjectFactory;
import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.constant.DialogStyle;
import net.gtaun.shoebill.event.DialogEventHandler;
import net.gtaun.shoebill.event.dialog.DialogCancelEvent;
import net.gtaun.shoebill.event.dialog.DialogResponseEvent;
import net.gtaun.shoebill.object.Dialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import net.gtaun.util.event.ManagedEventManager;

public abstract class AbstractDialog
{
	protected final Shoebill shoebill;
	protected final EventManager rootEventManager;
	protected final Player player;

	private final ManagedEventManager eventManager;

	private final Dialog dialog;
	private final DialogStyle style;

	private String caption = "None";
	private String buttonOk = "OK";
	private String buttonCancel = "Cancel";


	protected AbstractDialog(DialogStyle style, Player player, Shoebill shoebill, EventManager rootEventManager)
	{
		this.style = style;
		this.shoebill = shoebill;
		this.player = player;
		this.rootEventManager = rootEventManager;
		this.eventManager = new ManagedEventManager(rootEventManager);

		SampObjectFactory factory = shoebill.getSampObjectFactory();
		dialog = factory.createDialog();

		//eventManager.registerHandler(DialogResponseEvent.class, dialog, dialogEventHandler, HandlerPriority.NORMAL);
		//eventManager.registerHandler(DialogCancelEvent.class, dialog, dialogEventHandler, HandlerPriority.NORMAL);
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		destroy();
	}

	protected void destroy()
	{
		eventManager.cancelAll();
	}
	private DialogEventHandler dialogEventHandler = new DialogEventHandler()
	{
		public void onDialogResponse(DialogResponseEvent event)
		{
			AbstractDialog.this.onDialogResponse(event);
		}

		public void onDialogCancel(DialogCancelEvent event)
		{
			AbstractDialog.this.onDialogCancel(event);
		}
	};
	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	public void setButtonOk(String buttonOk)
	{
		this.buttonOk = buttonOk;
	}

	public void setButtonCancel(String buttonCancel)
	{
		this.buttonCancel = buttonCancel;
	}

	protected void show(String text)
	{
		player.showDialog(dialog, style, caption, text, buttonOk, buttonCancel);
	}

	public abstract void show();

	protected void onDialogResponse(DialogResponseEvent event)
	{
		destroy();
	}

	protected void onDialogCancel(DialogCancelEvent event)
	{
		destroy();
	}

	public Dialog getDialog() {
		return dialog;
	}
}