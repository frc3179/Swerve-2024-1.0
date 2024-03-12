package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class IntakeSubsystem extends SubsystemBase{
    public Spark intake = new Spark(ArmConstants.kIntakeMotorPort);


    
    public void IntakeMove(double intakeSpeed){
        intake.set(intakeSpeed);
    }

    /**
     * @param IR            The IR Value from the Color Sensor
     * @param initSpeed      The initial speed of the intake when the
     * @return                Returns the checked speed of the intake
     */
    public double intakeCheck(boolean IR, double initSpeed){
        if(IR == false){
            return 0.0;
        }
        
        return initSpeed;
    }
}
