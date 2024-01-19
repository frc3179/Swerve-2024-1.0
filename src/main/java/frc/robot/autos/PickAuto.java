package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.autos.AutoList;

public class PickAuto {

    public final AutoList m_autoList = new AutoList();

    public Command run(){
        return m_autoList.autotest();
    }
}



