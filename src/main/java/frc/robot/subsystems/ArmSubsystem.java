package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem {
    // motor controllers for each arm ellement
    public final CANSparkMax upDown = new CANSparkMax(ArmConstants.kUpDownMotorPort, MotorType.kBrushless);
    public final CANSparkMax lShoot = new CANSparkMax(ArmConstants.kLeftShootMotorPort, MotorType.kBrushless);
    public final CANSparkMax rShoot = new CANSparkMax(ArmConstants.kRightShootMotorPort, MotorType.kBrushless);
    public final CANSparkMax intake = new CANSparkMax(ArmConstants.kIntakeMotorPort, MotorType.kBrushless);

    // group of the shooting motors
    //TODO invert one of the shooting motors so it does not break
    public final MotorControllerGroup shoot = new MotorControllerGroup(lShoot, rShoot);


    public void armMove(double upDownSpeed, double shootSpeed, double intakeSpeed){
        this.upDown.set(upDownSpeed);
        this.shoot.set(shootSpeed);
        this.intake.set(intakeSpeed);
    }
}
