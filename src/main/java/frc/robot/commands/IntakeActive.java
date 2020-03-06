/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;

public class IntakeActive extends CommandBase {

  private final Intake intake;
  private final Storage storage;
  private final Shooter shooter_;
  private double speed =0;
  
  public IntakeActive(Intake intake_, double speed_, Storage storage_, Shooter shooter) {
    storage = storage_;
    speed = speed_;
    intake = intake_;
    shooter_ = shooter;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    intake.intake(-speed);
    storage.storageActive(-0.65);
    shooter_.shooter(0.45);
  }

  @Override
  public void end(boolean interrupted) { 
    intake.intake(0);
    storage.storageActive(0);
    shooter_.shooter(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
