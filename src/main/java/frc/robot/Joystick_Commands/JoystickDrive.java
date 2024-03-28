package frc.robot.Joystick_Commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class JoystickDrive extends Command{
    DriveSubsystem m_RobotDrive;
    Supplier<Double> xSpeed;
    Supplier<Double> ySpeed;
    Supplier<Double> rot;
    Supplier<Boolean> resetGyro;
    Supplier<Boolean> fieldRelative;
    Supplier<Boolean> rateLimit;
    Supplier<Boolean> trackRobot;
    Supplier<Boolean> fastForJD;
    Supplier<Boolean> slowForJd;
    PIDController Trackpid = new PIDController(0.01, 0, 999999999);
    double rotation;
    double x;
    double y;

    public JoystickDrive(
        DriveSubsystem m_RobotDrive,
        Supplier<Double> xSpeed,
        Supplier<Double> ySpeed,
        Supplier<Double> rot,
        Supplier<Boolean> resetGyro,
        Supplier<Boolean> fieldRelative,
        Supplier<Boolean> rateLimit,
        Supplier<Boolean> trackRobot,
        Supplier<Boolean> fastForJD,
        Supplier<Boolean> slowForJd
        ) {

        this.m_RobotDrive = m_RobotDrive;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rot = rot;
        this.resetGyro = resetGyro;
        this.fieldRelative = fieldRelative;
        this.rateLimit = rateLimit;
        this.trackRobot = trackRobot;
        this.fastForJD = fastForJD;
        this.slowForJd = slowForJd;

        addRequirements(m_RobotDrive);
    }

    @Override
    public void initialize() { 
        Trackpid.setTolerance(0.1);
        Trackpid.setSetpoint(0);
    }

    @Override
    public void execute() {
        //Default
        x = xSpeed.get()/2;
        y = ySpeed.get()/2;
        rotation = rot.get()/2;

        //Speed Change
        if(fastForJD.get() == true) {
            x = xSpeed.get();
            y = ySpeed.get();
            rotation = rot.get();
        }

        if(slowForJd.get() == true) {
            x = xSpeed.get()/4;
            y = ySpeed.get()/4;
            rotation = rot.get()*0.3; //TODO: Tune
        }

        //Track robot
        if(trackRobot.get() == true) {
            rotation = Trackpid.calculate(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(ArmSubsystem.upDownEncoder.get()));
        }

        //Put the values to Drive
        m_RobotDrive.drive(
            x,
            y,
            rotation, 
            resetGyro.get(), 
            fieldRelative.get(), 
            rateLimit.get()
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