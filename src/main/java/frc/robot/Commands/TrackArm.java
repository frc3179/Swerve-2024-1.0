package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.TrackingConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.TrackingSubsystem;

public class TrackArm extends Command{

    ArmSubsystem m_ArmSubsystem;
    DriveSubsystem m_DriveSubsystem;
    TrackingSubsystem m_TrackingSubsystem;
    double rotations;
    Supplier<Double> limeLightY;
    Supplier<Double> encoder;

    public TrackArm(ArmSubsystem m_ArmSubsystem, DriveSubsystem m_DirveSubsystem, TrackingSubsystem m_TrackingSubsystem, Supplier<Double> limeLightY, Supplier<Double> encoder){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.m_DriveSubsystem = m_DirveSubsystem;
        this.m_TrackingSubsystem = m_TrackingSubsystem;
        this.limeLightY = limeLightY;
        this.encoder = encoder;
        addRequirements(m_ArmSubsystem, m_DirveSubsystem, m_TrackingSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
        SmartDashboard.putBoolean("Done Track", false);
        this.rotations = m_TrackingSubsystem.angleToRotations(m_TrackingSubsystem.limelightToAngle(SmartDashboard.getNumber("Limelight ty", 0)));
    }

    @Override
    public void execute(){
        SmartDashboard.putNumber("Goal value", rotations);

        if(this.encoder.get() > this.rotations){
            m_ArmSubsystem.armMove(this.encoder.get()/1.5, 0, 0);
        } else if(this.encoder.get() < this.rotations){
            m_ArmSubsystem.armMove(-this.encoder.get()/1.5, 0, 0);
        }
    }
    
    @Override
    public void end(boolean interrupted){
        SmartDashboard.putBoolean("Done Track", true);
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
    }

    @Override
    public boolean isFinished(){
        if(this.encoder.get() < this.rotations+TrackingConstants.kRotationOffsetTrack && this.encoder.get() > this.rotations-TrackingConstants.kRotationOffsetTrack){
            return true;
        }
        return false;
    }
}
