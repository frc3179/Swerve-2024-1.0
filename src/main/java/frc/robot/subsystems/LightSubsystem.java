package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightConstants;

public class LightSubsystem extends SubsystemBase{
    public Spark lightSpark = new Spark(LightConstants.kLEDStripPort);

    /**
     * Sets the color of the  LED strip to a specified RGB value.
     * @param color  The color value for the LED strip
     */
    private void setColor(double color) {
        lightSpark.setVoltage(color);
    }

    /**
     * Sets the Color the the led to Red
     */
    public void setRedColor() {
        setColor(LightConstants.kRedColor);
    }

    /**
     * Sets the Color the the led to Blue
     */
    public void setBlueColor() {
        setColor(LightConstants.kBlueColor);
    }

    /**
     * Sets the Color the the led to Note in Color (Green)
     */
    public void setNoteColor() {
        setColor(LightConstants.kNoteColor);
    }
}
