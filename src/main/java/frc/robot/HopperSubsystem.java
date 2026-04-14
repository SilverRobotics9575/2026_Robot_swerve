// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class HopperSubsystem extends SubsystemBase {

    private SparkMax  leftShooterMotor;
    private SparkMax  rightShooterMotor;

    private SparkFlex beltMotor;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public HopperSubsystem() {

        leftShooterMotor  = new SparkMax(6, MotorType.kBrushless);
        rightShooterMotor = new SparkMax(7, MotorType.kBrushless);

        SparkMaxConfig sparkMaxConfig = new SparkMaxConfig();

        sparkMaxConfig.encoder.velocityConversionFactor(1.0);
        sparkMaxConfig.inverted(false);

        leftShooterMotor.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        sparkMaxConfig.inverted(true);
        rightShooterMotor.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        beltMotor = new SparkFlex(8, MotorType.kBrushless);

        SparkFlexConfig sparkFlexConfig = new SparkFlexConfig();
        sparkFlexConfig.encoder.velocityConversionFactor(1.0);
        sparkFlexConfig.inverted(false);

        beltMotor.configure(sparkFlexConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
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

        SmartDashboard.putNumber("Shooter Speed", leftShooterMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber("Belt Speed", beltMotor.getEncoder().getPosition());
    }

    public void setShooterSpeed(double speed) {
        leftShooterMotor.set(speed);
        rightShooterMotor.set(speed);
    }

    public void setBeltSpeed(double speed) {
        beltMotor.set(speed);
    }
}
