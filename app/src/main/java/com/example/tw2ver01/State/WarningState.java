package com.example.tw2ver01.State;

import com.example.tw2ver01.MainActivity;

public class WarningState implements HandLeavingAlert_State {
    private final String stateType = "Warning";

    public WarningState() {
        System.out.println("Warning state");
    }

    @Override
    public String getStateType() {
        return stateType;
    }

    @Override
    public void switchState() {
        MainActivity.state = new WorkingState();
        MainActivity.setTimeleft(MainActivity.state.getStateType());
    }


}
