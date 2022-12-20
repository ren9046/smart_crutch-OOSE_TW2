package com.example.tw2ver01.State;

import com.example.tw2ver01.MainActivity;

public class WorkingState implements HandLeavingAlert_State {
    private final String stateType = "Working";

    public WorkingState() {
        System.out.println("working state");
    }

    @Override
    public String getStateType() {
        return stateType;
    }

    @Override
    public void switchState() {
        MainActivity.state = new WarningState();
        MainActivity.setTimeleft(MainActivity.state.getStateType());
    }
}
