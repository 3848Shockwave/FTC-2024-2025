package org.firstinspires.ftc.teamcode.util;

public class State{
    private int stateCheck;
    State(int stateCheck){
        this.stateCheck = stateCheck;
    }
    public int getState(){
        return stateCheck;
    }
    public void setState(int stateCheck){
        this.stateCheck = stateCheck;
    }
}
