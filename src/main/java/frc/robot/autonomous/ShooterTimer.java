/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;

public class ShooterTimer extends CommandBase {

  private final Shooter shooter;
  private final Storage storage;

  private double time_ = 0;
  private Timer timer = new Timer();
  
  public ShooterTimer(Shooter m_shooter, Storage m_storage, double time) {
    shooter = m_shooter;
    storage = m_storage;
    time_ = time;
  }

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    shooter.moveToVelocity(10000);

    if(timer.get() >= 3){
      storage.storageActive(-0.6);
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooter.moveToVelocity(0);
    storage.storageActive(0);
  }

  @Override
  public boolean isFinished() {
    if(timer.get() >= time_){
      return true;
    } else {
      return false;
    }
  }
}
