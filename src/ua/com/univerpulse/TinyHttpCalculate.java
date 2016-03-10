package ua.com.univerpulse;

/**
 * Created by svivanov on 10.03.16.
 */
public class TinyHttpCalculate {
    private int operand1;
    private int operand2;

    public TinyHttpCalculate(Integer operand1, Integer operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public TinyHttpCalculate() {
    }

    public void operand2(Integer operand2) {
        this.operand2 = operand2;
    }

    public void operand1(Integer operand1) {
        this.operand1 = operand1;
    }

    public int add(){ return operand1 + operand2 ;};
    public int mul(){ return operand1 * operand2 ;};
    public int div(){ return operand1 / operand2 ;};
    public int sub(){ return operand1 - operand2 ;};
}
