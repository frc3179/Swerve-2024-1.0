package frc.robot.subsystems;



import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase{
    // motor controllers for each arm ellement
    public final CANSparkMax LupDown = new CANSparkMax(ArmConstants.kLUpDownMotorPort, MotorType.kBrushless);
    public final static CANSparkMax RupDown = new CANSparkMax(ArmConstants.kRUpDownMotorPort, MotorType.kBrushless);
    public final CANSparkMax lShoot = new CANSparkMax(ArmConstants.kLeftShootMotorPort, MotorType.kBrushless);
    public final CANSparkMax rShoot = new CANSparkMax(ArmConstants.kRightShootMotorPort, MotorType.kBrushless);
    public final CANSparkMax intake = new CANSparkMax(ArmConstants.kIntakeMotorPort, MotorType.kBrushless);
    
    // TODO Invert
    // TODO use .follow() instead of MotorControllerGroup
    

    // group of the shooting motors
    public final MotorControllerGroup shoot = new MotorControllerGroup(lShoot, rShoot);
    public final MotorControllerGroup upDown = new MotorControllerGroup(LupDown, RupDown);

    //Encoder
    public final static AbsoluteEncoder upDownEncoder = (AbsoluteEncoder) RupDown.getEncoder();

    // Timer
    public Timer Armtimer = new Timer();


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

        outintake = intakeCheck(SmartDashboard.getNumber("IR", 0.0), outintake);

        armMove(upDownSpeed, outshoot, outintake);
    }

    public void armMoveTime(double upDownSpeed, double shootSpeed, double intakeSpeed, double time){ // I think seconds
        Armtimer.restart();

        while (Armtimer.get() < time){
            armMove(upDownSpeed, shootSpeed, intakeSpeed);
        }
    }

    public void armMoveAngle(double degAngle, double shootSpeed){
        double rottations = angleToRotations(degAngle);

        while (upDownEncoder.getPosition() < Math.abs(rottations)){
            if(rottations < 0){
                armMove(-1, shootSpeed, 0);
            }
            else if(rottations > 0){
                armMove(1, shootSpeed, 0);
            }
             
        }
    }

    public double intakeCheck(double IR, double initSpeed){
        if(IR > 20.0){
            return 0.0;
        }

        return initSpeed;
    }

    public double angleToRotations(double degAngle){
        return 0.0;
    }

    
}
