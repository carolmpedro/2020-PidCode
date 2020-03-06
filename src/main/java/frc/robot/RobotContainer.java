/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.buttons.JoystickAxis;
import frc.robot.buttons.JoystickAxisButton;
import frc.robot.buttons.JoystickPOVButton;
import frc.robot.PID.GyroTurn;
import frc.robot.PID.PIDGyro;
import frc.robot.PID.Reseet;
import frc.robot.PID.Shooter_Move;
import frc.robot.PID.TargetPID;
import frc.robot.autonomous.DriverTimer;
import frc.robot.autonomous.SequentialAuto;
import frc.robot.autonomous.ShooterTimer;
import frc.robot.commands.CommandDrive;
import frc.robot.commands.ControlPanelMove;
import frc.robot.commands.Elevator;
import frc.robot.commands.IntakeActive;
import frc.robot.commands.RobotUp;
import frc.robot.commands.ShooterMove;
import frc.robot.commands.StorageOn;
import frc.robot.commands.ToSlopeIntake;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


public class RobotContainer {

  //subsystens
  private final Driver m_driver = new Driver();
  private final Intake m_intake = new Intake();
  private final Storage storage = new Storage();
  private final Shooter shooter = new Shooter();
  private final Climber climber = new Climber();
  private final ControlPanel controlPanel = new ControlPanel();

  //PID Commnad
  private final TargetPID pidT = new TargetPID(m_driver); 
  private final CommandDrive driver = new CommandDrive(m_driver);
  private final Reseet reset = new Reseet(m_driver);
  private final GyroTurn gyro = new GyroTurn(m_driver, 90);
  private final PIDGyro gyroPID = new PIDGyro(90, m_driver);

  //Autonomous Command
  private final DriverTimer driverTimer = new DriverTimer(m_driver, 0, 0, 0);
  private final ShooterTimer shooterTimer = new ShooterTimer(shooter, storage, 0);
  private final SequentialAuto sequentialAuto = new SequentialAuto(shooter, storage, m_driver);

  //Teleoperated Command
  private final IntakeActive intakeActive = new IntakeActive(m_intake, 0, storage, shooter);
  private final ToSlopeIntake slopeIntake = new ToSlopeIntake(m_intake, 0);
  private final StorageOn storageOn = new StorageOn(storage, 0);
  private final RobotUp robotUp = new RobotUp(climber);
  private final Elevator elevator = new Elevator(climber, 0);
  private final ControlPanelMove movePanel = new ControlPanelMove(controlPanel, 0, 0);
  private final Shooter_Move shooter_move = new Shooter_Move(shooter);

  public RobotContainer() {
    configureButtonBindings();
    m_driver.setDefaultCommand(driver);
    climber.setDefaultCommand(robotUp);
  }

  private void configureButtonBindings() {

    //Xbox Controller
    XboxController xbox = new XboxController(0);
    JoystickButton d_A = new JoystickButton(xbox, 1);
    JoystickButton d_B = new JoystickButton(xbox, 2);
    JoystickButton d_X = new JoystickButton(xbox, 3);
    JoystickButton d_Y = new JoystickButton(xbox, 4);
    JoystickButton d_LB = new JoystickButton(xbox, 5);
    JoystickButton d_RB = new JoystickButton(xbox, 6);
    JoystickAxis axis_d_LT = new JoystickAxis(xbox, 2);
    JoystickAxis axis_d_RT = new JoystickAxis(xbox, 3);
    JoystickAxisButton d_LT = new JoystickAxisButton(axis_d_LT, false, 0.5);
    JoystickAxisButton d_RT = new JoystickAxisButton(axis_d_RT, false, 0.5);

    //Manche Controller
    Joystick manche = new Joystick(1);
    JoystickButton j_three = new JoystickButton(manche, 3);
    JoystickButton j_fire = new JoystickButton(manche, 1);
    JoystickButton j_four = new JoystickButton(manche, 4);
    JoystickButton j_five = new JoystickButton(manche, 5);
    JoystickButton j_six = new JoystickButton(manche, 6);
    JoystickPOVButton pov_1 = new JoystickPOVButton(manche, 1);
    JoystickPOVButton pov_2 = new JoystickPOVButton(manche, 5);

    //PID Buttons 
    d_A.whenPressed(new TargetPID(m_driver));
    //d_B.whenPressed(new Reseet(m_driver));
    //d_X.whenPressed(new GyroTurn(m_driver, 90) );
    d_Y.whenPressed(new PIDGyro(90, m_driver));
    j_four.whileHeld(new Shooter_Move(shooter));
    d_X.whenPressed(new SequentialAuto(shooter, storage, m_driver));

    //Teleoperated Buttons
    d_LT.whileHeld(new IntakeActive(m_intake, 0.8, storage, shooter));
    d_RT.whileHeld(new IntakeActive(m_intake, 0.8, storage, shooter));
    j_five.whileHeld(new ToSlopeIntake(m_intake, 1));
    j_six.whileHeld(new ToSlopeIntake(m_intake, -0.5)); 
    d_LB.whileHeld(new StorageOn(storage, 0.5));
    d_RB.whileHeld(new StorageOn(storage, -0.5));
    pov_1.whileHeld(new Elevator(climber, 0.5));
    pov_2.whileHeld(new Elevator(climber, -0.5));
    d_B.whileHeld(new ControlPanelMove(controlPanel, 0.4, 0.5));
    j_fire.whileHeld(new ShooterMove(shooter, storage));
  }

  public void gyroC(){
    m_driver.calibrate();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public SequentialCommandGroup getAutonomousCommand() {
    return sequentialAuto;
  }
}
