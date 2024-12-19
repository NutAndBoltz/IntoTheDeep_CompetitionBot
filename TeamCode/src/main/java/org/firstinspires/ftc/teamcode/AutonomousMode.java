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
    static final double LINEAR_SLIDE_SPEED = 0.7;

    @Override
    public void runOpMode() {
        robot.init();

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // Step 1: Drive forward
            driveForward(1.0);

            // Step 2: Bring the linear slide up
            moveLinearSlide(LINEAR_SLIDE_SPEED, 2.0);

            // Step 3: Drive forward a little more
            driveForward(0.3);

            // Step 4: Bring the linear slide down (to attach specimen)
            moveLinearSlide(-LINEAR_SLIDE_SPEED, 1.5);

            // Step 5: Open the claw to release the specimen
            robot.slideClawServo.setPosition(robot.SLIDE_CLAW_OPEN);
            sleep(500); // Wait for the claw to open

            // Step 6: Drive backwards slightly
            driveBackward(0.3);

            // Step 7: Strafe right
            strafeRight(1.0);

            // Steps 8-10: Perform block movement pattern 3 times
            for (int i = 0; i < 3; i++) {
                // Move forward a little
                driveForward(0.3);

                // Move right a little
                strafeRight(0.3);

                // Move backward a little
                driveBackward(0.3);

                // Small pause between iterations
                sleep(200);
            }

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);
        }
    }

    private void driveForward(double time) {
        setDrivePowers(FORWARD_SPEED, FORWARD_SPEED, FORWARD_SPEED, FORWARD_SPEED, time);
    }

    private void driveBackward(double time) {
        setDrivePowers(-FORWARD_SPEED, -FORWARD_SPEED, -FORWARD_SPEED, -FORWARD_SPEED, time);
    }

    private void strafeRight(double time) {
        setDrivePowers(FORWARD_SPEED, -FORWARD_SPEED, -FORWARD_SPEED, FORWARD_SPEED, time);
    }

    private void setDrivePowers(double fl, double fr, double bl, double br, double time) {
        robot.frontLeftDrive.setPower(fl);
        robot.frontRightDrive.setPower(fr);
        robot.backLeftDrive.setPower(bl);
        robot.backRightDrive.setPower(br);
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
}