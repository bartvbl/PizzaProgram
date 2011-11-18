package pizzaProgram.core;

import pizzaProgram.dataObjects.Order;

public class Constants{


	public static final double DELIVER_MOMS = 0.25;
	public static final double PICKUP_MOMS = 0.14;

	public static final int FREE_DELIVERY_TRESHOLD = 300;
	public static final int DELIVERY_COST = 50;

	public static final String RESTAURANT_NAME = "Papa Di Pizza";

	public static final int RECIPT_WIDTH = 160;
	public static final int RECIPT_ROW_HEIGHT = 20;

	//gui representation of true/false
	public static final String GUI_TRUE = "Ja";
	public static final String GUI_FALSE = "Nei";

	//norsk delivery metjod
	public static final String GUI_DELIVER = "Lever hjem";
	public static final String GUI_PICKUP = "Hent selv";
	
	public static final String SETTING_KEY_FREE_DELIVERY_LIMIT = "freeDeliveryLimit";
	public static final String SETTING_KEY_RESTAURANT_NAME = "restaurantName";
	public static final String SETTING_KEY_DELIVERY_PRICE = "deliveryPrice";
	public static final String SETTING_KEY_DELIVERY_AT_HOME_TAX = "deliveryAtHomeTax";
	public static final String SETTING_KEY_PICKUP_AT_RESTAURANT_TAX = "pickupAtRestaurantTax";
	
	public static final int CUSTOMERS_TABLE_NUM_COLUMNS= 9;
	public static final int CUSTOMER_NOTES_TABLE_NUM_COLUMNS = 2;
	public static final int DISHES_TABLE_NUM_COLUMNS = 10;
	public static final int EXTRAS_TABLE_NUM_COLUMNS = 4;
	public static final int ORDER_COMMENTS_TABLE_NUM_COLUMNS = 2;
	public static final int ORDERS_TABLE_NUM_COLUMNS = 6;
	public static final int ORDER_CONTENTS_TABLE_NUM_COLUMNS = 3;

	/**
	 * The guiword for registered status
	 */
	public static final String GUI_REGISTERED = "Registrert";
	
	/**
	 * The guiword for cooking status
	 */
	public static final String GUI_COOKING = "Tilberedes";
	
	/**
	 * The guiword for cooking status
	 */
	public static final String GUI_FINCOOKING = "Klar for levering";
	
	/**
	 * The guiword for being cooked status
	 */
	public static final String GUI_DELIVERING = "Underveis";
	public static final String SEARCH_FIELD_DEFAULT = "";
	
	public static String translateDeliveryMethod(String s){
		if(s.equals(Order.DELIVER_AT_HOME)){
			return GUI_DELIVER;
		}
		else{
			return GUI_PICKUP;
		}
	}
	
	public static String translateOrderStatus(String s){
		if(s.equals(Order.REGISTERED)){
			return GUI_REGISTERED;
		}
		else if(s.equals(Order.BEING_COOKED)){
			return GUI_COOKING;
		}
		else if(s.equals(Order.HAS_BEEN_COOKED)){
			return GUI_FINCOOKING;
		}
		else{
			return GUI_DELIVERING;
		}
		
	}
}