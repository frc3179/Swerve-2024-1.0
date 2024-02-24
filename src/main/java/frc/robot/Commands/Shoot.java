package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class Shoot extends Command{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final DriveSubsystem m_DriveSubsystem;
    private final double speed;
    Timer aTimer = new Timer();
    private final double waitSeconds;

    public Shoot(ArmSubsystem m_ArmSubsystem, DriveSubsystem m_DriveSubsystem, double speed, double waitSeconds){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.speed = speed;
        this.waitSeconds = waitSeconds;
        this.m_DriveSubsystem = m_DriveSubsystem;
        addRequirements(m_ArmSubsystem, m_DriveSubsystem);
    }

    @Override
    public void initialize(){
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        aTimer.restart();
        while (aTimer.get() < waitSeconds){
            m_ArmSubsystem.armMove(0, speed, 0); // speed up
        }
    }

    @Override
    public void execute(){
        aTimer.restart();
        while(aTimer.get() < 1){
            m_ArmSubsystem.armMove(0, speed, -1);
        }
    }
    
    @Override
    public void end(boolean interrupted){
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
        m_ArmSubsystem.armMove(0, 0, 0);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
