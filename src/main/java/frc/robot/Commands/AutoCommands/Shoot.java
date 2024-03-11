package frc.robot.Commands.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShootingSubsystem;

public class Shoot extends Command{
    private final double speed;
    ShootingSubsystem m_shoot;

    Timer sTimer = new Timer();

    public Shoot(ShootingSubsystem m_shoot, double speed){
        this.speed = speed;
        this.m_shoot = m_shoot;
        addRequirements(m_shoot);
    }

    @Override
    public void initialize(){
        sTimer.restart();
        m_shoot.ShootMove(speed, 0);
        m_shoot.ShootMove(0, 0);
    }

    @Override
    public void execute(){
        m_shoot.ShootMove(speed, -1);

    }
    
    @Override
    public void end(boolean interrupted){
        m_shoot.ShootMove(0,0);
    }

    @Override
    public boolean isFinished(){
        if (sTimer.get() < 0.75){
            return false;
        }
        return true;
    }
}
