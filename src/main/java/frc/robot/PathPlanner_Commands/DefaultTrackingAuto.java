package frc.robot.PathPlanner_Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Utils.TrackingUtils;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;



public class DefaultTrackingAuto extends Command{
    ArmSubsystem m_ArmSubsystem;
    Supplier<Double> ty;
    double shootSpeed = 0;
    Supplier<Double> encoder;
    double speed;
    double angle;
    IntakeSubsystem m_IntakeSubsystem;
    ShootSubsystem m_Shoot;

    public DefaultTrackingAuto(
        ArmSubsystem m_ArmSubsystem,
        IntakeSubsystem m_Intake,
        ShootSubsystem m_Shoot,
        Supplier<Double> ty,
        Supplier<Double> encoder,
        double shootSpeed
        ){
        
        this.ty = ty;
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.shootSpeed = shootSpeed;
        this.encoder = encoder;
        this.m_IntakeSubsystem = m_Intake;
        this.m_Shoot = m_Shoot;

        addRequirements(m_ArmSubsystem, m_IntakeSubsystem, m_Shoot);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        double y = ty.get();
        if (y > -4) {
            angle = TrackingUtils.limelightToAngle(y);
            angle = TrackingUtils.angleToRotations(angle);

            speed = (this.encoder.get() - angle)*20;
            
            m_ArmSubsystem.armMove(speed > 0 ? speed/2 : speed/4);
        }
    }
    
    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0);
        m_IntakeSubsystem.intakeMove(0);
        m_Shoot.shootMove(0);
    }

    @Override
    public boolean isFinished(){
        
        if(this.encoder.get() > angle-0.0008 && this.encoder.get() < angle+0.0008){
            return true;

        }
        return false;
    }
}