
package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class TrackRobot extends Command{

    DriveSubsystem m_DriveSubsystem;
    ArmSubsystem m_ArmSubsystem;
    Supplier<Boolean> done;

    public TrackRobot(DriveSubsystem m_DirveSubsystem, ArmSubsystem m_ArmSubsystem, Supplier<Boolean> done){
        this.m_DriveSubsystem = m_DirveSubsystem;
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.done = done;
        addRequirements(m_DirveSubsystem, m_ArmSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0, 0, 0);
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
        SmartDashboard.putBoolean("Robot Track", false);
    }

    @Override
    public void execute(){
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, true, false);
    }
    
    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0, 0, 0);
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
    }

    @Override
    public boolean isFinished(){
        if(this.done.get()){
            return true;
        }
        return false;
    }
}
