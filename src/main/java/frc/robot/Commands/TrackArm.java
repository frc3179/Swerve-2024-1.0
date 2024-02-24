package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class TrackArm extends Command{

    ArmSubsystem m_ArmSubsystem;
    DriveSubsystem m_DriveSubsystem;
    double rotations;
    Supplier<Double> limeLightY;

    public TrackArm(ArmSubsystem m_ArmSubsystem, DriveSubsystem m_DirveSubsystem, Supplier<Double> limeLightY){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.m_DriveSubsystem = m_DirveSubsystem;
        this.limeLightY = limeLightY;
        addRequirements(m_ArmSubsystem, m_DirveSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
        SmartDashboard.putBoolean("Done Track", false);
        this.rotations = m_ArmSubsystem.angleToRotations(SmartDashboard.getNumber("Tracking Angle", 0));
    }

    @Override
    public void execute(){
        SmartDashboard.putNumber("Goal value", rotations);
        m_ArmSubsystem.armMoveRotations(rotations, ()->m_ArmSubsystem.upDownEncoder.getDistance());

        
    }
    
    @Override
    public void end(boolean interrupted){
        SmartDashboard.putBoolean("Done Track", true);
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        m_DriveSubsystem.drive(0, 0, 0, false, false, false, false, false);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
