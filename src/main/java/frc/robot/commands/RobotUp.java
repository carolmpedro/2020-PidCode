/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class RobotUp extends CommandBase {
  
  private final Climber climber; 
  private Joystick manche = new Joystick(1);

  public RobotUp(Climber m_climber) {
    climber = m_climber;
    addRequirements(climber);  
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    climber.robotUp(manche.getRawAxis(1));

  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
