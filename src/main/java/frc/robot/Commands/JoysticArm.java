package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OIConstants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimbingSubsystem;
import frc.robot.subsystems.TrackingSubsystem;

public class JoysticArm extends Command{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final ClimbingSubsystem m_climber;
    private final Supplier<Double> upDownSpeed;
    private Supplier<Boolean> intakespeed;
    private Supplier<Boolean> invertintake;
    private Supplier<Boolean> climberSpeed;
    private Supplier<Boolean> climberInvert;
    private Supplier<Boolean> OverRideIntakeCheck;
    Supplier<Boolean> Shoot;

    TrackingSubsystem m_TrackingSubsystem;
    Supplier<Double> ty;
    Supplier<Double> encoder;
    double shootSpeed;
    Supplier<Boolean> override;
    
    SlewRateLimiter limiter;

    public JoysticArm(
        ArmSubsystem m_ArmSubsystem, 
        ClimbingSubsystem m_climber, 
        Supplier<Double> upDownSpeed, 
        Supplier<Boolean> intakespeed, 
        Supplier<Boolean> invertintake, 
        Supplier<Boolean> climbSpeed, 
        Supplier<Boolean> climbInvert,
        Supplier<Boolean> OverRideIntakeCheck,
        Supplier<Boolean> Shoot,
        //Tracking
        TrackingSubsystem m_TrackingSubsystem,
        Supplier<Double> ty,
        Supplier<Double> encoder,
        double shootSpeed,
        Supplier<Boolean> override){

        this.m_ArmSubsystem = m_ArmSubsystem;
        this.intakespeed = intakespeed;
        this.upDownSpeed = upDownSpeed;
        this.invertintake = invertintake;
        this.m_climber = m_climber;
        this.climberSpeed = climbSpeed;
        this.climberInvert = climbInvert;
        this.OverRideIntakeCheck = OverRideIntakeCheck;
        this.Shoot = Shoot;

        this.m_TrackingSubsystem = m_TrackingSubsystem;
        this.ty = ty;
        this.encoder = encoder;
        this.shootSpeed = shootSpeed;
        this.override = override;

        

        addRequirements(m_ArmSubsystem, m_climber, m_TrackingSubsystem);
    }

    @Override
    public void initialize(){
       limiter = new SlewRateLimiter(1/0.75);
    }

    @Override
    public void execute() {
            double intakeSpeed = intakespeed.get() ? 0.4 : 0; 
            if (OverRideIntakeCheck.get()) {
                intakeSpeed = intakeSpeed;
            } else {
                intakeSpeed = m_ArmSubsystem.intakeCheck(RobotContainer.m_colorSensor.getIR(), intakeSpeed);
            }
            double invert = invertintake.get() ? 1 : -1;
            m_ArmSubsystem.armMove(Math.abs(upDownSpeed.get()) > OIConstants.kArmDeadband ? upDownSpeed.get() : 0, 0, invert * intakeSpeed);
    
            double climbSpeed = climberInvert.get() ? -1 : 1;
            climbSpeed *= climberSpeed.get() ? -1 : 0;
            m_climber.climbMove(climbSpeed);
        
    
        if(this.override.get() == true) {
            if (ty.get() > -4) {
                double angle = m_TrackingSubsystem.limelightToAngle(this.ty.get());
                angle = m_TrackingSubsystem.angleToRotations(angle);

                m_ArmSubsystem.armMove((this.encoder.get() - angle)*20, limiter.calculate(this.shootSpeed), Shoot.get()? -1:0);
            }
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
