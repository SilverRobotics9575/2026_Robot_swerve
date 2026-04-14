package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoCommand {

    private SwerveDriveSubsystem swerveDriveSubsystem;
    private HopperSubsystem      hopperSubsystem;
    private ClimbSubsystem       climbSubsystem;

    SendableChooser<AutoPattern> autoPatternChooser = new SendableChooser<>();
    SendableChooser<Integer>     waitTimeChooser    = new SendableChooser<>();
    AutoPattern                  autoPattern;
    int                          waitTime           = 0;
    Step                         step               = null;
    Timer                        stepTimer          = new Timer();

    public static enum AutoPattern {
        DO_NOTHING, SHOOT_ONLY, CLIMB_ONLY, SHOOT_AND_CLIMB;
    }

    private enum Step {
        ALIGN_WHEELS, WAIT, MOVE_TO_SHOOT, SHOOT, MOVE_TO_CLIMB, CLIMB, DONE;
    };

    public AutoCommand(SwerveDriveSubsystem swerveDriveSubsystem, HopperSubsystem hopperSubsystem,
        ClimbSubsystem climbSubsystem) {

        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.hopperSubsystem      = hopperSubsystem;
        this.climbSubsystem       = climbSubsystem;

        /*
         * Put the Auto selectors on the SmartDashboard
         */
        autoPatternChooser.setDefaultOption("Do Nothing", AutoPattern.DO_NOTHING);
        SmartDashboard.putData("Auto Pattern", autoPatternChooser);
        autoPatternChooser.addOption("Shoot Only", AutoPattern.SHOOT_ONLY);
        autoPatternChooser.addOption("Climb Only", AutoPattern.CLIMB_ONLY);
        autoPatternChooser.addOption("Shoot and Climb", AutoPattern.SHOOT_AND_CLIMB);

        waitTimeChooser.setDefaultOption("No wait", 0);
        SmartDashboard.putData("Auto Wait Time", waitTimeChooser);
        waitTimeChooser.addOption("3 seconds", 3);
        waitTimeChooser.addOption("5 seconds", 5);
    }

    public void init() {

        /*
         * Initialize the gyro to zero
         * pointing away from the driver station
         */
        swerveDriveSubsystem.setGyroAngle(0);
        swerveDriveSubsystem.resetDistanceEncoders();
        climbSubsystem.resetEncoders();

        autoPattern = autoPatternChooser.getSelected();
        waitTime    = waitTimeChooser.getSelected();

        // Initial state is always to align the wheels to 180 for 1/2 second.
        nextStep(Step.ALIGN_WHEELS);
    }

    public void periodic() {

        /*
         * The auto command is a big state machine. Each state will be
         * "active" until the state changes and will be called every
         * 20ms in auto
         */

        if (autoPattern == AutoPattern.DO_NOTHING) {
            return;
        }

        switch (step) {

        case ALIGN_WHEELS:

            // Align the wheels to 180 for 0.5 seconds before moving
            swerveDriveSubsystem.setWheelAngle(180);

            if (stepTimer.hasElapsed(0.5)) {
                nextStep(Step.WAIT);
            }

            return;

        case WAIT:

            // If there is a wait, then wait for the prescribed time.
            if (stepTimer.hasElapsed(waitTime)) {

                if (autoPattern == AutoPattern.CLIMB_ONLY) {
                    nextStep(Step.MOVE_TO_CLIMB);
                }
                else {
                    nextStep(Step.MOVE_TO_SHOOT);
                }

            }
            return;

        case MOVE_TO_SHOOT:

            swerveDriveSubsystem.setDriveSpeed(0.2);
            swerveDriveSubsystem.setWheelAngle(180);

            if (Math.abs(swerveDriveSubsystem.getDistanceInches()) >= 20) {
                swerveDriveSubsystem.setDriveSpeed(0);
                nextStep(Step.SHOOT);
            }

            return;

        case SHOOT:

            hopperSubsystem.setShooterSpeed(1.0);
            if (stepTimer.hasElapsed(0.5)) {
                hopperSubsystem.setBeltSpeed(1);
            }
            if (stepTimer.hasElapsed(10)) {
                hopperSubsystem.setBeltSpeed(0);
                hopperSubsystem.setShooterSpeed(0);
                if (autoPattern == AutoPattern.SHOOT_AND_CLIMB) {
                    nextStep(Step.CLIMB);
                }
                else {
                    nextStep(Step.DONE);
                }
            }
            return;

        case MOVE_TO_CLIMB:

            if (swerveDriveSubsystem.getDistanceInches() >= 30) {
                swerveDriveSubsystem.setDriveSpeed(0);
            }
            else {
                swerveDriveSubsystem.setDriveSpeed(0.2);
                swerveDriveSubsystem.setWheelAngle(180);
            }

            if (climbSubsystem.getClimbPositionInches() >= 10) {
                climbSubsystem.setSpeed(0);
            }
            else {
                climbSubsystem.setSpeed(.8);
            }

            if (swerveDriveSubsystem.getDistanceInches() >= 30
                && climbSubsystem.getClimbPositionInches() >= 10) {
                nextStep(Step.CLIMB);
            }
            return;

        case CLIMB:

            climbSubsystem.setSpeed(-0.8);
            if (climbSubsystem.getClimbPositionInches() <= 8) {
                climbSubsystem.setSpeed(0);
                nextStep(Step.DONE);
            }
            return;

        case DONE:
            // Do nothing, we are done with auto.
            return;
        }
    }

    private void nextStep(Step step) {
        this.step = step;
        stepTimer.restart();
    }
}
