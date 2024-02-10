package frc.robot.subsystems;

import java.util.function.Supplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase{
    // motor controllers for each arm ellement
    public CANSparkMax LupDown = new CANSparkMax(ArmConstants.kLUpDownMotorPort, MotorType.kBrushless);
    public CANSparkMax RupDown = new CANSparkMax(ArmConstants.kRUpDownMotorPort, MotorType.kBrushless);
    public CANSparkMax lShoot = new CANSparkMax(ArmConstants.kLeftShootMotorPort, MotorType.kBrushless);
    public CANSparkMax rShoot = new CANSparkMax(ArmConstants.kRightShootMotorPort, MotorType.kBrushless);
    public Spark intake = new Spark(ArmConstants.kIntakeMotorPort);
    
    //Encoder
    public final static DutyCycleEncoder upDownEncoder = new DutyCycleEncoder(ArmConstants.kArmEncoderPort);

    // Timer
    public Timer Armtimer = new Timer();


    public void armMove(double upDownSpeed, double shootSpeed, double intakeSpeed){
        LupDown.follow(RupDown, true);

        RupDown.set(upDownSpeed);
        rShoot.set(shootSpeed);
        lShoot.set(-shootSpeed);
        intake.set(intakeSpeed);
    }

    public void armMoveTime(double upDownSpeed, double shootSpeed, double intakeSpeed, double time){ // I think seconds
        Armtimer.restart();

        while (Armtimer.get() < time){
            armMove(upDownSpeed, shootSpeed, intakeSpeed);
        }
    }

    
    public double intakeCheck(double IR, double initSpeed){
        if(IR > 20.0){
            return 0.0;
        }
        
        return initSpeed;
    }
    
    public void armMoveRotations(double rotations, Supplier<Double> encoder){
        while(encoder.get() > rotations+ArmConstants.kRotationOffsetTrack || encoder.get() < rotations-ArmConstants.kRotationOffsetTrack){
            if(rotations > encoder.get()){
                armMove(-0.5, 0, 0);
            }
            else if(rotations < encoder.get()){
                armMove(0.5, 0, 0);
            }
        }
    }

    public double angleToRotations(double degAngle){
        return degAngle*0.0304;
    }

    public double limelightToAngle(){
        double limelightY = SmartDashboard.getNumber("Limelight ty", 0.0);
        double opposite = 1.177925;
        double ajacent = (6.3384*Math.log(14.0331-(0.14686*limelightY)))-13.1394;

        double result = Math.atan(opposite/ajacent);

        return result;
    }

}
