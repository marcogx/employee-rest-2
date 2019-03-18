package com.example.employeerestdemo.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guang on 8:15 PM 10/27/18.
 */
@Component
public class Cache {
    private Map<Object, Object> map = new HashMap<>();

    public void put(Object key, Object val) {
        map.put(key, val);
    }

    public Object get(Long key) {
        return map.get(key);
    }
}
