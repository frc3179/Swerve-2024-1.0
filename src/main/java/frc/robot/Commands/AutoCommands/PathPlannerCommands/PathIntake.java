package frc.robot.Commands.AutoCommands.PathPlannerCommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ShootingSubsystem;

public class PathIntake extends Command{
    ArmSubsystem m_ArmSubsystem;
    Supplier<Boolean> IR;
    Supplier<Double> ArmEnc;
    Timer maxTimmer;
    ShootingSubsystem m_shoot;

    public PathIntake(ArmSubsystem m_ArmSubsystem, ShootingSubsystem m_shoot, Supplier<Boolean> IR, Supplier<Double> ArmEncoder){
        this.m_ArmSubsystem = m_ArmSubsystem;
        this.ArmEnc = ArmEncoder;
        this.m_shoot = m_shoot;

        addRequirements(m_ArmSubsystem, m_shoot);
    }

    @Override
    public void initialize(){
        maxTimmer.restart();
    }

    @Override
    public void execute(){
        if(ArmEnc.get() >= 0.365){
            m_ArmSubsystem.armMove(0);
            m_shoot.ShootMove(0,1);
        }
        m_ArmSubsystem.armMove(-0.2);
        m_shoot.ShootMove(0,1);
    }
    
    @Override
    public void end(boolean interrupted){
        m_ArmSubsystem.armMove(0);
        m_shoot.ShootMove(0,0);
    }

    @Override
    public boolean isFinished(){
        if (!IR.get() || maxTimmer.get() > 1.75) {
            return true;
        }
        return false;
    }
}
