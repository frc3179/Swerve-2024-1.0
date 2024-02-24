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

/*
 * <Class Summary>
 * This class creates all the nessary ellements for the arm to work.
 * These include the following:
    * Motors
        * LupDown (left up down motor)
        * RupDown (right up down motor)
        * lShoot (left shoot motor)
        * rShoot (right shoot motor)
        * intake (intake motor)

    * Encoder
        * UpDownEncoder (Through the bore encoder on the RupDown motor)

    * Timer
        * Armtimer (Timer for the function of moving the arm with time)

    * Functions (see summaries below)
        * armMove
        * armMoveTime
        * intakeCheck
        * armMoveRotations
        * angleToRotations
        * limelightToAngle
 * </Class Summary>
 */
public class ArmSubsystem extends SubsystemBase{
    // motor controllers for each arm ellement
    public CANSparkMax LupDown = new CANSparkMax(ArmConstants.kLUpDownMotorPort, MotorType.kBrushless);
    public CANSparkMax RupDown = new CANSparkMax(ArmConstants.kRUpDownMotorPort, MotorType.kBrushless);
    public CANSparkMax lShoot = new CANSparkMax(ArmConstants.kLeftShootMotorPort, MotorType.kBrushed);
    public CANSparkMax rShoot = new CANSparkMax(ArmConstants.kRightShootMotorPort, MotorType.kBrushed);
    public Spark intake = new Spark(ArmConstants.kIntakeMotorPort);
    
    // Encoder
    public final static DutyCycleEncoder upDownEncoder = new DutyCycleEncoder(ArmConstants.kArmEncoderPort);

    // Timer
    public Timer Armtimer = new Timer();


    /*
     * <summary>
     * This is the basic function that moves the arm. It takes in the upDownSpeed
     * (speed for the arm up and down), shootSpeed (speed for the shoot motors), 
     * intakeSpeed (speed for the intake). It then sets all the inversions and the
     * motors that need to follow other motors. Then it gets the arm encoder position
     * (from the smart dashboard) and compares that to the lowest position we want the
     * arm to go. If it is on or lower than the lowest position it converts all downward
     * (negative) values to zero and keeps all upward (positive) values. Finally it sets
     * all the inputed and updated speeds to motor speeds.
     * </summary>
     */
    public void armMove(double upDownSpeed, double shootSpeed, double intakeSpeed){
        LupDown.follow(RupDown, true);

        //check if arm is in starting position
        if(SmartDashboard.getNumber("Arm Encoder", 0.37) >= 0.37){
            upDownSpeed = upDownSpeed < 0 ? 0 : upDownSpeed;
        }

        RupDown.set(upDownSpeed);
        rShoot.set(-shootSpeed); //Just inverted this for new shoot motors
        lShoot.set(shootSpeed);
        intake.set(intakeSpeed);
    }

    /*
     * <summary>
     * This takes all the same paramaters as the basic moving arm, adding time. 
     * (seconds the arm sould be using the values provided) It then sends those
     * values to the basic move arm function until the time is reached. (by the timmer)
     * </summary>
     */
    public void armMoveTime(double upDownSpeed, double shootSpeed, double intakeSpeed, double time){
        Armtimer.restart();

        while (Armtimer.get() < time){
            armMove(upDownSpeed, shootSpeed, intakeSpeed);
        }
    }

    /*
     * <summary>
     * This takes IR (the IR value from the color/IR sensor) as a paramater and the initspeed 
     * (the speed the intake will be at if the note is not intaked far enough) 
     * and uses this info to get if the note is in the intake far enough. It then returns the
     * checked speed.
     * </summary>
     */
    public double intakeCheck(double IR, double initSpeed){
        if(IR > 20.0){
            return 0.0;
        }
        
        return initSpeed;
    }
    
    /*
     * <summary>
     * This takes rotations (encoder value (name is misleading)) and a supplier double for encoder 
     * (so we can get the most updated encoder value) and moves the arm to the encoder value provided. (rotations) 
     * To move the arm we use the basic moving arm function declared at the top of this class.
     * </summary>
     */
    public void armMoveRotations(double rotations, Supplier<Double> encoder){
        while(encoder.get() > rotations+TrackingConstants.kRotationOffsetTrack || encoder.get() < rotations-TrackingConstants.kRotationOffsetTrack){
            if(rotations > encoder.get()){
                armMove(-0.3, 0, 0);
            }
            else if(rotations < encoder.get()){
                armMove(0.3, 0, 0);
            }
        }
    }

    /*
     * <summary>
     * This converts an angle in degrees (usually the one outputed by limelightToAngle function)
     * to encoder value for the arm to rotate to. This was all found by finding the encoder value
     * to 90 degrees and then using a simple ratio and then subtracting the value from the resting 
     * position. We subtract because the farther our arm moves up the lower the encoder value. If
     * the encoder values gets larger the farther the arm goes up the change the subtraction to addition.
     * </summary>
     */
    public double angleToRotations(double degAngle){
        double ans = (TrackingConstants.kEncoderTo90Deg/90)*degAngle;
        return (TrackingConstants.kArmZeroEncoderValue-ans)+0.04; //*NOTE: 0.04 is just a simple ofset
    }

    /*
     * <summary>
     * This converts limelight ty values to an angle for the robot arm to rotate to.
     * This will be different for each robot and each arm.
     * Expermintation is the only way we found this. (with a little trig)
     * </summary>
     */
    public static double limelightToAngle(double limelightY){
        double opposite = TrackingConstants.kCenterOfAprilTagMeters-TrackingConstants.kHeightOfLensOfLimeLight;
        double ajacent = (0.00425644*(limelightY*limelightY))-(0.188139*limelightY)+3.49207; //*NOTE: This was found through expermintation only. (get distance function)
        ajacent += 0.1524;
        SmartDashboard.putNumber("Distance", ajacent);

        double result = Math.atan(opposite/ajacent);
        result *= 180;
        
        double ArmAng = 180-(((90-result)/ajacent)+result);

        SmartDashboard.putNumber("Angle", ArmAng);
        return ArmAng;
    }
}
