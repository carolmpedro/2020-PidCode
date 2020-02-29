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
  }

  @Override
  public void execute() {
    if(timer.get() >= time_){
      storage.storageActive(0);
      shooter.shooter(0);
    } else{
      storage.storageActive(0.6);
      shooter.shooter(0.6);
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
