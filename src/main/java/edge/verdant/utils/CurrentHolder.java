package edge.verdant.utils;

public class CurrentHolder {
    public static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    public static void setCurrent(Object value) {
        THREAD_LOCAL.set(value);
    }

    public static Object getCurrent() {
        return THREAD_LOCAL.get();
    }

    public static void removeCurrent() {
        THREAD_LOCAL.remove();
    }
}
