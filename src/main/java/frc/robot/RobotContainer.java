// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OIConstants;
import frc.robot.autos.AutoList;
import frc.robot.autos.PickAuto;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimbingSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Commands.*;

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

  // The driver's controller
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
  Joystick m_armController = new Joystick(OIConstants.kArmControllerPort);

  //auto object
  PickAuto m_pickauto = new PickAuto();

  //colorsensor object
  final static ColorSensorV3 m_colorSensor = new ColorSensorV3(Constants.ColorSensorConstants.kColorSensorPort);
  

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
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
    

    m_ArmMove.setDefaultCommand(
      new JoysticArm(
        m_ArmMove, 
        m_climber, 
        () -> m_armController.getRawAxis(1), 
        () ->  m_armController.getRawButton(2), 
        () -> m_armController.getRawButton(9), 
        () -> m_armController.getRawButton(7), 
        () -> m_armController.getRawButton(8))
    );

    
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
    
    // Path Weaver
    new JoystickButton(m_driverController, 5) //left bumper
        .whileTrue(new RunCommand(
            () -> AutoList.Auto1.auto1(m_robotDrive, m_ArmMove), 
            m_robotDrive));
    
    // shoot
    new JoystickButton(m_armController, 1).onTrue(new Shoot(m_ArmMove, m_robotDrive, 1, 1.5)); //button placeholder
    
    // track arm
    new JoystickButton(m_armController, 11).onTrue(
      new SequentialCommandGroup(
        new TrackArm(m_ArmMove, m_robotDrive, () -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0)), 
        new Shoot(m_ArmMove, m_robotDrive, 1, 1),
        new WaitSec(m_ArmMove, m_robotDrive, 0.5)
        )
    );

    // move arm to auto start
    new JoystickButton(m_armController, 5).onTrue(new ArmMoveRotations(m_ArmMove, m_robotDrive, 0.177));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_pickauto.run(m_robotDrive, m_ArmMove);
  }
}
