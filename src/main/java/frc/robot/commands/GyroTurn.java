/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Driver;

public class GyroTurn extends CommandBase {

  private final Driver m_driver;
  
  private double currentAngle;
  private double targetAngle;

  public GyroTurn(Driver driver, double angle) {
    targetAngle = angle;
    m_driver = driver;
    addRequirements(m_driver);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.currentAngle = m_driver.getAngle();
    if(this.targetAngle > 0 && this.currentAngle < this.targetAngle){
      m_driver.tankDriver(-0.6, 0.6);
    } else if(this.targetAngle < 0 && this.currentAngle < this.targetAngle){
      m_driver.tankDriver(0.6, -0.6);
    } else m_driver.tankDriver(0, 0);
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
