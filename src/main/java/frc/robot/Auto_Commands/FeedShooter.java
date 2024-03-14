package frc.robot.Auto_Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.IntakeSubsystem;

public class FeedShooter extends Command{
    IntakeSubsystem m_Intake;

    public FeedShooter(
        IntakeSubsystem m_Intake
        ) {

        this.m_Intake = m_Intake;
        
        addRequirements(m_Intake);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        m_Intake.intakeMove(-1);
    }
    
    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
