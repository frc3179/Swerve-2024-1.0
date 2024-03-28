package frc.robot.PathPlanner_Commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;

public class MoveArm extends Command{
    private final PIDController m_armPID = new PIDController(5, 0, 0);
    private final ArmSubsystem m_ArmSubsystem;
    IntakeSubsystem m_Intake;
    private double position;
    ShootSubsystem m_shoot;

    public MoveArm(ArmSubsystem m_ArmSubsystem, ShootSubsystem m_shoot, IntakeSubsystem m_Intake, double position){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.position = position;
        this.m_shoot = m_shoot;
        this.m_Intake = m_Intake;
        addRequirements(m_ArmSubsystem, m_shoot, m_Intake);
    }

    @Override
    public void initialize(){
        m_ArmSubsystem.armMove(0); //reset
        m_shoot.shootMove(0);
        m_Intake.intakeMove(0);

        m_armPID.setSetpoint(position);
        m_armPID.setTolerance(0.01);
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
