package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ShootSubsystem extends SubsystemBase{
    public CANSparkMax lShoot = new CANSparkMax(ArmConstants.kLeftShootMotorPort, MotorType.kBrushed);
    public CANSparkMax rShoot = new CANSparkMax(ArmConstants.kRightShootMotorPort, MotorType.kBrushed);

    /**
     * Moves the shoot motors at the given speed
     * @param shootSpeed  The speed to move the shoot motors (-1 to 1)
     */
    public void shootMove(double shootSpeed) {
        rShoot.set(shootSpeed);
        lShoot.set(shootSpeed);
    }
}
