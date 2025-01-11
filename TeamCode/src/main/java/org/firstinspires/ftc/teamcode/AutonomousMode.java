package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Robot: Auto Drive By Encoder", group="Robot")
public class AutonomousMode extends LinearOpMode {

    RobotHardware robot = new RobotHardware(this);
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 537.7;
    static final double DRIVE_GEAR_REDUCTION = 1.0;
    static final double WHEEL_DIAMETER_INCHES = 4.09;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() {
        robot.init();

        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // Go forward 28 inches
            moveForward(28);

            // Lift linear slide
            liftLinearSlide(5);

            // Go forward 2 inches
            moveForward(2);

            // Lower linear slide
            lowerLinearSlide(5);

            // Open claw
            openClaw();

            // Move backwards 30 inches
            moveForward(-30);

            // Strafe right 50 inches
            strafeLeft(50);

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);
        }
    }

    public void moveForward(double inches) {
        int newFLTarget = robot.frontLeftDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        int newFRTarget = robot.frontRightDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        int newBLTarget = robot.backLeftDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        int newBRTarget = robot.backRightDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);

        robot.frontLeftDrive.setTargetPosition(newFLTarget);
        robot.frontRightDrive.setTargetPosition(newFRTarget);
        robot.backLeftDrive.setTargetPosition(newBLTarget);
        robot.backRightDrive.setTargetPosition(newBRTarget);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeftDrive.setPower(Math.abs(-DRIVE_SPEED));
        robot.frontRightDrive.setPower(Math.abs(-DRIVE_SPEED));
        robot.backLeftDrive.setPower(Math.abs(DRIVE_SPEED));
        robot.backRightDrive.setPower(Math.abs(DRIVE_SPEED));

        while (opModeIsActive() &&
                (robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy() &&
                        robot.backLeftDrive.isBusy() && robot.backRightDrive.isBusy())) {
            telemetry.addData("Path", "Moving: %2.1f inches", inches);
            telemetry.update();
        }

        stopRobot();
    }

    public void strafeLeft(double inches) {
        int newFLTarget = robot.frontLeftDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);
        int newFRTarget = robot.frontRightDrive.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH);
        int newBLTarget = robot.backLeftDrive.getCurrentPosition() - (int)(inches * COUNTS_PER_INCH);
        int newBRTarget = robot.backRightDrive.getCurrentPosition() + (int)(inches * COUNTS_PER_INCH);

        robot.frontLeftDrive.setTargetPosition(newFLTarget);
        robot.frontRightDrive.setTargetPosition(newFRTarget);
        robot.backLeftDrive.setTargetPosition(newBLTarget);
        robot.backRightDrive.setTargetPosition(newBRTarget);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeftDrive.setPower(Math.abs(DRIVE_SPEED));
        robot.frontRightDrive.setPower(Math.abs(DRIVE_SPEED));
        robot.backLeftDrive.setPower(Math.abs(DRIVE_SPEED));
        robot.backRightDrive.setPower(Math.abs(DRIVE_SPEED));

        while (opModeIsActive() &&
                (robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy() &&
                        robot.backLeftDrive.isBusy() && robot.backRightDrive.isBusy())) {
            telemetry.addData("Path", "Strafing right: %2.1f inches", inches);
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

    private void liftLinearSlide(int count) {
        int newLSTarget = robot.linearSlide.getCurrentPosition() + (count);
        robot.linearSlide.setTargetPosition(newLSTarget);
        robot.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.linearSlide.setPower(Math.abs(DRIVE_SPEED));

    }

    private void lowerLinearSlide(int count) {
        int newLSTarget = robot.linearSlide.getCurrentPosition() + (-count);
        robot.linearSlide.setTargetPosition(newLSTarget);
        robot.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.linearSlide.setPower(Math.abs(DRIVE_SPEED));

    }

    private void openClaw() {
        // Implement claw opening logic
        // You may need to adjust this based on your hardware setup
        robot.slideClawServo.setPosition(robot.SLIDE_CLAW_OPEN);
        sleep(500); // Wait for the claw to open
    }
}

// swavam was here
