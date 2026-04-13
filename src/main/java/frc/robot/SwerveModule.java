// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class SwerveModule {

    private static final double Kp             = 1 / 90.0;

    private SparkMax            driveMotor;
    private SparkMax            turnMotor;
    private CANcoder            angleEncoder;
    private double              angleEncoderOffset;
    private String              moduleName;

    private double              distanceOffset = 0;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public SwerveModule(String moduleName, int driveMotorCanId, int turnMotorCanId, int encoderCanId, double angleOffset) {

        driveMotor = new SparkMax(driveMotorCanId, MotorType.kBrushless); // Initialize SparkMax on port 30 for NEO motor
        turnMotor  = new SparkMax(turnMotorCanId, MotorType.kBrushless);

        // Configure the speed and turn motors to defaults
        SparkMaxConfig sparkMaxConfig = new SparkMaxConfig();

        sparkMaxConfig.inverted(false);
        sparkMaxConfig.idleMode(IdleMode.kBrake);
        sparkMaxConfig.encoder.positionConversionFactor(1);
        sparkMaxConfig.encoder.velocityConversionFactor(1);

        driveMotor.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        turnMotor.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        angleEncoder            = new CANcoder(encoderCanId);

        this.moduleName         = moduleName;
        this.angleEncoderOffset = angleOffset;
    }

    public void setDriveSpeed(double speed) {
        driveMotor.set(speed);
    }

    public void setTurnSpeed(double speed) {
        turnMotor.set(speed);
    }

    public double getAngle() {

        double angle = -angleEncoder.getAbsolutePosition().getValueAsDouble() * 360;

        // add offset
        angle += angleEncoderOffset;

        angle %= 360.0d;
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public void turnToAngle(double setPointAngle) {

        double speed = 0;

        double error = setPointAngle - getAngle();

        if (error > 180) {
            error -= 360;
        }
        if (error < -180) {
            error += 360;
        }
        speed = error * Kp;
        turnMotor.set(speed);
    }

    public double getDistance() {
        return driveMotor.getEncoder().getPosition() + distanceOffset;
    }

    public void resetDistanceEncoder() {
        distanceOffset = -driveMotor.getEncoder().getPosition();
    }

    public void periodic() {
        SmartDashboard.putNumber(moduleName + " Turn Speed", turnMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber(moduleName + " Drive Speed", driveMotor.getEncoder().getVelocity());
        SmartDashboard.putNumber(moduleName + " Drive Distance", getDistance());
        SmartDashboard.putNumber(moduleName + " Angle", getAngle());
    }

}
