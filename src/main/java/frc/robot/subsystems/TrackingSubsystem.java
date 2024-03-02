package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TrackingConstants;

public class TrackingSubsystem extends SubsystemBase{
    
    /*
     * <summary>
     * This converts an angle measured in degrees (usually outputed by "limelightToAngle" function)
     * to encoder value for the arm to rotate to. This was all found by finding what the encoder value
     * was at 90 degrees, then using a simple ratio, then subtracting the value from the resting 
     * position. We subtracted because the farther our arm moves up the lower the encoder value. If
     * the encoder values gets larger the farther the arm goes up the change the subtraction to addition.
     * </summary>
     */
    public double angleToRotations(double degAngle){
        double ans = (TrackingConstants.kEncoderTo90Deg/90)*degAngle;
        return (TrackingConstants.kArmZeroEncoderValue-ans)+(SmartDashboard.getNumber("Distance", 0)/100);
    }

    /*
     * <summary>
     * This converts limelight ty values to an angle for the robot arm to rotate to.
     * This will be different for each robot and each arm.
     * Experimentation is the only way we found this. (with a little trig)
     * </summary>
     */
    public double limelightToAngle(double limelightY){
        double opposite = TrackingConstants.kCenterOfAprilTagMeters-TrackingConstants.kHeightOfLensOfLimeLight;
        double ajacent = SmartDashboard.getNumber("Distance", 0); 
        ajacent += 0.1524;

        double result = Math.atan(opposite/ajacent);
        result *= 180;
        
        double ArmAng = 180-(((90-result)/ajacent)+result);

        SmartDashboard.putNumber("ArmAng", ArmAng);
        return ArmAng;
    }
}
