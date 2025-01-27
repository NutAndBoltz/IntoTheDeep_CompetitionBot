package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Telemetry Test", group="Test")
public class TelemetryTest extends LinearOpMode {

    RobotHardware robot = new RobotHardware(this);

    @Override
    public void runOpMode() {
        robot.init();

        // Reset encoders
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motors to run using encoders
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Drive Motors
            telemetry.addData("FL Drive Encoder", robot.frontLeftDrive.getCurrentPosition());
            telemetry.addData("FR Drive Encoder", robot.frontRightDrive.getCurrentPosition());
            telemetry.addData("BL Drive Encoder", robot.backLeftDrive.getCurrentPosition());
            telemetry.addData("BR Drive Encoder", robot.backRightDrive.getCurrentPosition());

            // Linear Slide
            telemetry.addData("Linear Slide Encoder", robot.linearSlide.getCurrentPosition());

            // Servos
            telemetry.addData("Slide Claw Position", String.format("%.2f", robot.slideClawServo.getPosition()));
            telemetry.addData("Bucket Position", String.format("%.2f", robot.bucketServo.getPosition()));
            telemetry.addData("Arm Position", String.format("%.2f", robot.armServo.getPosition()));
            telemetry.addData("Claw Position", String.format("%.2f", robot.clawServo.getPosition()));
            telemetry.addData("Wrist Position", String.format("%.2f", robot.wristServo.getPosition()));

            // Manual controls for testing servo movements
            if (gamepad1.a) robot.slideClawServo.setPosition(robot.slideClawServo.getPosition() + 0.01);
            if (gamepad1.b) robot.slideClawServo.setPosition(robot.slideClawServo.getPosition() - 0.01);
            if (gamepad1.x) robot.bucketServo.setPosition(robot.bucketServo.getPosition() + 0.01);
            if (gamepad1.y) robot.bucketServo.setPosition(robot.bucketServo.getPosition() - 0.01);
            if (gamepad1.dpad_up) robot.armServo.setPosition(robot.armServo.getPosition() + 0.01);
            if (gamepad1.dpad_down) robot.armServo.setPosition(robot.armServo.getPosition() - 0.01);
            if (gamepad1.dpad_left) robot.clawServo.setPosition(robot.clawServo.getPosition() + 0.01);
            if (gamepad1.dpad_right) robot.clawServo.setPosition(robot.clawServo.getPosition() - 0.01);
            if (gamepad1.left_bumper) robot.wristServo.setPosition(robot.wristServo.getPosition() + 0.01);
            if (gamepad1.right_bumper) robot.wristServo.setPosition(robot.wristServo.getPosition() - 0.01);

            telemetry.update();

            // Small delay to avoid flooding the telemetry
            sleep(50);
        }
    }
}
