package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase{
    public CANSparkMax LupDown = new CANSparkMax(ArmConstants.kLUpDownMotorPort, MotorType.kBrushless);
    public CANSparkMax RupDown = new CANSparkMax(ArmConstants.kRUpDownMotorPort,  MotorType.kBrushless);

    public final static DutyCycleEncoder upDownEncoder = new DutyCycleEncoder(ArmConstants.kArmEncoderPort);

    /**
     * Sets the speed of the Arm Up Down
     * @param upDownSpeed  The speed to set the arm's motor to
     */
    public void armMove(double upDownSpeed) {
        LupDown.follow(RupDown, true);

        RupDown.set(upDownSpeed);
    }

    /**
     * Checks if the arm is resting at the botom. 
     * If so it will not led any negatives through
     * @param initSpeed  The initial speed that was given to move the arm
     * @return           The new speed after the initSpeed is checked
     */
    public double armRestingCheck(double initSpeed) {
        return upDownEncoder.get() >= 0.365 ? Math.max(initSpeed, 0) : initSpeed;
        // double ans;

        // if(upDownEncoder.get() >= 0.365){
        //     ans = initSpeed < 0 ? 0 : initSpeed;
        // } else {
        //     ans = initSpeed;
        // }

        // return ans;
    }

    /**
     * Gets the Arm Encoder Value
     * @return  The current value of the encoder
     */
    public double getArmEncoder() {
        return upDownEncoder.get();
    }
}
