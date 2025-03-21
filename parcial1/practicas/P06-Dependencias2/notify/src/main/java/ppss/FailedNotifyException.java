package ppss;

public class FailedNotifyException extends RuntimeException {
    public FailedNotifyException(String message) {
        super(message);
    }

    public FailedNotifyException() {}
}
