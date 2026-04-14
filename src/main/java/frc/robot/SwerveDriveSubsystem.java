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
        frontLeft  = new SwerveModule("FL", 10, 11, 12, 52.2);
        frontRight = new SwerveModule("FR", 20, 21, 22, 129.6);
        backLeft   = new SwerveModule("BL", 30, 31, 32, 147.0);
        backRight  = new SwerveModule("BR", 40, 41, 42, 18.5);
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

    public void drive(double x, double y, double omega, boolean angleLock) {

        double speed;

        if (angleLock) {
            setTurnSpeed(0);
            speed = y;
            setDriveSpeed(speed);
        }
        else if (!(x == 0 && y == 0) && omega == 0) {

            double theta   = Math.atan2(y, x);
            double heading = 90 - theta * 180 / Math.PI;

            speed = (Math.pow(x * x + y * y, .5)) % 1.0;

            if ((heading - frontLeft.getAngle()) > 90 && (heading - frontLeft.getAngle()) < 270) {
                heading -= 180;
                speed    = -speed;
            }

            if (heading < 0) {
                heading += 360.0;
            }

            if (heading > 360) {
                heading -= 360.0;
            }

            turnToAngle(heading);
            setDriveSpeed(speed);
        }
        else if (omega != 0) {
            frontRight.turnToAngle(135);
            frontLeft.turnToAngle(45);
            backRight.turnToAngle(225);
            backLeft.turnToAngle(315);

            setDriveSpeed(omega);
        }
        else if (!(x == 0 && y == 0 && omega == 0)) {
            double fl_x = x + Math.cos(45) * omega;
            double fl_y = y + Math.sin(45) * omega;

            double fr_x = x + Math.cos(135) * omega;
            double fr_y = y + Math.sin(135) * omega;

            double bl_x = x + Math.cos(315) * omega;
            double bl_y = y + Math.sin(315) * omega;

            double br_x = x + Math.cos(225) * omega;
            double br_y = y + Math.sin(225) * omega;

            frontLeft.setDriveSpeed((Math.pow(fl_x * fl_x + fl_y * fl_y, .5)) % 1.0);
            frontLeft.turnToAngle(Math.atan2(fl_y, fl_x) * 180 / Math.PI);

            frontRight.setDriveSpeed((Math.pow(fr_x * fr_x + fr_y * fr_y, .5)) % 1.0);
            frontRight.turnToAngle(Math.atan2(fr_y, fr_x) * 180 / Math.PI);

            backLeft.setDriveSpeed((Math.pow(bl_x * bl_x + bl_y * bl_y, .5)) % 1.0);
            backLeft.turnToAngle(Math.atan2(bl_y, bl_x) * 180 / Math.PI);

            backRight.setDriveSpeed((Math.pow(br_x * br_x + br_y * br_y, .5)) % 1.0);
            backRight.turnToAngle(Math.atan2(br_y, br_x) * 180 / Math.PI);

        }
        else {
            setTurnSpeed(0);
            speed = (Math.pow(x * x + y * y, .5)) % 1.0;
            setDriveSpeed(speed);
        }

    }


    public void clam(boolean clam, double omega) {

        // If clam is active, drive speed is given by the right stick
        // This allows the robot to spin in place, (also currently the only way to spin)

        if (clam) {
            frontRight.turnToAngle(135);
            frontLeft.turnToAngle(45);
            backRight.turnToAngle(225);
            backLeft.turnToAngle(315);

            setDriveSpeed(omega);

        }
        else {
            // do nothing
        }
    }

}
