package frc.robot.Joystick_Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ArmSubsystem;

public class JoystickArm extends Command{
    ArmSubsystem m_Arm;
    Supplier<Double> upDownSpeed;

    public JoystickArm(
        ArmSubsystem m_Arm,
        Supplier<Double> upDownSpeed
        ) {

        this.m_Arm = m_Arm;
        this.upDownSpeed = upDownSpeed;

        addRequirements(m_Arm);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() { 
        m_Arm.armMove(m_Arm.armRestingCheck(upDownSpeed.get()));
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() { 
        return false; 
    } //This command will never finish
}
