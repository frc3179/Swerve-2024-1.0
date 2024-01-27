package frc.robot.autos;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.GetTrajectory;

public class AutoList {
    // Create config for trajectory
    public static TrajectoryConfig config = new TrajectoryConfig(
        AutoConstants.kMaxSpeedMetersPerSecond,
        AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(DriveConstants.kDriveKinematics);


    public static class Autotest{ //Default Auto

        public static Command autotest(DriveSubsystem m_robotDrive, ArmSubsystem m_ArmMove){
            // An example trajectory to follow. All units in meters.
            Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                
                List.of(
                    new Translation2d(1, 1), 
                    new Translation2d(2, -1)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(3, 0, new Rotation2d(0)),
            config);

            var thetaController = new ProfiledPIDController(
            AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
            thetaController.enableContinuousInput(-Math.PI, Math.PI);

            SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
            exampleTrajectory,
            m_robotDrive::getPose, // Functional interface to feed supplier
            DriveConstants.kDriveKinematics,

            // Position controllers
            new PIDController(AutoConstants.kPXController, 0, 0),
            new PIDController(AutoConstants.kPYController, 0, 0),
            thetaController,
            m_robotDrive::setModuleStates,
            m_robotDrive);

        // Reset odometry to the starting pose of the trajectory.
        m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return Commands.runOnce(
            () -> m_robotDrive.drive(0, 0, 0, false, false, false, false, false), m_robotDrive)
            .andThen(Commands.runOnce(() ->m_ArmMove.armMove(0, 0, 0), m_ArmMove))
            .andThen(swerveControllerCommand)
            .andThen(() -> m_ArmMove.armMoveTime(0, 0, 1, 5))
            .andThen(/*next */)
            .andThen(Commands.runOnce(() -> m_robotDrive.drive(0, 0, 0, false, false, false, false, false), m_robotDrive))
            .andThen(Commands.runOnce(() -> m_ArmMove.armMove(0, 0, 0), m_ArmMove));

        }
        
    }

    public static class Auto1{ //Auto1

        public static Command auto1(DriveSubsystem m_robotDrive, ArmSubsystem m_ArmMove){
            Trajectory driveJSON = GetTrajectory.get("C:\\Users\\tamal\\OneDrive\\Desktop\\Swerve-2024-1.0\\src\\main\\deploy\\paths\\Test.wpilib.json");

            var thetaController = new ProfiledPIDController(
            AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
            thetaController.enableContinuousInput(-Math.PI, Math.PI);

            SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
            driveJSON,
            m_robotDrive::getPose, // Functional interface to feed supplier
            DriveConstants.kDriveKinematics,

            // Position controllers
            new PIDController(AutoConstants.kPXController, 0, 0),
            new PIDController(AutoConstants.kPYController, 0, 0),
            thetaController,
            m_robotDrive::setModuleStates,
            m_robotDrive);
            

            return Commands.runOnce(
                () -> m_robotDrive.drive(0, 0, 0, false, false, false, false, false), m_robotDrive)
                .andThen(Commands.runOnce(() ->m_ArmMove.armMove(0, 0, 0), m_ArmMove))
                .andThen(swerveControllerCommand)
                .andThen(() -> m_ArmMove.armMoveTime(0, 0, 1, 5))
                .andThen(/*next */)
                .andThen(Commands.runOnce(() -> m_robotDrive.drive(0, 0, 0, false, false, false, false, false), m_robotDrive))
                .andThen(Commands.runOnce(() -> m_ArmMove.armMove(0, 0, 0), m_ArmMove));
        }
    }
}
