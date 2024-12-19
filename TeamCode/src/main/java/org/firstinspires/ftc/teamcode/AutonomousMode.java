package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="AutonomousMode", group="Robot")
public class AutonomousMode extends LinearOpMode {

    RobotHardware robot = new RobotHardware(this);
    private ElapsedTime runtime = new ElapsedTime();

    // Define drive speed
    public static final double DRIVE_SPEED = 0.6;
    public static final double LINEAR_SLIDE_SPEED = 0.7;
    public static final double ARM_SPEED = 0.3;
    public static final double WRIST_SPEED = 0.02;

    // Define deadzone threshold
    public static final double DEADZONE = 0.1;

    @Override
    public void runOpMode() {
        robot.init();

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // Step 1: Drive forward
            driveForTime(1.0, 0.0, 1.0);

            // Step 2: Bring the linear slide up
            moveLinearSlide(LINEAR_SLIDE_SPEED, 2.0);

            // Step 3: Drive forward a little more
            driveForTime(0.3, 0.0, 0.3);

            // Step 4: Bring the linear slide down (to attach specimen)
            moveLinearSlide(-LINEAR_SLIDE_SPEED, 1.5);

            // Step 5: Open the claw to release the specimen
            robot.slideClawServo.setPosition(robot.SLIDE_CLAW_OPEN);
            sleep(500); // Wait for the claw to open

            // Step 6: Drive backwards slightly
            driveForTime(-0.3, 0.0, 0.3);

            // Step 7: Strafe right
            driveForTime(0.0, 1.0, 1.0);

            // Steps 8-10: Perform block movement pattern 3 times
            for (int i = 0; i < 3; i++) {
                // Move forward a little
                driveForTime(0.3, 0.0, 0.3);

                // Move right a little
                driveForTime(0.0, 0.3, 0.3);

                // Move backward a little
                driveForTime(-0.3, 0.0, 0.3);

                // Small pause between iterations
                sleep(200);
            }

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);
        }
    }

    private void driveForTime(double vertical, double horizontal, double time) {
        double turn = 0; // No turning in this autonomous routine
        double frontLeftPower = vertical + horizontal - turn;
        double frontRightPower = vertical - horizontal + turn;
        double backLeftPower = vertical - horizontal - turn;
        double backRightPower = vertical + horizontal + turn;

        // Normalize wheel powers
        double max = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        robot.frontLeftDrive.setPower(frontLeftPower * DRIVE_SPEED);
        robot.frontRightDrive.setPower(frontRightPower * DRIVE_SPEED);
        robot.backLeftDrive.setPower(backLeftPower * DRIVE_SPEED);
        robot.backRightDrive.setPower(backRightPower * DRIVE_SPEED);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Elapsed Time: %2.5f S", runtime.seconds());
            telemetry.update();
        }
        stopRobot();
    }

    private void stopRobot() {
        robot.frontLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.backRightDrive.setPower(0);
    }

    private void moveLinearSlide(double power, double time) {
        robot.linearSlide.setPower(power);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Linear Slide", "Moving: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        robot.linearSlide.setPower(0);
    }

    // Helper method to apply deadzone (from Drive.java)
    private double applyDeadzone(double value) {
        return Math.abs(value) < DEADZONE ? 0 : value;
    }

    // Helper method to apply exponential control (from Drive.java)
    private double applyExponentialControl(double value) {
        return value * Math.abs(value);
    }
}