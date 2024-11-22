package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import org.firstinspires.ftc.teamcode.RobotHardware;

@TeleOp(name="Drive", group="Robot")
public class Drive extends LinearOpMode {

    // Define drive speed
    public static final double DRIVE_SPEED = 0.8;
    public static final double ARM_SPEED = 0.7;

    // Create a RobotHardware object to be used to access robot hardware.
    // Prefix any hardware functions with "robot." to access this class.
    RobotHardware robot       = new RobotHardware(this);

    @Override
    public void runOpMode() {
        double vertical;
        double horizontal;
        double turn;

        // initialize all the hardware, using the hardware class. See how clean and simple this is?
        robot.init();

        // Send telemetry message to signify robot waiting;
        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //Mecanum wheels - calculate power from gamepad, left stick moves robot around field, right stick rotates
            vertical = DRIVE_SPEED * (-gamepad1.left_stick_y); //move forward, backward
            horizontal = DRIVE_SPEED * (gamepad1.left_stick_x); //strafe left, right
            turn = DRIVE_SPEED * (-gamepad1.right_stick_x); //rotate left, right

            //Mecanum wheels - run motors
            robot.frontLeftDrive.setPower(vertical + horizontal - turn);
            robot.frontRightDrive.setPower(vertical - horizontal + turn);
            robot.backLeftDrive.setPower(vertical - horizontal - turn);
            robot.backRightDrive.setPower(vertical + horizontal + turn);

            //Control lift with left stick up and down
            robot.lift.setPower(gamepad2.left_stick_y);

            //Control arm with right stick up and down
            robot.arm.setPower(ARM_SPEED * gamepad2.right_stick_y);

            //Open and close claw with right and left bumpers
            if (gamepad2.right_bumper) {//right bumper closes
                robot.claw.setPosition(robot.CLAW_CLOSED);
            }
            else if (gamepad2.left_bumper) {//left bumper opens
                robot.claw.setPosition(robot.CLAW_OPEN);
            }

            // Send telemetry messages to explain controls and show robot status
            telemetry.addData("Drive & Strafe", "Left Stick");
            telemetry.addData("Rotate", "Right Stick");

            telemetry.addData("Vertical", "%.2f", vertical);
            telemetry.addData("Horizontal",  "%.2f", horizontal);
            telemetry.addData("Turn",  "%.2f", turn);
            telemetry.update();

            // Pace this loop so hands move at a reasonable speed.
            sleep(50);
        }
    }
}