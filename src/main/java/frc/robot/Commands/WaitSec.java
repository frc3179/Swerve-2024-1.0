
package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class WaitSec extends Command{

    ArmSubsystem m_ArmSubsystem;
    DriveSubsystem m_DriveSubsystem;
    double waitSec;
    Timer wTimer = new Timer();

    public WaitSec(ArmSubsystem m_ArmSubsystem, DriveSubsystem m_DirveSubsystem, double waitSec){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.m_DriveSubsystem = m_DirveSubsystem;
        this.waitSec = waitSec;
        addRequirements(m_ArmSubsystem, m_DirveSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0, 0, 0);
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
        this.wTimer.restart();
        SmartDashboard.putBoolean("Wait finsihed", false);
    }

    @Override
    public void execute(){

        SmartDashboard.putBoolean("Wait finsihed", false);
        SmartDashboard.putBoolean("Wait finsihed", true);
    }
    
    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0, 0, 0);
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
    }

    @Override
    public boolean isFinished(){
        if(this.wTimer.get() >= this.waitSec){
            return true;
        }
        return false;
    }
}
