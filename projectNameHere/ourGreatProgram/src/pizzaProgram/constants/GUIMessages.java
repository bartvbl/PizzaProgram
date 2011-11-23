package pizzaProgram.constants;
/**
 * A class containing all the messages for error and information messages that are displayed in the UI
 * @author Håvard
 *
 */
public class GUIMessages {
	public static final String TOO_MANY_COMMAS_IN_VALUE = "Tall kan ikke inneholde mer enn ett komma!";
	public static final String ILLEGAL_NUMBER_INPUT = "Du har skrevet inn noe som ikke er et tall!";
	public static final String TOO_MANY_DIGITS_AFTER_COMMA = "Tall må ha nøyaktig to siffer etter komma!";
	public static final String UNABLE_TO_GET_DISHES_FROM_DATABASE = "Kunne ikke hente retter fra databasen!";
	public static final String UNABLE_TO_GET_EXTRAS_FROM_DATABASE = "Kunne ikke hente tilbehør fra databasen!";
	public static final String UNABLE_TO_GET_ORDERS_FROM_DATABASE = "Kunne ikke hente ordre fra databasen!";
	public static final String UNABLE_TO_GET_CUSTOMERS_FROM_DATABASE = "Kunne ikke hente kunder fra databasen!";
	public static final String UNABLE_TO_DELETE_CUSTOMER_FROM_DATABASE = "Kunne ikke slette kunde fra databasen!";
	public static final String UNABLE_TO_EDIT_EXTRA = "Kunne ikke endre tilbehør!";
	public static final String UNABLE_TO_EDIT_DISH = "Kunne ikke endre rett!";
	public static final String UNABLE_TO_EDIT_SETTING = "Kunne ikke endre innstilling!";
	public static final String UNABLE_TO_ADD_EXTRA = "Kunne ikke legge til tilbehør!";
	public static final String UNABLE_TO_ADD_DISH = "Kunne ikke legge til rett!";
	public static final String UNABLE_TO_CHANGE_ORDER_STATUS = "Kunne ikke endre status på ordre!";
	public static final String UNABLE_TO_ADD_CUSTOMER = "Kunne ikke legge til kunde!";
	public static final String UNABLE_TO_EDIT_CUSTOMER = "Kunne ikke endre kunde!";
	public static final String UNABLE_TO_ADD_ORDER = "Kunne ikke legge til ordre!";
	public static final String ILLEGAL_POSTAL_CODE = "Postnummer må være et tall med fire siffer!";
	public static final String ILLEGAL_PHONE_NUMBER = "Telefonnummer må være et tall!";
	public static final String ILLEGAL_FIRST_NAME = "Fornavn kan ikke være tomt!";
	public static final String ILLEGAL_LAST_NAME = "Etternavn kan ikke være tomt!";
	public static final String ILLEGAL_ADDRESS = "Adressen kan ikke være tom!";
	public static final String ILLEGAL_CITY = "By kan ikke være tom!";
	public static final String ILLEGAL_RESTAURANT_NAME_LENGTH = "Navnet på restauranten kan ikke "
			+ "være mindre enn 3 bokstaver eller mer enn 10 bokstaver.";
	public static final String ILLEGAL_RESTAURANT_ADDRESS_LENGTH = "Addressen til "
			+ "restauranten kan ikke være mindre enn 3 bokstaver.";
	public static final String ILLEGAL_RESTAURANT_CITY_LENGTH = "Addressen til "
			+ "restauranten kan ikke være mindre enn 1 bokstav.";
	public static final String NOTHING_TO_CHANGE = "Ingenting å endre.";
	public static final String SETTINGS_CHANGED_SUCCESSFULLY = "Innstillinger endret.";
	public static final String NAME_FIELD_CANNOT_BE_EMPTY = "Feltet med navn kan ikke være tomt!";
	public static final String ILLEGAL_EXTRA_OPERAND = "Første tegn i prisen skal være +(pluss)"
			+ " -(minus) eller *(gange)\nDette angir om prisen på tilbehøret er pristillegg, "
			+ "prisfradrag, eller en multiplikasjon (dvs endring av pris basert på prosent).";
	public static final String DISH_DESCRIPTION_CANNOT_BE_EMPTY = "Feltet med innhold i retten kan ikke være tomt!";
	public static final String NO_CUSTOMER_SELECTED = "Ingen kunde valgt!";
	public static final String ORDER_MUST_CONTAIN_ONE_OR_MORE_DISHES = "Ordren må innholde en eller flere retter!";
	public static final String CANNOT_ADD_EXTRA_WHEN_NO_DISH_SELECTED = "Du må velge en rett i tillegg til tilbehør!";
}
