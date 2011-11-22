package pizzaProgram.core;

/**
 * Class containing constants describing the names of the tables and columns in the database.
 */
public class DatabaseConstants {
	/*
	 * Constants pertaining to the Customer Table of the database
	 */
	public static final String CUSTOMER_TABLE_NAME = "Customer";
	public static final String CUSTOMER_ID = CUSTOMER_TABLE_NAME + ".CustomerID";
	public static final String CUSTOMER_FIRST_NAME = CUSTOMER_TABLE_NAME + ".FirstName";
	public static final String CUSTOMER_LAST_NAME = CUSTOMER_TABLE_NAME + ".LastName";
	public static final String CUSTOMER_ADDRESS = CUSTOMER_TABLE_NAME + ".Address";
	public static final String CUSTOMER_POSTAL_CODE = CUSTOMER_TABLE_NAME + ".PostalCode";
	public static final String CUSTOMER_CITY = CUSTOMER_TABLE_NAME + ".City";
	public static final String CUSTOMER_PHONE_NUMBER = CUSTOMER_TABLE_NAME + ".TelephoneNumber";
	public static final String CUSTOMER_IDENTIFIER = CUSTOMER_TABLE_NAME + ".Identifier";
	public static final String CUSTOMER_ALL_COLS = CUSTOMER_TABLE_NAME + ".*";
	/*
	 * Constants pertaining to the Dishes Table of the database
	 */
	public static final String DISH_TABLE_NAME = "Dishes"; 
	public static final String DISH_ID = DISH_TABLE_NAME + ".DishID";
	public static final String DISH_PRICE = DISH_TABLE_NAME + ".Price";
	public static final String DISH_NAME = DISH_TABLE_NAME + ".Name";
	public static final String DISH_CONTAINS_GLUTEN = DISH_TABLE_NAME + ".ContainsGluten";
	public static final String DISH_CONTAINS_NUTS = DISH_TABLE_NAME + ".ContainsNuts";
	public static final String DISH_CONTAINS_DAIRY = DISH_TABLE_NAME + ".ContainsDairy";
	public static final String DISH_IS_VEGETARIAN = DISH_TABLE_NAME + ".IsVegetarian";
	public static final String DISH_IS_SPICY = DISH_TABLE_NAME + ".IsSpicy";
	public static final String DISH_DESCRIPTION = DISH_TABLE_NAME + ".Description";
	public static final String DISH_IS_ACTIVE = DISH_TABLE_NAME + ".isActive";
	public static final String DISHES_ALL_COLS = DISH_TABLE_NAME + ".*";
	/*
	 * Constants pertaining to the Extras Table of the database
	 */
	public static final String EXTRAS_TABLE_NAME = "Extras";
	public static final String EXTRAS_ID = EXTRAS_TABLE_NAME + ".ExtrasID";
	public static final String EXTRAS_NAME = EXTRAS_TABLE_NAME + ".Name";
	public static final String EXTRAS_PRICE = EXTRAS_TABLE_NAME + ".Price";
	public static final String EXTRAS_IS_ACTIVE = EXTRAS_TABLE_NAME + ".isActive";
	public static final String EXTRAS_ALL_COLS = EXTRAS_TABLE_NAME + ".*";
	/*
	 * Constants pertaining to the Orders Table of the database
	 */
	public static final String ORDERS_TABLE_NAME = "Orders";
	public static final String ORDERS_ID = ORDERS_TABLE_NAME + ".OrdersID";
	public static final String ORDERS_TIME_REGISTERED = ORDERS_TABLE_NAME + ".TimeRegistered";
	public static final String ORDERS_STATUS = ORDERS_TABLE_NAME + ".OrdersStatus";
	public static final String ORDERS_DELIVERY_METHOD = ORDERS_TABLE_NAME + ".DeliveryMethod";
	public static final String ORDERS_TO_ORDERCOMMENT_ID = ORDERS_TABLE_NAME + ".CommentID";
	public static final String ORDERS_TO_CUSTOMER_ID = ORDERS_TABLE_NAME + ".CustomerID";
	public static final String ORDERS_ALL_COLS = ORDERS_TABLE_NAME + ".*";
	/*
	 * Constants pertaining to the OrdersComments Table of the database
	 */
	public static final String ORDERS_COMMENT_TABLE_NAME = "OrderComments";
	public static final String ORDERS_COMMENT_TO_ORDER_ID = ORDERS_COMMENT_TABLE_NAME + ".CommentID"; 
	public static final String ORDERS_COMMENT = ORDERS_COMMENT_TABLE_NAME + ".Comment";
	public static final String ORDERS_COMMENT_ALL_COLS = ORDERS_COMMENT_TABLE_NAME + ".*";
	/*
	 * Constants pertaining to the Config Table of the database
	 */
	public static final String CONFIG_TABLE_NAME = "Config";
	public static final String CONFIG_KEY = CONFIG_TABLE_NAME + ".ConfigKey";
	public static final String CONFIG_VALUE = CONFIG_TABLE_NAME + ".ConfigValue";
	public static final String CONFIG_ALL_COLS = CONFIG_TABLE_NAME + ".*"; 
	/*
	 * Constants pertaining to the OrdersContents Table of the database
	 */
	public static final String ORDERS_CONTENTS_TABLE_NAME = "OrdersContents"; 
	public static final String ORDERS_CONTENTS_ID = ORDERS_CONTENTS_TABLE_NAME + ".OrdersContentsID";
	public static final String ORDERS_CONTENTS_TO_ORDER_ID = ORDERS_CONTENTS_TABLE_NAME + ".OrdersID";
	public static final String ORDERS_CONTENTS_TO_DISH_ID = ORDERS_CONTENTS_TABLE_NAME + ".DishID";
	public static final String ORDERS_CONTENTS_ALL_COLS = ORDERS_CONTENTS_TABLE_NAME + ".*"; 
	/*
	 * Constants pertaining to the DishExtrasChosen Table of the database 
	 */
	public static final String DISH_EXTRAS_CHOSEN_TABLE_NAME = "DishExtrasChosen";
	public static final String DISH_EXTRAS_TO_ORDERCONTENTS = DISH_EXTRAS_CHOSEN_TABLE_NAME + ".OrdersContentsID";
	public static final String DISH_EXTRAS_CHOSEN_TO_EXTRAS_ID = DISH_EXTRAS_CHOSEN_TABLE_NAME + ".DishExtraID";
	public static final String DISH_EXTRAS_CHOSEN_ALL_COLS = DISH_EXTRAS_CHOSEN_TABLE_NAME + ".*";
	/*
	 * Constants pertaining to the values in the key fields of the Config table
	 */
	public static final String SETTING_KEY_FREE_DELIVERY_LIMIT = "freeDeliveryLimit";
	public static final String SETTING_KEY_RESTAURANT_NAME = "restaurantName";
	public static final String SETTING_KEY_RESTAURANT_ADDRESS = "restaurantAddress";
	public static final String SETTING_KEY_RESTAURANT_CITY = "restaurantCity";
	public static final String SETTING_KEY_DELIVERY_PRICE = "deliveryPrice";
	public static final String SETTING_KEY_DELIVERY_AT_HOME_TAX = "deliveryAtHomeTax";
	public static final String SETTING_KEY_PICKUP_AT_RESTAURANT_TAX = "pickupAtRestaurantTax";
}
