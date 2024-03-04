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
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Commands.AutoCommands.ArmMoveRotations;
import frc.robot.Commands.AutoCommands.MoveArm;
import frc.robot.Commands.AutoCommands.Shoot;
import frc.robot.Commands.AutoCommands.ShootSpeedUp;
import frc.robot.Commands.AutoCommands.TrackArm;
import frc.robot.Commands.AutoCommands.WaitSec;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.GetTrajectory;
import frc.robot.subsystems.TrackingSubsystem;

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
            //.andThen(() -> m_ArmMove.armMoveTime(0, 0, 1, 5))
            .andThen(/*next */)
            .andThen(Commands.runOnce(() -> m_robotDrive.drive(0, 0, 0, false, false, false, false, false), m_robotDrive))
            .andThen(Commands.runOnce(() -> m_ArmMove.armMove(0, 0, 0), m_ArmMove));

        }
        
    }

    public static class Auto1{ //Auto1

        public static Command auto1(DriveSubsystem m_robotDrive, ArmSubsystem m_ArmMove, TrackingSubsystem m_TrackingSubsystem){
            Trajectory drive1JSON = GetTrajectory.get("/home/lvuser/deploy/paths/output/Blue-Middle-1-2.wpilib.json");
            Trajectory drive2JSON = GetTrajectory.get("/home/lvuser/deploy/paths/output/Blue-_-1-2-2-1.wpilib.json");
            Trajectory drive2backJSON = GetTrajectory.get("/home/lvuser/deploy/paths/output/Blue-_-1-2-2-1-Back.wpilib.json");

            var thetaController = new ProfiledPIDController(
            AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
            thetaController.enableContinuousInput(-Math.PI, Math.PI);

            SwerveControllerCommand drive1 = new SwerveControllerCommand(
            drive1JSON,
            m_robotDrive::getPose, // Functional interface to feed supplier
            DriveConstants.kDriveKinematics,
            // Position controllers
            new PIDController(AutoConstants.kPXController, 0, 0),
            new PIDController(AutoConstants.kPYController, 0, 0),
            thetaController,
            m_robotDrive::setModuleStates,
            m_robotDrive);


            SwerveControllerCommand drive2 = new SwerveControllerCommand(
            drive2JSON,
            m_robotDrive::getPose, // Functional interface to feed supplier
            DriveConstants.kDriveKinematics,
            // Position controllers
            new PIDController(AutoConstants.kPXController, 0, 0),
            new PIDController(AutoConstants.kPYController, 0, 0),
            thetaController,
            m_robotDrive::setModuleStates,
            m_robotDrive);


            SwerveControllerCommand drive2back = new SwerveControllerCommand(
            drive2backJSON,
            m_robotDrive::getPose, // Functional interface to feed supplier
            DriveConstants.kDriveKinematics,
            // Position controllers
            new PIDController(AutoConstants.kPXController, 0, 0),
            new PIDController(AutoConstants.kPYController, 0, 0),
            thetaController,
            m_robotDrive::setModuleStates,
            m_robotDrive);


            return new SequentialCommandGroup(
                new MoveArm(m_ArmMove,0,0),
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                new TrackArm(m_ArmMove, m_robotDrive, m_TrackingSubsystem,()->NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0), () -> ArmSubsystem.upDownEncoder.get()),
                new WaitSec(m_ArmMove, m_robotDrive, 0.5),
                new ShootSpeedUp(m_ArmMove, m_robotDrive, 2, 1),
                new Shoot(m_ArmMove, m_robotDrive , 1), //shoot preloaded note

                new ParallelCommandGroup(
                    drive1
                    ), //dirve course 1 and reset arm position and intake note
                new ArmMoveRotations(m_ArmMove, m_robotDrive, 0.37, ()->SmartDashboard.getNumber(null, 0)),
                    
                new MoveArm(m_ArmMove, 0, 1),

                new TrackArm(m_ArmMove, m_robotDrive, m_TrackingSubsystem, () -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0), () -> ArmSubsystem.upDownEncoder.get()), //track for arm angle
                new ShootSpeedUp(m_ArmMove, m_robotDrive, 1, 1),
                new Shoot(m_ArmMove, m_robotDrive, 1), //shoot

                new ParallelCommandGroup(
                    drive2
                ), //drive course 2 and reset arm position and intake note
                    
                new ArmMoveRotations(m_ArmMove, m_robotDrive, 0.37, ()->ArmSubsystem.upDownEncoder.getDistance()),
                new MoveArm(m_ArmMove, 0, 1),

                //drive2back, //drive back from course 2
                new TrackArm(m_ArmMove, m_robotDrive, m_TrackingSubsystem, () -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0), () -> ArmSubsystem.upDownEncoder.get()),
                new ShootSpeedUp(m_ArmMove, m_robotDrive, 1, 1),
                new Shoot(m_ArmMove, m_robotDrive, 1),

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                new MoveArm(m_ArmMove, 0, 0)
            );
        }
    }
}
