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
import frc.robot.PID.TargetPID;
import frc.robot.autonomous.DriverTimer;
import frc.robot.autonomous.SequentialAuto;
import frc.robot.autonomous.ShooterTimer;
import frc.robot.commands.CommandDrive;
import frc.robot.commands.ControlPanelMove;
import frc.robot.commands.Elevator;
import frc.robot.commands.IntakeActive;
import frc.robot.commands.RobotUp;
import frc.robot.commands.SolenoidOn;
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

  private double angle = 0;

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
  private final IntakeActive intakeActive = new IntakeActive(m_intake, 0, storage);
  private final ToSlopeIntake slopeIntake = new ToSlopeIntake(m_intake);
  private final SolenoidOn solenoid = new SolenoidOn(storage);
  private final StorageOn storageOn = new StorageOn(storage, 0);
  private final RobotUp robotUp = new RobotUp(climber);
  private final Elevator elevator = new Elevator(climber, 0);
  private final ControlPanelMove movePanel = new ControlPanelMove(controlPanel, 0, 0);

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_driver.setDefaultCommand(driver);
    climber.setDefaultCommand(robotUp);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
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
    JoystickButton j_fire = new JoystickButton(manche, 2);
    JoystickPOVButton pov_1 = new JoystickPOVButton(manche, 1);
    JoystickPOVButton pov_2 = new JoystickPOVButton(manche, 5);

    //PID Buttons 
    d_A.whenPressed(new TargetPID(m_driver));
    //d_B.whenPressed(new Reseet(m_driver));
    //d_X.whenPressed(new GyroTurn(m_driver, 90) );
    d_Y.whenPressed(new PIDGyro(90, m_driver));

    //Teleoperated Buttons
    d_LT.whileHeld(new IntakeActive(m_intake, 0.5, storage));
    d_RT.whileHeld(new IntakeActive(m_intake, -0.5, storage));
    j_three.whenPressed(new ToSlopeIntake(m_intake));

    d_X.whenPressed(new SolenoidOn(storage));  
    d_LB.whenPressed(new StorageOn(storage, 0.5));
    d_RB.whenPressed(new StorageOn(storage, -0.5));

    pov_1.whenPressed(new Elevator(climber, 0.5));
    pov_2.whenPressed(new Elevator(climber, -0.5));

    d_B.whenPressed(new ControlPanelMove(controlPanel, 0.4, 6));
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
