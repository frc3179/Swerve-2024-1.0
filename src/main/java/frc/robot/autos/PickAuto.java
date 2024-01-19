package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.autos.AutoList;
import frc.robot.subsystems.DriveSubsystem;

public class PickAuto {

    public final AutoList m_autoList = new AutoList();

    public Command run(DriveSubsystem m_robotDrive){
        return m_autoList.autotest(m_robotDrive);
    }
}



