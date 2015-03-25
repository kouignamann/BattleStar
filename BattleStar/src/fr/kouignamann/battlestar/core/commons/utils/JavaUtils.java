package fr.kouignamann.battlestar.core.commons.utils;

import java.util.List;

public final class JavaUtils {

    public static int[] toIntArray(List<Integer> items) {
    	int[] result = new int[items.size()];
    	int i=0;
    	for (Integer item : items) {
    		result[i++] = item.intValue();
    	}
    	return result;
    }
}
