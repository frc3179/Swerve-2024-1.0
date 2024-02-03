package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Robot;
import frc.robot.Commands.Shoot;

public class PickAuto {

    //public final AutoList m_autoList = new AutoList();

    public Command run(DriveSubsystem m_robotDrive, ArmSubsystem m_ArmMove){
        String picked = Robot.autoSelectedDashboard;
        switch(picked){
            case Robot.kAuto1:
                //return AutoList.Auto1.auto1(m_robotDrive, m_ArmMove);
        }
        
        //default
        //return AutoList.Autotest.autotest(m_robotDrive, m_ArmMove);
        return new SequentialCommandGroup(new Shoot(m_ArmMove, 0.5, 0.5));
    }
}



