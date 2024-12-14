package frc.robot.PathPlanner_Commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.DriveSubsystem;

public class TrackNote extends Command{
    double x;
    boolean relative;
    double rotation;
    double y;
    DriveSubsystem mDriveSubsystem;
    PIDController Trackpid = new PIDController(.013, 0, 0);

    public TrackNote(
        DriveSubsystem mDriveSubsystem
        ){
        
        this.mDriveSubsystem = mDriveSubsystem;

        addRequirements(mDriveSubsystem);
    }

    @Override
    public void initialize(){
        Trackpid.setTolerance(0.05);
        Trackpid.setSetpoint(0);
        NetworkTableInstance.getDefault().flush();
    }

    @Override
    public void execute(){
        rotation = Trackpid.calculate(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0/*doesNotMatter*/));
        x = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0/*Does not matter*/);
        relative = false;
        y = 0;

        x = x<0?-0.5:0;

        mDriveSubsystem.drive(
            x,
            y, 
            rotation, 
            false, 
            relative,
            true,  
            false
        );
    }
    
    @Override
    public void end(boolean interrupted){
        
    }

    @Override
    public boolean isFinished(){
        if(x > 0) {
            return true;
        }
        return false;
    }
}
