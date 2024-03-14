package frc.robot.Utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.TrackingConstants;

public class TrackingUtils {
    /**
     * Converts degres to encoder value
     * @param degAngle  Degree angle
     * @return  Encoder Value
     */
    public static double angleToRotations(double degAngle) {
        double ans = (TrackingConstants.kEncoderTo90Deg/90)*degAngle;
        return (TrackingConstants.kArmZeroEncoderValue-ans)+(SmartDashboard.getNumber("Distance", 0)/100)-0.01;
    }

    /**
     * Converts Limelight ty to angle in degrees
     * @param limelightY  Ty from the limelight
     * @return  Angle in degrees
     */
    public static double limelightToAngle(double limelightY) {
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