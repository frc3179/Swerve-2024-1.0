package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.DriveSubsystem;

public class RobotRotAngle extends CommandBase{
    
    private final DriveSubsystem m_DriveSubsystem;
    Timer aTimer = new Timer();
    private final double angle;
    Supplier<Double> GyroAngle;

    public RobotRotAngle(DriveSubsystem m_DriveSubsystem, double angle, Supplier<Double> sdGryoAngle){
        this.m_DriveSubsystem = m_DriveSubsystem;
        this.angle = angle;
        this.GyroAngle = sdGryoAngle;
        addRequirements(m_DriveSubsystem);
    }

    @Override
    public void initialize(){
        new RunCommand(() -> m_DriveSubsystem.drive(0, 0, 0, false, true, true, false, false), m_DriveSubsystem);
    }

    @Override
    public void execute(){
        while (this.GyroAngle.get() < this.angle){
            m_DriveSubsystem.drive(0, 0, 0.5, false, true, true, false, false);
        }
    }
    
    @Override
    public void end(boolean interrupted){
        new RunCommand(() -> m_DriveSubsystem.drive(0, 0, 0, false, true, true, false, false), m_DriveSubsystem);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
