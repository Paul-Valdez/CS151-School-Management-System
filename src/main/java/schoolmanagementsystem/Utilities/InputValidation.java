package schoolmanagementsystem.Utilities;

/**
 * ValidationUtil is a utility class providing methods for validating various types of input data.
 * It includes methods to validate names, email addresses, phone numbers, addresses, and dates.
 * These methods are used to ensure that inputs conform to expected formats and rules before they
 * are processed or stored in the database.
 */
public class InputValidation {

    /**
     * Validates if the provided name is valid.
     * A valid name contains only letters, spaces, apostrophes, hyphens, and periods.
     *
     * @param name The name to validate.
     * @return true if the name is valid, false otherwise.
     */
    public static boolean isValidName(String name) {
        name = name.trim();
        return name != null && name.matches("^[\\p{L} .',-]+$");
    }

    /**
     * Validates if the provided email address is valid.
     * The method uses a basic pattern to check the format of the email address.
     *
     * @param email The email address to validate.
     * @return true if the email address is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    /**
     * Validates if the provided phone number is valid.
     * The method checks against a basic pattern that allows digits, plus signs, parentheses, and hyphens.
     *
     * @param phoneNumber The phone number to validate.
     * @return true if the phone number is valid, false otherwise.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^[0-9+()-]+$");
    }

    /**
     * Validates if the provided address is valid.
     * Currently, this method only checks the length of the address.
     * Additional validation rules can be added as per requirements.
     *
     * @param address The address to validate.
     * @return true if the address is valid, false otherwise.
     */
    public static boolean isValidAddress(String address) {
        return address != null && address.length() <= 255;
    }

    /**
     * Validates if the provided address is valid.
     * Currently, this method only checks the length of the address.
     * Additional validation rules can be added as per requirements.
     *
     * @param string The string to validate as a positive integer.
     * @return true if the address is valid, false otherwise.
     */
    public static boolean isPositiveIntegerString(String string) {
        try {
            int value = Integer.parseInt(string);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}