package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;

public class DefaultDriveCommand extends Command {

    private final SwerveSubsystem swerve;
    private final DoubleSupplier x;
    private final DoubleSupplier y;
    private final DoubleSupplier rot;

    public DefaultDriveCommand(SwerveSubsystem swerve,
                               DoubleSupplier x,
                               DoubleSupplier y,
                               DoubleSupplier rot) {

        this.swerve = swerve;
        this.x = x;
        this.y = y;
        this.rot = rot;

        addRequirements(swerve);
    }

    @Override
    public void execute() {

        double xs = -MathUtil.applyDeadband(x.getAsDouble(), OIConstants.kDeadband);
        double ys = -MathUtil.applyDeadband(y.getAsDouble(), OIConstants.kDeadband);
        double rs = -MathUtil.applyDeadband(rot.getAsDouble(), OIConstants.kDeadband);

        swerve.drive(
            xs * DriveConstants.kMaxSpeed,
            ys * DriveConstants.kMaxSpeed,
            rs * DriveConstants.kMaxAngularSpeed
        );
    }

    @Override
    public void end(boolean interrupted) {
        swerve.stop();
    }
}