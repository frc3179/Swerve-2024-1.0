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
    private double targetAngle;
    private final double rotationSpeed;
    double currentAngle;
    double gryo;

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
        /*
         * 0 to 180 right
         * or
         * (<0) to (>-180) left
         */
        if((int) targetAngle / 180 >= 0 && (int) targetAngle / 180 <= 1){
            targetAngle = targetAngle;
        } else {
            targetAngle = targetAngle-360;
        }
    }

    // The execute method continuously updates the current angle and calculates the error.
    // It then rotates the robot based on the sign of the error and the specified rotation speed.
    @Override
    public void execute() {
        gryo = driveTrain.m_gyro.getAngle();
        //double currentAngle = (driveTrain.m_gyro.getAngle() % 360 + 360) % 360;
        if(gryo % 360 == 0){
            currentAngle = 0;
        }
        else{
            currentAngle = gryo - (((int) (gryo / 360))*360);
        }
        double error = targetAngle - currentAngle;
        driveTrain.drive(0, 0, -5*((rotationSpeed * Math.signum(error))), false, false, false, false, false);
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
        double error = Math.abs(targetAngle - currentAngle);
        return error < 10; // Adjust this value to control the rotation precision
    }
}