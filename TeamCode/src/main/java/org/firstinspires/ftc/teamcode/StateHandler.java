package org.firstinspires.ftc.teamcode;

public class StateHandler {
    double step = .01;
    Hardware hardware;
    public StateHandler(Hardware hardware) {
        this.hardware = hardware;
    }
    public State extendHorizontal = new State(StateType.INACTIVE);
    public  State extendVertical = new State(StateType.INACTIVE);
    public State gripHorizontal = new State(StateType.INACTIVE);
    public State gripVertical = new State(StateType.INACTIVE);
    public State pivotHorizontal = new State(StateType.INACTIVE);
    public State pivotVertical = new State(StateType.INACTIVE);
    public State rotateHorizontal = new State(StateType.INACTIVE);
    public State rotateVertical = new State(StateType.INACTIVE);
    public State rotateArmHorizontal = new State(StateType.INACTIVE);
    public State rotateArmVertical = new State(StateType.INACTIVE);

    public void handler() {
        if (extendHorizontal.getState()==StateType.POSTIVE) {
            hardware.setServoPosition("horzExtL", hardware.getServoPosition("horzExtL")+step);
            hardware.setServoPosition("horzExtR", hardware.getServoPosition("horzExtR")+step);
        }
        else if(extendHorizontal.getState()==StateType.NEGATIVE) {
            hardware.setServoPosition("horzExtL", hardware.getServoPosition("horzExtL") - step);
            hardware.setServoPosition("horzExtR", hardware.getServoPosition("horzExtR") - step);
        }
        if (extendVertical.getState()==StateType.POSTIVE) {
            hardware.setDcMotorPower("spoolLeft", 1);
            hardware.setDcMotorPower("spoolRight", 1);
        }
        else if(extendVertical.getState()==StateType.NEGATIVE) {
            hardware.setDcMotorPower("spoolLeft", -1);
            hardware.setDcMotorPower("spoolRight", -1);
        }
        if (gripHorizontal.getState()==StateType.POSTIVE) {
            hardware.setServoPosition("horzClawGrip", servoPositions.HORZCLAWGRIPCLOSE.getPosition());
        }
        else if(gripHorizontal.getState()==StateType.NEGATIVE) {
            hardware.setServoPosition("horzClawGrip", servoPositions.HORZCLAWGRIPOPEN.getPosition());
        }
        if (gripVertical.getState()==StateType.POSTIVE) {
            hardware.setServoPosition("vertClawGrip", servoPositions.VERTCLAWGRIPCLOSE.getPosition());
        }
        else if(gripVertical.getState()==StateType.NEGATIVE) {
            hardware.setServoPosition("vertClawGrip", servoPositions.VERTCLAWGRIPOPEN.getPosition());
        }
        if (pivotHorizontal.getState()==StateType.POSTIVE) {
            hardware.setServoPosition("horzClawPiv", hardware.getServoPosition("horzClawPiv")+step);
        }
        else if(pivotHorizontal.getState()==StateType.NEGATIVE) {
            hardware.setServoPosition("horzClawPiv", hardware.getServoPosition("horzClawPiv")-step);
        }
        if (pivotVertical.getState()==StateType.POSTIVE) {
            hardware.setServoPosition("vertClawPiv", hardware.getServoPosition("vertClawPiv")+step);
        }
        else if(pivotVertical.getState()==StateType.NEGATIVE) {
            hardware.setServoPosition("vertClawPiv", hardware.getServoPosition("vertClawPiv")-step);
        }
        if (rotateHorizontal.getState()==StateType.POSTIVE) {
            hardware.setServoPosition("horzClawRot", hardware.getServoPosition("horzClawRot")+step);
        }
        else if(rotateHorizontal.getState()==StateType.NEGATIVE) {
            hardware.setServoPosition("horzClawRot", hardware.getServoPosition("horzClawRot")-step);
        }
        if (rotateVertical.getState()==StateType.POSTIVE) {
            hardware.setServoPosition("vertClawRot", hardware.getServoPosition("vertClawRot")+step);
        }
        else if(rotateVertical.getState()==StateType.NEGATIVE) {
            hardware.setServoPosition("vertClawRot", hardware.getServoPosition("vertClawRot") - step);
        }
        if (rotateArmHorizontal.getState()==StateType.POSTIVE) {
            hardware.setServoPosition("horzArmRotL", hardware.getServoPosition("horzArmRotL")+step);
            hardware.setServoPosition("horzArmRotR", hardware.getServoPosition("horzArmRotR")+step);
        }
        else if(rotateArmHorizontal.getState()==StateType.NEGATIVE) {
            hardware.setServoPosition("horzArmRotL", hardware.getServoPosition("horzArmRotL")-step);
            hardware.setServoPosition("horzArmRotR", hardware.getServoPosition("horzArmRotR")-step);
        }
        if (rotateArmVertical.getState()==StateType.POSTIVE) {
            hardware.setServoPosition("vertArmRotL", hardware.getServoPosition("vertArmRotL")+step);
            hardware.setServoPosition("vertArmRotR", hardware.getServoPosition("vertArmRotR")+step);
        }
        else if(rotateArmVertical.getState()==StateType.NEGATIVE) {
            hardware.setServoPosition("vertArmRotL", hardware.getServoPosition("vertArmRotL")-step);
            hardware.setServoPosition("vertArmRotR", hardware.getServoPosition("vertArmRotR")-step);
        }



    }
}
