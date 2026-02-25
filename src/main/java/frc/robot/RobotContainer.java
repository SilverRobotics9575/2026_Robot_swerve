package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.Constants.OIConstants;

public class RobotContainer {

    private final SwerveSubsystem swerve = new SwerveSubsystem();
    private final XboxController controller = new XboxController(OIConstants.kDriverPort);

    public RobotContainer() {
        swerve.setDefaultCommand(
            new DefaultDriveCommand(
                swerve,
                () -> controller.getLeftY(),
                () -> controller.getLeftX(),
                () -> controller.getRightX()
            )
        );
    }

    public Command getAutonomousCommand() {
        return null; // no auto
    }
}