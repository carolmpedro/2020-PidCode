/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class Elevator extends CommandBase {
  
  private final Climber climber ;
  private double speed;
  
  public Elevator(Climber climber_, double sp) {
    speed = sp;
    climber = climber_;
    addRequirements(climber);
    }


  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    /*if(speed < 0){
      if(climber.limitSwitchElevator()){
        climber.elevatorMove(speed);
        } else {         
        climber.elevatorMove(speed);
        }
    } else {
      climber.elevatorMove(speed);
    }*/

  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
