package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase{
    // motor controllers for each arm ellement
    public CANSparkMax LupDown = new CANSparkMax(ArmConstants.kLUpDownMotorPort, MotorType.kBrushless);
    public CANSparkMax RupDown = new CANSparkMax(ArmConstants.kRUpDownMotorPort, MotorType.kBrushless);
    
    // Encoder
    public final static DutyCycleEncoder upDownEncoder = new DutyCycleEncoder(ArmConstants.kArmEncoderPort);

    // Timer
    public Timer Armtimer = new Timer();

    /**
     * @param upDownSpeed Speed for the arm to move up and down
     */
    public void armMove(double upDownSpeed){
        LupDown.follow(RupDown, true);

        //check if arm is in starting position
        if(upDownEncoder.get() >= 0.365){
            upDownSpeed = upDownSpeed < 0 ? 0 : upDownSpeed;
        }

        RupDown.set(upDownSpeed);
    }

    @Override
    public void periodic() {
        // SmartDashboard.putNumber("Arm E", 0)
    }
}