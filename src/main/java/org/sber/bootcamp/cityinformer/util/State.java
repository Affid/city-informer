package org.sber.bootcamp.cityinformer.util;

public class State {
    private final State previous;
    private final int value;

    public State(State previous, int value) {
        this.previous = previous;
        this.value = value;
    }

    public State getChild(int childState){
        return new State(this, childState);
    }

    public State getPrevious() {
        return previous;
    }

    public int getValue() {
        return value;
    }
}
