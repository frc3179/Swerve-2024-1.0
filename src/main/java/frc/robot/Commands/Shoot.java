package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class Shoot extends CommandBase{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final double speed;
    Timer aTimer = new Timer();
    private final double waitSeconds;

    public Shoot(ArmSubsystem m_ArmSubsystem, double speed, double waitSeconds){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.speed = speed;
        this.waitSeconds = waitSeconds;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        aTimer.restart();
        while (aTimer.get() < waitSeconds){
            m_ArmSubsystem.armMove(0, speed, 0); // speed up
        }
    }

    @Override
    public void execute(){
        aTimer.restart();
        while(aTimer.get() < 0.4){
            m_ArmSubsystem.armMove(0, speed, -1);
        }
    }
    
    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0, 0, 0);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
