package org.framefx

import tornadofx.Stylesheet
import java.awt.Color

/**
 * The stylesheet class
 */
class FXFrameStyle : Stylesheet() {

    companion object {

        /**
         * These are the colors of the generated graphics for the
         * title bar action buttons.
         *
         * Close, Minimize, Maximize, Restore, etc.
         *
         * If you change the skin theme, you may want to change these values.
         */
        val white = Color(166, 181, 197)
        val whiteDark = Color(107, 133, 158)
    }
}