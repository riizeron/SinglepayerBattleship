package battleship.utils;

import java.lang.reflect.Array;

public final class ArrayUtils {
    private ArrayUtils(){
        System.out.println("Unable to create the instance");
    }
    public static <T> T[] concatenate(T[] a, T[] b){
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
