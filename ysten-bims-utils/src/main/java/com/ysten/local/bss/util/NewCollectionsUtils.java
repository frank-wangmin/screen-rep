package com.ysten.local.bss.util;

import java.util.*;

/**
 * Created by frank on 14-6-20.
 */
public class NewCollectionsUtils {

    public static <K, V> Map<K, V> map() {
        return new HashMap<K, V>();
    }

    public static <K, V> LinkedHashMap<K, V> linkedMap() {
        return new LinkedHashMap<K, V>();
    }

    public static <T> List<T> list() {
        return new ArrayList<T>();
    }

    public static <T> LinkedList<T> lList() {
        return new LinkedList<T>();
    }

    public static <T> Set<T> set() {
        return new HashSet<T>();
    }

    public static <T> Queue<T> queue() {
        return new LinkedList<T>();
    }
}
