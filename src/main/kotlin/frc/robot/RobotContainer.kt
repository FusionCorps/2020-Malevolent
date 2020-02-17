/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
class RobotContainer {
    // The robot's subsystems and commands are defined here...
//    private val m_exampleSubsystem: ExampleSubsystem = ExampleSubsystem()

//    val m_autoCommand: ExampleCommand = ExampleCommand(m_exampleSubsystem)
    var m_autoCommandChooser: SendableChooser<Command> = SendableChooser()

    /**
    * The container for the robot.  Contains subsystems, OI devices, and commands.
    */
    init {
        // Configure the button bindings
        configureButtonBindings()
//        m_autoCommandChooser.setDefaultOption("Default Auto", m_autoCommand)
        SmartDashboard.putData("Auto mode", m_autoCommandChooser)
    }

    /**
    * Use this method to define your button->command mappings.  Buttons can be created by
    * instantiating a {@link GenericHID} or one of its subclasses ({@link
    * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling passing it to a
    * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
    */
    fun configureButtonBindings() {
    }

    fun getAutonomousCommand(): Command {
        // Return the selected command
        return m_autoCommandChooser.getSelected()
    }
}
