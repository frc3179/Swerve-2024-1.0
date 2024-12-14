package frc.robot.PathPlanner_Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.IntakeSubsystem;

public class Intake extends Command{
    IntakeSubsystem m_intake;

    public Intake(IntakeSubsystem m_intake){
        this.m_intake = m_intake;
        addRequirements(m_intake);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        m_intake.intakeMove(-1);
    }
    
    @Override
    public void end(boolean interrupted){
        m_intake.intakeMove(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
