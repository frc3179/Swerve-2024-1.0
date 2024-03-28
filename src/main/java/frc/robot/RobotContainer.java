// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Auto_Commands.ArmToEncoder;
import frc.robot.Auto_Commands.DefaultTracking;
import frc.robot.Auto_Commands.FeedShooter;
import frc.robot.Auto_Commands.ShootSpeedUp;
import frc.robot.Constants.OIConstants;
import frc.robot.Joystick_Commands.JoystickArm;
import frc.robot.Joystick_Commands.JoystickClimb;
import frc.robot.Joystick_Commands.JoystickDrive;
import frc.robot.Joystick_Commands.JoystickIntake;
import frc.robot.Joystick_Commands.JoystickShoot;
import frc.robot.PathPlanner_Commands.DefaultTrackingAuto;
import frc.robot.PathPlanner_Commands.Intake;
import frc.robot.PathPlanner_Commands.MoveArm;
import frc.robot.PathPlanner_Commands.RobotTrack;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class RobotContainer {
  private final ClimbSubsystem m_Climb = new ClimbSubsystem();
  private final DriveSubsystem m_Drive = new DriveSubsystem();
  private final IntakeSubsystem m_Intake = new IntakeSubsystem();
  private final ArmSubsystem m_Arm = new ArmSubsystem();
  private final ShootSubsystem m_Shoot = new ShootSubsystem();

  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
  Joystick m_armController = new Joystick(OIConstants.kArmControllerPort);

  private SendableChooser<Command> autoChooser;

  public RobotContainer() {
    configureAutoBindings();

    m_Drive.setDefaultCommand(
      new JoystickDrive(
        m_Drive, 
        () -> -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
        () -> -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
        () -> -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
        () -> m_driverController.getRawButton(4),
        () -> !m_driverController.getRightBumper(), 
        () -> !(Math.abs(m_driverController.getRightTriggerAxis())>=0.36), //rate limit
        () -> m_driverController.getAButton(),
        () -> (Math.abs(m_driverController.getLeftTriggerAxis())>=0.31),
        () -> m_driverController.getLeftBumper()
      )
    );

    m_Arm.setDefaultCommand(
      new JoystickArm(
        m_Arm, 
        () -> m_armController.getRawAxis(1))
    );

    m_Climb.setDefaultCommand(
      new JoystickClimb(
        m_Climb, 
        () -> m_armController.getRawButton(7)?1.0:0.0,
        () -> m_armController.getRawButton(8)
      )
    );

    m_Shoot.setDefaultCommand(
      new JoystickShoot(
        m_Shoot,
        () -> m_armController.getRawButton(6)?1.0:0.0
      )
    );

    m_Intake.setDefaultCommand(
      new JoystickIntake(
        m_Intake, 
        () -> m_armController.getRawButton(2)?-0.4:0.0,
        () -> m_armController.getRawButton(9),
        () -> m_armController.getRawButton(4)
        )
    );

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    //Tracking
    new JoystickButton(m_armController, 11)
      .whileTrue(
        new ParallelCommandGroup(
          new DefaultTracking(
            m_Arm, 
            () -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(ArmSubsystem.upDownEncoder.get()),
            () -> ArmSubsystem.upDownEncoder.get()
          ),
          new ShootSpeedUp(
            m_Shoot, 
            1
          )
        )
    );

    //feed Shoot
    new JoystickButton(m_armController, 1)
      .whileTrue(
        new FeedShooter(
          m_Intake
        )
    );

    //X wheels
    new JoystickButton(m_driverController, 3)
      .whileTrue(
        new RunCommand(
          () -> m_Drive.setX(), 
          m_Drive
        )
      );
    
    //Arm Start Auto
    new JoystickButton(m_armController, 5)
      .onTrue(
        new ArmToEncoder(
          m_Arm, 
          0.177
        )
      );

    //Arm to Speaker preset
    new JoystickButton(m_armController, 3)
      .onTrue(
        new ArmToEncoder(m_Arm, 0.34)
      );
  }

  private void configureAutoBindings() {
    //*NOTE: KINDA OLD AUTO COMMANDS
    //TODO: Make Newer and better
    NamedCommands.registerCommand("Move Arm", new MoveArm(m_Arm, m_Shoot, m_Intake, 0.335));
    NamedCommands.registerCommand("Reset Arm", new MoveArm(m_Arm, m_Shoot, m_Intake, 0.38).withTimeout(1));
    NamedCommands.registerCommand("Intake", new Intake(m_Intake).withTimeout(3.5));

    //Track Arm 
    NamedCommands.registerCommand(
      "Track Arm",
      new DefaultTrackingAuto(m_Arm, m_Intake, m_Shoot, () -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0),
      () -> ArmSubsystem.upDownEncoder.get(), 1).withTimeout(1)
    );
    //Shoot
    NamedCommands.registerCommand(
      "Shoot", 
      new SequentialCommandGroup(
        new ShootSpeedUp(m_Shoot, 1).withTimeout(0.75),
        new FeedShooter(m_Intake).withTimeout(0.3)
      ).withTimeout(1.05)
    );

    //TODO:
    NamedCommands.registerCommand("Track April Tag", new RobotTrack(m_Drive));

    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Choosher", autoChooser);
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
