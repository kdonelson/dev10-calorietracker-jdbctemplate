package learn.calorietracker.data;

public class DataAccessException extends Exception {
    /**
     * We want to throw our own exception.
     * @param message
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * We want to wrap an exception in our own exception.
     * @param message
     * @param cause
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
