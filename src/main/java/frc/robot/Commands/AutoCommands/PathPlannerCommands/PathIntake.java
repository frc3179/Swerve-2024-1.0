package frc.robot.Commands.AutoCommands.PathPlannerCommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class PathIntake extends Command{
    ArmSubsystem m_ArmSubsystem;
    Supplier<Double> IR;
    Supplier<Double> ArmEnc;
    Timer maxTimmer;

    public PathIntake(ArmSubsystem m_ArmSubsystem, Supplier<Double> IR, Supplier<Double> ArmEncoder){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.ArmEnc = ArmEncoder;

        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize(){
        maxTimmer.restart();
    }

    @Override
    public void execute(){
        if(ArmEnc.get() >= 0.365){
            m_ArmSubsystem.armMove(0, 0, 1);
        }
        m_ArmSubsystem.armMove(-0.2, 0, 1);
    }
    
    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0, 0, 0);
    }

    @Override
    public boolean isFinished(){
        if (IR.get() > 20 || maxTimmer.get() > 1.75) {
            return true;
        }
        return false;
    }
}
