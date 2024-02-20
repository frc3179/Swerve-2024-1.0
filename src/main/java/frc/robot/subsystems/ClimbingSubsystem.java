package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbingConstants;

public class ClimbingSubsystem extends SubsystemBase{

    public CANSparkMax CANclimbLeft = new CANSparkMax(ClimbingConstants.kLeftClimbPort, MotorType.kBrushed);
    public CANSparkMax CANclimbRight = new CANSparkMax(ClimbingConstants.kRightClimbPort, MotorType.kBrushed);

    

    public void climbMove(double speed){
        CANclimbLeft.follow(CANclimbRight, false);

        CANclimbRight.set(speed);
    }

}
