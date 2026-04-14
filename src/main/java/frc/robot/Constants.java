package frc.robot;

public final class Constants {
    public static final class autoConstants {
        public static final double INCHES_TO_SHOOT      = 10.0;
        public static final double INCHES_TO_CLIMB      = 10.0;
        public static final double INCHES_TO_CLIMB_DOWN = 2.0;
        public static final double INCHES_TO_CLIMB_UP   = 10.0;

        public static final double AUTO_SPEED           = 0.5;
        public static final double AUTO_CLIMB_SPEED     = 0.5;
    }

    public static final class driveConstants {
        public static final double FL_DRIVE_ID     = 10;
        public static final double FR_DRIVE_ID     = 20;
        public static final double BL_DRIVE_ID     = 30;
        public static final double BR_DRIVE_ID     = 40;

        public static final double FL_TURN_ID      = 11;
        public static final double FR_TURN_ID      = 21;
        public static final double BL_TURN_ID      = 31;
        public static final double BR_TURN_ID      = 41;

        public static final double FL_CAN_ID       = 12;
        public static final double FR_CAN_ID       = 22;
        public static final double BL_CAN_ID       = 32;
        public static final double BR_CAN_ID       = 42;

        public static final double FL_ANGLE_OFFSET = 59.2 + 180;
        public static final double FR_ANGLE_OFFSET = 125.5 + 180;
        public static final double BL_ANGLE_OFFSET = 145.5 + 180;
        public static final double BR_ANGLE_OFFSET = 16.9 + 180;
    }

    public static final class climbConstants {
        public static final double LEFT_CLIMB_ID    = 51;
        public static final double RIGHT_CLIMB_ID   = 52;

        public static final double CLIMB_GEAR_RATIO = 100 / 1;
    }

    public static final class hopperConstants {
        public static final double BELT_ID          = 8;
        public static final double LEFT_SHOOTER_ID  = 6;
        public static final double RIGHT_SHOOTER_ID = 7;
    }
}