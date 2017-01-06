package com.huanke.sshshell.util.tuple;

public class Tuple2<A, B> {

    public final A a;
    public final B b;

    public Tuple2(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    @Override
    public String toString() {
        return "Tuple2 [a=" + a + ", b=" + b + "]";
    }

}
