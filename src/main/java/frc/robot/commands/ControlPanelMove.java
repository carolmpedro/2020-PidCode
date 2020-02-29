/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanel;

public class ControlPanelMove extends CommandBase {

  private final ControlPanel controlPanel;
  private double speed, time;
  private Timer timer = new Timer();

  public ControlPanelMove(ControlPanel control_, double speed_, double time_) {
    controlPanel = control_;
    speed = speed_;
    time = time_;
    addRequirements(controlPanel);
  }

  @Override
  public void initialize() {
    timer.start();
  }

  @Override
  public void execute() {
    if(timer.get() >= time){   
     controlPanel.rotationPanel(0);
    }
     controlPanel.rotationPanel(speed);
  }

  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
