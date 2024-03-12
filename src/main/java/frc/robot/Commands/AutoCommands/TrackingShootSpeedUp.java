package frc.robot.Commands.AutoCommands;

import edu.wpi.first.math.filter.SlewRateLimiter;
//import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShootingSubsystem;

public class TrackingShootSpeedUp extends Command{
    private final double speed;
    Timer aTimer = new Timer();
    private double speedUp;
    ShootingSubsystem m_shoot;
    SlewRateLimiter limiter;

    public TrackingShootSpeedUp(ShootingSubsystem m_shoot, double speed){
        this.speed = speed;
        this.m_shoot = m_shoot;
        addRequirements(m_shoot);
    }

    @Override
    public void initialize(){
        aTimer.restart();
    }

    @Override
    public void execute(){
        //SlewRateLimiter limiter = new SlewRateLimiter(1.0 / this.waitSeconds);
        //speedUp = limiter.calculate(this.speed);
        speedUp = (this.aTimer.get()/0.75)*this.speed+0.1;
        m_shoot.ShootMove(speedUp);
    }
    
    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
