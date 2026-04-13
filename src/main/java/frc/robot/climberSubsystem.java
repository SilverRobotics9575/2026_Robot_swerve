// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public class climberSubsystem {

    private ClimbModule climbers;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public climberSubsystem() {
        climbers = new ClimbModule("Left Climber", "Right Climber", 51, 52);
    }

    @Override
    public void periodic() {
        climbers.periodic();
    }

    public void setLClimbSpeed(double speed) {
        climbers.setLClimbSpeed(speed);
    }

    public void setRClimbSpeed(double speed) {
        climbers.setRClimbSpeed(speed);
    }

    public void climb(double left, double right, boolean polarity) {

        if (polarity) {
            setLClimbSpeed(-left);
            setRClimbSpeed(-right);
        }
        else {
            setLClimbSpeed(left);
            setRClimbSpeed(right);
        }
    }
}