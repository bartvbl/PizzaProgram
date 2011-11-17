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

	//norsk status
	public static final String GUI_REGISTERED = "Registrert";
	public static final String GUI_COOKING = "Tilberedes";
	public static final String GUI_FINCOOKING = "Klar for levering";
	public static final String GUI_DELIVERING = "Underveis";
	public static final String GUI_DELIVERED = "Levert";
	
	
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
		else if(s.equals(Order.BEING_DELIVERED)){
			return GUI_DELIVERING;
		}
		else{
			return GUI_DELIVERED;
		}
	}
}