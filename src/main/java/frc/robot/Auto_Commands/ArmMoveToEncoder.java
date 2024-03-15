package frc.robot.Auto_Commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class ArmMoveToEncoder extends Command{
    ArmSubsystem m_Arm;
    Supplier<Double> encoder;
    double goalEncoder;
    //TODO: TUNE
    PIDController pid = new PIDController(5, 0.01, 0);

    public ArmMoveToEncoder(
        ArmSubsystem m_Arm, 
        Supplier<Double> encoder, 
        double goalEncoder
        ) {

        this.m_Arm = m_Arm;
        this.encoder = encoder;
        this.goalEncoder = goalEncoder;

        addRequirements(m_Arm);
    }

    @Override
    public void initialize(){
        pid.setSetpoint(goalEncoder);
        pid.setTolerance(0.0008);
    }

    @Override
    public void execute(){
        m_Arm.armMove(-pid.calculate(encoder.get()));
    }
    
    @Override
    public void end(boolean interrupted){
        
    }

    @Override
    public boolean isFinished(){
        return pid.atSetpoint();
    }
}
