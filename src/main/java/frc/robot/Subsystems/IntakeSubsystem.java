package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.ColorSensorConstants;

public class IntakeSubsystem extends SubsystemBase{
    public Spark intake = new Spark(ArmConstants.kIntakeMotorPort);

    public static DigitalInput m_IR = new DigitalInput(ColorSensorConstants.kColorSensorPort);

    /**
     * Sets the intake to the given speed
     * @param intakeSpeed  The desired speed of the intake, from -
     */
    public void intakeMove(double intakeSpeed) {
        intake.set(intakeSpeed);
    }

    /**
     * If noteIn then it stops the intake
     * @param noteIn  if true, runs the intake, else stops
     * @param initSpeed   sets the initial speed of the intake when
     * @return            The updated speed
     */
    public double intakeCheck(boolean noteIn, double initSpeed) {
        if (noteIn == false) {
            return 0.0;
        }
        return initSpeed;
    }

    /**
     * Gets the IR value
     * @return  The boolean of the Beam sensor
     */
    public boolean getIRValue() {
        return m_IR.get();
    }
}
