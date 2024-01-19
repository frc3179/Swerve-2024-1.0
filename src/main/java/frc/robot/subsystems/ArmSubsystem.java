package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem {
    // motor controllers for each arm ellement
    public final CANSparkMax LupDown = new CANSparkMax(ArmConstants.kUpDownMotorPort, MotorType.kBrushless);
    public final CANSparkMax RupDown = new CANSparkMax(ArmConstants.kUpDownMotorPort, MotorType.kBrushless);
    public final CANSparkMax lShoot = new CANSparkMax(ArmConstants.kLeftShootMotorPort, MotorType.kBrushless);
    public final CANSparkMax rShoot = new CANSparkMax(ArmConstants.kRightShootMotorPort, MotorType.kBrushless);
    public final CANSparkMax intake = new CANSparkMax(ArmConstants.kIntakeMotorPort, MotorType.kBrushless);
    
    // TODO Invert
    

    // group of the shooting motors
    public final MotorControllerGroup shoot = new MotorControllerGroup(lShoot, rShoot);
    public final MotorControllerGroup upDown = new MotorControllerGroup(LupDown, RupDown);



    public void armMove(double upDownSpeed, double shootSpeed, double intakeSpeed){
        this.upDown.set(upDownSpeed);
        this.shoot.set(shootSpeed);
        this.intake.set(intakeSpeed);
    }
    
}
