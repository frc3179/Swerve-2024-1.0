package frc.robot.Commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimbingSubsystem;

public class JoysticArm extends CommandBase{
    
    private final ArmSubsystem m_ArmSubsystem;
    private final ClimbingSubsystem m_climber;
    private final Supplier<Double> upDownSpeed;
    private Supplier<Boolean> intakespeed;
    private Supplier<Boolean> invertintake;
    private Supplier<Boolean> climberSpeed;
    private Supplier<Boolean> climberInvert;

    public JoysticArm(ArmSubsystem m_ArmSubsystem, ClimbingSubsystem m_climber, Supplier<Double> upDownSpeed, Supplier<Boolean> intakespeed, Supplier<Boolean> invertintake, Supplier<Boolean> climbSpeed, Supplier<Boolean> climbInvert){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.intakespeed = intakespeed;
        this.upDownSpeed = upDownSpeed;
        this.invertintake = invertintake;
        this.m_climber = m_climber;
        this.climberSpeed = climbSpeed;
        this.climberInvert = climbInvert;
        addRequirements(m_ArmSubsystem, m_climber);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        double intakeSpeed = intakespeed.get() ? 0.45:0;
        intakeSpeed = m_ArmSubsystem.intakeCheck(SmartDashboard.getNumber("IR", 0.0), intakeSpeed);
        double invert = invertintake.get() ? 1:-1;
        m_ArmSubsystem.armMove(upDownSpeed.get(), 0, invert*intakeSpeed);

        //climb
        double dClimbSpeed = ((climberInvert.get() ? -1:1)*(climberSpeed.get() ? -1:0));
        m_climber.climbMove(dClimbSpeed);
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
