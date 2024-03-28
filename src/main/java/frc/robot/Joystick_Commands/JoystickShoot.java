package frc.robot.Joystick_Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShootSubsystem;

public class JoystickShoot extends Command{
    ShootSubsystem m_Shoot;
    Supplier<Double> shootSpeed;
    
    public JoystickShoot(
        ShootSubsystem m_Shoot,
        Supplier<Double> shootSpeed
        ) {

        this.m_Shoot = m_Shoot;
        this.shootSpeed = shootSpeed;

        addRequirements(m_Shoot);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_Shoot.shootMove(shootSpeed.get());
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    } //This Command will never finish
}
