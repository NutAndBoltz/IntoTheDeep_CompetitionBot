package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Drive", group="Robot")
public class Drive extends LinearOpMode {

    // Define drive speed
    public static final double DRIVE_SPEED = 0.6;
    public static final double LINEAR_SLIDE_SPEED = 0.7;
    public static final double ARM_SPEED = 0.06;
    public static final double WRIST_SPEED = 0.02;

    // Define deadzone threshold
    public static final double DEADZONE = 0.1;

    // Define positions for 90-degree arm and wrist
    public static final double ARM_90_DEGREES = 0.5; // Adjust this value as needed
    public static final double WRIST_90_DEGREES = 0.5; // Adjust this value as needed

    // Create a RobotHardware object
    RobotHardware robot = new RobotHardware(this);

    // Variables for toggle controls
    private boolean clawClosed = false;
    private boolean lastClawToggleState = false;

    @Override
    public void runOpMode() {
        double vertical;
        double horizontal;
        double turn;

        // Initialize all the hardware
        robot.init();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Mecanum drive controls with deadzone and exponential control
            vertical = -applyExponentialControl(applyDeadzone(gamepad1.left_stick_y)) * DRIVE_SPEED;
            horizontal = applyExponentialControl(applyDeadzone(gamepad1.left_stick_x)) * DRIVE_SPEED;
            turn = -applyExponentialControl(applyDeadzone(gamepad1.right_stick_x)) * DRIVE_SPEED;

            double frontLeftPower = vertical + horizontal - turn;
            double frontRightPower = vertical - horizontal + turn;
            double backLeftPower = vertical - horizontal - turn;
            double backRightPower = vertical + horizontal + turn;

            // Normalize wheel powers to be within -1.0 to 1.0
            double max = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower),
                    Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
            if (max > 0.75) {
                frontLeftPower /= max;
                frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }

            robot.frontLeftDrive.setPower(-frontLeftPower);
            robot.frontRightDrive.setPower(-frontRightPower);
            robot.backLeftDrive.setPower(backLeftPower);
            robot.backRightDrive.setPower(backRightPower);

            // Linear slide control with deadzone
            robot.linearSlide.setPower(-applyDeadzone(gamepad2.left_stick_y) * LINEAR_SLIDE_SPEED);

            // Arm control using right stick with deadzone
            double armPosition = robot.armServo.getPosition();
            armPosition += -applyDeadzone(gamepad2.right_stick_y) * ARM_SPEED;
            armPosition = Range.clip(armPosition, 0, 1); // Clamp between 0 and 1
            robot.armServo.setPosition(armPosition);

            // Slide claw control
            if (gamepad2.right_bumper) {
                robot.slideClawServo.setPosition(robot.SLIDE_CLAW_CLOSED);
            } else if (gamepad2.left_bumper) {
                robot.slideClawServo.setPosition(robot.SLIDE_CLAW_OPEN);
            }

            // Bucket control
            if (gamepad2.dpad_up) {
                robot.bucketServo.setPosition(robot.BUCKET_UP);
            } else if (gamepad2.dpad_down) {
                robot.bucketServo.setPosition(robot.BUCKET_DOWN);
            }

            // Claw control with toggle
            boolean clawToggleState = gamepad1.right_bumper;
            if (clawToggleState && !lastClawToggleState) {
                clawClosed = !clawClosed;
                robot.clawServo.setPosition(clawClosed ? robot.CLAW_CLOSED : robot.CLAW_OPEN);
            }
            lastClawToggleState = clawToggleState;

            // Wrist control
            if (gamepad2.y) {
                robot.wristServo.setPosition(robot.WRIST_UP);
            } else if (gamepad2.x) {
                robot.wristServo.setPosition(robot.WRIST_DOWN);
            }

            // New control for 90-degree arm and wrist position
            if (gamepad1.a) { // Using 'A' button on gamepad1, change as needed
                robot.armServo.setPosition(ARM_90_DEGREES);
                robot.wristServo.setPosition(WRIST_90_DEGREES);
            }

            // Send telemetry message to signify robot running
            telemetry.addData("Status", "Running");
            telemetry.addData("Drive Motors", "FL (%.2f), FR (%.2f), BL (%.2f), BR (%.2f)",
                    frontLeftPower, frontRightPower, backLeftPower, backRightPower);
            telemetry.addData("Linear Slide", "Power (%.2f)", robot.linearSlide.getPower());
            telemetry.addData("Slide Claw", "Position (%.2f)", robot.slideClawServo.getPosition());
            telemetry.addData("Bucket", "Position (%.2f)", robot.bucketServo.getPosition());
            telemetry.addData("Arm", "Position (%.2f)", robot.armServo.getPosition());
            telemetry.addData("Claw", "Position (%.2f), Closed (%b)", robot.clawServo.getPosition(), clawClosed);
            telemetry.addData("Wrist", "Position (%.2f)", robot.wristServo.getPosition());
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed
            sleep(50);
        }
    }

    // Helper method to apply deadzone
    private double applyDeadzone(double value) {
        return Math.abs(value) < DEADZONE ? 0 : value;
    }

    // Helper method to apply exponential control
    private double applyExponentialControl(double value) {
        return value * Math.abs(value);
    }
}

// hi swavam was here once more