package frc.robot.Commands.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class Shoot extends Command{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final DriveSubsystem m_DriveSubsystem;
    private final double speed;
    Timer aTimer = new Timer();

    public Shoot(ArmSubsystem m_ArmSubsystem, DriveSubsystem m_DriveSubsystem, double speed){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.speed = speed;
        this.m_DriveSubsystem = m_DriveSubsystem;
        addRequirements(m_ArmSubsystem, m_DriveSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0, speed,0);
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        aTimer.restart();
    }

    @Override
    public void execute(){
        m_ArmSubsystem.armMove(0, speed, -1);

    }
    
    @Override
    public void end(boolean interrupted){
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
        m_ArmSubsystem.armMove(0, 0, 0);
    }

    @Override
    public boolean isFinished(){
        if(aTimer.get() < 1){
            return false;
        }
        return true;
    }
}
