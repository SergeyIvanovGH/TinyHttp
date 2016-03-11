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

<<<<<<< HEAD
    public int add(){ return operand1 + operand2 ;};
    public int mul(){ return operand1 * operand2 ;};
    public int div(){ return operand1 / operand2 ;};
    public int sub(){ return operand1 - operand2 ;};
=======
    public int add() {
        return operand1 + operand2;
    }

    public int mul() {
        return operand1 * operand2;
    }

    public int div() {
        return operand1 / operand2;
    }

    public int sub() {
        return operand1 - operand2;
    }
>>>>>>> 180e46832073fa7cc13384e4f8b05b8cf70cea59
}
