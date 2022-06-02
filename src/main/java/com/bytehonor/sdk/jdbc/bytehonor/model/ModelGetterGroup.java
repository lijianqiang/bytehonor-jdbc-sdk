package com.bytehonor.sdk.jdbc.bytehonor.model;

import java.util.ArrayList;
import java.util.List;

import com.bytehonor.sdk.jdbc.bytehonor.function.ClassGetter;

public class ModelGetterGroup<T> {

    private List<ModelGetter<T>> list;

    private ModelGetterGroup() {
        this.list = new ArrayList<ModelGetter<T>>();
    }

    public static <T> ModelGetterGroup<T> create(Class<T> clazz) {
        return new ModelGetterGroup<T>();
    }

    public ModelGetterGroup<T> add(String key, ClassGetter<T, ?> getter) {
        list.add(ModelGetter.create(key, getter));
        return this;
    }

    public List<ModelGetter<T>> getList() {
        return list;
    }

    public void setList(List<ModelGetter<T>> list) {
        this.list = list;
    }

    public List<ModelColumnValue> out(T t) {
        List<ModelColumnValue> result = new ArrayList<ModelColumnValue>();
        for (ModelGetter<T> item : list) {
            ModelColumnValue value = item.value(t);
            if (value == null) {
                continue;
            }
            result.add(value);
        }
        return result;
    }
}
