/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.AnalogGyro
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.simulation.AnalogGyroSim
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim
import edu.wpi.first.wpilibj.simulation.EncoderSim
import edu.wpi.first.wpilibj.smartdashboard.Field2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.system.plant.DCMotor
import edu.wpi.first.wpilibj.util.Units
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpiutil.math.VecBuilder


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
class Robot : TimedRobot() {
    lateinit var mAutonomousCommand: Command
    lateinit var mRobotContainer: RobotContainer

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */

    private val m_field = Field2d()

    private val m_leftEncoder: Encoder = Encoder(0, 1)
    private val m_rightEncoder: Encoder = Encoder(2, 3)

    private val m_leftEncoderSim = EncoderSim(m_leftEncoder)
    private val m_rightEncoderSim = EncoderSim(m_rightEncoder)

    private val m_gyro = AnalogGyro(1)

    private val m_gyroSim = AnalogGyroSim(m_gyro)

    var m_driveSim = DifferentialDrivetrainSim(
            DCMotor.getNEO(2),  // 2 NEO motors on each side of the drivetrain.
            7.29,  // 7.29:1 gearing reduction.
            7.5,  // MOI of 7.5 kg m^2 (from CAD model).
            60.0,  // The mass of the robot is 60 kg.
            Units.inchesToMeters(3.0),  // The robot uses 3" radius wheels.
            0.7112,  // The track width is 0.7112 meters.
            VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005))

    init {
        SmartDashboard.putData("Field", m_field)
    }



    override fun robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        mRobotContainer = RobotContainer()
        // Automatically grab auto command to ensure m_autonomousCommand is defined before teleopInit is run
        mAutonomousCommand = mRobotContainer.getAutonomousCommand()

        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1)
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1)

        val mField = Field2d()
        SmartDashboard.putData("Field", mField)
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    override fun robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run()
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     */
    override fun disabledInit() {
    }

    override fun disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    override fun autonomousInit() {
        mAutonomousCommand = mRobotContainer.getAutonomousCommand()

        // schedule the autonomous command (example)
        mAutonomousCommand.let { mAutonomousCommand.schedule() }
    }

    /**
     * This function is called periodically during autonomous.
     */
    override fun autonomousPeriodic() {
    }

    override fun teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        mAutonomousCommand.let { mAutonomousCommand.cancel() }
    }

    /**
     * This function is called periodically during operator control.
     */
    override fun teleopPeriodic() {
    }

    override fun testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll()
    }

    /**
     * This function is called periodically during test mode.
     */
    override fun testPeriodic() {
    }

    override fun simulationPeriodic() {

        SmartDashboard.putData("Field", m_field)

        m_driveSim.setInputs(12.0* Controls.controller.getRawAxis(1) - 6.0* Controls.controller.getRawAxis(0),
                12.0* Controls.controller.getRawAxis(1) - 6.0* Controls.controller.getRawAxis(0))

        m_driveSim.update(0.02)

        m_leftEncoderSim.distance = m_driveSim.leftPositionMeters
        m_leftEncoderSim.rate = m_driveSim.leftVelocityMetersPerSecond
        m_rightEncoderSim.distance = m_driveSim.rightPositionMeters
        m_rightEncoderSim.rate = m_driveSim.rightVelocityMetersPerSecond
        m_gyroSim.angle = -m_driveSim.heading.degrees
    }

}
