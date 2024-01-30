package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class TrackArm extends CommandBase{

    ArmSubsystem m_ArmSubsystem;
    double rotations;
    boolean done;

    public TrackArm(ArmSubsystem m_ArmSubsystem/*needs for other stuff */){
        this.m_ArmSubsystem = m_ArmSubsystem;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        this.rotations = m_ArmSubsystem.angleToRotations(m_ArmSubsystem.limelightToAngle());
    }

    @Override
    public void execute(){
        this.done = m_ArmSubsystem.armMoveRotations(rotations);
    }

    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0, 0, 0); //reset
    }

    @Override
    public boolean isFinished(){
        return done;
    }
}
