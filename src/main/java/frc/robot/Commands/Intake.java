package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

public class Intake extends Command{
    IntakeSubsystem m_intake;
    Supplier<Boolean> intakespeed;
    Supplier<Boolean> OverRideIntakeCheck;
    Supplier<Boolean> invertintake;

    public Intake(IntakeSubsystem m_intake, Supplier<Boolean> intakespeed, Supplier<Boolean> OverRideIntakeCheck, Supplier<Boolean> invertintake){
        this.intakespeed = intakespeed;
        this.OverRideIntakeCheck = OverRideIntakeCheck;
        this.invertintake = invertintake;
        this.m_intake = m_intake;
        addRequirements(m_intake);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        double intakeSpeed = intakespeed.get() ? 0.4 : 0;
        if (OverRideIntakeCheck.get()) {
            intakeSpeed = m_intake.intakeCheck(RobotContainer.m_IR.get(), intakeSpeed);
        }
        double invert = invertintake.get() ? 1 : -1;
        m_intake.IntakeMove(invert * intakeSpeed);
    }
    
    @Override
    public void end(boolean interrupted){
        m_intake.IntakeMove(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
