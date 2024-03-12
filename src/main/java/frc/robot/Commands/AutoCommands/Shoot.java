package frc.robot.Commands.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

public class Shoot extends Command{
    private final double speed;
    ShootingSubsystem m_shoot;
    IntakeSubsystem m_intake;

    Timer sTimer = new Timer();

    public Shoot(ShootingSubsystem m_shoot, IntakeSubsystem m_intake, double speed){
        this.speed = speed;
        this.m_shoot = m_shoot;
        this.m_intake = m_intake;
        addRequirements(m_shoot, m_intake);
    }

    @Override
    public void initialize(){
        sTimer.restart();
        m_shoot.ShootMove(speed);
    }

    @Override
    public void execute(){
        m_shoot.ShootMove(speed);
        m_intake.IntakeMove(-1);
    }
    
    @Override
    public void end(boolean interrupted){
        m_shoot.ShootMove(0);
        m_intake.IntakeMove(0);
    }

    @Override
    public boolean isFinished(){
        if (sTimer.get() < 0.75){
            return false;
        }
        return true;
    }
}
