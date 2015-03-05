package tel.panfilov.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Andrey B. Panfilov <andrew@panfilov.tel>
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
        super();
    }

    public static Method getMethod(Class<?> clazz, String name,
            Class<?>... parameterTypes) {
        Class<?> cls = clazz;
        while (true) {
            try {
                Method method = cls.getDeclaredMethod(name, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                if (cls == Object.class) {
                    return null;
                }
                cls = cls.getSuperclass();
            }
        }
    }

    public static Field getField(Class<?> clazz, String name) {
        Class<?> cls = clazz;
        while (true) {
            try {
                Field field = cls.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                if (cls == Object.class) {
                    return null;
                }
                cls = cls.getSuperclass();
            }
        }
    }

    public static void setValue(Object object, String fieldName, Object value)
        throws IllegalAccessException {
        Field field = getField(object.getClass(), fieldName);
        if (field == null) {
            return;
        }
        field.set(object, value);
    }

    public static Object getValue(Object object, String fieldName)
        throws IllegalAccessException {
        Field field = getField(object.getClass(), fieldName);
        if (field == null) {
            return null;
        }
        return field.get(object);
    }

}
