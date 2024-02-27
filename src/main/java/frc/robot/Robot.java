// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
//import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.ArmSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;


  //auto choser
  public static final String kDefaultAuto = "Default";
  public static final String kAuto1 = "Auto 1";
  public static String autoSelectedDashboard;
  public final static SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    m_chooser.setDefaultOption("Default", kDefaultAuto);
    m_chooser.addOption("Auto 1", kAuto1);
    SmartDashboard.putData("Auto picker", m_chooser);

    //other camera
    UsbCamera camera = CameraServer.startAutomaticCapture();
    //camera.setVideoMode(PixelFormat.kMJPEG, 180, 180, 30);

    


    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.

    // limelight values
    SmartDashboard.putNumber("Limelight tx", NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
    SmartDashboard.putNumber("Limelight ty", NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0));
    SmartDashboard.putNumber("Limelight ta", NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0));

    //color sensor values
    Color detectedColor = RobotContainer.m_colorSensor.getColor();
    double IR = RobotContainer.m_colorSensor.getIR();
    SmartDashboard.putNumber("Color Red", detectedColor.red);
    SmartDashboard.putNumber("Color Green", detectedColor.green);
    SmartDashboard.putNumber("Color Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);

    //Arm Encoder
    SmartDashboard.putNumber("Arm Encoder", ArmSubsystem.upDownEncoder.getDistance());

    //Tracking values (updated in ArmSubsystem.java)
    SmartDashboard.putNumber("Tracking Angle", ArmSubsystem.limelightToAngle(NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0)));

    RobotController.setBrownoutVoltage(5.0);

    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
