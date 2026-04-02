// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class SwerveDriveSubsystem extends SubsystemBase {

    private SwerveModule frontRight;
    private SwerveModule frontLeft;
    private SwerveModule backRight;
    private SwerveModule backLeft;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public SwerveDriveSubsystem() {
        frontLeft  = new SwerveModule("FL", 10, 11, 12, 18.5);
        frontRight = new SwerveModule("FR", 20, 21, 22, 147.0);
        backLeft   = new SwerveModule("BL", 30, 31, 32, 129.6);
        backRight  = new SwerveModule("BR", 40, 41, 42, 53.2);
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
    }

    private void setTurnSpeed(double speed) {
        frontRight.setTurnSpeed(speed);
        frontLeft.setTurnSpeed(speed);
        backRight.setTurnSpeed(speed);
        backLeft.setTurnSpeed(speed);
    }

    private void setDriveSpeed(double speed) {
        frontRight.setDriveSpeed(speed);
        frontLeft.setDriveSpeed(speed);
        backRight.setDriveSpeed(speed);
        backLeft.setDriveSpeed(speed);
    }

    private void turnToAngle(double heading) {
        frontRight.turnToAngle(heading);
        frontLeft.turnToAngle(heading);
        backRight.turnToAngle(heading);
        backLeft.turnToAngle(heading);
    }

    public void drive(double x, double y, double omega) {

        if (!(x == 0 && y == 0)) {

            double theta   = Math.atan2(y, x);
            double heading = 90 - theta * 180 / Math.PI;

            if (heading < 0) {
                heading += 360.0;
            }

            if (heading > 360) {
                heading -= 360.0;
            }

            turnToAngle(heading);

        }
        else {
            setTurnSpeed(0);
        }

        double speed = Math.pow(x * x + y * y, .5);

        setDriveSpeed(speed);
    }


    public void clam(boolean clam, double omega) {

        // FIXME If the clam is active, should the drive speed be zero?

        if (clam) {
            frontRight.turnToAngle(135);
            frontLeft.turnToAngle(45);
            backRight.turnToAngle(225);
            backLeft.turnToAngle(315);

            frontRight.setDriveSpeed(omega);
            backLeft.setDriveSpeed(omega);
            backRight.setDriveSpeed(omega);
            frontLeft.setDriveSpeed(omega);

        }
        else {
            // do nothing
        }
    }

}
