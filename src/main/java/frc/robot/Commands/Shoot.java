package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class Shoot extends CommandBase{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final double speed;

    public Shoot(ArmSubsystem m_ArmSubsystem, double speed){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.speed = speed;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0, 0, 0); //reset
        m_ArmSubsystem.armMove(0, speed, 0); //speed up
    }

    @Override
    public void execute(){
        m_ArmSubsystem.armMove(0, speed, 0); //continue speed
        m_ArmSubsystem.armMove(0, speed, 1); //feed the shotters could be negative
    }

    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0, 0, 0);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
