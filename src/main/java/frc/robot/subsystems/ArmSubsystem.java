package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase{
    // motor controllers for each arm ellement
    public static CANSparkMax LupDown = new CANSparkMax(ArmConstants.kLUpDownMotorPort, MotorType.kBrushless);
    public CANSparkMax RupDown = new CANSparkMax(ArmConstants.kRUpDownMotorPort, MotorType.kBrushless);
    public CANSparkMax lShoot = new CANSparkMax(ArmConstants.kLeftShootMotorPort, MotorType.kBrushless);
    public CANSparkMax rShoot = new CANSparkMax(ArmConstants.kRightShootMotorPort, MotorType.kBrushless);
    public CANSparkMax intake = new CANSparkMax(ArmConstants.kIntakeMotorPort, MotorType.kBrushless);
    
    //Encoder
    public final static AbsoluteEncoder upDownEncoder = (AbsoluteEncoder) LupDown.getEncoder();

    // Timer
    public Timer Armtimer = new Timer();


    public void armMove(double upDownSpeed, double shootSpeed, double intakeSpeed){
        LupDown.follow(RupDown, true);
        lShoot.follow(rShoot, true);

        RupDown.set(upDownSpeed);
        rShoot.set(shootSpeed);
        intake.set(intakeSpeed);
    }

    public void armMoveTime(double upDownSpeed, double shootSpeed, double intakeSpeed, double time){ // I think seconds
        Armtimer.restart();

        while (Armtimer.get() < time){
            armMove(upDownSpeed, shootSpeed, intakeSpeed);
        }
    }

    public boolean armMoveRotations(double rotations){
        while(upDownEncoder.getPosition() > rotations+ArmConstants.kRotationOffsetTrack || upDownEncoder.getPosition() < rotations-ArmConstants.kRotationOffsetTrack){
            armMove(Math.abs((rotations-upDownEncoder.getPosition())), 0, 0);
        }
        return true;
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

    public double limelightToAngle(){
        double limelightY = SmartDashboard.getNumber("Limelight ty", 0.0);
        return limelightY*10;
    }

}
