/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Driver;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SequentialAuto extends SequentialCommandGroup {

  //private final Shooter shooter;
  //private final Storage storage;
  /**
   * Creates a new SequentialAuto.
   */
  public SequentialAuto(Shooter shooter_, Storage storage_, Driver driver_) {  
      super(new ShooterTimer(shooter_, storage_, 5), new DriverTimer(driver_, 4, -0.7, -0.7));

    
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
  }
  
}
