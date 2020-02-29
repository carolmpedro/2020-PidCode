/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.PID;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Driver;

public class Reseet extends CommandBase {
  private final Driver driver;
  private final PIDGyro resetPID;
  /**
   * Creates a new Reseet.
   */
  public Reseet(Driver m_driver) {
    driver = m_driver;
    resetPID = new PIDGyro(0, driver);
    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //driver.zeroSensors();
    driver.resetGyro();
    resetPID.resetPID();
    
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
