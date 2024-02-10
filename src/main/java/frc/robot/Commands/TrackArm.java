package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;

public class TrackArm extends CommandBase{

    ArmSubsystem m_ArmSubsystem;
    double rotations;

    public TrackArm(ArmSubsystem m_ArmSubsystem/*needs for other stuff*/ ){
        this.m_ArmSubsystem = m_ArmSubsystem;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        this.rotations = m_ArmSubsystem.angleToRotations(m_ArmSubsystem.limelightToAngle());
        SmartDashboard.putBoolean("Done Track", false);
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
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
