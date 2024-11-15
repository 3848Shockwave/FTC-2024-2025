package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.servoPositions;

public class StateHandler {
    double step = .01;
    Hardware hardware;
    public StateHandler(Hardware hardware) {
        this.hardware = hardware;
    }
    public State extendHorizontal = new State(0);
    public  State extendVertical = new State(0);
    public State gripHorizontal = new State(0);
    public State gripVertical = new State(0);
    public State pivotHorizontal = new State(0);
    public State pivotVertical = new State(0);
    public State rotateHorizontal = new State(0);
    public State rotateVertical = new State(0);
    public State rotateArmHorizontal = new State(0);
    public State rotateArmVertical = new State(0);

    public void handler() {
        if (extendHorizontal.getState()==1) {
            hardware.setServoPosition("horzExtL", hardware.getServoPosition("horzExtL")+step);
            hardware.setServoPosition("horzExtR", hardware.getServoPosition("horzExtR")+step);
        }
        else if(extendHorizontal.getState()==2) {
            hardware.setServoPosition("horzExtL", hardware.getServoPosition("horzExtL") - step);
            hardware.setServoPosition("horzExtR", hardware.getServoPosition("horzExtR") - step);
        }
        if (extendVertical.getState()==1) {
            hardware.setDcMotorPower("spoolLeft", 1);
            hardware.setDcMotorPower("spoolRight", 1);
        }
        else if(extendVertical.getState()==2) {
            hardware.setDcMotorPower("spoolLeft", -1);
            hardware.setDcMotorPower("spoolRight", -1);
        }
        if (gripHorizontal.getState()==1) {
            hardware.setServoPosition("horzClawGrip", servoPositions.HORZCLAWGRIPCLOSE.getPosition());
        }
        else if(gripHorizontal.getState()==2) {
            hardware.setServoPosition("horzClawGrip", servoPositions.HORZCLAWGRIPOPEN.getPosition());
        }
        if (gripVertical.getState()==1) {
            hardware.setServoPosition("vertClawGrip", servoPositions.VERTCLAWGRIPCLOSE.getPosition());
        }
        else if(gripVertical.getState()==2) {
            hardware.setServoPosition("vertClawGrip", servoPositions.VERTCLAWGRIPOPEN.getPosition());
        }
        if (pivotHorizontal.getState()==1) {
            hardware.setServoPosition("horzClawPiv", hardware.getServoPosition("horzClawPiv")+step);
        }
        else if(pivotHorizontal.getState()==2) {
            hardware.setServoPosition("horzClawPiv", hardware.getServoPosition("horzClawPiv")-step);
        }
        if (pivotVertical.getState()==1) {
            hardware.setServoPosition("vertClawPiv", hardware.getServoPosition("vertClawPiv")+step);
        }
        else if(pivotVertical.getState()==2) {
            hardware.setServoPosition("vertClawPiv", hardware.getServoPosition("vertClawPiv")-step);
        }
        if (rotateHorizontal.getState()==1) {
            hardware.setServoPosition("horzClawRot", hardware.getServoPosition("horzClawRot")+step);
        }
        else if(rotateHorizontal.getState()==2) {
            hardware.setServoPosition("horzClawRot", hardware.getServoPosition("horzClawRot")-step);
        }
        if (rotateVertical.getState()==1) {
            hardware.setServoPosition("vertClawRot", hardware.getServoPosition("vertClawRot")+step);
        }
        else if(rotateVertical.getState()==2) {
            hardware.setServoPosition("vertClawRot", hardware.getServoPosition("vertClawRot") - step);
        }
        if (rotateArmHorizontal.getState()==1) {
            hardware.setServoPosition("horzArmRotL", hardware.getServoPosition("horzArmRotL")+step);
            hardware.setServoPosition("horzArmRotR", hardware.getServoPosition("horzArmRotR")+step);
        }
        else if(rotateArmHorizontal.getState()==2) {
            hardware.setServoPosition("horzArmRotL", hardware.getServoPosition("horzArmRotL")-step);
            hardware.setServoPosition("horzArmRotR", hardware.getServoPosition("horzArmRotR")-step);
        }
        if (rotateArmVertical.getState()==1) {
            hardware.setServoPosition("vertArmRotL", hardware.getServoPosition("vertArmRotL")+step);
            hardware.setServoPosition("vertArmRotR", hardware.getServoPosition("vertArmRotR")+step);
        }
        else if(rotateArmVertical.getState()==2) {
            hardware.setServoPosition("vertArmRotL", hardware.getServoPosition("vertArmRotL")-step);
            hardware.setServoPosition("vertArmRotR", hardware.getServoPosition("vertArmRotR")-step);
        }



    }
}
