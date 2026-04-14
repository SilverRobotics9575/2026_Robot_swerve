// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {

    private SwerveDriveSubsystem swerveDriveSubsystem;
    private ClimbSubsystem       climbSubsystem;
    private HopperSubsystem      hopperSubsystem;
    private GameController       driverController;
    private GameController       operatorController;
    private AutoCommand          autoCommand;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public Robot() {
    }

    @Override
    public void robotInit() {

        driverController     = new GameController(0);
        operatorController   = new GameController(1);

        swerveDriveSubsystem = new SwerveDriveSubsystem();
        climbSubsystem       = new ClimbSubsystem();
        hopperSubsystem      = new HopperSubsystem();

        autoCommand          = new AutoCommand(swerveDriveSubsystem, hopperSubsystem, climbSubsystem);

    }

    /**
     * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>
     * This runs AFTER the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {

        swerveDriveSubsystem.periodic();
        climbSubsystem.periodic();
        hopperSubsystem.periodic();

        SmartDashboard.putString("Driver", driverController.toString());
        SmartDashboard.putString("Operator", operatorController.toString());
    }

    /** This function is called once when autonomous is enabled. */
    @Override
    public void autonomousInit() {
        autoCommand.init();
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        autoCommand.periodic();
    }

    /** This function is called once when teleop is enabled. */
    @Override
    public void teleopInit() {
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {

        /*
         * Swerve Drive
         */
        if (driverController.getRightBumperButton()) {
            swerveDriveSubsystem.clam();
        }
        else if (driverController.getBButton()) {
            swerveDriveSubsystem.angleLock(driverController.getLeftY());
        }
        else {
            swerveDriveSubsystem.drive(
                driverController.getLeftX(),
                driverController.getLeftY(),
                driverController.getRightX());
        }

        /*
         * Climb
         */
        // Run the motors together
        if (driverController.getYButton()) {
            climbSubsystem.setSpeed(1.0);
        }
        else if (driverController.getAButton()) {
            climbSubsystem.setSpeed(-1.0);
        }
        else {
            // Drive the climbs independently using the POV
            if (driverController.getPOV() == 0) {
                climbSubsystem.setLeftSpeed(1.0);
            }
            else if (driverController.getPOV() == 180) {
                climbSubsystem.setLeftSpeed(-1.0);
            }
            else {
                climbSubsystem.setLeftSpeed(0);
            }

            if (driverController.getPOV() == 270) {
                climbSubsystem.setRightSpeed(1.0);
            }
            else if (driverController.getPOV() == 90) {
                climbSubsystem.setRightSpeed(-1.0);
            }
            else {
                climbSubsystem.setRightSpeed(0);
            }
        }

        /*
         * Hopper Controls
         */
        // FIXME: validate hopper controls
        if (operatorController.getYButton()) {
            hopperSubsystem.setShooterSpeed(.65);
        }
        else {
            hopperSubsystem.setShooterSpeed(operatorController.getRightY());
        }

        hopperSubsystem.setBeltSpeed(operatorController.getLeftY());
    }

    /** This function is called once when the robot is disabled. */
    @Override
    public void disabledInit() {
    }

    /** This function is called periodically when disabled. */
    @Override
    public void disabledPeriodic() {
    }

    /** This function is called once when test mode is enabled. */
    @Override
    public void testInit() {
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
    }

    /** This function is called once when the robot is first started up. */
    @Override
    public void simulationInit() {
    }

    /** This function is called periodically whilst in simulation. */
    @Override
    public void simulationPeriodic() {
    }

}
