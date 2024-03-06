# ArmSubsystem Documentation

The `ArmSubsystem` class manages various elements related to our robot arm.

## Motors

1. **LupDown**: Controls the left up-down movement of the arm.
2. **RupDown**: Controls the right up-down movement of the arm.
3. **lShoot**: Manages the left shooting mechanism.
4. **rShoot**: Manages the right shooting mechanism.
5. **intake**: Controls the intake mechanism.

## Encoder

- **UpDownEncoder**: This encoder is connected to the bore of the `RupDown` motor. It tells us where the arm is.

## Timer

- **Armtimer**: A timer we use to track how long the arm moves.

## Functions (Summaries)

1. **armMove**: Moves the arm to a specific position.
2. **armMoveTime**: Moves the arm for a specified duration.
3. **intakeCheck**: Checks if the intake mechanism is working.
4. **armMoveRotations**: Moves the arm a certain number of rotations.
5. **angleToRotations**: Converts an angle to motor rotations.
6. **limelightToAngle**: Converts Limelight data to an arm angle.

## Usage

- Create an `ArmSubsystem` object to control the arm's motors, encoder, and timer.
- Use the provided functions to make the arm do what you need.