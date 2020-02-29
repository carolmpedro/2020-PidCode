/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

  private VictorSP climberUp = new VictorSP(4);
  private VictorSP climberUp_2 = new VictorSP(5);

  private VictorSP elevator_1 = new VictorSP(6);
  private VictorSP elevator_2 = new VictorSP(7);

  private Encoder encoderElevator = new Encoder(5, 6);
  private DigitalInput digitalInput = new DigitalInput(9);

  public Climber() {

  }

  public void robotUp (double speed){
    climberUp.set(speed);
    climberUp_2.set(speed);
  }

  public void elevatorMove(double speed){
    elevator_1.set(speed);
    elevator_2.set(speed);
  }

  public double pulsesElevator(){
    return encoderElevator.getDistance();
  }

  public boolean limitSwitchElevator(){
    return digitalInput.get();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("elevator pulses", pulsesElevator());
    SmartDashboard.putBoolean("status Elevator Limit Switch", limitSwitchElevator());
  }
}
