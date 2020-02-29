/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class ToSlopeIntake extends CommandBase {
  
  private final Intake intake;

  public ToSlopeIntake(Intake intake_) {
    intake = intake_;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }


  @Override
  public void execute() {
    if(intake.limitSwitchUp()){
      intake.status = !intake.status;
      } 

      if(intake.status){
        intake.intake(-0.5);
      }
    
    if (intake.limitSwitchDown()){ 
       intake.status = !intake.status;
    }

    if(intake.status == false){
      intake.intake(0.5);
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
