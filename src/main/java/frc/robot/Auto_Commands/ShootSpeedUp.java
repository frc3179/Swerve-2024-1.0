package frc.robot.Auto_Commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ShootSubsystem;

public class ShootSpeedUp extends Command{
    ShootSubsystem m_Shoot;
    double speed;
    Timer aTimer = new Timer();
    
    public ShootSpeedUp(
        ShootSubsystem m_Shoot, 
        double speed
        ) {

        this.m_Shoot = m_Shoot;
        this.speed = speed;

        addRequirements(m_Shoot);
    }

    @Override
    public void initialize(){
        aTimer.restart();
    }

    @Override
    public void execute(){
        double speedUp = (this.aTimer.get()/0.75)*this.speed+0.1;
        m_Shoot.shootMove(speedUp);
    }
    
    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
