/* Copyright (c) 2022 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotHardware {

    // Declare OpMode members.
    private LinearOpMode myOpMode = null;   // gain access to methods in the calling OpMode.

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)
    public DcMotor frontLeftDrive   = null;
    public DcMotor frontRightDrive  = null;
    public DcMotor backLeftDrive    = null;
    public DcMotor backRightDrive   = null;
    public DcMotor linearSlide      = null;
    public Servo slideClawServo     = null;
    public Servo bucketServo        = null;
    public Servo armServo           = null;
    public Servo clawServo          = null;
    public Servo wristServo         = null;

    public static final double SLIDE_CLAW_OPEN    = 0;
    public static final double SLIDE_CLAW_CLOSED  = 0.5;
    public static final double BUCKET_UP          = 0.1;
    public static final double BUCKET_DOWN        = 0.9;
    public static final double ARM_UP             = 0.1;
    public static final double ARM_DOWN           = 0.35;
    public static final double CLAW_OPEN          = 0;
    public static final double CLAW_CLOSED        = 0.6;
    public static final double WRIST_UP           = 1.0;
    public static final double WRIST_DOWN         = 0.1;

    // Define a constructor that allows the OpMode to pass a reference to itself.
    public RobotHardware(LinearOpMode opmode) {
        myOpMode = opmode;
    }

    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     * All of the hardware devices are accessed via the hardware map, and initialized.
     */
    public void init()    {
        // Define and Initialize Motors (note: need to use reference to actual OpMode).
        frontLeftDrive  = myOpMode.hardwareMap.get(DcMotor.class, "FL_drive");
        frontRightDrive = myOpMode.hardwareMap.get(DcMotor.class, "FR_drive");
        backLeftDrive   = myOpMode.hardwareMap.get(DcMotor.class, "BL_drive");
        backRightDrive  = myOpMode.hardwareMap.get(DcMotor.class, "BR_drive");
        linearSlide     = myOpMode.hardwareMap.get(DcMotor.class, "linear_slide");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        linearSlide.setDirection(DcMotor.Direction.FORWARD);

        slideClawServo  = myOpMode.hardwareMap.get(Servo.class, "slide_claw");
        bucketServo     = myOpMode.hardwareMap.get(Servo.class, "bucket");
        armServo        = myOpMode.hardwareMap.get(Servo.class, "arm");
        clawServo       = myOpMode.hardwareMap.get(Servo.class, "claw");
        wristServo      = myOpMode.hardwareMap.get(Servo.class, "wrist");

        slideClawServo.setPosition(SLIDE_CLAW_CLOSED);
        bucketServo.setPosition(BUCKET_UP);
        armServo.setPosition(0.5);
        clawServo.setPosition(CLAW_CLOSED);
        wristServo.setPosition(WRIST_DOWN);

        myOpMode.telemetry.addData(">", "Hardware Initialized");
        myOpMode.telemetry.update();
    }
}

// hi swavam was here a second time