package org.lcr.server.tools;

import java.util.List;

public class ArrayListUtils {

    public static boolean isEmpty(List<?> array) {
        return array == null || array.size() == 0;
    }

    public static boolean isNotEmpty(List<?> array) {
        return array != null && array.size() > 0;
    }
}
