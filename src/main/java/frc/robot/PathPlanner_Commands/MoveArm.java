package frc.robot.PathPlanner_Commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ArmSubsystem;
import frc.robot.Subsystems.ShootSubsystem;

public class MoveArm extends Command{
    private final PIDController m_armPID = new PIDController(5, 0, 0);
    private final ArmSubsystem m_ArmSubsystem;
    private double position;
    ShootSubsystem m_shoot;

    public MoveArm(ArmSubsystem m_ArmSubsystem, ShootSubsystem m_shoot, double position){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.position = position;
        addRequirements(m_ArmSubsystem, m_shoot);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0); //reset
        m_armPID.setSetpoint(position);
        m_armPID.setTolerance(0.0008);
    }

    @Override
    public void execute(){
        // intakeSpeed = m_shoot.intakeCheck(RobotContainer.m_IR.get(), intakeSpeed);
            
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
