package frc.robot.Joystick_Commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.Subsystems.ArmSubsystem;
import frc.robot.Subsystems.DriveSubsystem;

public class JoystickDrive extends Command{
    DriveSubsystem m_RobotDrive;
    Supplier<Double> xSpeed;
    Supplier<Double> ySpeed;
    Supplier<Double> rot;
    Supplier<Boolean> resetGyro;
    Supplier<Boolean> fieldRelative;
    Supplier<Boolean> rateLimit;
    Supplier<Boolean> trackRobot;
    Supplier<Boolean> slowForJD;
    double rotation;
    double x;
    double y;
    boolean relative;
    //TODO: TUNE
    PIDController Trackpid = new PIDController(.013, 0, 0);

    public JoystickDrive(
        DriveSubsystem m_RobotDrive,
        Supplier<Double> xSpeed,
        Supplier<Double> ySpeed,
        Supplier<Double> rot,
        Supplier<Boolean> resetGyro,
        Supplier<Boolean> fieldRelative,
        Supplier<Boolean> rateLimit,
        Supplier<Boolean> trackRobot,
        Supplier<Boolean> slowForJD
        ) {

        this.m_RobotDrive = m_RobotDrive;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rot = rot;
        this.resetGyro = resetGyro;
        this.fieldRelative = fieldRelative;
        this.rateLimit = rateLimit;
        this.trackRobot = trackRobot;
        this.slowForJD = slowForJD;

        addRequirements(m_RobotDrive);
    }

    @Override
    public void initialize() { 
        Trackpid.setTolerance(0.05);
        Trackpid.setSetpoint(0);
        NetworkTableInstance.getDefault().flush();
    }

    @Override
    public void execute() {
        if(trackRobot.get() == true) {
            rotation = Trackpid.calculate(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0/*doesNotMatter*/));
            x = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0/*Does not matter*/);
            relative = false;
            y = 0;

            x = x<0?-0.5:0;

        } else {
            rotation = rot.get();
            x = xSpeed.get();
            relative = fieldRelative.get();
            y = ySpeed.get();
        }

        m_RobotDrive.drive(
            x,
            y, 
            rotation, 
            resetGyro.get(), 
            relative,
            rateLimit.get(),  
            slowForJD.get()
        );
    } 

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() { 
        return false;
    } //This command does not finish on its own 
}