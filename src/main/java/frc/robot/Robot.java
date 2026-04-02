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
    private GameController       gameController;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public Robot() {
    }

    @Override
    public void robotInit() {

        gameController       = new GameController(0);     // Initialize GameController on port 0
        // swerveModule = new SwerveModule(40, 41, 42, 53.2);
        // swerveModule2 = new SwerveModule(20, 21, 22, 147.0);
        swerveDriveSubsystem = new SwerveDriveSubsystem();
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
        SmartDashboard.putNumber("DPad", gameController.getPOV());
    }

    /** This function is called once when autonomous is enabled. */
    @Override
    public void autonomousInit() {
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
    }

    /** This function is called once when teleop is enabled. */
    @Override
    public void teleopInit() {
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {

        // FIXME the swerve drive() method and clam() method will fight each other
        // which one takes priority? can we use an if/else construct?

        swerveDriveSubsystem.drive(gameController.getLeftX(), gameController.getLeftY(),
            gameController.getRightX());

        swerveDriveSubsystem.clam(gameController.getRightBumperButton(), gameController.getRightX());

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
