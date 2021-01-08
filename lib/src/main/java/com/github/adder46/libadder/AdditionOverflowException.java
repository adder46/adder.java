package com.github.adder46.libadder;

public class AdditionOverflowException extends Exception {
    public AdditionOverflowException(String errorMessage) { super(errorMessage); }
}