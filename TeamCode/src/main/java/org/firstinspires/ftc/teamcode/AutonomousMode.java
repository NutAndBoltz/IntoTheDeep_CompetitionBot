package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutonomousMode", group="Robot")
public class AutonomousMode extends LinearOpMode {

    RobotHardware robot = new RobotHardware(this);
    private ElapsedTime runtime = new ElapsedTime();

    static final double FORWARD_SPEED = 0.5;
    static final double TURN_SPEED = 0.5;
    static final double ARM_SPEED = 0.5;

    @Override
    public void runOpMode() {
        robot.init();

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // Step 1: Close Linear Slide Claw
            sleep(3000);
            robot.slideClawServo.setPosition(robot.SLIDE_CLAW_CLOSED);
            sleep(500);

            // Step 2: Go Forward
            driveForward(1.0);

            // Step 3: Lift Arm
            robot.armServo.setPosition(robot.ARM_UP);
            sleep(1000);

            // Step 4: Go Forward just a little bit more
            driveForward(0.3);

            // Step 5: Lower Arm a little
            robot.armServo.setPosition(robot.ARM_UP - 0.1);
            sleep(500);

            // Step 6: Open Linear Slide Claw
            robot.slideClawServo.setPosition(robot.SLIDE_CLAW_OPEN);
            sleep(500);

            // Step 7: Go Backwards just a little bit
            driveBackward(0.3);

            // Step 8: Strafe Right
            strafeRight(1.0);

            // Step 9: Go Forward a little
            driveForward(0.5);

            // Step 10: Strafe Right a little
            strafeRight(0.5);

            // Steps 11-16: Push Blocks Back and Go Forward (3 times)
            for (int i = 0; i < 3; i++) {
                // Push Blocks Back
                driveBackward(1.0);

                // Go Forwards
                driveForward(1.0);

                // Strafe Right a little
                if (i < 2) {
                    strafeRight(0.5);
                }
            }

            // Step 17: Go Backwards
            driveBackward(1.0);

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);
        }
    }

    // Helper methods for movement
    private void driveForward(double time) {
        robot.frontLeftDrive.setPower(FORWARD_SPEED);
        robot.frontRightDrive.setPower(FORWARD_SPEED);
        robot.backLeftDrive.setPower(FORWARD_SPEED);
        robot.backRightDrive.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        stopRobot();
    }

    private void driveBackward(double time) {
        robot.frontLeftDrive.setPower(-FORWARD_SPEED);
        robot.frontRightDrive.setPower(-FORWARD_SPEED);
        robot.backLeftDrive.setPower(-FORWARD_SPEED);
        robot.backRightDrive.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        stopRobot();
    }

    private void strafeRight(double time) {
        robot.frontLeftDrive.setPower(FORWARD_SPEED);
        robot.frontRightDrive.setPower(-FORWARD_SPEED);
        robot.backLeftDrive.setPower(-FORWARD_SPEED);
        robot.backRightDrive.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
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
}