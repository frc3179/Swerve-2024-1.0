package frc.robot.Auto_Commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Utils.TrackingUtils;
import frc.robot.subsystems.ArmSubsystem;

public class DefaultTracking extends Command{
    ArmSubsystem m_Arm;
    Supplier<Double> ty;
    Supplier<Double> encoder;
    //TODO: TUNE
    PIDController pid = new PIDController(15,4,0);
    
    public DefaultTracking(
        ArmSubsystem m_Arm,
        Supplier<Double> ty,
        Supplier<Double> encoder
        ) {
        
        this.m_Arm = m_Arm;
        this.ty = ty;
        this.encoder = encoder;

        addRequirements(m_Arm);
    }

    @Override
    public void initialize(){
        //TODO: TUNE
        pid.setTolerance(0.0008);
    }

    @Override
    public void execute(){
        if (ty.get() > -4) {
            double goalEncoder = TrackingUtils.limelightToAngle(ty.get());
            goalEncoder = TrackingUtils.angleToRotations(goalEncoder);

            double speed = (this.encoder.get() - goalEncoder)*20;;
            SmartDashboard.putNumber("Goal Encoder", goalEncoder);

            m_Arm.armMove(speed > 0 ? speed/2 : speed/4);
        }  else {
            m_Arm.armMove(0);
        }
    }
    
    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
