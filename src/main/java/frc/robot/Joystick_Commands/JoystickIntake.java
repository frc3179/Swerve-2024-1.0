package frc.robot.Joystick_Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class JoystickIntake extends Command{
    IntakeSubsystem m_Intake;
    Supplier<Double> intakeSpeed;
    Supplier<Boolean> intakeInvert;
    Supplier<Boolean> override;

    public JoystickIntake(
        IntakeSubsystem m_Intake,
        Supplier<Double> intakeSpeed,
        Supplier<Boolean> intakeInvert,
        Supplier<Boolean> overrideIntakeCheck
        ) {

        this.m_Intake = m_Intake;
        this.intakeSpeed = intakeSpeed;
        this.intakeInvert = intakeInvert;
        this.override = overrideIntakeCheck;

        addRequirements(m_Intake);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double speed = (intakeInvert.get()? -1:1)*intakeSpeed.get();
        if (override.get() == true) {
            m_Intake.intakeMove(speed);
        } else {
            m_Intake.intakeMove(m_Intake.intakeCheck(m_Intake.getIRValue(), speed));
        }
    }

    @Override 
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished(){
        return false;
    } //This Command will never end
}
