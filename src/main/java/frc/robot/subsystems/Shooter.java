/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PID.Constants_Velocity;

public class Shooter extends SubsystemBase {

  private TalonSRX shooter = new TalonSRX(0);

  public Shooter() {
    shooter.setInverted(false);
    shooterInit();
	  shooter.setNeutralMode(NeutralMode.Brake);
	  resetEncoder();
  }
  
  public void shooter(double speed){
    shooter.set(ControlMode.PercentOutput, speed);
  }
   public void moveToVelocity(double distance){
	  shooter.set(ControlMode.Velocity, distance);
  }
 
  public int resetEncoder(){
	  return shooter.getSelectedSensorVelocity(Constants_Velocity.kPIDLoopIdx);
  }

  public int velocityShooter(){
    return shooter.getSelectedSensorVelocity();
  }

  public int loopError(){
    return shooter.getClosedLoopError(Constants_Velocity.kPIDLoopIdx);
  }

  public void shooterInit(){
    shooter.configFactoryDefault();

		/* Config sensor used for Primary PID [Velocity] */
        shooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                                            Constants_Velocity.kPIDLoopIdx, 
                                            Constants_Velocity.kTimeoutMs);

        /**
		 * Phase sensor accordingly. 
         * Positive Sensor Reading should match Green (blinking) Leds on Talon
         */
		shooter.setSensorPhase(true);

		/* Config the peak and nominal outputs */
		shooter.configNominalOutputForward(0, Constants_Velocity.kTimeoutMs);
		shooter.configNominalOutputReverse(0, Constants_Velocity.kTimeoutMs);
		shooter.configPeakOutputForward(1, Constants_Velocity.kTimeoutMs);
		shooter.configPeakOutputReverse(-1, Constants_Velocity.kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		shooter.config_kF(Constants_Velocity.kPIDLoopIdx, Constants_Velocity.kGains_Velocit.kF, Constants_Velocity.kTimeoutMs);
		shooter.config_kP(Constants_Velocity.kPIDLoopIdx, Constants_Velocity.kGains_Velocit.kP, Constants_Velocity.kTimeoutMs);
		shooter.config_kI(Constants_Velocity.kPIDLoopIdx, Constants_Velocity.kGains_Velocit.kI, Constants_Velocity.kTimeoutMs);
		shooter.config_kD(Constants_Velocity.kPIDLoopIdx, Constants_Velocity.kGains_Velocit.kD, Constants_Velocity.kTimeoutMs);
	}
  @Override
  public void periodic() {
    //SmartDashboard.putNumber("Velocity PID", velocityShooter());
    SmartDashboard.putNumber("Loop Error", loopError());
    SmartDashboard.putNumber("Sensor Velocity", velocityShooter());
    
    
  }
}
