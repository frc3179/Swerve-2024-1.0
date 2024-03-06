package frc.robot.Commands.AutoCommands;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.TrackingSubsystem;



public class DefaultTracking extends Command{
    ArmSubsystem m_ArmSubsystem;
    TrackingSubsystem m_TrackingSubsystem;
    Supplier<Double> ty;
    double shootSpeed = 0;
    Supplier<Double> encoder;
    Supplier<Boolean> override;
    SlewRateLimiter limiter;

    public DefaultTracking(
        ArmSubsystem m_ArmSubsystem, 
        TrackingSubsystem m_TrackingSubsystem,
        Supplier<Double> ty,
        Supplier<Double> encoder,
        double shootSpeed,
        Supplier<Boolean> override){
        
        this.ty = ty;
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.m_TrackingSubsystem = m_TrackingSubsystem;
        this.shootSpeed = shootSpeed;
        this.encoder = encoder;
        this.override = override;
        addRequirements(m_ArmSubsystem, m_TrackingSubsystem);
    }

    @Override
    public void initialize(){
        limiter = new SlewRateLimiter(1/0.75);
    }

    @Override
    public void execute(){
        if(this.override.get() == true){
            if (ty.get() > -4) {
                double angle = m_TrackingSubsystem.limelightToAngle(this.ty.get());
                angle = m_TrackingSubsystem.angleToRotations(angle);

                m_ArmSubsystem.armMove((this.encoder.get() - angle)*20, limiter.calculate(this.shootSpeed), 0);
            }
        } else{
            m_ArmSubsystem.armMove(0, 0, 0);
        }
    }
    
    @Override
    public void end(boolean interrupted){
        
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}