package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Robot;

/*
 * <Class Summary>
 * This class creates all the necessary elements for the arm to work.
 * This includes the following:
    * Functions (see summaries below)
        * run
 * </Class Summary>
 */
public class PickAuto {

    //public final AutoList m_autoList = new AutoList();

    /*
     * <summary>
     * This command takes in the nessary subsystems that all autos could ever need.
     * It then takes the selected value from the smart dashboard and uses that to
     * deside what auto to run. It then supplies the subsystems that are nessary to 
     * run that auto and returns it to run it.
     * </summary>
     */
    public Command run(DriveSubsystem m_robotDrive, ArmSubsystem m_ArmMove){
        String picked = Robot.m_chooser.getSelected();
        switch(picked){
            case Robot.kAuto1:
                return AutoList.Auto1.auto1(m_robotDrive, m_ArmMove);
        }
        
        //default
        return AutoList.Autotest.autotest(m_robotDrive, m_ArmMove);
    }
}



