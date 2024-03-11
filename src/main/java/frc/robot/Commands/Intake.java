package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShootingSubsystem;

public class Intake extends Command{
    ShootingSubsystem m_shoot;
    Supplier<Boolean> intakespeed;
    Supplier<Boolean> OverRideIntakeCheck;
    Supplier<Boolean> invertintake;

    public Intake(ShootingSubsystem m_shoot, Supplier<Boolean> intakespeed, Supplier<Boolean> OverRideIntakeCheck, Supplier<Boolean> invertintake){
        this.intakespeed = intakespeed;
        this.OverRideIntakeCheck = OverRideIntakeCheck;
        this.invertintake = invertintake;
        this.m_shoot = m_shoot;
        addRequirements(m_shoot);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        double intakeSpeed = intakespeed.get() ? 0.4 : 0;
        if (OverRideIntakeCheck.get()) {
            intakeSpeed = m_shoot.intakeCheck(RobotContainer.m_IR.get(), intakeSpeed);
        }
        double invert = invertintake.get() ? 1 : -1;
        m_shoot.ShootMove(0, invert * intakeSpeed);
    }
    
    @Override
    public void end(boolean interrupted){
        m_shoot.ShootMove(0, 0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
