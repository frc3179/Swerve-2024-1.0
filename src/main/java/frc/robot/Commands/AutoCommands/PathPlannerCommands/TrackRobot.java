
package frc.robot.Commands.AutoCommands.PathPlannerCommands;

import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class TrackRobot extends Command{

    DriveSubsystem m_DriveSubsystem;
    double tx;
    

    public TrackRobot(DriveSubsystem m_DirveSubsystem){
        this.m_DriveSubsystem = m_DirveSubsystem;
        addRequirements(m_DirveSubsystem);
    }

    @Override
    public void initialize(){
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
    }

    @Override
    public void execute(){
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0)/120.0;
        System.out.println(tx);
        m_DriveSubsystem.drive(0, 0, -tx, false, false, false, false, false);
    }
    
    @Override
    public void end(boolean interrupted){
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
    }

    @Override
    public boolean isFinished(){
        if (Math.abs(tx*120) < 2 && tx*120 != 0){
            return true;
        }
        return false;
    }
}
