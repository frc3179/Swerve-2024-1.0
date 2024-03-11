// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ColorSensorConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimbingSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShootingSubsystem;
import frc.robot.subsystems.TrackingSubsystem;
import frc.robot.Commands.*;
import frc.robot.Commands.AutoCommands.ArmMoveRotations;
import frc.robot.Commands.AutoCommands.DefaultTracking;
import frc.robot.Commands.AutoCommands.DefaultTrackingAuto;
import frc.robot.Commands.AutoCommands.MoveArm;
import frc.robot.Commands.AutoCommands.RotateRobot;
import frc.robot.Commands.AutoCommands.Shoot;
import frc.robot.Commands.AutoCommands.ShootSpeedUp;
import frc.robot.Commands.AutoCommands.PathPlannerCommands.PathIntake;
import frc.robot.Commands.AutoCommands.PathPlannerCommands.TrackRobot;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final ArmSubsystem m_ArmMove = new ArmSubsystem();
  private final ClimbingSubsystem m_climber = new ClimbingSubsystem();
  private final TrackingSubsystem m_TrackingSubsystem = new TrackingSubsystem();
  private final ShootingSubsystem m_shoot = new ShootingSubsystem();

  // The driver's controller
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
  Joystick m_armController = new Joystick(OIConstants.kArmControllerPort);

  //colorsensor object
  public static DigitalInput m_IR = new DigitalInput(ColorSensorConstants.kColorSensorPort);

  
 private final SendableChooser<Command> autoChooser;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    /* 
    TODO:Auto Commands
      *[x] - Rotate robot to 0 deg
      *[x] - Track April tag
      *[x] - Move arm down to intake Turn intake on, then drive forward slowly until note it deteced by color sensor
      *[x] - Track Arm
      *[x] - Shoot 
      *[] - Move arm down to ground
 
    */
    //*DONE:
    NamedCommands.registerCommand("new shoot", new MoveArm(m_ArmMove, m_shoot, 0.315).withTimeout(2.5));
    //Track Arm 
    NamedCommands.registerCommand(
      "Track Arm", 
      new DefaultTrackingAuto(m_ArmMove, m_TrackingSubsystem, () -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0),
       () -> ArmSubsystem.upDownEncoder.get(), 1, null).withTimeout(2.5)
    );
    //Shoot
    NamedCommands.registerCommand(
      "Shoot", 
      new SequentialCommandGroup(
        new ShootSpeedUp(m_shoot, 1, 1.5),
        new Shoot(m_shoot, 1)
      )
    );
    //Intake+Arm Move+Until Color Sensor
    NamedCommands.registerCommand( 
      "Intake",
      new PathIntake(
        m_ArmMove, 
        m_shoot,
        () -> m_IR.get(), 
        () -> ArmSubsystem.upDownEncoder.get()
        )
    );
    //Robot Rotate to 0 deg
    NamedCommands.registerCommand(
      "Reset Robot Rot", 
      new RotateRobot(
        m_robotDrive, 
        0, 
        0.1
      )
    );
    //Robot Rotate to 55 deg
    NamedCommands.registerCommand(
      "Robot Rot 55", 
      new RotateRobot(
        m_robotDrive, 
        55, 
        0.1
      )
    );
    //Robot Rotate to 55 deg
    NamedCommands.registerCommand(
      "Robot Rot-45", 
      new RotateRobot(
        m_robotDrive, 
        30, 
        0.1
      )
    );
    //Robot Rotate to 55 deg
    NamedCommands.registerCommand(
      "Robot Rot TOP", 
      new RotateRobot(
        m_robotDrive, 
        -40, 
        0.1
      )
    );
    //Robot Rotate to 305 deg
    NamedCommands.registerCommand(
      "Robot Rot 305", 
      new RotateRobot(
        m_robotDrive, 
        305, 
        0.1
      )
    );
    //Track April Tag
    NamedCommands.registerCommand(
      "Track April Tag", 
      new TrackRobot(
        m_robotDrive
      )
    );

    

    // Build an auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();

    // Another option that allows you to specify the default auto by its name
    // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

    SmartDashboard.putData("Auto Chooser", autoChooser);

    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                m_driverController.getRawButton(4),
                !m_driverController.getRightBumper(), 
                !(Math.abs(m_driverController.getRightTriggerAxis())>=0.36), //rate limit
                m_driverController.getAButton(),
                (Math.abs(m_driverController.getLeftTriggerAxis())>=0.31)), //half speed
            m_robotDrive));
    

            /* ArmSubsystem m_ArmSubsystem, 
        ClimbingSubsystem m_climber, 
        Supplier<Double> upDownSpeed, 
        Supplier<Boolean> intakespeed, 
        Supplier<Boolean> invertintake, 
        Supplier<Boolean> climbSpeed, 
        Supplier<Boolean> climbInvert,
        Supplier<Boolean> OverRideIntakeCheck,
        //Tracking
        TrackingSubsystem m_TrackingSubsystem,
        Supplier<Double> ty,
        Supplier<Double> encoder,
        double shootSpeed,
        Supplier<Boolean> override */

        /*
         * ArmSubsystem m_ArmSubsystem, 
        ClimbingSubsystem m_climber,
        Supplier<Double> upDownSpeed, 
        Supplier<Boolean> climbSpeed, 
        Supplier<Boolean> climbInvert
         */
    m_ArmMove.setDefaultCommand(
      new JoysticArm(
        m_ArmMove, 
        m_climber,
        () -> m_armController.getRawAxis(1), 
        () -> m_armController.getRawButton(7), 
        () -> m_armController.getRawButton(8)
        )
    );
   
   /* m_TrackingSubsystem.setDefaultCommand(
      new DefaultTracking(
        m_ArmMove, 
        m_TrackingSubsystem, 
        () -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0), 
        () -> ArmSubsystem.upDownEncoder.get(), 
        1,
        () -> m_armController.getRawButton(10),
        () -> !m_armController.getRawButton(11)
        )
        
    ); */
    
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {

    // X wheels
    new JoystickButton(m_driverController, 3)
        .whileTrue(new RunCommand(
            () -> m_robotDrive.setX(),
            m_robotDrive));
    
    // shoot
    new JoystickButton(m_armController, 1).onTrue(
      new SequentialCommandGroup(
        new ShootSpeedUp(m_shoot, 1, 1.5),
        new Shoot(m_shoot, 1)
      )
    );

    new JoystickButton(m_armController, 6).onTrue(
      new SequentialCommandGroup(
        new ShootSpeedUp(m_shoot, 1, 0.75),
        new Shoot(m_shoot, 0.5)
      )
    );

    //Intake
    new JoystickButton(m_armController, 2)
      .whileTrue(
        new Intake(m_shoot, () -> true,() -> m_armController.getRawButton(4), () -> m_armController.getRawButton(9))
    );

    // track arm
    new JoystickButton(m_armController, 11)
      .whileTrue(
        new DefaultTracking(
          m_ArmMove, 
          m_TrackingSubsystem, 
          () -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0),
          () -> ArmSubsystem.upDownEncoder.get(),
          1, 
          () -> m_armController.getRawButton(1)
          )
    );

    // move arm to auto start
    new JoystickButton(m_armController, 5).onTrue(new ArmMoveRotations(m_ArmMove, m_robotDrive, 0.177, ()->ArmSubsystem.upDownEncoder.getDistance()));
    new JoystickButton(m_armController, 3).onTrue(new ArmMoveRotations(m_ArmMove, m_robotDrive, 0.15, ()->ArmSubsystem.upDownEncoder.getDistance()));

    //Joystick button to rotate robot to 0 deg
    new JoystickButton(m_driverController, 5).onTrue(new RotateRobot(m_robotDrive, 0, 0.2));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //?   JD added
    return autoChooser.getSelected();
  }


}
 