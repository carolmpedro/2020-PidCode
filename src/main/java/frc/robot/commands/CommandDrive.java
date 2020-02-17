/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Driver;

public class CommandDrive extends CommandBase {

  public static final double minR = 0.4D, difR = 0.5D;
  

  private final Driver driver;
  private final XboxController xbox;
  private double linearSpeed;
  private double rotationSpeed;
  /**
   * Creates a new CommandDrive.
   */
  public CommandDrive(Driver driver_) {
    xbox = new XboxController(0);
    driver = driver_;
    addRequirements(driver);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    this.rotationSpeed= xbox.getRawAxis(1);
   
    this.linearSpeed = xbox.getRawAxis(4);
    this.arcadeDrive( this.rotationSpeed,this.linearSpeed);
  }

  public void arcadeDrive(double speed, double rotation) {
		double modifier = minR + difR * Math.pow(1 - Math.abs(speed), 2);
		double rate = Math.pow(rotation, 3) * modifier;
		driver.tankDriver((speed + rate), rate - speed);
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
