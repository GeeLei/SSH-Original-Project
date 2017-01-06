package com.huanke.sshshell.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.zlg.util.ListMapLoopCallback;
import cn.zlg.util.New;

public class ListMap2<K, V> {

    private Map<K, List<V>> map;

    public ListMap2() {
        this(new HashMap<K, List<V>>());
    }

    public ListMap2(Map<K, List<V>> map) {
        this.map = map;
        if (map == null) {
            throw new IllegalArgumentException();
        }
    }

    public static ListMap2 getInstance() {
        return new ListMap2();
    }

    public static ListMap2 getInstance(Map map) {
        return new ListMap2(map);
    }

    public ListMap2<K, V> add(K k, V v) {
        if (map.get(k) == null) {
            List<V> list = New.arraylist();
            list.add(v);
            map.put(k, list);
        } else {
            ((List) map.get(k)).add(v);
        }
        return this;
    }

    public ListMap2<K, V> addAll(K k, List<V> v) {
        if (map.get(k) == null) {
            map.put(k, v);
        } else {
            ((List) map.get(k)).addAll(v);
        }
        return this;
    }

    public List<V> getList(K k) {
        return map.get(k);
    }

    public void each(ListMapLoopCallback<K, V> cb) {
        Set<Entry<K, List<V>>> set = map.entrySet();
        java.util.Map.Entry<K, List<V>> e;
        for (Iterator<Entry<K, List<V>>> it = set.iterator(); it.hasNext(); cb
                .call(e.getKey(), e.getValue())) {
            e = it.next();
        }

    }

    public boolean containsInList(K k, V v) {
        List<V> list = map.get(k);
        if (list == null) {
            return false;
        }
        Iterator it = list.iterator();
        Object lv;
        do {
            if (!it.hasNext()) {
                return false;
            }
            lv = it.next();
        } while (!lv.equals(v));
        return true;
    }
}
