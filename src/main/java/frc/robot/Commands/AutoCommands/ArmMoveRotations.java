package frc.robot.Commands.AutoCommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.TrackingConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class ArmMoveRotations extends Command{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final DriveSubsystem m_DriveSubsystem;
    private double angle;
    boolean done;
    Supplier<Double> encoder;

    public ArmMoveRotations(ArmSubsystem m_ArmSubsystem, DriveSubsystem m_DriveSubsystem, double angle, Supplier<Double> encoder){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.angle = angle;
        this.m_DriveSubsystem = m_DriveSubsystem;
        this.encoder = encoder;
        addRequirements(m_ArmSubsystem, m_DriveSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0,0,0);
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
    }

    @Override
    public void execute(){
        if(this.encoder.get() > this.angle){
            m_ArmSubsystem.armMove(0.15, 0, 0);
        } else if(this.encoder.get() < this.angle){
            m_ArmSubsystem.armMove(-0.15, 0, 0);
        }
    }

    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0,0,0);
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
    }

    @Override
    public boolean isFinished(){
        if(this.encoder.get() < this.angle+TrackingConstants.kRotationOffsetTrack && this.encoder.get() > this.angle-TrackingConstants.kRotationOffsetTrack){
            return true;
        }
        return false;
    }
}
