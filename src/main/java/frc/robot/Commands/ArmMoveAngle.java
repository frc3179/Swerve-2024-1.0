package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class ArmMoveAngle extends CommandBase{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final double angle;
    boolean done;

    public ArmMoveAngle(ArmSubsystem m_ArmSubsystem, double angle){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.angle = angle;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0,0,0);
    }

    @Override
    public void execute(){
        m_ArmSubsystem.armMoveRotations(m_ArmSubsystem.angleToRotations(angle), ()->m_ArmSubsystem.upDownEncoder.getDistance());
    }

    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0,0,0);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
