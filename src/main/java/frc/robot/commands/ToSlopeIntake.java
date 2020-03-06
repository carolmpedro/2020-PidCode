/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class ToSlopeIntake extends CommandBase {
  
  private final Intake intake;
  private final Timer timer = new Timer();
  private double time = 5;
  private double sp_ = 0;

  public ToSlopeIntake(Intake intake_, double sp) {
    intake = intake_;
    sp_ = sp;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }


  @Override
  public void execute() {
    SmartDashboard.putBoolean("status", intake.status);

    intake.slopeIntake(sp_);

   /*if(timer.get() >= time){
      intake.slopeIntake(0);;
    } else {
      intake.slopeIntake(sp_);
    }


    /*if (intake.status){
      intake.slopeIntake(0.7);
      if(timer.get() >= time){
        intake.intake(0.0);
      }
    } else {
      intake.slopeIntake(-0.3);{
        if(timer.get() >= time){
          intake.intake(0.0);
        }
      }
    }


    /*if(intake.limitSwitchUp()){ 
      intake.status = !intake.status;    
      } 

    if(intake.status){
      intake.slopeIntake(0.5);
    } 

    if (intake.limitSwitchDown()){ 
      intake.status = !intake.status; 
       }
     
      if(intake.status == false){
       intake.slopeIntake(-0.5);
      }*/
  }

  @Override
  public void end(boolean interrupted) {
    intake.slopeIntake(0);

    
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
