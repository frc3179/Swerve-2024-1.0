package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbingConstants;

/*
 * <Class Summary>
 * This class creates all the necessary elements for the arm to work.
 * This includes the following:
    * Motors
        * CANclimbLeft (left, climb motor)
        * CANclimbRight (right, climb motor)

    * Functions (see summaries below)
        * climbMove
 * </Class Summary>
 */
public class ClimbingSubsystem extends SubsystemBase{

    public CANSparkMax CANclimbLeft = new CANSparkMax(ClimbingConstants.kLeftClimbPort, MotorType.kBrushed);
    public static CANSparkMax CANclimbRight = new CANSparkMax(ClimbingConstants.kRightClimbPort, MotorType.kBrushed);

    //public static RelativeEncoder climbEncoder = CANclimbRight.getEncoder();
    
    /*
     * <summary>
     * This function takes in the "speed" for the climbing motors to use.
     * It then does all the nessary inversions and motors following other
     * motors. Finally it sets the "speed" to the speed for the motors.
     * </summary>
     */
    public void climbMove(double speed){
        CANclimbLeft.follow(CANclimbRight, false);

        CANclimbRight.set(speed);
    }

}
