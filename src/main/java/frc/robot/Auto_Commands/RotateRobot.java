package frc.robot.Auto_Commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.PIDValues;
import frc.robot.subsystems.DriveSubsystem;

public class RotateRobot extends Command{
    DriveSubsystem m_Drive;
    double degree;
    PIDController m_drivePID = new PIDController(PIDValues.kDriveP, PIDValues.kDriveI, PIDValues.kDriveD);
    
    public RotateRobot(DriveSubsystem m_Drive, double degree) {
        this.m_Drive = m_Drive;
        this.degree = degree;

        addRequirements(m_Drive);
    }

    @Override
    public void initialize() {
        m_Drive.drive(0, 0, 0, false, false, false); //reset
        m_drivePID.setTolerance(PIDValues.kDriveTolerance);
        m_drivePID.setSetpoint(degree);
    }

    @Override
    public void execute() {
        m_Drive.drive(0, 0, m_drivePID.calculate(m_Drive.convertGyroRange(m_Drive.m_gyro.getAngle())), false, false, false);
    }

    @Override
    public void end(boolean interrupted) {
        m_Drive.drive(0, 0, 0, false, false, false);
    }

    @Override
    public boolean isFinished() {
        return m_drivePID.atSetpoint();
    }
}
