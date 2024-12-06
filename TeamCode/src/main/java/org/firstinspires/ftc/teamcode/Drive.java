package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Drive", group="Robot")
public class Drive extends LinearOpMode {

    // Define drive speed
    public static final double DRIVE_SPEED = 0.6;
    public static final double LINEAR_SLIDE_SPEED = 0.7;
    public static final double ARM_SPEED = 0.5;

    // Create a RobotHardware object
    RobotHardware robot = new RobotHardware(this);

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

            // Mecanum drive controls
            vertical = -gamepad1.left_stick_y * DRIVE_SPEED;
            horizontal = gamepad1.left_stick_x * DRIVE_SPEED;
            turn = -gamepad1.right_stick_x * DRIVE_SPEED;

            robot.frontLeftDrive.setPower(vertical + horizontal - turn);
            robot.frontRightDrive.setPower(vertical - horizontal + turn);
            robot.backLeftDrive.setPower(vertical - horizontal - turn);
            robot.backRightDrive.setPower(vertical + horizontal + turn);

            // Linear slide control
            robot.linearSlide.setPower(-gamepad2.left_stick_y * LINEAR_SLIDE_SPEED);

            // Arm control using right stick
            double armPower = -gamepad2.right_stick_y * ARM_SPEED;
            robot.armServo.setPosition(robot.armServo.getPosition() + armPower);

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

            // Claw control
            if (gamepad1.right_bumper) {
                robot.clawServo.setPosition(robot.CLAW_CLOSED);
            } else if (gamepad1.left_bumper) {
                robot.clawServo.setPosition(robot.CLAW_OPEN);
            }

            // Send telemetry message to signify robot running
            telemetry.addData("Status", "Running");
            telemetry.addData("Linear Slide", "Power (%.2f)", robot.linearSlide.getPower());
            telemetry.addData("Slide Claw", "Position (%.2f)", robot.slideClawServo.getPosition());
            telemetry.addData("Bucket", "Position (%.2f)", robot.bucketServo.getPosition());
            telemetry.addData("Arm", "Position (%.2f)", robot.armServo.getPosition());
            telemetry.addData("Claw", "Position (%.2f)", robot.clawServo.getPosition());
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed
            sleep(50);
        }
    }
}