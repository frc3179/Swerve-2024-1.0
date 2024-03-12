package frc.robot.Commands.AutoCommands;

import java.util.Timer;
import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.TrackingSubsystem;



public class DefaultTrackingAuto extends Command{
    ArmSubsystem m_ArmSubsystem;
    TrackingSubsystem m_TrackingSubsystem;
    Supplier<Double> ty;
    double shootSpeed = 0;
    Supplier<Double> encoder;
    Supplier<Boolean> override;
    SlewRateLimiter limiter;
    Supplier<Boolean> Shoot;
    double speed;
    double angle;

    public DefaultTrackingAuto(
        ArmSubsystem m_ArmSubsystem, 
        TrackingSubsystem m_TrackingSubsystem,
        Supplier<Double> ty,
        Supplier<Double> encoder,
        double shootSpeed,
        Supplier<Boolean> Shoot){
        
        this.ty = ty;
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.m_TrackingSubsystem = m_TrackingSubsystem;
        this.shootSpeed = shootSpeed;
        this.encoder = encoder;
        this.Shoot = Shoot;

        addRequirements(m_ArmSubsystem, m_TrackingSubsystem);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        double y = ty.get();
        if (y > -4) {
            angle = m_TrackingSubsystem.limelightToAngle(y);
            angle = m_TrackingSubsystem.angleToRotations(angle);

            speed = (this.encoder.get() - angle)*20;
            
            m_ArmSubsystem.armMove(speed > 0 ? speed/2 : speed/4);
        }
    }
    
    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0);
    }

    @Override
    public boolean isFinished(){
        
        if(this.encoder.get() > angle-0.0008 && this.encoder.get() < angle+0.0008){
            return true;

        }
        return false;
    }
}