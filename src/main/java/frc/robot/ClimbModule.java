// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ClimbModule {

    private static final double Kp = 1 / 90.0;

    private SparkMax            leftClimb;
    private SparkMax            rightClimb;
    private String              leftClimbInfo;
    private String              rightClimbInfo;
    // private AHRS navX;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public ClimbModule(String left, String right, int LeftCanId, int RightCanId) {

        leftClimb  = new SparkMax(LeftCanId, MotorType.kBrushless); // Initialize SparkMaxs for climb
        rightClimb = new SparkMax(RightCanId, MotorType.kBrushless);

        // Configure the speed and turn motors to defaults
        SparkMaxConfig sparkMaxConfig = new SparkMaxConfig();

        sparkMaxConfig.inverted(false);
        sparkMaxConfig.idleMode(IdleMode.kBrake);
        sparkMaxConfig.encoder.positionConversionFactor(1);
        sparkMaxConfig.encoder.velocityConversionFactor(1);

        leftClimb.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        rightClimb.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        this.leftClimbInfo  = leftClimbInfo;
        this.rightClimbInfo = rightClimbInfo;
        // i have yet to grasp the concept of "this" so im writing the above line anyway
    }

    public void setLClimbSpeed(double speed) {
        leftClimb.set(speed);
    }

    public void setRClimbSpeed(double speed) {
        rightClimb.set(speed);
    }

    public void periodic() {
        SmartDashboard.putNumber(leftClimbInfo + " Left Climber Speed", leftClimb.getEncoder().getVelocity());
        SmartDashboard.putNumber(rightClimbInfo + " Right Climber Speed", rightClimb.getEncoder().getVelocity());
    }
}