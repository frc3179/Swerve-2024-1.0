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
import frc.robot.Constants.TrackingConstants;

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
        while(encoder.get() > rotations+TrackingConstants.kRotationOffsetTrack || encoder.get() < rotations-TrackingConstants.kRotationOffsetTrack){
            if(rotations > encoder.get()){
                armMove(-0.5, 0, 0);
            }
            else if(rotations < encoder.get()){
                armMove(0.5, 0, 0);
            }
        }
    }

    public double angleToRotations(double degAngle){
        return -0.0000175696*(degAngle*degAngle)+0.000870624*degAngle+0.301081;
    }

    public double limelightToAngle(){
        double limelightY = SmartDashboard.getNumber("Limelight ty", 0.0);
        double opposite = 1.4732;
        double ajacent = (6.3384*Math.log(14.0331-(0.14686*limelightY)))-13.1394;
        ajacent += 0.1524;
        SmartDashboard.putNumber("Distance", ajacent);

        double result = Math.atan(opposite/ajacent);
        result *= 180;
        
        double ArmAng = 180-(result+TrackingConstants.kArmAngleToHypot);
        ArmAng += 5;
        
        SmartDashboard.putNumber("Angle", ArmAng);
        return ArmAng;
    }

}
