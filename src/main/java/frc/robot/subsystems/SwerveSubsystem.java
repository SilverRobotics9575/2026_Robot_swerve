package frc.robot.subsystems;

    import edu.wpi.first.math.kinematics.ChassisSpeeds;
    import edu.wpi.first.math.kinematics.SwerveModuleState;
    import edu.wpi.first.wpilibj2.command.SubsystemBase;
    import frc.robot.Constants.CANIDs;
    import frc.robot.Constants.DriveConstants;

    public class SwerveSubsystem extends SubsystemBase {

        private final SwerveModule fl = new SwerveModule(
            CANIDs.FL_DRIVE, CANIDs.FL_TURN, 22, 0.0, false, false);

        private final SwerveModule fr = new SwerveModule(
            CANIDs.FR_DRIVE, CANIDs.FR_TURN, 12, 0.0, true, false);

        private final SwerveModule bl = new SwerveModule(
            CANIDs.BL_DRIVE, CANIDs.BL_TURN, 32, 0.0, false, false);

        private final SwerveModule br = new SwerveModule(
            CANIDs.BR_DRIVE, CANIDs.BR_TURN, 42, 0.0, true, false);

        public void drive(double xSpeed, double ySpeed, double rot) {

            ChassisSpeeds speeds = new ChassisSpeeds(xSpeed, ySpeed, rot);

            SwerveModuleState[] states =
                DriveConstants.kKinematics.toSwerveModuleStates(speeds);

            fl.setDesiredState(states[0]);
            fr.setDesiredState(states[1]);
            bl.setDesiredState(states[2]);
            br.setDesiredState(states[3]);
        }

        public void stop() {
            fl.stop();
            fr.stop();
            bl.stop();
            br.stop();
        }
    }