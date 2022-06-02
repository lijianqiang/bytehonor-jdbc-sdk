package com.bytehonor.sdk.jdbc.bytehonor.model;

import java.util.Objects;

import com.bytehonor.sdk.jdbc.bytehonor.function.ClassGetter;

public class ModelGetter<T> {

    private String key;

    private ClassGetter<T, ?> getter;

    public static <T> ModelGetter<T> create(String key, ClassGetter<T, ?> getter) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(getter, "getter");

        ModelGetter<T> model = new ModelGetter<T>();
        model.setKey(key);
        model.setGetter(getter);

        return model;
    }

    public Object value(T t) {
        Objects.requireNonNull(t, "t");

        return this.getter.apply(t);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ClassGetter<T, ?> getGetter() {
        return getter;
    }

    public void setGetter(ClassGetter<T, ?> getter) {
        this.getter = getter;
    }

}
