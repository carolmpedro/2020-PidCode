/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Storage extends SubsystemBase {

  public boolean stSolenoid = true;
  
  private VictorSP  storage = new VictorSP(2);
  private Solenoid solenoidStorage = new Solenoid(0);

  public Storage() {
  }

  public void storageActive(double speed){
    storage.set(speed);
  }

  public void solenoidOn(boolean st){
    solenoidStorage.set(st);
  }

  @Override
  public void periodic() {
  }
}
