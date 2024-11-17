package org.firstinspires.ftc.teamcode;

public class State{
    private StateType stateCheck;
    State(StateType stateCheck){
        this.stateCheck = stateCheck;
    }
    public StateType getState(){
        return stateCheck;
    }
    public void setState(StateType stateCheck){
        this.stateCheck = stateCheck;
    }
}
