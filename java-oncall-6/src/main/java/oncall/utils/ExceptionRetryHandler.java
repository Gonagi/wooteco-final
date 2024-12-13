package oncall.utils;

import java.util.function.Supplier;

public final class ExceptionRetryHandler {
    private ExceptionRetryHandler() {
    }

    public static <T> T retryOnException(final Supplier<T> task) {
        while (true) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
