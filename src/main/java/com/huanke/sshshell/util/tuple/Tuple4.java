package com.huanke.sshshell.util.tuple;

public class Tuple4<A, B, C, D> {

    public final A a;
    public final B b;
    public final C c;
    public final D d;

    public Tuple4(A a, B b, C c, D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }

    public D getD() {
        return d;
    }

    @Override
    public String toString() {
        return "Tuple4 [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
    }

}
