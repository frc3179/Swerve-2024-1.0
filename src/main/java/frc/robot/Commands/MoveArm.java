package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class MoveArm extends Command{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final double UpDownspeed;
    private double intakeSpeed;

    public MoveArm(ArmSubsystem m_ArmSubsystem, double UpDownSpeed, double intakeSpeed){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.UpDownspeed = UpDownSpeed;
        this.intakeSpeed = intakeSpeed;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0, 0, 0); //reset
    }

    @Override
    public void execute(){
        intakeSpeed = m_ArmSubsystem.intakeCheck(SmartDashboard.getNumber("IR", 0.0), intakeSpeed);
        m_ArmSubsystem.armMove(UpDownspeed, 0, intakeSpeed);
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
