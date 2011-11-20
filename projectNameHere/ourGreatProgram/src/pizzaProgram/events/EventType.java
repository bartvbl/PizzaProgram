package pizzaProgram.events;

/**
 * A class containing constants representing event types. ALL objects
 * dispatching events should use constants from this class to represent event
 * types.
 * 
 */
public class EventType {
	/**
	 * A request that the database retrieves a customer by its first and last name
	 */
	public static final String DATABASE_SEARCH_CUSTOMER_INFO_BY_NAME = "databaseSearchCustomerInfoByName";
	/**
	 * The program requests termination
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
	/**
	 * An event dispatched when a general system data refresh should commence
	 */
	public static final String DATA_REFRESH_REQUESTED = "dataRefreshRequested";
	/**
	 * A request sent to the order GUI that it should update its customer list
	 */
	public static final String ORDER_GUI_UPDATE_CUSTOMER_LIST = "orderGuiUpdateCustomerList";
	/**
	 * A request sent to the database to retrieve all customers from the database and send them to the order GUI
	 */
	public static final String DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS = "databaseUpdateOrderGUISendAllCustomers";
	
	public static final String DATABASE_UPDATE_ORDER_GUI_DISH_LIST = "databaseUpdateOrderGUIDishList";
	
	public static final String ORDER_GUI_UPDATE_DISH_LIST = "orderGUIUpdateDishList";
	
	public static final String DATABASE_UPDATE_ORDER_GUI_EXTRAS_LIST = "databaseUpdateOrderGUIExtrasListByDishID";
	
	public static final String ORDER_GUI_UPDATE_EXTRAS_LIST = "orderGUIUpdateExtrasList";
	
	public static final String DATABASE_ADD_NEW_ORDER = "databaseAddNewOrder";
	
	public static final String DATABASE_ADD_NEW_CUSTOMER = "databaseAddNewCustomer";
	
	public static final String DATABASE_UPDATE_CUSTOMER_BY_CUSTOMER_ID = "databaseUpdateCustomer";
	
	public static final String DATABASE_UPDATE_ORDER_GUI_SEARCH_CUSTOMERS_BY_KEYWORDS = "databaseUpdateOrderGUISearchCustomersByKeywords";
	
	public static final String DATABASE_UPDATE_COOK_GUI_SEND_ALL_ORDERS = "databaseUpdateCookGUISendAllOrders";
	
	public static final String COOK_GUI_UPDATE_ORDER_LIST = "cookGUIUpdateOrderList";
	
	public static final String DATABASE_MARK_ORDER_FINISHED_COOKING = "databaseMarkOrderFinishedCooking";
	
	public static final String DATABASE_MARK_ORDER_IN_PROGRESS = "databaseMarkOrderInProgress";
	
	public static final String DATABASE_UPDATE_COOK_GUI_SEARCH_ORDERS_BY_KEYWORDS = "databaseUpdateCookGUISearchOrdersByKeywords";
	
	public static final String DATABASE_UPDATE_DELIVERY_GUI_SEND_ALL_ORDERS = "databaseUpdateDeliveryGUISendAllOrders";
	
	public static final String DELIVERY_GUI_UPDATE_ORDER_LIST = "deliveryGUIUpdateOrderList";
	
	public static final String DATABASE_UPDATE_DELIVERY_GUI_SEARCH_ORDERS = "databaseUpdateDeliveryGUISearchOrders";
	
	public static final String DATABASE_MARK_ORDER_DELIVERED = "databaseMarkOrderDelivered";
	
	public static final String DATABASE_MARK_ORDER_BEING_DELIVERED = "databaseMarkOrderAsBeingDelivered";
	
	public static final String DATABASE_ADD_NEW_EXTRA = "databaseAddNewExtra";
	
	public static final String DATABASE_UPDATE_EXTRA_BY_EXTRA_ID = "databaseUpdateExtraByExtraID";
	
	public static final String DATABASE_ADD_NEW_DISH = "databaseAddNewDish";
	
	public static final String DATABASE_UPDATE_DISH_BY_DISH_ID = "databaseUpdateDishByDishID";
	
	public static final String DATABASE_UPDATE_CONFIG_VALUE = "databaseUpdateConfigValue";
	
	public static final String ADMIN_GUI_UPDATE_DISH_LIST = "adminGUIUpdateDishList";
	
	public static final String ADMIN_GUI_UPDATE_EXTRA_LIST = "adminGUIUpdateExtraList";
	
	public static final String ADMIN_GUI_REQUESTED = "adminGUIRequested";
	
	public static final String DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_DISHES = "dupdatesendalldishes";
	
	public static final String DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_EXTRAS = "dupatesendallextras";
	
	public static final String DATABASE_UPDATE_ORDER_GUI_SEARCH_DISHES = "databaseUpdateOrderGUISearchDishes";
	
	public static final String DATABASE_UPDATE_ORDER_GUI_SEARCH_EXTRAS = "databaseUpdateOrderGUISearchExtras";
	
	public static final String DATABASE_UPDATE_ADMIN_GUI_SEARCH_DISHES = "databaseUpdateAdminGUISearchDishes";
	
	public static final String DATABASE_UPDATE_ADMIN_GUI_SEARCH_EXTRAS = "databaseUpdateAdminGUISearchExtras";
	
	public static final String DATABASE_DELETE_CUSTOMER = "databaseDeleteCustomer";
}
