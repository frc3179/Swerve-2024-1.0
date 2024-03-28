package frc.robot.Auto_Commands;

import java.util.Optional;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LightSubsystem;

public class LightColor extends Command{
    LightSubsystem m_Light;
    Supplier<Boolean> in;
    Optional<Alliance> alliance;

    public LightColor(LightSubsystem m_Light, Supplier<Boolean> inValue) {
        this.m_Light = m_Light;
        this.in = inValue;

        addRequirements(m_Light);
    }

    @Override
    public void initialize() {
        alliance = DriverStation.getAlliance();
    }

    @Override
    public void execute() {
        if(in.get() == true) {
            m_Light.setNoteColor();
        } else if(alliance.get() == Alliance.Blue) {
            m_Light.setBlueColor();
        } else if(alliance.get() == Alliance.Red) {
            m_Light.setRedColor();
        }
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
