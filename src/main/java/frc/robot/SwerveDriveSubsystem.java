// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class SwerveDriveSubsystem extends SubsystemBase {

    // 4 inch wheels with a drive gear ratio of 6.75:1
    private static final double DRIVE_INCHES_PER_ENCODER_COUNT = Math.PI * 4.0 / 6.75;

    private SwerveModule        frontRight;
    private SwerveModule        frontLeft;
    private SwerveModule        backRight;
    private SwerveModule        backLeft;

    private AHRS                navX                           = new AHRS(NavXComType.kMXP_SPI);

    double                      gyroOffset                     = 0;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public SwerveDriveSubsystem() {
        frontLeft  = new SwerveModule("FL", 10, 11, 12, 59.2 + 180);
        frontRight = new SwerveModule("FR", 20, 21, 22, 125.5 + 180);
        backLeft   = new SwerveModule("BL", 30, 31, 32, 145.5 + 180);
        backRight  = new SwerveModule("BR", 40, 41, 42, 16.9 + 180);
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
        frontRight.periodic();
        frontLeft.periodic();
        backRight.periodic();
        backLeft.periodic();

        SmartDashboard.putData("Gyro", navX);
        SmartDashboard.putNumber("SwerveDistance (in)", getDistanceInches());
    }

    public void setDriveSpeed(double speed) {
        frontRight.setDriveSpeed(speed);
        frontLeft.setDriveSpeed(speed);
        backRight.setDriveSpeed(speed);
        backLeft.setDriveSpeed(speed);
    }

    public void setWheelAngle(double angle) {
        frontRight.turnToAngle(angle);
        frontLeft.turnToAngle(angle);
        backRight.turnToAngle(angle);
        backLeft.turnToAngle(angle);
    }

    private void setTurnSpeed(double speed) {
        frontRight.setTurnSpeed(speed);
        frontLeft.setTurnSpeed(speed);
        backRight.setTurnSpeed(speed);
        backLeft.setTurnSpeed(speed);
    }

    public void drive(double x, double y, double omega) {

        double speed;

        if (!(x == 0 && y == 0)) {

            // Driving is a priority. If the x or y drive vectors are
            // non zero, then use the magnitude and angle to drive
            // without spinning
            double theta   = Math.atan2(y, x);
            double heading = 90 - theta * 180 / Math.PI;

            if (heading < 0) {
                heading += 360.0;
            }

            if (heading > 360) {
                heading -= 360.0;
            }

            setWheelAngle(heading);

            speed = (Math.pow(x * x + y * y, .5)) % 1.0;
            setDriveSpeed(speed);

        }
        else if (omega != 0) {

            // If the robot spin is input, then spin
            frontRight.turnToAngle(135);
            frontLeft.turnToAngle(45);
            backRight.turnToAngle(225);
            backLeft.turnToAngle(315);

            setDriveSpeed(omega);
        }
        else {
            setTurnSpeed(0);
            setDriveSpeed(0);
        }
    }

    public void angleLock(double speed) {
        // this is important to the driver, locks the angle and takes speed from L stick
        setTurnSpeed(0);
        setDriveSpeed(speed);

    }

    public void clam() {

        // If clam is active, the drive is locked in an X pattern and the robot is stopped

        frontLeft.turnToAngle(135);
        frontRight.turnToAngle(225);

        backLeft.turnToAngle(45);
        backRight.turnToAngle(315);

        setDriveSpeed(0);
    }

    public void setGyroAngle(double angle) {

        gyroOffset = 0;
        gyroOffset = -getGyroAngle() + angle;
    }

    public double getGyroAngle() {

        double angle = navX.getAngle() + gyroOffset;

        angle %= 360;

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    public double getDistanceInches() {
        return (frontLeft.getDistance()
            + frontRight.getDistance()
            + backLeft.getDistance()
            + backRight.getDistance())
            / 4.0
            * DRIVE_INCHES_PER_ENCODER_COUNT;
    }

    public void resetDistanceEncoders() {

        frontLeft.resetDistanceEncoder();
        frontRight.resetDistanceEncoder();
        backLeft.resetDistanceEncoder();
        backRight.resetDistanceEncoder();
    }
}
