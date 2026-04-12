// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class ClimbSubsystem extends SubsystemBase {

    private SparkMax leftMotor;
    private SparkMax rightMotor;


    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public ClimbSubsystem() {

        leftMotor  = new SparkMax(51, MotorType.kBrushless);
        rightMotor = new SparkMax(52, MotorType.kBrushless);

        SparkMaxConfig sparkMaxConfig = new SparkMaxConfig();

        sparkMaxConfig.encoder.positionConversionFactor(1);
        sparkMaxConfig.inverted(false);

        leftMotor.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        sparkMaxConfig.inverted(true);
        rightMotor.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        leftMotor.getEncoder().setPosition(0);
        rightMotor.getEncoder().setPosition(0);
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
    public void periodic() {

        SmartDashboard.putNumber("Left Climb", leftMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("Right Climb", rightMotor.getEncoder().getPosition());
    }

    public void setSpeed(double speed) {
        setLeftSpeed(speed);
        setRightSpeed(speed);
    }

    public void setLeftSpeed(double speed) {
        leftMotor.set(speed);
    }

    public void setRightSpeed(double speed) {
        rightMotor.set(speed);
    }

    public void resetEncoders() {
        leftMotor.getEncoder().setPosition(0);
        rightMotor.getEncoder().setPosition(0);
    }
}
