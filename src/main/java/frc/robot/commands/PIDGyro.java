/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Driver;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PIDGyro extends PIDCommand {
  /**
   * Creates a new PIDGyro.
   */
  public PIDGyro(double targetAngle, Driver driver) {
    
    super(
        // The controller that the command will use
        new PIDController(0.01, 0.015, 0.0005),
        // This should return the measurement
        driver::getAngle ,
        // This should return the setpoint (can also be a constant)
        targetAngle,
        // This uses the output
        output -> driver.arcadeDrive(0, output)
          // Use the output here
        );
        addRequirements(driver);
    // Use addRequirements() here to declare subsystem dependencies.

    getController().enableContinuousInput(-180, 180);
    getController().setTolerance(5, 5);
    // Configure additional PID options by calling `getController` here.
  }

  public void resetPID(){
    getController().reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
