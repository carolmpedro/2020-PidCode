/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  
  public boolean status = true;

  private VictorSP intake =  new VictorSP(0);
  private VictorSP slopeIntake = new VictorSP(1);

  private DigitalInput limitSwitchUp = new DigitalInput(7);
  private DigitalInput limitSwitchDown = new DigitalInput(6);

  public Intake() {
  }

  public void intake(double speed){
    intake.set(speed);
  }

  public void slopeIntake(double speed){
    slopeIntake.set(speed);
  }

  public boolean limitSwitchUp(){
    return limitSwitchUp.get();
  }

  public boolean limitSwitchDown(){
    return limitSwitchDown.get();
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Status Limit Switch Down", limitSwitchDown());
    SmartDashboard.putBoolean("Status Limit Switch Up", limitSwitchUp());
  }
}
