
package frc.robot.Commands.AutoCommands.PathPlannerCommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class TrackRobot extends Command{

    DriveSubsystem m_DriveSubsystem;
    Supplier<Double> tx;

    public TrackRobot(DriveSubsystem m_DirveSubsystem, Supplier<Double> tx){
        this.m_DriveSubsystem = m_DirveSubsystem;
        addRequirements(m_DirveSubsystem);
    }

    @Override
    public void initialize(){
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
    }

    @Override
    public void execute(){
        m_DriveSubsystem.drive(0, 0, -tx.get()/75, false, false, false, false, false);
    }
    
    @Override
    public void end(boolean interrupted){
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
    }

    @Override
    public boolean isFinished(){
        if (Math.abs(tx.get()) < 0.5){
            return true;
        }
        return false;
    }
}
