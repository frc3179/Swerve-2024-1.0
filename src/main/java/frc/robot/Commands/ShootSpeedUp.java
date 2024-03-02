package frc.robot.Commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class ShootSpeedUp extends Command{
    private final ArmSubsystem m_ArmSubsystem;
    private final DriveSubsystem m_DriveSubsystem;
    private final double speed;
    Timer aTimer = new Timer();
    private final double waitSeconds;
    private double speedUp;

    public ShootSpeedUp(ArmSubsystem m_ArmSubsystem, DriveSubsystem m_DriveSubsystem, double speed, double waitSeconds){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.speed = speed;
        this.waitSeconds = waitSeconds;
        this.m_DriveSubsystem = m_DriveSubsystem;
        addRequirements(m_ArmSubsystem, m_DriveSubsystem);
    }

    @Override
    public void initialize(){
        //m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        aTimer.restart();
    }

    @Override
    public void execute(){
        SlewRateLimiter limiter = new SlewRateLimiter(1.0 / this.waitSeconds);
        //speedUp = limiter.calculate(this.speed);
        speedUp = (this.aTimer.get()/waitSeconds)*this.speed+0.1;
        m_ArmSubsystem.armMove(0, speedUp, 0);

    }
    
    @Override
    public void end(boolean interrupted){
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
        m_ArmSubsystem.armMove(0, 0, 0);
    }

    @Override
    public boolean isFinished(){
        if(aTimer.get() < this.waitSeconds){
            return false;
        }
        return true;
    }
}
