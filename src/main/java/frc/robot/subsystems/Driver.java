/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PID.Constants;

public class Driver extends SubsystemBase {

 private WPI_TalonSRX masterLeft = new WPI_TalonSRX(0);
 private WPI_TalonSRX slaveLeft  = new WPI_TalonSRX(2);
 private WPI_TalonSRX masterRight  = new WPI_TalonSRX(3);
 private WPI_TalonSRX slaveRight  = new WPI_TalonSRX(1);

 private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
  

  public Driver() {
    masterLeft.setNeutralMode(NeutralMode.Brake);
    masterRight.setNeutralMode(NeutralMode.Brake);
    driverPIDinit();
  }

  public double getAngle(){
    return gyro.getAngle();
  }



 
public void tankDriver(double spR, double spL){
  masterLeft.set(ControlMode.PercentOutput, spL);
  masterRight.set(ControlMode.PercentOutput, spR);
  
 slaveRight.follow(masterRight);
 slaveLeft.follow(masterLeft);
}



public void setPosition(double position){
  /* Configured for Position Closed loop on Quad Encoders' Sum and Auxiliary PID on Quad Encoders' Difference */
 masterRight.set(ControlMode.Position, position);
 masterLeft.follow(masterRight);
 masterLeft.setInverted(true);
 
 slaveRight.follow(masterRight);
 slaveLeft.follow(masterLeft);
}

  public void driverPIDinit(){
    masterRight.configFactoryDefault();

    masterRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx,Constants.kTimeoutMs);

  masterRight.setSensorPhase(Constants.kSensorPhase);

    masterRight.setInverted(Constants.kMotorInvert);

		masterRight.configNominalOutputForward(0, Constants.kTimeoutMs);
		masterRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
		masterRight.configPeakOutputForward(1, Constants.kTimeoutMs);
		masterRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		masterRight.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

		masterRight.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		masterRight.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		masterRight.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		masterRight.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);

		int absolutePosition = masterRight.getSensorCollection().getPulseWidthPosition();

		absolutePosition &= 0xFFF;
		if (Constants.kSensorPhase) { absolutePosition *= -1; }
		if (Constants.kMotorInvert) { absolutePosition *= -1; }

    masterRight.
    setSelectedSensorPosition(absolutePosition, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
   }

   public void reset(){
     gyro.reset();
   }

  public void resetEncoder(){
	  masterRight.setSelectedSensorPosition(0, 0, 30);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("angle", getAngle());
  
    // This method will be called once per scheduler run
  }
}
