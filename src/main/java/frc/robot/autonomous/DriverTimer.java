/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Driver;

public class DriverTimer extends CommandBase {

  private Driver driver;
  private double time_, spLeft_, spRight_ = 0;
  private Timer timer = new Timer();
  
  public DriverTimer(Driver m_driver, double time, double spRight, double spLeft) {
    driver = m_driver;
    time_ = time;
    spRight_ = spRight;
    spLeft_ = spLeft;

    addRequirements(driver);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(timer.get() >= time_){
      driver.tankDriver(0, 0);
    } else{
      driver.tankDriver(spRight_, spLeft_);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driver.tankDriver(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
