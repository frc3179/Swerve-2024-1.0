package frc.robot.Utils;

import edu.wpi.first.networktables.NetworkTableInstance;
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
        double limelightY = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double distance = (0.00425644*(limelightY*limelightY))-(0.188139*limelightY)+3.49207;
        //Changed to make less relient on smart dashboard
        return (TrackingConstants.kArmZeroEncoderValue-ans)+(distance/100)+0.05; //TODO: REVERT TO ORGINAL (-0.01)

        /*
         * (0.00425644*(limelightY*limelightY))-(0.188139*limelightY)+3.49207
         * NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(ArmSubsystem.upDownEncoder.get())
         */
    }

    /**
     * Converts Limelight ty to angle in degrees
     * @param limelightY  Ty from the limelight
     * @return  Angle in degrees
     */
    public static double limelightToAngle(double limelightY) {
        double opposite = TrackingConstants.kCenterOfAprilTagMeters-TrackingConstants.kHeightOfLensOfLimeLight;
        //Changed to make not relient on Smart Dashboard
        double ajacent = (0.00425644*(limelightY*limelightY))-(0.188139*limelightY)+3.49207;
        ajacent += 0.1524;

        double result = Math.atan(opposite/ajacent);
        result *= 180;
        
        double ArmAng = 180-(((90-result)/ajacent)+result);

        SmartDashboard.putNumber("ArmAng", ArmAng);
        return ArmAng;
    }
}