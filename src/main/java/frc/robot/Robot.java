// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Subsystems.ArmSubsystem;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    // limelight values
    SmartDashboard.putNumber("Limelight tx", NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
    SmartDashboard.putNumber("Limelight ty", NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0));
    SmartDashboard.putNumber("Limelight ta", NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0));

    double limelightY = SmartDashboard.getNumber("Limelight ty", 0);
    SmartDashboard.putNumber("Distance", (0.00425644*(limelightY*limelightY))-(0.188139*limelightY)+3.49207);

    //color sensor values
    // Color detectedColor = RobotContainer.m_colorSensor.getColor();
    //boolean IR = RobotContainer.m_IR.get();
    // SmartDashboard.putNumber("Color Red", detectedColor.red);
    // SmartDashboard.putNumber("Color Green", detectedColor.green);
    // SmartDashboard.putNumber("Color Blue", detectedColor.blue);
    //SmartDashboard.putBoolean("IR new", IR);

    //Arm Encoder
    SmartDashboard.putNumber("Arm Encoder", ArmSubsystem.upDownEncoder.getDistance());

    //SmartDashboard.putNumber("Climbing Encoder Position", ClimbingSubsystem.climbEncoder.getPosition());

    RobotController.setBrownoutVoltage(5.0);
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
