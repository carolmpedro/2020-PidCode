/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.CommandDrive;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.PIDGyro;
import frc.robot.commands.Reseet;
import frc.robot.commands.TargetPID;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private double angle = 0;
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Driver m_driver = new Driver();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final TargetPID pidT = new TargetPID(m_driver); 
  private final CommandDrive driver = new CommandDrive(m_driver);
  private final Reseet reset = new Reseet(m_driver);
  private final GyroTurn gyro = new GyroTurn(m_driver, 90);
  private final PIDGyro gyroPID = new PIDGyro(90, m_driver);
    
  

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_driver.setDefaultCommand(driver);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    XboxController xbox = new XboxController(0);
    JoystickButton d_A = new JoystickButton(xbox, 1);
    JoystickButton d_B = new JoystickButton(xbox, 2);
    JoystickButton d_X = new JoystickButton(xbox, 3);
    JoystickButton d_Y = new JoystickButton(xbox, 4);
    

    d_A.whenPressed(new TargetPID(m_driver));
    d_B.whenPressed(new Reseet(m_driver));
    d_X.whenPressed(new GyroTurn(m_driver, 90) );
    d_Y.whenPressed(new PIDGyro(90, m_driver));
  }

  public void gyroC(){
    m_driver.calibrate();
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
