package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ShootingSubsystem extends SubsystemBase{
    // motor controllers for each arm ellement
    public CANSparkMax lShoot = new CANSparkMax(ArmConstants.kLeftShootMotorPort, MotorType.kBrushed);
    public CANSparkMax rShoot = new CANSparkMax(ArmConstants.kRightShootMotorPort, MotorType.kBrushed);
    public Spark intake = new Spark(ArmConstants.kIntakeMotorPort);


    
    public void ShootMove(double shootSpeed, double intakeSpeed){
        rShoot.set(shootSpeed); //Just inverted this for new shoot motors
        lShoot.set(shootSpeed);
        intake.set(intakeSpeed);
    }

    /*
     * <summary>
     * This takes "IR" (the IR value from the color/IR sensor) as a paramater and the "initspeed"
     * (the speed the intake will be at if the note is not intaked far enough)and uses this info 
     * to get if the note is in the intake far enough. It then returns the checked speed. 
     * In simple, if the note is in far enough the sensor sees it and returns the value "0"
     * </summary>
     */
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
