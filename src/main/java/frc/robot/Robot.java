// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.NavX.AHRS;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {

  private GameController gameController;
  private SparkMax       driveMotor;
  private SparkMax       turnMotor;
  private AHRS           navX;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
  }

  @Override
  public void robotInit() {

    gameController = new GameController(0);                  // Initialize GameController on port 0

    driveMotor     = new SparkMax(30, MotorType.kBrushless); // Initialize SparkMax on port 30 for NEO motor
    turnMotor      = new SparkMax(31, MotorType.kBrushless);

    // Configure the speed and turn motors to defaults
    SparkMaxConfig sparkMaxConfig = new SparkMaxConfig();

    sparkMaxConfig.inverted(false);
    sparkMaxConfig.encoder.positionConversionFactor(1);
    sparkMaxConfig.encoder.velocityConversionFactor(1);

    driveMotor.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    turnMotor.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    navX = new AHRS();
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

    double speed = gameController.getLeftY(); // Example: Get left joystick Y-axis value
    driveMotor.set(speed); // Set motor speed based on joystick input

    double turn = gameController.getRightX(); // Example: Get right joystick X-axis value
    turnMotor.set(turn);

    SmartDashboard.putNumber("Turn Speed", turnMotor.getEncoder().getVelocity());
    SmartDashboard.putNumber("Drive Speed", driveMotor.getEncoder().getVelocity());
    SmartDashboard.putData("Gyro", navX);
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
