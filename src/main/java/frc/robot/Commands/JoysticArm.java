package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class JoysticArm extends CommandBase{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final Supplier<Double> upDownSpeed;
    private Supplier<Boolean> intakespeed;
    private Supplier<Boolean> invertintake;

    public JoysticArm(ArmSubsystem m_ArmSubsystem, Supplier<Double> upDownSpeed, Supplier<Boolean> intakespeed, Supplier<Boolean> invertintake){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.intakespeed = intakespeed;
        this.upDownSpeed = upDownSpeed;
        this.invertintake = invertintake;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        double intakeSpeed = intakespeed.get() ? 1:0;
        intakeSpeed = m_ArmSubsystem.intakeCheck(SmartDashboard.getNumber("IR", 0.0), intakeSpeed);
        double invert = invertintake.get() ? 1:-1;
        m_ArmSubsystem.armMove(upDownSpeed.get(), 0, invert*intakeSpeed);
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
