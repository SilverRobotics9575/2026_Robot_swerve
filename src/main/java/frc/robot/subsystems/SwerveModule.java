package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.configs.CANcoderConfiguration;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule {

    private final SparkMax driveMotor;
    private final SparkMax turnMotor;

    private final CANcoder cancoder;
    private final PIDController turnPID;

    private final double angleOffset; // in radians

    public SwerveModule(int driveID, int turnID, int cancoderID, double angleOffsetRad,
                        boolean driveInvert, boolean turnInvert) {

        this.angleOffset = angleOffsetRad;

        driveMotor = new SparkMax(driveID, MotorType.kBrushless);
        turnMotor  = new SparkMax(turnID, MotorType.kBrushless);

        driveMotor.setInverted(driveInvert);
        turnMotor.setInverted(turnInvert);

        
        cancoder = new CANcoder(cancoderID);
        CANcoderConfiguration cfg = new CANcoderConfiguration();
        cancoder.getConfigurator().apply(cfg);

        
        turnPID = new PIDController(1.2, 0.0, 0.0);
        turnPID.enableContinuousInput(-Math.PI, Math.PI);
    }

    
    private double getAbsoluteAngle() {
        double rotations = cancoder.getAbsolutePosition().getValueAsDouble();
        double radians = rotations * 2.0 * Math.PI;
        return radians - angleOffset;
    }

    public void setDesiredState(SwerveModuleState state) {

        
        Rotation2d currentAngle = new Rotation2d(getAbsoluteAngle());
        SwerveModuleState optimized = SwerveModuleState.optimize(state, currentAngle);

        
        double driveOutput = optimized.speedMetersPerSecond / 4.0;
        driveMotor.set(driveOutput);

        
        double turnOutput = turnPID.calculate(currentAngle.getRadians(),
                                              optimized.angle.getRadians());
        turnMotor.set(turnOutput);
    }

    public void stop() {
        driveMotor.set(0);
        turnMotor.set(0);
    }
}