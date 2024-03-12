package frc.robot.Commands.AutoCommands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

public class FeedShoot extends Command{
    IntakeSubsystem m_intake;
    Timer timer = new Timer();
    double speed;
    public FeedShoot(IntakeSubsystem m_intake, double speed){
        this.m_intake = m_intake;
        this.speed = speed;
        addRequirements(m_intake);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        m_intake.IntakeMove(speed);
    }
    
    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
