/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
object Constants {
    object Inputs {
        const val ID_CONTROLLER = 0
    }

    object Chassis {
        const val ID_TALONFX_F_L = 0
        const val ID_TALONFX_B_L = 1
        const val ID_TALONFX_F_R = 2
        const val ID_TALONFX_B_R = 3
    }

    object Hopper {
        const val ID_VICTORSPX = 0
    }

    object Indexer {
        const val ID_TALONFX = 0
    }

    object Intake {
        const val ID_VICTORSPX = 0
    }

    object Lift {
        const val ID_TALONFX_EXTEND = 0
        const val ID_TALONFX_RETRACT = 1
    }

    object Shooter {
        const val ID_TALONFX_LEFT = 0
        const val ID_TALONFX_RIGHT = 1
    }
}
