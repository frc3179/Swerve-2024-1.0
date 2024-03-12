package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimbingSubsystem;

public class JoysticArm extends Command{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final ClimbingSubsystem m_climber;
    private final Supplier<Double> upDownSpeed;
    private Supplier<Boolean> climberSpeed;
    private Supplier<Boolean> climberInvert;
    
    SlewRateLimiter limiter;

    public JoysticArm(
        ArmSubsystem m_ArmSubsystem, 
        ClimbingSubsystem m_climber,
        Supplier<Double> upDownSpeed, 
        Supplier<Boolean> climbSpeed, 
        Supplier<Boolean> climbInvert){

        this.m_ArmSubsystem = m_ArmSubsystem;
        this.upDownSpeed = upDownSpeed;
        this.m_climber = m_climber;
        this.climberSpeed = climbSpeed;
        this.climberInvert = climbInvert;
        addRequirements(m_ArmSubsystem, m_climber);
    }

    @Override
    public void initialize(){
       limiter = new SlewRateLimiter(1/0.75);
    }

    @Override
    public void execute() {
        m_ArmSubsystem.armMove(Math.abs(upDownSpeed.get()) > OIConstants.kArmDeadband ? upDownSpeed.get() : 0);

        double climbSpeed = climberInvert.get() ? -1 : 1;
        climbSpeed *= climberSpeed.get() ? -1 : 0;
        m_climber.climbMove(climbSpeed);
    
        /* if(this.override.get() == true) {
            if (ty.get() > -4) {
                double angle = m_TrackingSubsystem.limelightToAngle(this.ty.get());
                angle = m_TrackingSubsystem.angleToRotations(angle);

                m_ArmSubsystem.armMove((this.encoder.get() - angle)*20, limiter.calculate(this.shootSpeed), Shoot.get()?-1:0);
            }
        } */
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}