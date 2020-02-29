package frc.robot.buttons;
import edu.wpi.first.wpilibj.XboxController;

public class JoystickAxis {
	public XboxController joystick;
	public int axis;

	public JoystickAxis(XboxController xbox, int axisID) {
		joystick = xbox;
		axis = axisID;
	}

	public double get() {
		return joystick.getRawAxis(axis);
	}
}