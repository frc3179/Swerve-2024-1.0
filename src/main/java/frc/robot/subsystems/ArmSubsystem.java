package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase{
    // motor controllers for each arm ellement
    public final CANSparkMax LupDown = new CANSparkMax(ArmConstants.kLUpDownMotorPort, MotorType.kBrushless);
    public final CANSparkMax RupDown = new CANSparkMax(ArmConstants.kRUpDownMotorPort, MotorType.kBrushless);
    public final CANSparkMax lShoot = new CANSparkMax(ArmConstants.kLeftShootMotorPort, MotorType.kBrushless);
    public final CANSparkMax rShoot = new CANSparkMax(ArmConstants.kRightShootMotorPort, MotorType.kBrushless);
    public final CANSparkMax intake = new CANSparkMax(ArmConstants.kIntakeMotorPort, MotorType.kBrushless);
    
    // TODO Invert
    

    // group of the shooting motors
    public final MotorControllerGroup shoot = new MotorControllerGroup(lShoot, rShoot);
    public final MotorControllerGroup upDown = new MotorControllerGroup(LupDown, RupDown);



    public void armMove(double upDownSpeed, double shootSpeed, double intakeSpeed){
        upDown.set(upDownSpeed);
        shoot.set(shootSpeed);
        intake.set(intakeSpeed);
    }

    public void joysticMove(double upDownSpeed, boolean shoot, boolean intake){
        double outshoot = 0.0;
        double outintake = 0.0;

        if(shoot){
            outshoot = 1.0; //default commands
        } 
        else if (intake){
            outintake = 1.0; //default commands
        }
        

        armMove(upDownSpeed, outshoot, outintake);
    }
    
}
