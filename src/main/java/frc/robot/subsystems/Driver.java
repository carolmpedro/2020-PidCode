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
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.cscore.CameraServerCvJNI;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PID.Constants;

public class Driver extends SubsystemBase {

 private WPI_TalonSRX masterLeft = new WPI_TalonSRX(3);
 private WPI_TalonSRX slaveLeft  = new WPI_TalonSRX(4);
 private WPI_TalonSRX masterRight  = new WPI_TalonSRX(1);
 private WPI_TalonSRX slaveRight  = new WPI_TalonSRX(2);

 
 public static final double minR = 0.4D, difR = 0.5D;

 private ADXRS450_Gyro gyro = new ADXRS450_Gyro();



  public Driver() {
    masterLeft.setNeutralMode(NeutralMode.Brake);
    masterRight.setNeutralMode(NeutralMode.Brake);
    masterRight.selectProfileSlot(Constants.kSlot_Distanc, Constants.PID_PRIMARY);
    masterRight.selectProfileSlot(Constants.kSlot_Turning, Constants.PID_TURN);
    
    //slaveRight.setInverted(false);
    slaveLeft.setInverted(false);

    zeroSensors();
    driverPIDinit();
  }



public void arcadeDrive(double speed, double rotation) {
  double modifier = minR + difR * Math.pow(1 - Math.abs(speed), 2);
  double rate = Math.pow(rotation, 3) * modifier;
 tankDriver((speed - rate), -(rate + speed));
 
 slaveLeft.follow(masterLeft);
 slaveRight.follow(masterRight);
}

public void calibrate(){
  gyro.calibrate();
}

public void resetGyro(){
  gyro.reset();
}

public double getAngle(){
  return gyro.getAngle();
}

public void zeroSensors() {
	masterLeft.getSensorCollection().setQuadraturePosition(0, Constants.kTimeoutMs);
	masterRight.getSensorCollection().setQuadraturePosition(0, Constants.kTimeoutMs);
}
 
public void tankDriver(double spR, double spL){
  masterLeft.set(ControlMode.PercentOutput, spL);
  masterRight.set(ControlMode.PercentOutput, spR);
}

public void reset(){
  masterLeft.getSensorCollection().setQuadraturePosition(0, Constants.kTimeoutMs);
  masterRight.getSensorCollection().setQuadraturePosition(0, Constants.kTimeoutMs);
}

public void setPosition(double position){
  /* Configured for Position Closed loop on Quad Encoders' Sum and Auxiliary PID on Quad Encoders' Difference */
  masterRight.set(ControlMode.Position, position, DemandType.AuxPID, 0.7);
  masterLeft.follow(masterRight, FollowerType.AuxOutput1);
  masterLeft.selectProfileSlot(Constants.kSlot_Distanc, Constants.PID_PRIMARY);
  masterRight.selectProfileSlot(Constants.kSlot_Turning, Constants.PID_TURN);
  
  slaveRight.follow(masterRight, FollowerType.AuxOutput1);
  slaveLeft.follow(masterLeft, FollowerType.AuxOutput1);
  //masterRight.set(ControlMode.Position, position);
}


