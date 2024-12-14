package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbingConstants;

public class ClimbSubsystem extends SubsystemBase{
    public CANSparkMax CANclimbLeft = new CANSparkMax(ClimbingConstants.kLeftClimbPort, MotorType.kBrushed);
    public CANSparkMax CANclimbRight = new CANSparkMax(ClimbingConstants.kRightClimbPort, MotorType.kBrushed);

    /**
     * Sets the climb motors to the given speed
     * @param climbSpeed  The speed at which the climb motor should run. -1 to 1.
     */
    public void climbMove(double climbSpeed) {
        CANclimbLeft.follow(CANclimbRight, false);

        CANclimbRight.set(climbSpeed);
    }
}
