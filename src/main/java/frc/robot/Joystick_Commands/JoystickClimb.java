package frc.robot.Joystick_Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ClimbSubsystem;

public class JoystickClimb extends Command{
    ClimbSubsystem m_Climb;
    Supplier<Double> climbSpeed;
    Supplier<Boolean> climbInvert;

    public JoystickClimb(
        ClimbSubsystem m_Climb,
        Supplier<Double> climbSpeed,
        Supplier<Boolean> climbInvert
        ) {

        this.m_Climb = m_Climb;
        this.climbSpeed = climbSpeed;
        this.climbInvert = climbInvert;

        addRequirements(m_Climb);
    }
    
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (climbInvert.get() == true) {
            m_Climb.climbMove(-climbSpeed.get());
        }
        m_Climb.climbMove(climbSpeed.get());
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    } //This command will not finish
}