  public void driverPIDinit(){

    masterRight.set(ControlMode.PercentOutput, 0);
    masterLeft.set(ControlMode.PercentOutput, 0);
   
    masterRight.configFactoryDefault();
    masterLeft.configFactoryDefault();
    
    masterLeft.configSelectedFeedbackSensor(	FeedbackDevice.QuadEncoder,				// Local Feedback Source
                          Constants.PID_PRIMARY,					// PID Slot for Source [0, 1]
                          Constants.kTimeoutMs);					// Configuration Timeout
   
    /* Configure the Remote Talon's selected sensor as a remote sensor for the right Talon */
    masterRight.configRemoteFeedbackFilter(masterLeft.getDeviceID(),					// Device ID of Source
                        RemoteSensorSource.TalonSRX_SelectedSensor,	// Remote Feedback Source
                        Constants.REMOTE_1,							// Source number [0, 1]
                        Constants.kTimeoutMs);						// Configuration Timeout
    
    /* Setup Sum signal to be used for Distance */
    masterRight.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor1, Constants.kTimeoutMs);				// Feedback Device of Remote Talon
    masterRight.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kTimeoutMs);	// Quadrature Encoder of current Talon
    
    /* Setup Difference signal to be used for Turn */
    masterRight.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor1, Constants.kTimeoutMs);
    masterRight.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kTimeoutMs);
    
    /* Configure Sum [Sum of both QuadEncoders] to be used for Primary PID Index */
    masterRight.configSelectedFeedbackSensor(	FeedbackDevice.SensorSum, 
                          Constants.PID_PRIMARY,
                          Constants.kTimeoutMs);
    
    /* Scale Feedback by 0.5 to half the sum of Distance */
    masterRight.configSelectedFeedbackCoefficient(	0.5, 						// Coefficient
                            Constants.PID_PRIMARY,		// PID Slot of Source 
                            Constants.kTimeoutMs);		// Configuration Timeout
    
    /* Configure Difference [Difference between both QuadEncoders] to be used for Auxiliary PID Index */
    masterRight.configSelectedFeedbackSensor(	FeedbackDevice.SensorDifference, 
                          Constants.PID_TURN, 
                          Constants.kTimeoutMs);
    
    /* Scale the Feedback Sensor using a coefficient */
    masterRight.configSelectedFeedbackCoefficient(1, Constants.PID_TURN, Constants.kTimeoutMs);
    
    /* Configure output and sensor direction */
    masterLeft.setInverted(false);
    masterLeft.setSensorPhase(true);
    masterRight.setInverted(false);
    masterRight.setSensorPhase(true);
    
    /* Set status frame periods to ensure we don't have stale data */
    masterRight.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, Constants.kTimeoutMs);
    masterRight.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, Constants.kTimeoutMs);
    masterRight.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, Constants.kTimeoutMs);
    masterLeft.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, Constants.kTimeoutMs);
   
    /* Configure neutral deadband */
    masterRight.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);
    masterLeft.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);
   
    /* Max out the peak output (for all modes).  
     * However you can limit the output of a given PID object with configClosedLoopPeakOutput().
     */
    masterLeft.configPeakOutputForward(+1.0, Constants.kTimeoutMs);
    masterLeft.configPeakOutputReverse(-1.0, Constants.kTimeoutMs);
    masterRight.configPeakOutputForward(+1.0, Constants.kTimeoutMs);
    masterRight.configPeakOutputReverse(-1.0, Constants.kTimeoutMs);
   
    /* FPID Gains for distance servo */
    masterRight.config_kP(Constants.kSlot_Distanc, Constants.kGains_Distanc.kP, Constants.kTimeoutMs);
    masterRight.config_kI(Constants.kSlot_Distanc, Constants.kGains_Distanc.kI, Constants.kTimeoutMs);
    masterRight.config_kD(Constants.kSlot_Distanc, Constants.kGains_Distanc.kD, Constants.kTimeoutMs);
    masterRight.config_kF(Constants.kSlot_Distanc, Constants.kGains_Distanc.kF, Constants.kTimeoutMs);
    masterRight.config_IntegralZone(Constants.kSlot_Distanc, Constants.kGains_Distanc.kIzone, Constants.kTimeoutMs);
    masterRight.configClosedLoopPeakOutput(Constants.kSlot_Distanc, Constants.kGains_Distanc.kPeakOutput, Constants.kTimeoutMs);
   
    /* FPID Gains for turn servo */
    masterRight.config_kP(Constants.kSlot_Turning, Constants.kGains_Turning.kP, Constants.kTimeoutMs);
    masterRight.config_kI(Constants.kSlot_Turning, Constants.kGains_Turning.kI, Constants.kTimeoutMs);
    masterRight.config_kD(Constants.kSlot_Turning, Constants.kGains_Turning.kD, Constants.kTimeoutMs);
    masterRight.config_kF(Constants.kSlot_Turning, Constants.kGains_Turning.kF, Constants.kTimeoutMs);
    masterRight.config_IntegralZone(Constants.kSlot_Turning, Constants.kGains_Turning.kIzone, Constants.kTimeoutMs);
    masterRight.configClosedLoopPeakOutput(Constants.kSlot_Turning, Constants.kGains_Turning.kPeakOutput, Constants.kTimeoutMs);
      
    /* 1ms per loop.  PID loop can be slowed down if need be.
     * For example,
     * - if sensor updates are too slow
     * - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
     * - sensor movement is very slow causing the derivative error to be near zero.
     */
        int closedLoopTimeMs = 1;
        masterRight.configClosedLoopPeriod(0, closedLoopTimeMs, Constants.kTimeoutMs);
        masterRight.configClosedLoopPeriod(1, closedLoopTimeMs, Constants.kTimeoutMs);
   
    /* configAuxPIDPolarity(boolean invert, int timeoutMs)
     * false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
     * true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
     */
    masterRight.configAuxPIDPolarity(false, Constants.kTimeoutMs);
   
   }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("GetAngle", getAngle());
    // This method will be called once per scheduler run
  }
}
