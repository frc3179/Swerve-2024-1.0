package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ShootingSubsystem extends SubsystemBase{
    // motor controllers for each arm ellement
    public CANSparkMax lShoot = new CANSparkMax(ArmConstants.kLeftShootMotorPort, MotorType.kBrushed);
    public CANSparkMax rShoot = new CANSparkMax(ArmConstants.kRightShootMotorPort, MotorType.kBrushed);

    /**
     * Sets the Shoot speed of the motors
     * 
     * @param shootSpeed 
     */
    public void ShootMove(double shootSpeed){
        rShoot.set(shootSpeed); //Just inverted this for new shoot motors
        lShoot.set(shootSpeed);
    }
}
