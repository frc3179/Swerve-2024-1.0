package frc.robot.Auto_Commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class ArmToEncoder extends Command{
    private final PIDController m_armPID = new PIDController(5, 0, 0);
    private final ArmSubsystem m_ArmSubsystem;
    private double position;

    public ArmToEncoder(ArmSubsystem m_ArmSubsystem, double position){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.position = position;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0); //reset

        m_armPID.setSetpoint(position);
        m_armPID.setTolerance(0.01);
    }

    @Override
    public void execute(){
        m_ArmSubsystem.armMove(-m_armPID.calculate(ArmSubsystem.upDownEncoder.get()));
    }

    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0); 
    }

    @Override
    public boolean isFinished(){
        return m_armPID.atSetpoint();
    }
}
