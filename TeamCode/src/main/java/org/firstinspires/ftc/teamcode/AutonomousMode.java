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

        initializeEncoders();

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            // AUTO ROUTINE GOES HERE
            // ----------------------

            closeSlideClaw();

            moveForward(-23);
            sleep(1000);

            liftLinearSlide(3000);
            moveForward(-5);

            lowerLinearSlide(-1400);
            sleep(500);

            openSlideClaw();
            sleep(250);

            moveForward(12);
            strafeLeft(60);

            moveForward(-24);
            turnLeft(11); // turn 90 degrees

            liftLinearSlide(1500);
            setBucketUp();

            // ----------------------
            // Functions: moveForward(inches), strafeRight(inches), strafeLeft(inches), turnLeft(inches), turnRight(inches),
            // stopRobot(), liftLinearSlide(count), lowerLinearSlide(count), openSlideClaw(), closeSlideClaw(),
            // setBucketUp(), setBucketDown(), setArmUp(), setArmDown(), openClaw(), closeClaw(), setWristUp(), setWristDown()
            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);
        }
    }

    private void initializeEncoders() {
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //  -------------->MOVE FORWARD<----------------

    public void moveForward(double inches) {
        int newFLTarget = robot.frontLeftDrive.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
        int newFRTarget = robot.frontRightDrive.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
        int newBLTarget = robot.backLeftDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
        int newBRTarget = robot.backRightDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);

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
            telemetry.addData("Path", "Moving: %2.1f inches", inches);
            telemetry.update();
        }

        stopRobot();
    }

    //  -------------->STRAFE RIGHT<----------------

    public void strafeRight(double inches) {
        int newFLTarget = robot.frontLeftDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
        int newFRTarget = robot.frontRightDrive.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
        int newBLTarget = robot.backLeftDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
        int newBRTarget = robot.backRightDrive.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);

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
            telemetry.addData("Path", "Strafing Right: %2.1f inches", inches);
            telemetry.update();
        }

        stopRobot();
    }

    //  -------------->STRAFE LEFT<----------------

    public void strafeLeft(double inches) {
        int newFLTarget = robot.frontLeftDrive.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
        int newFRTarget = robot.frontRightDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
        int newBLTarget = robot.backLeftDrive.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
        int newBRTarget = robot.backRightDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);

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
            telemetry.addData("Path", "Strafing Left: %2.1f inches", inches);
            telemetry.update();
        }

        stopRobot();
    }

    //  -------------->TURN LEFT<----------------

    public void turnLeft(double inches) {
        int newFLTarget = robot.frontLeftDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
        int newFRTarget = robot.frontRightDrive.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
        int newBLTarget = robot.backLeftDrive.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
        int newBRTarget = robot.backRightDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);

        robot.frontLeftDrive.setTargetPosition(newFLTarget);
        robot.frontRightDrive.setTargetPosition(newFRTarget);
        robot.backLeftDrive.setTargetPosition(newBLTarget);
        robot.backRightDrive.setTargetPosition(newBRTarget);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeftDrive.setPower(Math.abs(TURN_SPEED));
        robot.frontRightDrive.setPower(Math.abs(TURN_SPEED));
        robot.backLeftDrive.setPower(Math.abs(TURN_SPEED));
        robot.backRightDrive.setPower(Math.abs(TURN_SPEED));

        while (opModeIsActive() &&
                (robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy() &&
                        robot.backLeftDrive.isBusy() && robot.backRightDrive.isBusy())) {
            telemetry.addData("Path", "Turning Left: %2.1f inches", inches);
            telemetry.update();
        }

        stopRobot();
    }

    //  -------------->TURN RIGHT<----------------

    public void turnRight(double inches) {
        int newFLTarget = robot.frontLeftDrive.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);
        int newFRTarget = robot.frontRightDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
        int newBLTarget = robot.backLeftDrive.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
        int newBRTarget = robot.backRightDrive.getCurrentPosition() - (int) (inches * COUNTS_PER_INCH);

        robot.frontLeftDrive.setTargetPosition(newFLTarget);
        robot.frontRightDrive.setTargetPosition(newFRTarget);
        robot.backLeftDrive.setTargetPosition(newBLTarget);
        robot.backRightDrive.setTargetPosition(newBRTarget);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontLeftDrive.setPower(Math.abs(TURN_SPEED));
        robot.frontRightDrive.setPower(Math.abs(TURN_SPEED));
        robot.backLeftDrive.setPower(Math.abs(TURN_SPEED));
        robot.backRightDrive.setPower(Math.abs(TURN_SPEED));

        while (opModeIsActive() &&
                (robot.frontLeftDrive.isBusy() && robot.frontRightDrive.isBusy() &&
                        robot.backLeftDrive.isBusy() && robot.backRightDrive.isBusy())) {
            telemetry.addData("Path", "Turning Right: %2.1f inches", inches);
            telemetry.update();
        }

        stopRobot();
    }

    //  -------------->STOP ROBOT<----------------

    private void stopRobot() {
        robot.frontLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.backRightDrive.setPower(0);
    }

    //  -------------->LIFT LINEAR SLIDE<----------------

    private void liftLinearSlide(int count) {
        int newLSTarget = robot.linearSlide.getCurrentPosition() - (count);
        robot.linearSlide.setTargetPosition(newLSTarget);
        robot.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.linearSlide.setPower(Math.abs(DRIVE_SPEED));

        while (opModeIsActive() && robot.linearSlide.isBusy()) {
            telemetry.addData("Path", "lifting slide");
            telemetry.update();
        }

        robot.linearSlide.setPower(0);
    }

    //  -------------->LOWER LINEAR SLIDE<----------------

    private void lowerLinearSlide(int count) {
        int newLSTarget = robot.linearSlide.getCurrentPosition() + (-count);
        robot.linearSlide.setTargetPosition(newLSTarget);
        robot.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.linearSlide.setPower(Math.abs(DRIVE_SPEED));

        while (opModeIsActive() && robot.linearSlide.isBusy()) {
            telemetry.addData("Path", "lowering slide");
            telemetry.update();
        }

        robot.linearSlide.setPower(0);
    }

    //  -------------->OPEN SLIDE CLAW<----------------

    private void openSlideClaw() {
        robot.slideClawServo.setPosition(robot.SLIDE_CLAW_OPEN);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Opening Slide Claw: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    //  -------------->CLOSE SLIDE CLAW<----------------

    private void closeSlideClaw() {
        robot.slideClawServo.setPosition(robot.SLIDE_CLAW_CLOSED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Closing Slide Claw: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    //  -------------->SET BUCKET UP/DUMP<----------------

    private void setBucketUp() {
        robot.bucketServo.setPosition(robot.BUCKET_UP);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Setting Bucket Up: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    //  -------------->SET BUCKET DOWN<----------------

    private void setBucketDown() {
        robot.bucketServo.setPosition(robot.BUCKET_DOWN);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Setting Bucket Down/Dumping: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    //  -------------->ARM UP<----------------

    private void setArmUp() {
        robot.armServo.setPosition(robot.ARM_UP);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Setting Arm Up: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    //  -------------->ARM DOWN<----------------

    private void setArmDown() {
        robot.armServo.setPosition(robot.ARM_DOWN);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Setting Arm Down: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    //  -------------->OPEN CLAW<----------------

    private void openClaw() {
        robot.clawServo.setPosition(robot.CLAW_CLOSED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Opening Claw: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    //  -------------->CLOSE CLAW<----------------

    private void closeClaw() {
        robot.clawServo.setPosition(robot.CLAW_OPEN);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Closing Claw: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    //  -------------->WRIST UP<----------------

    private void setWristUp() {
        robot.wristServo.setPosition(robot.WRIST_UP);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Setting Wrist Up: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    //  -------------->WRIST DOWN<----------------

    private void setWristDown() {
        robot.wristServo.setPosition(robot.WRIST_DOWN);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Setting Wrist Down: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
}