package frc.robot.Commands.AutoCommands;

import edu.wpi.first.math.filter.SlewRateLimiter;
//import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShootingSubsystem;

public class ShootSpeedUp extends Command{
    private final double speed;
    Timer aTimer = new Timer();
    private final double waitSeconds;
    private double speedUp;
    ShootingSubsystem m_shoot;
    SlewRateLimiter limiter;

    public ShootSpeedUp(ShootingSubsystem m_shoot, double speed, double waitSeconds){
        this.speed = speed;
        this.waitSeconds = waitSeconds;
        this.m_shoot = m_shoot;
        addRequirements(m_shoot);
    }

    @Override
    public void initialize(){
        aTimer.restart();
        limiter = new SlewRateLimiter(1/waitSeconds);
    }

    @Override
    public void execute(){
        //SlewRateLimiter limiter = new SlewRateLimiter(1.0 / this.waitSeconds);
        //speedUp = limiter.calculate(this.speed);
        speedUp = (this.aTimer.get()/waitSeconds)*this.speed+0.1;
        m_shoot.ShootMove(speedUp, 0);

    }
    
    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        if(aTimer.get() < this.waitSeconds){
            return false;
        }
        return true;
    }
}
