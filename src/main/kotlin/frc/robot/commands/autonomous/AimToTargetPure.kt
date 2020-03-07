package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis
import kotlin.math.absoluteValue
import mu.KotlinLogging

class AimToTargetPure : CommandBase() {
    private val acceptableError = 1.0

    private val kAim = -0.1
    private val kDistance = -0.1
    private val minAim = 0.05

    private var steeringAdjust = 0.0

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")

    private val tx: Double
        get() {
            return limelightTable.getEntry("tx").getDouble(0.0)
        }
    private val ty: Double
        get() {
            return limelightTable.getEntry("ty").getDouble(0.0)
        }
    private val headingError: Double
        get() {
            return -tx
        }
    private val distanceError: Double
        get() {
            return -ty
        }

    init {
        addRequirements(Chassis)
    }

    override fun execute() {
        if (tx > 1.0) {
            steeringAdjust = kAim * headingError - minAim
        } else if (tx < 1.0) {
            steeringAdjust = kAim * headingError + minAim
        }

        val distanceAdjust = kDistance * distanceError

        Chassis.tankDrive(steeringAdjust + distanceAdjust, -steeringAdjust + distanceAdjust)
    }

    override fun isFinished(): Boolean {
        KotlinLogging.logger("AimToTargetPure").info { "AimToTargetPure ended" }

        return (tx.absoluteValue < acceptableError && ty.absoluteValue < acceptableError)
    }

    override fun end(interrupted: Boolean) {
        Chassis.tankDrive(0.0, 0.0)
    }
}