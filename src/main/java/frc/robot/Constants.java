package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public final class Constants {

    public static final class DriveConstants {

        // robot dimensions in meters
        public static final double kTrackWidth = 0.7;
        public static final double kWheelBase = 0.7;

        public static final Translation2d FL = new Translation2d(+kWheelBase/2, +kTrackWidth/2);
        public static final Translation2d FR = new Translation2d(+kWheelBase/2, -kTrackWidth/2);
        public static final Translation2d BL = new Translation2d(-kWheelBase/2, +kTrackWidth/2);
        public static final Translation2d BR = new Translation2d(-kWheelBase/2, -kTrackWidth/2);

        public static final SwerveDriveKinematics kKinematics =
            new SwerveDriveKinematics(FL, FR, BL, BR);

        public static final double kMaxSpeed = 0.5; // m/s
        public static final double kMaxAngularSpeed = Math.PI * 2;
    }

    public static final class OIConstants {
        public static final int kDriverPort = 0;
        public static final double kDeadband = 0.05;
    }

    // IDs are good
    public static final class CANIDs {
        public static final int FL_DRIVE = 1;
        public static final int FL_TURN  = 11;

        public static final int FR_DRIVE = 2;
        public static final int FR_TURN  = 21;

        public static final int BL_DRIVE = 3;
        public static final int BL_TURN  = 31;

        public static final int BR_DRIVE = 4;
        public static final int BR_TURN  = 41;
    }
}