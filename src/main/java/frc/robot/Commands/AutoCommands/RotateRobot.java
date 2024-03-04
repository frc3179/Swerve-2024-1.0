// The RotateRobot class is a command that rotates the robot to a specified target angle.
// It utilizes the DriveSubsystem to control the robot's movement.
// The command takes in the DriveSubsystem, target angle, and rotation speed as parameters.
// The command starts by initializing the robot's movement to 0,0,0.
// The command then enters a loop where it continuously updates the current angle and calculates the error.
// The robot rotates based on the sign of the error and the specified rotation speed.
// The command ends when the error is less than a certain threshold (5 degrees in this case).
// This threshold can be adjusted to control the rotation precision.

package frc.robot.Commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class RotateRobot extends Command {
    private final DriveSubsystem driveTrain;
    private final double targetAngle;
    private final double rotationSpeed;

    // The constructor takes in the DriveSubsystem, target angle, and rotation speed.
    // It also adds the DriveSubsystem as a requirement for this command.
    public RotateRobot(DriveSubsystem driveTrain, double targetAngle, double rotationSpeed) {
        this.driveTrain = driveTrain;
        this.targetAngle = targetAngle;
        this.rotationSpeed = rotationSpeed;
        addRequirements(driveTrain);
    }

    // The initialize method sets the robot's movement to 0,0,0.
    @Override
    public void initialize() {
        driveTrain.drive(0,0,0, false,false, false, false, false);
    }

    // The execute method continuously updates the current angle and calculates the error.
    // It then rotates the robot based on the sign of the error and the specified rotation speed.
    @Override
    public void execute() {
        double currentAngle = driveTrain.getGyroAngle();
        double error = targetAngle - currentAngle;
        driveTrain.drive(0, 0, rotationSpeed * Math.signum(error), false, false, false, false, false);
    }

    // The end method sets the robot's movement to 0,0,0.
    @Override
    public void end(boolean interrupted) {
        driveTrain.drive(0,0,0, false,false, false, false, false);
    }

    // The isFinished method checks if the error is less than a certain threshold (5 degrees in this case).
    // It returns true if the error is less than the threshold, indicating that the command is finished.
    @Override
    public boolean isFinished() {
        double currentAngle = driveTrain.getGyroAngle();
        double error = Math.abs(targetAngle - currentAngle);
        return error < 5; // Adjust this value to control the rotation precision
    }
}