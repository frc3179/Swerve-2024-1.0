package frc.robot.PathPlanner_Commands;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class RobotTrack extends Command{
    DriveSubsystem m_Drive;
    PIDController pid = new PIDController(0.01, 0, 999999999);

    public RobotTrack(DriveSubsystem m_Drive) {
        this.m_Drive = m_Drive;
        addRequirements(m_Drive);
    }

    @Override
    public void initialize() {
        m_Drive.drive(0, 0, 0,false, false, false);
        pid.setSetpoint(0);
        pid.setTolerance(1);
    }

    @Override
    public void execute() {
        m_Drive.drive(0, 0, pid.calculate(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(ArmSubsystem.upDownEncoder.get())), false, false, false);
    }

    @Override
    public void end(boolean interrupted) {
        m_Drive.drive(0, 0, 0, false, false,false);
    }

    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }
}
