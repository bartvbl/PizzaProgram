package pizzaProgram.events;

/**
 * A class containing constants representing event types.
 * ALL objects dispatching events should use constants from this class to represent event types.
 * @author Bart
 *
 */
public class EventType {
	/**
	 * An event dispatched when the user tries to exit the program.
	 */
	public static final String PROGRAM_EXIT_REQUESTED = "programExitRequested";
	/**
	 * The user has tried to open the settings configuration window
	 */
	public static final String OPEN_SETTINGS_WINDOW_REQUESTED = "openSettingsWindowRequested";
	/**
	 * The user would like to switch to the order GUI
	 */
	public static final String ORDER_GUI_REQUESTED = "orderGUIRequested";
	/**
	 * The user would like to switch to the cook GUI
	 */
	public static final String COOK_GUI_REQUESTED = "cookGUIRequested";
	/**
	 * The user would like to switch to the delivery GUI
	 */
	public static final String DELIVERY_GUI_REQUESTED = "deliveryGUIRequested";
}
