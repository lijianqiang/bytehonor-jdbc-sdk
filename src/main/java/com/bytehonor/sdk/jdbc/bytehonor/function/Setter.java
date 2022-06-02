package com.bytehonor.sdk.jdbc.bytehonor.function;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface Setter<T, R> extends Function<T, R>, Serializable {
}