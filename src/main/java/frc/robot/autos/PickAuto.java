package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Robot;

public class PickAuto {

    public final AutoList m_autoList = new AutoList();

    public Command run(DriveSubsystem m_robotDrive, ArmSubsystem m_ArmMove){
        String picked = Robot.autoSelectedDashboard;
        switch(picked){
            case Robot.kAuto1:
                //auto 1
                break;
        }
        
        //default
        return m_autoList.autotest(m_robotDrive, m_ArmMove);
    }
}



